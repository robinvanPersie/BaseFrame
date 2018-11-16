package com.antimage.baseframe.di.module;

import com.antimage.baseframe.core.AppConfig;
import com.antimage.baseframe.net.api.ApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by xuyuming on 2018/10/16.
 */

@Module(includes = {HttpModule.class, ConverterModule.class})
public class ApiModule {

    @Singleton
    @Provides
    ApiService provideApiService(AppConfig appConfig, Converter.Factory converterFactory, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(appConfig.getApiHost())
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
                .create(ApiService.class);
    }
}
