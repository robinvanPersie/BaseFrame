package com.antimage.baseframe.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.antimage.baseframe.model.DaoMaster;
import com.antimage.baseframe.model.UserDao;

import org.greenrobot.greendao.database.Database;

import timber.log.Timber;

/**
 * Created by xuyuming on 2018/10/25.
 */

public class MOpenHelper extends DaoMaster.OpenHelper {

    public MOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Timber.w("oldVersion: " + oldVersion + " , newVersion: " + newVersion);
        MigrationHelper.migrate(db, new OnUpgradeListener() {
            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db, ifNotExists);
            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db, ifExists);
            }
        }, UserDao.class);
    }
}
