package com.antimage.basemodule.di.module;


import com.antimage.basemodule.core.AppConfig;

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

@Module(includes = {AppConfigModule.class, GsonModule.class, HttpModule.class, ConverterModule.class})
public class RetrofitModule {

    @Singleton
    @Provides
    Retrofit provideRetrofit(AppConfig appConfig, Converter.Factory converterFactory, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(appConfig.getApiHost())
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
//                .create(ApiService.class);
    }
}
