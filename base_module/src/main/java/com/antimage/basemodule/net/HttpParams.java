package com.antimage.basemodule.net;

/**
 * Created by xuyuming on 2019/6/12.
 */

public interface HttpParams {

    String HTTP_CACHE_FOLDER = "http";
    long HTTP_CACHE_MAX_SIZE = 1024 * 1024 * 20; // 20MB

    String HOST_TEST = "https://www.sojson.com/"; // 一个http code = 200，但是业务不通的api
    String HOST_RELEASE = "https://www.baidu.com";

}
