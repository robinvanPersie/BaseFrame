package com.antimage.basemodule.utils.android;

import android.widget.Toast;

import com.antimage.basemodule.BaseApp;

/**
 * Created by xuyuming on 2018/10/16.
 */

public class ToastUtils {

    public static void toastShort(String message) {
        Toast.makeText(BaseApp.getInstance(), message, Toast.LENGTH_SHORT).show();
    }

    public static void toastShort(int textId) {
        Toast.makeText(BaseApp.getInstance(), textId, Toast.LENGTH_SHORT).show();
    }

    public static void toastLong(String message) {
        Toast.makeText(BaseApp.getInstance(), message, Toast.LENGTH_LONG).show();
    }

    public static void toastLong(int textId) {
        Toast.makeText(BaseApp.getInstance(), textId, Toast.LENGTH_LONG).show();
    }
}
