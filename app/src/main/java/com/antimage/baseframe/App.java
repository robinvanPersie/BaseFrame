package com.antimage.baseframe;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.antimage.baseframe.core.AppConfig;
import com.antimage.baseframe.core.AppManager;
import com.antimage.baseframe.core.InjectConfig;
import com.antimage.baseframe.core.UserManager;
import com.antimage.baseframe.di.component.AppComponent;
import com.antimage.baseframe.di.component.DaggerAppComponent;
import com.antimage.baseframe.di.module.AppModule;
import com.antimage.baseframe.event.ForceLogoutStatusEvent;
import com.antimage.baseframe.event.RxBus;
import com.antimage.baseframe.net.api.ApiService;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

/**
 * Created by xuyuming on 2018/10/15.
 */

public class App extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Inject
    UserManager mUserManager;
    @Inject
    AppConfig mAppConfig;
    @Inject
    AppManager mAppManager;
    @Inject
    ApiService mApiService;
    @Inject
    Timber.Tree mTree;

    private AppComponent appComponent;

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initializeInjector();
        Timber.plant(mTree);
        initInjectConfig();
    }

    private void initInjectConfig() {
        InjectConfig.get().setUserManage(mUserManager)
                .setAppManager(mAppManager)
                .setAppConfig(mAppConfig)
                .setApiService(mApiService);
    }

    /**
     * 强制登出
     */
    private void registerEvent() {
        RxBus.register(ForceLogoutStatusEvent.class)
                .throttleFirst(5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(event -> {
                    int textId;
                    if (event.getStatus() == ForceLogoutStatusEvent.STATUS_MODIFY_PWD) {
                        textId = R.string.app_name;
                    } else {
                        textId = R.string.app_name;
                    }
                    //todo
                }, Timber::wtf);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public static App getInstance() {
        return instance;
    }

    private void initializeInjector() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        appComponent.inject(this);
    }
}
