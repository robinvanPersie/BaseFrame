package com.antimage.independentmodule.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.antimage.basemodule.ui.activity.BaseActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by xuyuming on 2019/6/14.
 */

public class LauncherActivity extends BaseActivity {

    @Override
    protected boolean needAppConfig() {
        return false;
    }

    @Override
    protected boolean needUserManager() {
        return false;
    }

    @Override
    protected boolean needAppManager() {
        return false;
    }

    private Disposable disposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disposable = Observable.timer(2000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    startActivity(new Intent(this, TestActivity.class));
                    finish();
                }, Timber::wtf);

    }

    @Override
    protected void onDestroy() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        super.onDestroy();
    }
}
