package com.antimage.baseframe.exception;

import android.text.TextUtils;

import com.antimage.baseframe.App;
import com.antimage.baseframe.event.ForceLogoutStatusEvent;
import com.antimage.baseframe.event.RxBus;
import com.antimage.baseframe.utils.android.NetworkUtils;
import com.antimage.baseframe.utils.android.ToastUtils;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.functions.Consumer;
import retrofit2.HttpException;

/**
 * Created by xuyuming on 2018/10/17.
 */

public class ApiExceptionHelper {

    private static final int FORCE_LOGOUT = 10010;
    private Consumer mConsumer;

    private ApiExceptionHelper() {}

    private ApiExceptionHelper(Consumer consumer) {
        mConsumer = consumer;
    }

    public static ApiExceptionHelper get() {
        return new ApiExceptionHelper();
    }

    public static ApiExceptionHelper get(Consumer consumer) {
        return new ApiExceptionHelper(consumer);
    }

    public void process(Throwable t) {
        if (t instanceof ApiException) {
            ApiException e = (ApiException) t;
            if (FORCE_LOGOUT == e.getErrorCode()) {
                RxBus.post(new ForceLogoutStatusEvent(ForceLogoutStatusEvent.STATUS_TOKEN_FAIL));
            } else {
                try {
                    if (mConsumer != null) {
                        mConsumer.accept(t);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        } else if (t instanceof HttpException) {
            HttpException e = (HttpException) t;
            String msg = e.message();
            if (TextUtils.isEmpty(msg)) {
                ToastUtils.toastShort("网络异常，请稍后再试");
            } else {
                ToastUtils.toastShort(msg);
            }
        } else if (t instanceof JsonParseException
                || t instanceof ParseException
                || t instanceof JSONException) {
            ToastUtils.toastShort("数据解析异常");
            t.printStackTrace();
        } else if (t instanceof IOException) {
            if (t instanceof UnknownHostException) {
                ToastUtils.toastShort("无网络，请稍后再试");
            } else if (t instanceof SocketTimeoutException) {
                ToastUtils.toastShort("连接超时");
            }
        } else {
            ToastUtils.toastShort("网络异常，请稍后再试");
            t.printStackTrace();
        }
    }
}
