package com.antimage.basemodule.di.module;

import android.content.Context;
import android.text.TextUtils;

import com.antimage.basemodule.core.AppConfig;
import com.antimage.basemodule.utils.android.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
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
    OkHttpClient provideOKHttpClient(Context context, AppConfig appConfig) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (!appConfig.isRelease()) {
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        builder.addNetworkInterceptor(new CustomInterceptor(context, appConfig));
        builder.readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .cache(new Cache(new File(appConfig.getHttpCacheDir()), appConfig.getHttpCacheMaxLength()));
        return builder.build();
    }

    private static class CustomInterceptor implements Interceptor {

        private Context context;
        private AppConfig appConfig;

        public CustomInterceptor(Context context, AppConfig appConfig) {
            this.context = context;
            this.appConfig = appConfig;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            // add request header
            Request originalRequest = chain.request();
            Request.Builder builder = originalRequest.newBuilder();

            Map<String, String> map = appConfig.getHeaders();
            for (String key : map.keySet()) {
                key = key == null ? "" : key;
                String value = map.get(key);
                value = value == null ? "" : URLEncoder.encode(value, "UTF-8");
                builder.header(key, value);
            }

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
