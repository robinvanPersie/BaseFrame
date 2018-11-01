package com.antimage.baseframe.database;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.internal.DaoConfig;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by xuyuming on 2018/10/25.
 * 注释掉的来源：https://stackoverflow.com/questions/13373170/greendao-schema-update-and-data-migration/30334668#30334668
 * 使用TableInfo的来源：https://github.com/yuweiguocn/GreenDaoUpgradeHelper/blob/master/README_CH.md
 */

public class MigrationHelper {

    private static final String SQLITE_MASTER = "sqlite_master";
    private static final String SQLITE_TEMP_MASTER = "sqlite_temp_master";

    private static WeakReference<OnUpgradeListener> weakListener;

    public static void migrate(SQLiteDatabase sqLiteDatabase, @NonNull OnUpgradeListener listener, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        weakListener = new WeakReference<>(listener);
        StandardDatabase db = new StandardDatabase(sqLiteDatabase);
//        generateTableIfNotExists(db, daoClasses);
        generateTempTable(db, daoClasses);
//        dropAllTables(db, true, daoClasses);
//        createAllTables(db, false, daoClasses);
        OnUpgradeListener onUpgradeListener = weakListener.get();
        if (onUpgradeListener != null) {
            onUpgradeListener.onDropAllTables(db, true);
            onUpgradeListener.onCreateAllTables(db, false);
        }
        restoreData(db, daoClasses);
    }

//    /**
//     * 生成表
//     */
//    private static void generateTableIfNotExists(StandardDatabase db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
//        reflectMethod(db, "createTable", true, daoClasses);
//        createAllTables(db, true, daoClasses);
//    }

