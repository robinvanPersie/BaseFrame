package com.antimage.baseframe.utils.android;

import android.os.Environment;
import android.text.TextUtils;

import com.antimage.baseframe.App;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import timber.log.Timber;

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
            return App.getInstance().getFilesDir().getAbsolutePath();
        }
        return getExternalFileDir();
    }

    /**
     * 外部存储可用，返回 Android/data/package name/files.
     */
    private static String getExternalFileDir() {
        return App.getInstance().getExternalFilesDir(null).getAbsolutePath();
    }

    /**
     * 外部存储可用，返回 Android/data/package name/caches
     */
    public static String getCacheDir() {
        if (!isSDCardEnable()) {
            return App.getInstance().getCacheDir().getAbsolutePath();
        }
        return getExternalCacheDir();
    }

    private static String getExternalCacheDir() {
        return App.getInstance().getExternalCacheDir().getAbsolutePath();
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

    /**
     * 创建文件
     * @param path
     * @return
     */
    public static boolean createFile(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 创建文件夹
     * @param path
     * @return
     */
    public static boolean mkDir(String path) {
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            return true;
        }
        return file.mkdir();
    }

    /**
     * 删除文件
     * @param path
     * @return
     */
    public static boolean deleteFile(String path) {
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 删除文件夹
     * @param path
     */
    public static void deleteDirectory(String path) {
        File dir = new File(path);
        if (!dir.exists() || !dir.isDirectory()) return;
        File[] files = dir.listFiles();
        if (files.length == 0) {
            dir.delete();
            return;
        }

        Stack<File> stack = new Stack<>();
        List<File> dirList = new LinkedList<>();

        stack.push(dir);
        // stack里都是非空dir
        while (stack.size() > 0) {
            File d = stack.pop();
            File[] fs = d.listFiles();
            for (File f : fs) {
                if (f.isDirectory() && f.list().length > 0) {
                    stack.push(f);
                } else {
                    f.delete();
                }
            }
            if (d.list().length == 0) {
                d.delete();
            } else {
                dirList.add(d);
            }
        }
        if (dirList.isEmpty()) return;
        for (File d : dirList) {
            d.delete();
        }
    }

    /**
     * 下载前检测path
     */
    public static void checkFile(String path) {
        deleteFile(path);
    }

    public static boolean renameTo(String src, String target) {
        if (TextUtils.isEmpty(src) || TextUtils.isEmpty(target)) {
            Timber.e("src or target is null");
            return false;
        }
        File srcFile = new File(src);
        return srcFile.renameTo(new File(target));
    }
}
