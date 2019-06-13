package com.antimage.basemodule.utils.android;

import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import timber.log.Timber;

/**
 * Created by xuyuming on 2019/6/12.
 */

public class FileUtils {

    /**
     * 创建文件
     * @param path
     * @return
     */
    public static boolean createFile(String path) {
        File file = new File(path);
        return createFile(file);
    }

    public static boolean createFile(File file) {
        try {
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
        return mkDir(file);
    }

    public static boolean mkDir(File file) {
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
        return deleteFile(file);
    }

    public static boolean deleteFile(File file) {
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
        deleteDirectory(dir);
    }

    public static void deleteDirectory(File dir) {

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
        File file = new File(path);
        deleteFile(file);
        mkDir(file.getParentFile());
        createFile(file);
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
