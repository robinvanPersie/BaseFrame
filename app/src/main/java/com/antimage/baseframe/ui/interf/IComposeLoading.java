package com.antimage.baseframe.ui.interf;

import android.support.annotation.StringRes;

import io.reactivex.ObservableTransformer;

/**
 * Created by xuyuming on 2018/10/19.
 * 通过compose()绑定loading的显示与隐藏
 */

public interface IComposeLoading {

    <T> ObservableTransformer<T, T> bindLoading(String message);

    <T> ObservableTransformer<T, T> bindLoading(@StringRes int message);
}
