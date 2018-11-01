package com.antimage.baseframe.di.module;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.antimage.baseframe.annotation.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xuyuming on 2018/10/15.
 */

@Module
public class ActivityModule {

    private final Activity activity;

    public ActivityModule(@NonNull Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    Activity provideActivity() {
        return this.activity;
    }
}
