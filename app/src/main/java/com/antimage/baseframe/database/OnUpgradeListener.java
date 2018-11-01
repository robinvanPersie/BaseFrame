package com.antimage.baseframe.database;

import org.greenrobot.greendao.database.Database;

/**
 * Created by xuyuming on 2018/10/25.
 */

public interface OnUpgradeListener {

    void onCreateAllTables(Database db, boolean ifNotExists);

    void onDropAllTables(Database db, boolean ifExists);
}
