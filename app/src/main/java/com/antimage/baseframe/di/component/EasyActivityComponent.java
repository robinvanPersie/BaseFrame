package com.antimage.baseframe.di.component;

import com.antimage.baseframe.annotation.ActivityScope;
import com.antimage.baseframe.di.module.ActivityModule;

import dagger.Component;

/**
 * Created by xuyuming on 2018/10/15.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface EasyActivityComponent extends ActivityComponent {

//    void inject(MainActivity act);
}
