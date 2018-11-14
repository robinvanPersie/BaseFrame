package com.antimage.baseframe.core;

import com.antimage.baseframe.App;
import com.antimage.baseframe.model.User;
import com.antimage.baseframe.utils.android.SPUtils;

import io.reactivex.Observable;

/**
 * Created by xuyuming on 2018/10/25.
 */

public class UserManager {

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

    public Observable<Boolean> login(String account, String password) {
        return null;
    }

    public Observable<Boolean> loginByVerificationCode(String number, String code) {
        return null;
    }
}
