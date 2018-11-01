package com.antimage.baseframe.ui.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.antimage.baseframe.R;
import com.antimage.baseframe.di.component.AppComponent;
import com.antimage.baseframe.di.module.FragmentModule;
import com.antimage.baseframe.ui.interf.ILoading;
import com.antimage.baseframe.ui.interf.IToast;
import com.antimage.baseframe.utils.android.ToastUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatDialogFragment;

import java.lang.reflect.Field;

/**
 * Created by xuyuming on 2018/10/24.
 */

public abstract class BaseDialogFragment extends RxAppCompatDialogFragment implements ILoading, IToast {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.AppTheme_Dialog);
        initializeInjector();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (getDialog() == null || getDialog().getWindow() == null) {
            setShowsDialog(false);
            return;
        }
        super.onActivityCreated(savedInstanceState);
    }

    protected FragmentModule getFragmentModule() {
        return new FragmentModule(this);
    }

    protected AppComponent getAppComponent() {
        return ((BaseActivity)getActivity()).getAppComponent();
    }

//    public void showAllowingStateLoss(FragmentManager manager, String tag) {
//        Class clazz = DialogFragment.class;
//        try {
//            Field mDismissed = clazz.getDeclaredField("mDismissed");
//            Field mShownByMe = clazz.getDeclaredField("mShownByMe");
//            mDismissed.setAccessible(true);
//            mShownByMe.setAccessible(true);
//            mDismissed.setBoolean(this, false);
//            mShownByMe.setBoolean(this,true);
//            FragmentUtils.add(manager,0,this,tag,false,null,true);
//        } catch (Exception e) {
//            super.show(manager,tag);
//        }
//    }

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

    protected abstract void initializeInjector();
}
