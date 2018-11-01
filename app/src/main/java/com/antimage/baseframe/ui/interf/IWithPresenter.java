package com.antimage.baseframe.ui.interf;

import com.antimage.baseframe.presenter.BasePresenter;

/**
 * Created by xuyuming on 2018/10/19.
 */

public interface IWithPresenter<P extends BasePresenter> {

    P getPresenter();
}
