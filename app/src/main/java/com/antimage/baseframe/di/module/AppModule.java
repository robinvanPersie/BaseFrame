package com.antimage.baseframe.di.module;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.antimage.baseframe.annotation.MainScheduler;
import com.antimage.baseframe.database.MOpenHelper;
import com.antimage.baseframe.model.DaoMaster;
import com.antimage.baseframe.model.DaoSession;

import org.greenrobot.greendao.database.Database;

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

    @Singleton
    @Provides
    DaoSession provideDaoSession() {
        MOpenHelper helper = new MOpenHelper(application, "base-frame");
        Database db = helper.getWritableDb();
        return new DaoMaster(db).newSession();
    }

}
