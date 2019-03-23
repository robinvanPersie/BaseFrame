package com.antimage.baseframe.ui.view.base;

import com.antimage.baseframe.presenter.base.BasePresenter;

/**
 * Created by xuyuming on 2018/10/19.
 */

public interface IWithPresenter<P extends BasePresenter> {

    P getPresenter();
}
