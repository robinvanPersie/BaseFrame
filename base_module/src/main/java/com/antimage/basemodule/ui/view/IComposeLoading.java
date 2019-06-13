package com.antimage.basemodule.ui.view;

import android.support.annotation.StringRes;

import io.reactivex.ObservableTransformer;

/**
 * Created by xuyuming on 2018/10/19.
 * 通过compose()绑定loading的显示与隐藏
 */

public interface IComposeLoading {

    <T> ObservableTransformer<T, T> bindLoading();

    <T> ObservableTransformer<T, T> bindLoading(String message);

    <T> ObservableTransformer<T, T> bindLoading(@StringRes int message);
}
