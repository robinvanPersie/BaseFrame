package com.antimage.baseframe.di.module;

import android.content.Context;
import android.text.TextUtils;

import com.antimage.baseframe.utils.android.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by xuyuming on 2018/10/16.
 */

@Module
public class HttpModule {

    @Singleton
    @Provides
    OkHttpClient provideOKHttpClient(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        builder.readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .cache(new Cache(new File(""), 1024 * 10));
        return builder.build();
    }

    private static class CustomInterceptor implements Interceptor {

        private Context context;

        public CustomInterceptor(Context context) {
            this.context = context;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            // add request header
            Request originalRequest = chain.request();
            Request.Builder builder = originalRequest.newBuilder();
            Headers originalHeaders = originalRequest.headers();

            Request newRequest = builder
                    .method(originalRequest.method(), originalRequest.body())
                    .build();

            if (!NetworkUtils.isNetworkAvailable(context)) {
                if (newRequest.cacheControl().isPublic()) {
                    newRequest = newRequest.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                }
            }
            Response response = chain.proceed(newRequest);
            if (NetworkUtils.isNetworkAvailable(context)) {
                // 判断是否有Cache-Control
                String value = originalRequest.cacheControl().toString();
                if (!TextUtils.isEmpty(value)) {
                    response = response.newBuilder().header("Cache-Control", value).removeHeader("Pragma").build();
                }
                return response;
            }
            return response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                    .removeHeader("Pragma")
                    .build();
        }
    }
}
