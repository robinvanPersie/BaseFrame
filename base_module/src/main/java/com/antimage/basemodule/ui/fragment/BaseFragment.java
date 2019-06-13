package com.antimage.basemodule.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.antimage.basemodule.BaseApp;
import com.antimage.basemodule.di.component.AppComponent;
import com.antimage.basemodule.di.module.FragmentModule;
import com.antimage.basemodule.ui.activity.BaseActivity;
import com.antimage.basemodule.ui.view.IBaseView;
import com.antimage.basemodule.ui.view.ILoading;
import com.antimage.basemodule.ui.view.IToolbar;
import com.antimage.basemodule.utils.android.ToastUtils;
import com.trello.rxlifecycle2.components.support.RxFragment;

import io.reactivex.Observable;

/**
 * Created by xuyuming on 2018/10/16.
 */

public abstract class BaseFragment extends RxFragment implements IBaseView, ILoading {

    /**
     * 没有toolbar时的默认实现
     */
    private IToolbar.NoneToolbarImpl noneToolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initializeInjector();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

//    protected abstract void initializeInjector();

    protected FragmentModule getFragmentModule() {
        return new FragmentModule(this);
    }

    protected AppComponent getAppComponet() {
        return BaseApp.getInstance().getAppComponent();
    }

    protected IToolbar getToolbar() {
        if (getActivity() instanceof IToolbar) {
            return (IToolbar) getActivity();
        }
        if (noneToolbar == null) {
            noneToolbar = new IToolbar.NoneToolbarImpl();
        }
        return noneToolbar;
    }

    @Override
    public void showLoading(String message) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showLoading(message);
        }
    }

    @Override
    public void showLoading(int textId) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showLoading(textId);
        }
    }

    @Override
    public void hideLoading() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).hideLoading();
        }
    }

    @Override
    public void showToast(String message) {
        ToastUtils.toastShort(message);
    }

    @Override
    public void showToast(int textId) {
        ToastUtils.toastShort(textId);
    }

    @Override
    public void showLongToast(String message) {
        ToastUtils.toastLong(message);
    }

    @Override
    public void showLongToast(int textId) {
        ToastUtils.toastLong(textId);
    }

    @Override
    public Observable<Boolean> ensurePermissionEnable(String permissions, String deniedMessage) {
        return ((BaseActivity) getActivity()).ensurePermissionEnable(permissions, deniedMessage);
    }

    @Override
    public Observable<Boolean> ensurePermissionEnable(String permissions, int resId) {
        return ensurePermissionEnable(permissions, getString(resId));
    }

    /**
     * @return false:不拦截，true:拦截
     */
    public boolean onBackPress() {
        return false;
    }
}
