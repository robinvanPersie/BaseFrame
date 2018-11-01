package com.antimage.baseframe.net.download;

/**
 * Created by xuyuming on 2018/10/24.
 */

public interface IDownloadCallback {

    String getUrl(String url);

    void onPause();

    void onStart();

    int onProgress(long progress, long contentLength);

    void onComplete(String target);

    void onFailure();

    public static class IDefaultDownloadCallback implements IDownloadCallback
    {
        @Override
        public String getUrl(String url) {
            return null;
        }

        @Override
        public void onPause() {

        }

        @Override
        public void onStart() {

        }

        @Override
        public int onProgress(long progress, long contentLength) {
            return 0;
        }

        @Override
        public void onComplete(String target) {

        }

        @Override
        public void onFailure() {

        }
    }
}
