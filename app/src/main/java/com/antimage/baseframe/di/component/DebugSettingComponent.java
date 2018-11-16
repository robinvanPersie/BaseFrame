package com.antimage.baseframe.di.component;

import com.antimage.baseframe.annotation.FragmentScope;
import com.antimage.baseframe.di.module.AppModule;
import com.antimage.baseframe.ui.fragment.SettingFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by xuyuming on 2018/11/15.
 */
@FragmentScope
@Component(dependencies = AppComponent.class)
public interface DebugSettingComponent {
    void inject(SettingFragment settingFragment);
}
