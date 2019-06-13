package com.antimage.independentmodule.di.component;

import com.antimage.basemodule.annotation.ActivityScope;
import com.antimage.basemodule.di.component.AppComponent;
import com.antimage.independentmodule.core.ApiService;
import com.antimage.independentmodule.di.module.ApiModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by xuyuming on 2019/6/13.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = ApiModule.class)
public interface IndependentModuleComponent {

    ApiService apiService();

}
