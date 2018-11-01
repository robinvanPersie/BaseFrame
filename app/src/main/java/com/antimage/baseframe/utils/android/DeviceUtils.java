package com.antimage.baseframe.utils.android;

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
}
