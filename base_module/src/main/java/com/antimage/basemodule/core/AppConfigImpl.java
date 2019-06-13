package com.antimage.basemodule.core;


import com.antimage.basemodule.BuildConfig;
import com.antimage.basemodule.net.HttpParams;
import com.antimage.basemodule.utils.android.SPUtils;
import com.antimage.basemodule.utils.android.StorageUtils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;


/**
 * Created by xuyuming on 2019/6/11.
 */

public class AppConfigImpl implements AppConfig {

    private boolean isRelease;

    @Inject
    public AppConfigImpl() {
        isRelease = !BuildConfig.DEBUG;
        setApiHost();
    }

    @Override
    public void setApiHost() {
        int env = SPUtils.getEnvironment();
        if (env == -1) {
            if ("debug".equals(BuildConfig.BUILD_TYPE)) {
                SPUtils.setEnvironment(G.ENVIRONMENT_TEST);
            } else {
                SPUtils.setEnvironment(G.ENVIRONMENT_RELEASE);
            }
        }
    }

    @Override
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
                return HttpParams.HOST_TEST;
            case G.ENVIRONMENT_RELEASE:
                return HttpParams.HOST_RELEASE;
        }
        return HttpParams.HOST_RELEASE;
    }

    @Override
    public boolean isRelease() {
        return isRelease;
    }

    /**
     * 添加请求头
     * @return
     */
    @Override
    public Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        return headers;
    }

    @Override
    public String getHttpCacheDir() {
        return StorageUtils.getCacheDir(HttpParams.HTTP_CACHE_FOLDER);
    }

    @Override
    public long getHttpCacheMaxLength() {
        return HttpParams.HTTP_CACHE_MAX_SIZE;
    }
}
