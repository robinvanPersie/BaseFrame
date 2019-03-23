package com.antimage.baseframe.presenter.base;

import android.content.Context;
import android.support.annotation.StringRes;

import com.antimage.baseframe.core.LoadingTransform;
import com.antimage.baseframe.ui.view.base.IComposeLoading;
import com.antimage.baseframe.ui.view.base.ILoading;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;

import javax.annotation.Nonnull;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by xuyuming on 2018/10/19.
 */

public class BasePresenter<E, V> implements LifecycleProvider<E>, IComposeLoading {

    protected V mView;
    private LifecycleProvider<E> lifecycleProvider;
    protected Context context;
    private boolean isAttach;

    public void onCreate(Context context, LifecycleProvider<E> lifecycleProvider) {
        this.context = context;
        this.lifecycleProvider = lifecycleProvider;
    }

    public void onAttachView(V v) {
        mView = v;
        isAttach = true;
    }

    public void onDetachView() {
        isAttach = false;
        mView = null;
    }

    public void onDestroy() {
        lifecycleProvider = null;
        context = null;
    }

    /**
     * 通过compose绑定loading的显示与消失
     * @param message
     * @param <T>
     * @return
     */
    @Override
    public <T> ObservableTransformer<T, T> bindLoading(String message) {
        if (mView instanceof ILoading) {
            return new LoadingTransform<>((ILoading) mView, message);
        }
        return upstream -> upstream;
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLoading(@StringRes int textId) {
        if (mView instanceof ILoading) {
            return new LoadingTransform<>((ILoading) mView, textId);
        }
        return upstream -> upstream;
    }

    @Nonnull
    @Override
    public Observable<E> lifecycle() {
        return lifecycleProvider == null ? BehaviorSubject.create() : lifecycleProvider.lifecycle();
    }

    @Nonnull
    @Override
    public <T> LifecycleTransformer<T> bindUntilEvent(@Nonnull E event) {
        if(lifecycleProvider == null) return RxLifecycle.bind(BehaviorSubject.create());
        return lifecycleProvider.bindUntilEvent(event);
    }

    @Nonnull
    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        if(lifecycleProvider == null) return RxLifecycle.bind(BehaviorSubject.create());
        return lifecycleProvider.bindToLifecycle();
    }
}
