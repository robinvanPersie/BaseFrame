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
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
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
    private List<DownloadModel> downloadQueue;

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
        downloadQueue = Collections.synchronizedList(new LinkedList<>());
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
        downloadQueue.add(model);
    }

    /**
     * @param model 下载model
     */
    public void downloadApk(DownloadModel model) {
        this.downloadApk(model.getUrl(), model.getTarget(), model.getDownloadProgress());
    }

    /**
     *
     * @param url        下载地址
     * @param target     目标地址
     * @param callback   下载监听
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

    /**
     *
     * @param url       下载地址
     * @param target    目标地址
     * @param callback  下载监听
     */
    public void downloadFile(String url, String target, IDownloadCallback callback) {
        this.downloadFile(url, target, 0, 0, callback);
    }

    public void downloadFile(String url, String target, int start, int len, IDownloadCallback callback) {
        if (TextUtils.isEmpty(url)) {
            Timber.e("download url is null");
            return;
        }
        if (TextUtils.isEmpty(target)) {
            Timber.e("download target is null");
        }
        StorageUtils.checkFile(target);
        if (callback == null) {
            progressQueue.offer(new IDownloadCallback.IDefaultDownloadCallback());
        } else {
            progressQueue.offer(callback);
        }
        String end = len <= 0 ? "" : String.valueOf(start + len);
        String range = "bytes=" + start + "-" + end;
        downloadService.downloadFile(url, range)
                .observeOn(Schedulers.computation())
                .subscribe(responseBody -> {
                    InputStream ins = null;
                    RandomAccessFile out = null;
                    File targetFile = new File(target);
                    StringBuilder tempSb = new StringBuilder(targetFile.getParent()).append(File.separator).append(System.currentTimeMillis());
                    try {
                        ins = responseBody.byteStream();
                        out = new RandomAccessFile(tempSb.toString(), "rw");
                        out.seek(start);
                        byte[] buffer = new byte[1024 * 2];
                        int length;
                        while ((length = ins.read(buffer)) != -1) {
                            out.write(buffer, 0, length);
                        }
                        StorageUtils.renameTo(tempSb.toString(), target);
                        if (callback != null) {
                            callback.onComplete(target);
                        }
                    } catch (IOException e) {
                        onError(e);
                    } finally {
                        try {
                            if (ins != null) {
                                ins.close();
                            }
                            if (out != null) {
                                out.close();
                            }
                        } catch (IOException e) {
                            Timber.e("download close stream error");
                        }
                    }
                }, Timber::e);
    }
}
