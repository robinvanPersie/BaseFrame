package com.antimage.basemodule.core;

import com.antimage.basemodule.utils.android.SPUtils;

import javax.inject.Inject;

/**
 * Created by xuyuming on 2018/10/25.
 */

public class UserManagerImpl implements UserManager {

    @Inject
    public UserManagerImpl() {}

    public boolean isLogin() {
        return SPUtils.isLogin();
    }

    /**
     * 登录
     */
//    public Observable<Boolean> login(String account, String password) {
//        if (TextUtils.isEmpty(account)) {
//            return Observable.error(new ApiException("请输入用户名"));
//        }
//        if (TextUtils.isEmpty(password)) {
//            return Observable.error(new ApiException("请输入密码"));
//        }
//        return apiService.login()
//                .map(response -> {
//                    // SPUtils.setLoginStatus(true);
//                    return true;
//                });
//    }

//    public Observable<Boolean> loginByVerificationCode(String number, String code) {
//        if (TextUtils.isEmpty(number)) {
//            return Observable.error(new ApiException("请输入用户名"));
//        }
//        if (TextUtils.isEmpty(code)) {
//            return Observable.error(new ApiException("请输入密码"));
//        }
//        return null;
//    }

    /**
     * 注册
     */
//    public Observable<Boolean> register() {
//        return apiService.register()
//                .map(response -> {
//                    // login
//                    return true;
//                });
//    }
}
