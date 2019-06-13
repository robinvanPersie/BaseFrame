package com.antimage.basemodule.utils.android;

import android.os.Environment;

import com.antimage.basemodule.BaseApp;

import java.io.File;

/**
 * Created by xuyuming on 2018/10/18.
 */

public class StorageUtils {

    /**
     * @return 外部存储是否可用
     */
    public static boolean isSDCardEnable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取app的文件存储路径
     */
    public static String getFileDir() {
        if (!isSDCardEnable()) {
            return BaseApp.getInstance().getFilesDir().getAbsolutePath();
        }
        return getExternalFileDir();
    }

    /**
     * 外部存储可用，返回 Android/data/package name/files.
     */
    private static String getExternalFileDir() {
        return BaseApp.getInstance().getExternalFilesDir(null).getAbsolutePath();
    }

    /**
     * 外部存储可用，返回 Android/data/package name/caches
     */
    public static String getCacheDir() {
        if (!isSDCardEnable()) {
            return BaseApp.getInstance().getCacheDir().getAbsolutePath();
        }
        return getExternalCacheDir();
    }

    /**
     * 在外部存储的缓存根目录里 创建一个下一级目录进行缓存
     * @param folder
     * @return
     */
    public static String getCacheDir(String folder) {
        String path = getCacheDir() + File.separator + folder;
        if (FileUtils.mkDir(path)) {
            return path;
        }
        return "";
    }

    private static String getExternalCacheDir() {
        return BaseApp.getInstance().getExternalCacheDir().getAbsolutePath();
    }

    /**
     * @return 返回相册文件夹
     */
    public static File getDCIMDirectory() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    }

    /**
     * @return 返回下载文件夹
     */
    public static File getDownloadDirectory() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    }


}
