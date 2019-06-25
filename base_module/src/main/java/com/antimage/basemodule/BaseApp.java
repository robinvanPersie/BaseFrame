package com.antimage.basemodule;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.antimage.basemodule.core.InjectManager;
import com.antimage.basemodule.di.component.AppComponent;
import com.antimage.basemodule.di.component.DaggerAppComponent;
import com.antimage.basemodule.di.module.AppModule;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by xuyuming on 2019/6/11.
 */

public class BaseApp extends Application {

    private static BaseApp instance;

    public static BaseApp getInstance() {
        return instance;
    }

    private AppComponent appComponent;

    @Inject
    InjectManager injectManager;
    @Inject
    Timber.Tree mTree;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initializeInjector();
        if (mTree != null)
            Timber.plant(mTree);
        ARouter.init(this);
        ARouter.openLog();
        ARouter.getInstance().openDebug();
    }

    public InjectManager injectManager() {
        return injectManager;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    private void initializeInjector() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        appComponent.inject(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
}
