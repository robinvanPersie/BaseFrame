package com.antimage.baseframe.di.component;

import android.content.Context;

import com.antimage.baseframe.App;
import com.antimage.baseframe.annotation.MainScheduler;
import com.antimage.baseframe.di.module.AppModule;
import com.antimage.baseframe.model.DaoSession;

import javax.inject.Singleton;

import dagger.Component;
import io.reactivex.Scheduler;
import timber.log.Timber;

/**
 * Created by xuyuming on 2018/10/15.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(App App);

    /**
     * context
     */
    Context context();

    /**
     * 返回日志显示
     */
    Timber.Tree timberTree();

    @MainScheduler
    Scheduler mainScheduler();

    DaoSession daoSession();
}
