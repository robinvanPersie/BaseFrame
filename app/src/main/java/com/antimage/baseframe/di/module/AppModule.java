package com.antimage.baseframe.di.module;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.antimage.baseframe.annotation.MainScheduler;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

/**
 * Created by xuyuming on 2018/10/15.
 */

@Module(includes = {ApiModule.class, GsonModule.class})
public class AppModule {

    private final Application application;

    public AppModule(@NonNull Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    Context provideContext() {
        return this.application;
    }

    @Singleton
    @Provides
    Application provideApplication() {
        return this.application;
    }

    @Singleton
    @Provides
    Timber.Tree provideTimberTree() {
        return new Timber.DebugTree();
    }

    /**
     * 主线程调度程序
     */
    @Singleton
    @Provides
    @MainScheduler
    Scheduler provideMainScheduler() {
        return AndroidSchedulers.mainThread();
    }

}
