package com.antimage.basemodule.di.component;

import android.app.Activity;


import com.antimage.basemodule.annotation.ActivityScope;
import com.antimage.basemodule.di.module.ActivityModule;

import dagger.Component;

/**
 * Created by xuyuming on 2018/10/15.
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    /**
     * @return activity;
     */
    Activity activity();
}
