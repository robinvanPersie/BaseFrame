package com.antimage.basemodule.di.module;

import com.antimage.basemodule.core.AppConfig;
import com.antimage.basemodule.core.AppConfigImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xuyuming on 2019/6/12.
 */

@Module
public class AppConfigModule {

    @Singleton
    @Provides
    AppConfig provideAppConfig(AppConfigImpl appConfig) {
        return appConfig;
    }
}
