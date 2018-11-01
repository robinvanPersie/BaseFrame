package com.antimage.baseframe.core;

import com.antimage.baseframe.utils.android.SPUtils;

import io.reactivex.Observable;

/**
 * Created by xuyuming on 2018/10/25.
 */

public class UserManager {

    public UserManager() {}

    public boolean isLogin() {
        return SPUtils.isLogin();
    }

    public Observable<Boolean> login(String account, String password) {
        return null;
    }

    public Observable<Boolean> loginByVerificationCode(String number, String code) {
        return null;
    }
}