    /**
     * 复制原有表到temp表
     */
    private static void generateTempTable(StandardDatabase db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        for (Class c : daoClasses) {
            DaoConfig daoConfig = new DaoConfig(db, c);
            String tableName = daoConfig.tablename;
            if (!isTableExists(db, false, tableName)) {
                continue;
            }
            try {
                String tempTableName = daoConfig.tablename.concat("_TEMP");
                StringBuilder dropTableSb = new StringBuilder();
                dropTableSb.append("DROP TABLE IF EXISTS ").append(tempTableName).append(";");
                db.execSQL(dropTableSb.toString());

                StringBuilder insertTableSb = new StringBuilder();
                insertTableSb.append("CREATE TEMPORARY TABLE ").append(tempTableName);
                insertTableSb.append(" AS SELECT * FROM ").append(tableName).append(";");
                db.execSQL(insertTableSb.toString());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean isTableExists(Database db, boolean isTemp, String tableName) {
        if (db == null || TextUtils.isEmpty(tableName)) {
            return false;
        }
        String dbName = isTemp ? SQLITE_TEMP_MASTER : SQLITE_MASTER;
        String sql = "SELECT COUNT(*) FROM " + dbName + " WHERE type = ? AND name = ?";
        Cursor cursor = null;
        int count = 0;
        try {
            cursor = db.rawQuery(sql, new String[]{"table", tableName});
            if (cursor == null || !cursor.moveToFirst()) {
                return false;
            }
            count = cursor.getInt(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return count > 0;
    }

    /**
     * 没有 dropTable 和 createTable 这两个方法，反射不到。
     */
//    private static void dropAllTables(StandardDatabase db, boolean ifExist, @NonNull Class<? extends AbstractDao<?, ?>>... daoClasses) {
//        reflectMethod(db, "dropTable", ifExist, daoClasses);
//    }

//    private static void createAllTables(StandardDatabase db, boolean ifExist, @NonNull Class<? extends AbstractDao<?, ?>>... daoClasses) {
//        reflectMethod(db, "createTable", ifExist, daoClasses);
//    }

    /**
     * 反射method
     */
//    private static void reflectMethod(StandardDatabase db, String methodName, boolean exist, @NonNull Class<? extends AbstractDao<?, ?>>... daoClasses) {
//        if (daoClasses.length < 1) {
//            Timber.e("reflectMethod() no daoClasses");
//            return;
//        }
//        try {
//            for (Class cls : daoClasses) {
//                Method method = cls.getDeclaredMethod(methodName, Database.class, boolean.class);
//                method.invoke(null, db, exist);
//            }
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 将temp表的数据复制到升级后到数据库，并删除temp表
     */
    private static void restoreData(StandardDatabase db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        if (daoClasses.length < 1) {
            Timber.e("restoreData() no daoClasses");
            return;
        }
        for (Class c : daoClasses) {
            DaoConfig daoConfig = new DaoConfig(db, c);
            String tableName = daoConfig.tablename;
            String tempTableName = daoConfig.tablename.concat("_TEMP");
            if (!isTableExists(db, true, tempTableName)) {
                continue;
            }

            try {
                //get all columns from tempTable, take careful to use the columns list
                List<TableInfo> newTableInfos = TableInfo.getTableInfo(db, tableName);
                List<TableInfo> tempTableInfos = TableInfo.getTableInfo(db, tempTableName);
                List<String> tempColumns = new ArrayList<>(newTableInfos.size());
                List<String> newColumns = new ArrayList<>(newTableInfos.size());

                // new 和 temp都有的列
                for (TableInfo tableInfo : tempTableInfos) {
                    if (newTableInfos.contains(tableInfo)) {
                        String column = '`' + tableInfo.name + '`';
                        newColumns.add(column);
                        tempColumns.add(column);
                    }
                }
                // NOT NULL columns list
                for (TableInfo tableInfo : newTableInfos) {
                    // 新增的 非空列
                    if (tableInfo.notnull && !tempTableInfos.contains(tableInfo)) {
                        String column = '`' + tableInfo.name + '`';
                        newColumns.add(column);

                        String value;
                        if (tableInfo.dfltValue != null) {
                            value = "'" + tableInfo.dfltValue + "' AS ";
                        } else {
                            value = "'' AS ";
                        }
                        tempColumns.add(value + column);
                    }
                }

                if (newColumns.size() != 0) {
                    StringBuilder insertTableStringBuilder = new StringBuilder();
                    insertTableStringBuilder.append("REPLACE INTO ").append(tableName).append(" (");
                    insertTableStringBuilder.append(TextUtils.join(",", newColumns));
                    insertTableStringBuilder.append(") SELECT ");
                    insertTableStringBuilder.append(TextUtils.join(",", tempColumns));
                    insertTableStringBuilder.append(" FROM ").append(tempTableName).append(";");
                    db.execSQL(insertTableStringBuilder.toString());
                }
                StringBuilder dropTableStringBuilder = new StringBuilder();
                dropTableStringBuilder.append("DROP TABLE ").append(tempTableName);
                db.execSQL(dropTableStringBuilder.toString());


//                List<String> columns = getColumns(db, tempTableName); // 找出temp表的所有列
//                List<String> properties = new ArrayList<>(columns.size());
//                for (Property property : daoConfig.properties) { // 实体类的所有字段，大于等于列
//                    String columnName = property.columnName;
//                    if (columns.contains(columnName)) {
//                        properties.add(columnName);
//                    }
//                }
//                //将temp的数据复制回原表
//                if (properties.size() > 0) {
//                    String columnSQL = TextUtils.join(",", properties); // 将字段拼接成 id,name,age的形式
//
//                    StringBuilder sb = new StringBuilder();
//                    sb.append("INSERT INTO ").append(tableName).append(" (");
//                    sb.append(columnSQL);
//                    sb.append(") SELECT ");
//                    sb.append(columnSQL);
//                    sb.append(" FROM ").append(tempTableName).append(";");
//                    db.execSQL(sb.toString());
//                }
//                //删除temp表
//                String dropTempTableSQL = String.format("DROP TABLE %s", tempTableName);
//                db.execSQL(dropTempTableSQL);
            } catch (SQLException e) {
                e.printStackTrace();
                Timber.e("Failed to restore data from temp table: " + tempTableName);
            }
        }
    }

    /**
     * 找出temp表的所有列
     */
//    private static List<String> getColumns(StandardDatabase db, String tableName) {
//        List<String> columns = null;
//        Cursor cursor = null;
//        cursor = db.rawQuery("SELECT * FROM " + tableName + " limit 0", null);
//        try {
//            if (cursor != null && cursor.getColumnCount() > 0) {
//                columns = Arrays.asList(cursor.getColumnNames());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//            if (columns == null) {
//                columns = Collections.emptyList();
//            }
//        }
//        return columns;
//    }
}
