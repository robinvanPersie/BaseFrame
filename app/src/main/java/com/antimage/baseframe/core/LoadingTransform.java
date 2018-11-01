package com.antimage.baseframe.core;

import android.support.annotation.StringRes;

import com.antimage.baseframe.ui.interf.ILoading;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

/**
 * Created by xuyuming on 2018/10/19.
 */

public class LoadingTransform<T> implements ObservableTransformer<T, T> {

    private ILoading mView;
    private String message;
    private int msgId;

    public LoadingTransform(ILoading mView, String message) {
        this.mView = mView;
        this.message = message;
    }

    public LoadingTransform(ILoading mView, @StringRes int msgId) {
        this.mView = mView;
        this.msgId = msgId;
    }

    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        return upstream.doOnSubscribe(disposable -> {
            if (msgId == 0) {
                mView.showLoading(message);
            } else {
                mView.showLoading(msgId);
            }
        })
                .doFinally(() -> mView.hideLoading());
    }
}
