package com.antimage.baseframe.utils.android;

import android.widget.Toast;

import com.antimage.baseframe.App;

/**
 * Created by xuyuming on 2018/10/16.
 */

public class ToastUtils {

    public static void toastShort(String message) {
        Toast.makeText(App.getInstance(), message, Toast.LENGTH_SHORT);
    }

    public static void toastShort(int textId) {
        Toast.makeText(App.getInstance(), textId, Toast.LENGTH_SHORT);
    }

    public static void toastLong(String message) {
        Toast.makeText(App.getInstance(), message, Toast.LENGTH_LONG);
    }

    public static void toastLong(int textId) {
        Toast.makeText(App.getInstance(), textId, Toast.LENGTH_LONG);
    }
}
