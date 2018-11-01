package com.antimage.baseframe.database;

import android.database.Cursor;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by xuyuming on 2018/10/25.
 */
class TableInfo {

    int cid;
    String name;
    String type;
    boolean notnull;
    String dfltValue;
    boolean pk;

    @Override
    public boolean equals(Object o) {
        return this == o
                || o != null
                && getClass() == o.getClass()
                && name.equals(((TableInfo) o).name);
    }

    @Override
    public String toString() {
        return "TableInfo{" +
                "cid=" + cid +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", notnull=" + notnull +
                ", dfltValue='" + dfltValue + '\'' +
                ", pk=" + pk +
                '}';
    }

    public static List<TableInfo> getTableInfo(Database db, String tableName) {
        String sql = "PRAGMA table_info(" + tableName + ")";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor == null)
            return Collections.emptyList();

        TableInfo tableInfo;
        List<TableInfo> tableInfos = new ArrayList<>();
        while (cursor.moveToNext()) {
            tableInfo = new TableInfo();
            tableInfo.cid = cursor.getInt(0);
            tableInfo.name = cursor.getString(1);
            tableInfo.type = cursor.getString(2);
            tableInfo.notnull = cursor.getInt(3) == 1;
            tableInfo.dfltValue = cursor.getString(4);
            tableInfo.pk = cursor.getInt(5) == 1;
            tableInfos.add(tableInfo);
        }
        cursor.close();
        return tableInfos;
    }
}
