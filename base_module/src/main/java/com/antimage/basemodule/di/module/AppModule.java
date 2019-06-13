package com.antimage.basemodule.di.module;

import android.content.Context;

import com.antimage.basemodule.BaseApp;
import com.antimage.basemodule.core.AppConfigImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

/**
 * Created by xuyuming on 2019/6/11.
 */
@Module(includes = {RetrofitModule.class})
public class AppModule {

    private BaseApp application;

    public AppModule(BaseApp application) {
        this.application = application;
    }

    @Singleton
    @Provides
    BaseApp provideApplication() {
        return application;
    }

    @Singleton
    @Provides
    Context provideContext() {
        return this.application;
    }

    @Singleton
    @Provides
    Timber.Tree provideTimberTree(AppConfigImpl appConfig) {
        return appConfig.isRelease() ? null : new Timber.DebugTree();
    }
}
