package com.antimage.baseframe.net.download;

import io.reactivex.disposables.Disposable;

/**
 * Created by xuyuming on 2018/10/24.
 */

public interface IDownloadCallback {

    String getUrl(String resId);

    boolean isPause(String resId);

    void onStart(String resId, long contentLength, String eTag);

    void onProgress(String resId, long progress, long contentLength);

    void onComplete(String resId, String targetPath);

    void onError(String resId, String path);

    int getStartOffset();

    int getLength();

    void onSubscribe(String resId, Disposable d);

    class IDefaultDownloadCallback implements IDownloadCallback {

        @Override
        public String getUrl(String resId) {
            return resId;
        }

        @Override
        public boolean isPause(String resId) {
            return false;
        }

        @Override
        public void onStart(String resId, long contentLength, String eTag) {

        }

        @Override
        public void onProgress(String resId, long progress, long contentLength) {
        }

        @Override
        public void onComplete(String resId, String targetPath) {

        }

        @Override
        public void onError(String resId, String path) {

        }

        @Override
        public int getStartOffset() {
            return 0;
        }

        @Override
        public int getLength() {
            return 0;
        }

        @Override
        public void onSubscribe(String resId, Disposable d) {

        }
    }
}
