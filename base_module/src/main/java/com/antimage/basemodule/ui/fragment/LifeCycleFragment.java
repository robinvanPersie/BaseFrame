package com.antimage.basemodule.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;


import com.antimage.basemodule.presenter.FragmentPresenter;
import com.antimage.basemodule.ui.view.IWithPresenter;

import javax.inject.Inject;

/**
 * Created by xuyuming on 2018/10/19.
 * 有使用rx的fragment都应继承此类
 */
public abstract class LifeCycleFragment<T extends FragmentPresenter> extends BaseFragment implements IWithPresenter<T> {

    @Inject
    T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeInjector();
        if (mPresenter != null) {
            mPresenter.onCreate(getActivity(), this);
        }
    }

    protected abstract void initializeInjector();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mPresenter != null) {
            mPresenter.onAttachView(this);
        }
    }

    @Override
    public T getPresenter() {
        if (mPresenter != null) return mPresenter;
        Fragment parent = getParentFragment();
        if (parent instanceof IWithPresenter) {
            return (T) ((IWithPresenter) parent).getPresenter();
        }
        return null;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.onDetachView();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }
}
