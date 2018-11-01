package com.antimage.baseframe.utils.android;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by xuyuming on 2018/10/16.
 */

public class NetworkUtils {

    /**
     * 是否有网络连接
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.isAvailable() && info.isConnectedOrConnecting();
    }

    /**
     * 是否是wifi
     * @return
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return info != null && info.isAvailable() && info.isConnected();
    }
}
