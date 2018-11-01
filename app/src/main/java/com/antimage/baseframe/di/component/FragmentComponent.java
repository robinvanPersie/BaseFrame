package com.antimage.baseframe.di.component;

import android.support.v4.app.Fragment;

import com.antimage.baseframe.annotation.FragmentScope;
import com.antimage.baseframe.di.module.FragmentModule;

import dagger.Component;

/**
 * Created by xuyuming on 2018/10/15.
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    /**
     * @return fragment
     */
    Fragment fragment();
}
