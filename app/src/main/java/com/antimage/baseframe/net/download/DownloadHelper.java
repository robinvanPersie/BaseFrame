package com.antimage.baseframe.net.download;

import android.text.TextUtils;

import com.antimage.baseframe.net.api.DownloadService;
import com.antimage.baseframe.utils.android.StorageUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import timber.log.Timber;

import static io.reactivex.plugins.RxJavaPlugins.onError;

/**
 * Created by xuyuming on 2018/10/23.
 */

public class DownloadHelper {

    private static final String APK_NAME = "xxx.apk";

    private static volatile DownloadHelper helper;
    private DownloadService downloadService;
    private Queue<IDownloadCallback> progressQueue = new LinkedList<>();
    private Map<String, DownloadModel> downloadMap;

    private String apkName = APK_NAME;

    public static DownloadHelper getInstance() {
        DownloadHelper downloadHelper = helper;
        if (downloadHelper == null) {
            synchronized (DownloadHelper.class) {
                if (downloadHelper == null) {
                    downloadHelper = new DownloadHelper();
                    helper = downloadHelper;
                }
            }
        }
        return downloadHelper;
    }

    private DownloadHelper() {
        downloadMap = Collections.synchronizedMap(new LinkedHashMap<>());
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
                .addNetworkInterceptor(chain -> {
                    Response response = chain.proceed(chain.request());
                    IDownloadCallback callback = progressQueue.poll();
                    if (callback == null || callback instanceof IDownloadCallback.IDefaultDownloadCallback) {
                        return response;
                    }
                    return response.newBuilder().body(new ResponseBodyWrapper(response.body(), callback)).build();
                })
                .build();
        downloadService = new Retrofit.Builder()
                .baseUrl(" ")
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
                .create(DownloadService.class);

    }

    /**
     * 下次启动下载前reset
     */
    public void reset() {
        apkName = APK_NAME;
    }

    public void push(DownloadModel model) {
        if (model.getDisposable() != null && !model.getDisposable().isDisposed()) {
            Timber.e("task do not finish!");
            return;
        }
        if (downloadMap.containsKey(model.getUrl())) {
            downloadMap.remove(model.getUrl());
        }
        downloadMap.put(model.getUrl(), model);
        downloadFile(model);
    }

    /**
     * @param model 下载model
     */
    public void downloadApk(DownloadModel model) {
        this.downloadApk(model.getUrl(), model.getTarget(), model.getDownloadProgress());
    }

    /**
     * @param url      下载地址
     * @param target   目标地址
     * @param callback 下载监听
     */
    public void downloadApk(String url, String target, IDownloadCallback callback) {
        if (TextUtils.isEmpty(target)) {
            target = new StringBuilder(StorageUtils.getDownloadDirectory().toString()).append(File.separator).append(apkName).toString();
        }
        downloadFile(url, target, callback);
    }

    /**
     * @param model 下载model
     */
    public void downloadFile(DownloadModel model) {
        this.downloadFile(model.getUrl(), model.getTarget(), 0, 0, model.getDownloadProgress());
    }

    public void downloadFile(String url, String target, IDownloadCallback callback) {
        this.downloadFile(url, target, 0, 0, callback);
    }

    public void downloadFile(String url, String target, int start, int len, IDownloadCallback callback) {
        this.downloadFile(null, url, target, start, len, callback);
    }

    /**
     * @param resId    获取url的id，通过callback的getUrl获取，如果不需要通过这个id获取url，可以直接将url赋值给它
     * @param url      下载地址
     * @param target   目标地址
     * @param callback 下载监听
     */
    public void downloadFile(String resId, String url, String target, int start, int len, IDownloadCallback callback) {
        if (TextUtils.isEmpty(url)) {
            Timber.e("download url is null");
            return;
        }
        if (TextUtils.isEmpty(target)) {
            Timber.e("download target is null");
        }
        if (callback == null) {
            callback = new FCallback();
            downloadMap.get(resId).setDownloadProgress(callback);
//            progressQueue.offer(new IDownloadCallback.IDefaultDownloadCallback());
        } else {
//            progressQueue.offer(callback);
        }
        String end = len <= 0 ? "" : String.valueOf(start + len);
        String range = "bytes=" + start + "-" + end;
        downloadService.downloadFile(url, range)
                .doOnSubscribe(disposable -> StorageUtils.checkFile(target))
                .observeOn(Schedulers.computation())
                .subscribe(new DownloadThread(callback, resId, target + ".tmp", target));
    }

    private class FCallback implements IDownloadCallback {
        @Override
        public String getUrl(String resId) {
            return downloadMap.get(resId).getUrl();
        }

        @Override
        public boolean isPause(String resId) {
            DownloadModel model = downloadMap.get(resId);
            if (model == null) return true;
            if (model.getDisposable() == null || model.getDisposable().isDisposed()) return true;
            return false;
        }

        @Override
        public void onStart(String resId, long contentLength, String eTag) {
        }

        @Override
        public void onProgress(String resId, long progress, long contentLength) {
//todo
        }

        @Override
        public void onComplete(String resId, String targetPath) {
            DownloadModel model = downloadMap.remove(resId);
            if (model == null) return;
            Disposable disposable = model.getDisposable();
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
            model.setDisposable(null);
        }

        @Override
        public void onError(String resId, String path) {
            DownloadModel model = downloadMap.remove(resId);
            if (model == null) return;
            Disposable disposable = model.getDisposable();
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
            model.setDisposable(null);
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
            downloadMap.get(resId).setDisposable(d);
        }
    }
}
