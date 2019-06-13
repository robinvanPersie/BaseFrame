package com.antimage.independentmodule.di.module;

import com.antimage.basemodule.annotation.ActivityScope;
import com.antimage.independentmodule.core.ApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by xuyuming on 2019/6/13.
 */
@Module
public class ApiModule {

    @ActivityScope
    @Provides
    ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}
