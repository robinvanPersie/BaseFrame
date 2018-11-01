package com.antimage.baseframe.net.download;

/**
 * Created by xuyuming on 2018/10/24.
 */

public class DownloadModel {

    private String url;      // 下载地址
    private String target;   // 目标地址
    private IDownloadCallback callback;  //下载监听

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public IDownloadCallback getDownloadProgress() {
        return callback;
    }

    public void setDownloadProgress(IDownloadCallback callback) {
        this.callback = callback;
    }
}
