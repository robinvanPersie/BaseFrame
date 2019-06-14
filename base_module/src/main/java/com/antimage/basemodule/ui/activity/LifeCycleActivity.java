package com.antimage.basemodule.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;


import com.antimage.basemodule.presenter.ActivityPresenter;
import com.antimage.basemodule.presenter.BasePresenter;
import com.antimage.basemodule.ui.view.IWithPresenter;

import javax.inject.Inject;

/**
 * Created by xuyuming on 2019/3/23.
 */

public abstract class LifeCycleActivity<P extends ActivityPresenter> extends BaseActivity implements IWithPresenter<P> {

    @Inject
    P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeInjector();
        if (mPresenter != null) {
            mPresenter.onCreate(this, this);
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mPresenter != null) {
            mPresenter.onAttachView(this);
        }
        Toolbar toolbar = buildToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(showBackBtn());
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }
    }

    protected abstract void initializeInjector();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDetachView();
            mPresenter.onDestroy();
        }
    }

    protected abstract Toolbar buildToolbar();

    protected boolean showBackBtn() {
        return true;
    }

    @Override
    public P getPresenter() {
        return mPresenter;
    }
}
