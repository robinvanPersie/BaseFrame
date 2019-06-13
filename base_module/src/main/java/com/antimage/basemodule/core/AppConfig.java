package com.antimage.basemodule.core;

import android.util.SparseArray;

import java.util.Map;



/**
 * Created by xuyuming on 2018/11/14.
 */
public interface AppConfig {

    /**
     * 设置环境
     */
    void setApiHost();

    String getApiHost();

    boolean isRelease();

    /**
     * 添加请求头
     * @return
     */
    Map<String, String> getHeaders();

    /**
     * http缓存目录
     * @return
     */
    String getHttpCacheDir();

    /**
     * http缓存最大值
     * @return
     */
    long getHttpCacheMaxLength();

}
