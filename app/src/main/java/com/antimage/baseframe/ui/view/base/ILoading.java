package com.antimage.baseframe.ui.view.base;

/**
 * Created by xuyuming on 2018/10/15.
 */

public interface ILoading {

    void showLoading(String message);

    void showLoading(int textId);

    void hideLoading();
}
