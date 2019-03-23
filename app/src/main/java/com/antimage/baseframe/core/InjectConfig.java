package com.antimage.baseframe.core;

import com.antimage.baseframe.net.api.ApiService;

/**
 * Created by xuyuming on 2019/3/22.
 */

public class InjectConfig {

    private static InjectConfig instance = new InjectConfig();

    private UserManager userManager;
    private AppConfig appConfig;
    private AppManager appManager;
    private ApiService apiService;

    private InjectConfig() {
    }

    public static InjectConfig get() {
        return instance;
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

    public ApiService apiService() {
        return apiService;
    }


    public InjectConfig setUserManage(UserManager userManage) {
        this.userManager = userManage;
        return this;
    }

    public InjectConfig setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
        return this;
    }

    public InjectConfig setAppManager(AppManager appManager) {
        this.appManager = appManager;
        return this;
    }

    public InjectConfig setApiService(ApiService apiService) {
        this.apiService = apiService;
        return this;
    }

}
