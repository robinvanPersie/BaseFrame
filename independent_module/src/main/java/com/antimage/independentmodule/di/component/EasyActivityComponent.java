package com.antimage.independentmodule.di.component;

import com.antimage.basemodule.annotation.ActivityScope;
import com.antimage.basemodule.di.component.ActivityComponent;
import com.antimage.basemodule.di.component.AppComponent;
import com.antimage.basemodule.di.module.ActivityModule;
import com.antimage.independentmodule.di.module.ApiModule;
import com.antimage.independentmodule.test.TestActivity;

import dagger.Component;

/**
 * Created by xuyuming on 2019/6/13.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, ApiModule.class})
public interface EasyActivityComponent extends ActivityComponent {

    void inject(TestActivity testActivity);
}
