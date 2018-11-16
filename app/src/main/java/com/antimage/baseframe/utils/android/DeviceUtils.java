package com.antimage.baseframe.utils.android;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

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
}
