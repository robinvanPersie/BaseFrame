package com.antimage.basemodule.ui.view;

import com.antimage.basemodule.presenter.BasePresenter;

/**
 * Created by xuyuming on 2018/10/19.
 */

public interface IWithPresenter<P extends BasePresenter> {

    P getPresenter();
}
