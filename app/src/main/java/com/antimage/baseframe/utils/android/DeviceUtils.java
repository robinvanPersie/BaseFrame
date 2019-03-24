package com.antimage.baseframe.utils.android;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;

import com.antimage.baseframe.App;

/**
 * Created by xuyuming on 2018/10/18.
 */

public class DeviceUtils {

    /**
     * 屏幕密度
     */
    public static float getScreenDensity() {
        float density = App.getInstance().getResources().getDisplayMetrics().density;
        return density;
    }

    public static int getScreenWidth() {
        return App.getInstance().getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return App.getInstance().getResources().getDisplayMetrics().heightPixels;
    }

    public static int getVersionCode() {
        App app = App.getInstance();
        PackageInfo info = null;
        try {
            info = app.getPackageManager().getPackageInfo(app.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static String getVersionName() {
        App app = App.getInstance();
        PackageInfo info = null;
        try {
            info = app.getPackageManager().getPackageInfo(app.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void fullScreen(Activity activity) {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT < 19) { // lower api
            View v = activity.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            View decorView = activity.getWindow().getDecorView();
            int uiOptions =
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}
