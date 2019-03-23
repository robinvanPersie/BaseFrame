package com.antimage.baseframe.ui.view.base;

import android.support.annotation.StringRes;

import io.reactivex.Observable;

/**
 * Created by xuyuming on 2018/10/22.
 */

public interface IPermissions {

    /**
     * 权限检测
     */
    Observable<Boolean> ensurePermissionEnable(String permissions, String deniedMessage);

    Observable<Boolean> ensurePermissionEnable(String permissions, @StringRes int resId);
}
