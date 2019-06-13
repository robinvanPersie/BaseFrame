package com.antimage.independentmodule;

import android.app.Application;
import android.util.Log;

/**
 * Created by xuyuming on 2019/6/11.
 */

public class IApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(getClass().getSimpleName(), "IndependentModule application onCreate()");
    }
}
