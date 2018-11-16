package com.antimage.baseframe.core;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;

import com.antimage.baseframe.App;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by xuyuming on 2018/11/16.
 */

public class AppManager {

    private LifeCycleCallback lifeCycleCallback;
    private App app;

    @Inject
    public AppManager() {
        lifeCycleCallback = new LifeCycleCallback();
        app = App.getInstance();
        app.registerActivityLifecycleCallbacks(lifeCycleCallback);
    }

    /**
     * 利用AlarmManager重启
     * @param killDelay   延时关闭时间
     * @param startDelay  关闭后延时重启时间
     */
    public void restartApp(long killDelay, long startDelay) {
        Observable.timer(killDelay, TimeUnit.SECONDS)
                .subscribe(aLong -> {
                    App app = App.getInstance();
                    Intent intent = app.getPackageManager().getLaunchIntentForPackage(app.getPackageName());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent pendingIntent = PendingIntent.getActivity(app, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) app.getSystemService(Context.ALARM_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        alarmManager.setExact(AlarmManager.RTC, System.currentTimeMillis() + startDelay, pendingIntent);
                    } else {
                        alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + startDelay, pendingIntent);
                    }
                    exitApp(0);
                });
    }

    private void exitApp(long delay) {
        Observable.timer(delay, TimeUnit.SECONDS)
                .subscribe(aLong -> {
                    app.unregisterActivityLifecycleCallbacks(lifeCycleCallback);
                    List<Activity> activities = lifeCycleCallback.getActivityList();
                    for (int i = activities.size() - 1; i >= 0; i--) {
                        Activity activity = activities.get(i);
                        if (activity != null && !activity.isFinishing()) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed())
                                continue;
                            activity.finish();
                        }
                    }
                    lifeCycleCallback.release();
                    lifeCycleCallback = null;
                    Process.killProcess(Process.myPid());
                });
    }

    private static class LifeCycleCallback implements Application.ActivityLifecycleCallbacks {

        private List<Activity> activityList;

        public LifeCycleCallback() {
            activityList = new LinkedList<>();
        }

        public List<Activity> getActivityList() {
            return activityList;
        }

        public void release() {
            activityList.clear();
            activityList = null;
        }

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            activityList.add(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            activityList.remove(activity);
        }
    }
}
