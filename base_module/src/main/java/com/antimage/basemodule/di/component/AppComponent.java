package com.antimage.basemodule.di.component;

import android.content.Context;

import com.antimage.basemodule.BaseApp;
import com.antimage.basemodule.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by xuyuming on 2019/6/11.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(BaseApp app);

    /**
     * context
     */
    Context context();

    Retrofit retrofit();
}
