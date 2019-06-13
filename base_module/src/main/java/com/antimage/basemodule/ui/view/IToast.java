package com.antimage.basemodule.ui.view;

/**
 * Created by xuyuming on 2018/10/16.
 */

public interface IToast {

    void showToast(String message);

    void showToast(int textId);

    void showLongToast(String message);

    void showLongToast(int textId);
}
