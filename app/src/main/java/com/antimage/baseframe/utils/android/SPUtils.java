package com.antimage.baseframe.utils.android;

import android.content.Context;
import android.content.SharedPreferences;

import com.antimage.baseframe.App;

/**
 * Created by xuyuming on 2018/10/17.
 * SharedPreference工具类
 */

public class SPUtils {

    private static final String SP_NAME = "antimage_sp";
    private static SharedPreferences sp = App.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);

    private static final String KEY_GUIDE = "guide"; // 首次启动引导页
    private static final String KEY_LOGIN = "login"; // 登录状态

    public static void putGuide(boolean isGuide) {
        putBoolean(KEY_GUIDE, isGuide);
    }

    public static boolean isGuide() {
        return getBoolean(KEY_GUIDE, false);
    }

    public static void putLoginStatus(boolean isLogin) {
        putBoolean(KEY_LOGIN, isLogin);
    }

    public static boolean isLogin() {
        return getBoolean(KEY_LOGIN, false);
    }

    private static void putString(String key, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    private static void putInt(String key, int value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    private static void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }


    private static String getString(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    private static int getInt(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    private static boolean getBoolean(String key, boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }
}
