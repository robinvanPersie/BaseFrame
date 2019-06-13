package com.antimage.basemodule.ui.view;

/**
 * Created by xuyuming on 2018/10/15.
 */

public interface ILoading {

    void showLoading(String message);

    void showLoading(int textId);

    void hideLoading();
}
