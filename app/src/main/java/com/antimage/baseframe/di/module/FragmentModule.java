package com.antimage.baseframe.di.module;

import android.support.v4.app.Fragment;

import com.antimage.baseframe.annotation.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xuyuming on 2018/10/15.
 */

@Module
public class FragmentModule {

    private final Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    Fragment provideFragment() {
        return this.fragment;
    }
}
