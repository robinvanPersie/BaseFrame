package com.antimage.baseframe.net.download;

import io.reactivex.disposables.Disposable;

/**
 * Created by xuyuming on 2018/10/24.
 */

public class DownloadModel {

    private String url;      // 下载地址
    private String target;   // 目标地址
    private IDownloadCallback callback;  //下载监听
    private Disposable disposable;

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

    public Disposable getDisposable() {
        return disposable;
    }

    public void setDisposable(Disposable disposable) {
        this.disposable = disposable;
    }
}
