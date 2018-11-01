package com.antimage.baseframe.utils.android;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

/**
 * Created by xuyuming on 2018/10/22.
 */

public class LocationUtils {

    private static ILocationListener listener;
    private static LocationListener locationListener;

    public static class MLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            if (listener != null) {
                listener.onLocationSuccess(location);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    public interface ILocationListener {

        void onLocationSuccess(Location location);
    }

    /**
     * 添加监听
     * @param context
     * @param provider 定位方式
     * @param time     请求间隔
     * @param meter    最小距离间隔
     * @param iListener
     */
    public static void setLocationListener(Context context, String provider, long time, float meter, ILocationListener iListener) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        listener = iListener;
        if (locationListener == null) {
            locationListener = new MLocationListener();
        }
        getLocationManager(context).requestLocationUpdates(provider, time, meter, locationListener);
    }

    /**
     * 系统判定最优方式
     */
    public static Location getBestLocation(@NonNull Context context, Criteria criteria) {
        Location location;
        LocationManager manager = getLocationManager(context);
        if (criteria == null) {
            criteria = new Criteria();
            criteria.setPowerRequirement(Criteria.POWER_MEDIUM); // 中等消耗
            criteria.setAccuracy(Criteria.ACCURACY_FINE); // 更精确的位置
            criteria.setAltitudeRequired(true);  //需要海拔信息
        }
        String provider = manager.getBestProvider(criteria, true);
        if (TextUtils.isEmpty(provider)) {
            //如果找不到最适合的定位，使用network定位
            location = getNetworkLocation(context);
        } else {
            //高版本的权限检查
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            //获取最适合的定位方式的最后的定位权限
            location = manager.getLastKnownLocation(provider);
        }
        return location;
    }

    /**
     * gps定位
     */
    public static Location getGPSLocation(@NonNull Context context) {
        LocationManager manager = getLocationManager(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {//是否支持GPS定位
            //获取最后的GPS定位信息，如果是第一次打开，一般会拿不到定位信息，一般可以请求监听，在有效的时间范围可以获取定位信息
            return manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        return null;
    }

    /**
     * 网络定位
     */
    public static Location getNetworkLocation(@NonNull Context context) {
        LocationManager manager = getLocationManager(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            return manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        return null;
    }

    private static LocationManager getLocationManager(@NonNull Context context) {
        return (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }
}
