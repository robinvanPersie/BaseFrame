package com.antimage.baseframe.net.base;

import com.antimage.baseframe.exception.ApiExceptionHelper;
import com.antimage.baseframe.model.Response;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by xuyuming on 2018/10/17.
 */

public abstract class BaseConsumer<T> implements Observer<Response<T>> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(Response<T> tResponse) {
        onAccept(tResponse.getData());
    }

    @Override
    public void onError(Throwable e) {
        ApiExceptionHelper.handleException(e, this);
    }

    @Override
    public void onComplete() {

    }

    public abstract void onAccept(T response);

    public abstract Throwable onError(int code, String message);
}
