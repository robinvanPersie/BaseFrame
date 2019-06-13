package com.antimage.basemodule.core;

import javax.inject.Inject;

/**
 * Created by xuyuming on 2019/3/22.
 */

public class InjectManager {

    private UserManager userManager;
    private AppConfig appConfig;
    private AppManager appManager;

    @Inject
    public InjectManager(AppConfig baseAppConfig) {
        appConfig = baseAppConfig;
    }

    public UserManager userManager() {
        return userManager;
    }

    public AppConfig appConfig() {
        return appConfig;
    }

    public AppManager appManager() {
        return appManager;
    }


    public InjectManager setUserManage(UserManager userManage) {
        this.userManager = userManage;
        return this;
    }

    public InjectManager setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
        return this;
    }

    public InjectManager setAppManager(AppManager appManager) {
        this.appManager = appManager;
        return this;
    }
}
