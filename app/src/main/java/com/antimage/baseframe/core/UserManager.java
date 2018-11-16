package com.antimage.baseframe.core;

import android.text.TextUtils;

import com.antimage.baseframe.App;
import com.antimage.baseframe.exception.ApiException;
import com.antimage.baseframe.model.User;
import com.antimage.baseframe.net.api.ApiService;
import com.antimage.baseframe.utils.android.SPUtils;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by xuyuming on 2018/10/25.
 */

public class UserManager {

    @Inject
    ApiService apiService;

    public UserManager() {}

    private User mUser;

    public User getUser() {
        if (mUser == null) {
            mUser = App.getInstance().getDaoSession().getUserDao().load(0l);
        }
        return mUser;
    }

    public boolean isLogin() {
        return SPUtils.isLogin();
    }

    /**
     * 登录
     */
    public Observable<Boolean> login(String account, String password) {
        if (TextUtils.isEmpty(account)) {
            return Observable.error(new ApiException("请输入用户名"));
        }
        if (TextUtils.isEmpty(password)) {
            return Observable.error(new ApiException("请输入密码"));
        }
        return apiService.login()
                .map(response -> {
                    // SPUtils.setLoginStatus(true);
                    return true;
                });
    }

    public Observable<Boolean> loginByVerificationCode(String number, String code) {
        if (TextUtils.isEmpty(number)) {
            return Observable.error(new ApiException("请输入用户名"));
        }
        if (TextUtils.isEmpty(code)) {
            return Observable.error(new ApiException("请输入密码"));
        }
        return null;
    }

    /**
     * 注册
     */
    public Observable<Boolean> register() {
        return apiService.register()
                .map(response -> {
                    // login
                    return true;
                });
    }


}
