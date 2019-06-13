package com.antimage.basemodule.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.antimage.basemodule.BaseApp;
import com.antimage.basemodule.core.AppConfig;
import com.antimage.basemodule.core.AppManager;
import com.antimage.basemodule.core.InjectManager;
import com.antimage.basemodule.core.UserManager;
import com.antimage.basemodule.di.component.AppComponent;
import com.antimage.basemodule.di.module.ActivityModule;
import com.antimage.basemodule.ui.fragment.dialog.LoadingDialogFragment;
import com.antimage.basemodule.ui.view.IBaseView;
import com.antimage.basemodule.ui.view.ILoading;
import com.antimage.basemodule.utils.android.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.Observable;
import timber.log.Timber;

/**
 * Created by xuyuming on 2018/10/15.
 */

public class BaseActivity extends RxAppCompatActivity implements IBaseView, ILoading {

    private static final String LOADING_TAG = "loadingTag";
    protected UserManager mUserManager;
    protected AppConfig mAppConfig;
    protected AppManager mAppManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectManager injectManager = BaseApp.getInstance().injectManager();
        if (needUserManager())
            mUserManager = injectManager.userManager();
        if (needAppConfig())
            mAppConfig = injectManager.appConfig();
        if (needAppManager())
            mAppManager = injectManager.appManager();
    }

    protected boolean needUserManager() {
        return true;
    }

    protected boolean needAppConfig() {
        return true;
    }

    protected boolean needAppManager() {
        return true;
    }

    public AppComponent getAppComponent() {
        return BaseApp.getInstance().getAppComponent();
    }

    public ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    @Override
    public void showLoading(String message) {
        showLoadingDialogFragment(message);
    }

    @Override
    public void showLoading(int textId) {
        showLoadingDialogFragment(getString(textId));
    }

    @Override
    public void hideLoading() {
        hideLoadingDialogFragment();
    }

    private void showLoadingDialogFragment(String message) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(LOADING_TAG);
        if (fragment != null && fragment.isAdded()) {
            Timber.w("loading dialogFragment has been added");
            LoadingDialogFragment loadingDialogFragment = (LoadingDialogFragment) fragment;
            loadingDialogFragment.updateMessage(message);
            return;
        }
        LoadingDialogFragment.newInstance(message).show(getSupportFragmentManager(), LOADING_TAG);
    }

    private void hideLoadingDialogFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(LOADING_TAG);
        if (fragment != null) {
            LoadingDialogFragment dialogFragment = (LoadingDialogFragment) fragment;
            dialogFragment.dismissAllowingStateLoss();
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
        RxPermissions rxPermissions = new RxPermissions(this);
        return rxPermissions.requestEach(permissions)
                .flatMap(permission -> {
                    if (permission.granted) {
                        return Observable.just(true);
                    } else if (!permission.shouldShowRequestPermissionRationale) {
                        if (!TextUtils.isEmpty(deniedMessage)) {
                            showToast(deniedMessage);
                        }
//                        AndroidHelper.jumpToPermissionSettings(getContext());
                    }
                    return Observable.just(false);
                });
    }

    @Override
    public Observable<Boolean> ensurePermissionEnable(String permissions, int resId) {
        return ensurePermissionEnable(permissions, getString(resId));
    }
}
