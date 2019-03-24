package com.antimage.baseframe.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.antimage.baseframe.exception.ApiExceptionHelper;
import com.antimage.baseframe.ui.base.BaseActivity;
import com.antimage.baseframe.utils.android.DeviceUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by xuyuming on 2019/3/24.
 */

public class LauncherActivity extends BaseActivity {

    Disposable disposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DeviceUtils.fullScreen(this);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        disposable = Observable.timer(3, TimeUnit.SECONDS)
                .subscribe(aLong -> {
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }, Timber::wtf);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
