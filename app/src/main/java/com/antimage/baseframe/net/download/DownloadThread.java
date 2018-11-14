package com.antimage.baseframe.net.download;

import com.antimage.baseframe.utils.android.StorageUtils;
import com.antimage.baseframe.utils.java.EncryptUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;
import timber.log.Timber;

/**
 * Created by xuyuming on 2018/11/5.
 */

public class DownloadThread implements Consumer<ResponseBody> {

    private boolean isRunning = true;

    private String tempFilePath;
    private String destFilePath;
    private String resId;
    private boolean resumeDownload = true;

    private String md5;
    private long fileLength;
    private IDownloadCallback callback;

    public DownloadThread(IDownloadCallback callback, String resId, String tempFile, String destFile) {
        this(callback, resId, tempFile, destFile, null, false);
    }

    public DownloadThread(IDownloadCallback callback, String resId, String tempFile, String destFile, String md5, boolean resume) {
        this.callback = callback;
        this.resId = resId;
        this.tempFilePath = tempFile;
        this.destFilePath = destFile;
        this.md5 = md5;
        this.resumeDownload = resume;
    }

    @Override
    public void accept(ResponseBody responseBody) {
        String url = callback.getUrl(resId);
//        int start = callback.getStartOffset();
//        int len = callback.getLength();
//        String end = len <= 0 ? "" : String.valueOf(start + len);
//        String range = "bytes=" + start + "-" + end;
        if (callback.isPause(resId)) {
            return;
        }
        InputStream ins = null;
        RandomAccessFile out = null;
        File tempFile = new File(tempFilePath);
        try {
            ins = responseBody.byteStream();
            out = new RandomAccessFile(tempFile, "rw");
//            out.seek(start);

            fileLength = responseBody.contentLength();

            byte[] buffer = new byte[1024 * 2];
            int length;
            while (isRunning && (length = ins.read(buffer)) != -1) {
                out.write(buffer, 0, length);
                isRunning = !callback.isPause(resId);
            }
            if (isSuccess(md5, fileLength, tempFile)) {
                if (StorageUtils.renameTo(tempFile.getAbsolutePath(), destFilePath)) {
                    callback.onComplete(resId, destFilePath);
                } else {
                    Timber.e("tempFile can not rename to destFile");
                    callback.onError(resId, tempFilePath);
                }
            } else {
                Timber.e("not success");
                callback.onError(resId, tempFilePath);
            }
        } catch (FileNotFoundException e) {
            Timber.e("tempFile does not exist");
            callback.onError(resId, tempFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            callback.onError(resId, tempFile.getAbsolutePath());
        } finally {
            try {
                if (ins != null) {
                    ins.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isSuccess(String md5, long contentLength, File localFile) {
        if (localFile == null) return false;
        if (md5 != null) {
            String fileMD5 = EncryptUtils.getFileMD5(localFile);
            return md5.equals(fileMD5);
        } else {
            return contentLength == localFile.length();
        }
    }
}
