package com.antimage.basemodule.utils.java;

import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import timber.log.Timber;

/**
 * Created by xuyuming on 2018/11/1.
 */

public class EncryptUtils {

    /**
     * 获取字符串md5
     * @param data
     * @return
     */
    public static String getMD5(String data) {
        if (TextUtils.isEmpty(data)) return data;
        byte[] md5Bytes = encryptByMessageDigest(data.getBytes(), "MD5");
        return bytes2HexString(md5Bytes);
    }

    /**
     * 获取文件md5
     * @param path 绝对路径
     * @return
     */
    public static String getFileMD5(String path) {
        if (TextUtils.isEmpty(path)) {
            Timber.e("path null or length zero");
            return null;
        }
        return getFileMD5(new File(path));
    }

    public static String getFileMD5(File file) {
        if (file == null || !file.exists()) return null;
        MessageDigest digest = null;
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            digest = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            byte[] buffer = new byte[8 * 1024];
            int len;
            while ((len = bis.read(buffer)) > 0) {
                digest.update(buffer, 0, len);
            }
            return bytes2HexString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * byteArr转hexString
     * bytes2HexString(new byte[] { 0, (byte) 0xa8 }) returns 00A8
     * @param bytes 字节数组
     * @return 16进制大写字符串
     */
    private static String bytes2HexString(byte[] bytes) {
        if (bytes == null) return null;
        StringBuilder sb = new StringBuilder();
        String hex;
        for (int i = 0; i < bytes.length; i++) {
            hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    private static byte[] encryptByMessageDigest(byte[] data, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
