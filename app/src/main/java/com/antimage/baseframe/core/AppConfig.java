package com.antimage.baseframe.core;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Process;
import android.util.ArrayMap;

import com.antimage.baseframe.App;
import com.antimage.baseframe.BuildConfig;
import com.antimage.baseframe.utils.android.SPUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * Created by xuyuming on 2018/11/14.
 * 没有直接在
 */
public class AppConfig {

    private static final String HOST_TEST = "http://test.xxx.com";
    private static final String HOST_RELEASE = "http://www.xxx.com";

    private boolean isRelease;

    @Inject
    public AppConfig() {
        App app = App.getInstance();
        try {
            ApplicationInfo info = app.getPackageManager().getApplicationInfo(app.getPackageName(), PackageManager.GET_META_DATA);
            isRelease = !info.metaData.getBoolean("DEBUG_MODE");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        setApiHost();
    }

    private void setApiHost() {
        int env = SPUtils.getEnvironment();
        if (env == -1) {
            if ("debug".equals(BuildConfig.BUILD_TYPE)) {
                SPUtils.setEnvironment(G.ENVIRONMENT_TEST);
            } else {
                SPUtils.setEnvironment(G.ENVIRONMENT_RELEASE);
            }
        }
    }

    public String getApiHost() {
        int env;
        if (isRelease) {
            env = G.ENVIRONMENT_RELEASE;
        } else {
            env = SPUtils.getEnvironment();
        }
        env = env == -1 ? G.ENVIRONMENT_TEST : env;
        switch (env) {
            case G.ENVIRONMENT_TEST:
                return HOST_TEST;
            case G.ENVIRONMENT_RELEASE:
                return HOST_RELEASE;
        }
        return HOST_TEST;
    }

    public boolean isRelease() {
        return isRelease;
    }

    /**
     * 添加请求头
     * @return
     */
    public Map<String, String> getHeaders() {
        Map<String, String> headers = new ArrayMap<>();
        return headers;
    }

}
