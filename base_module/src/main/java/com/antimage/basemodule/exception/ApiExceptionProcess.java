package com.antimage.basemodule.exception;

import android.text.TextUtils;

import com.antimage.basemodule.event.ForceLogoutStatusEvent;
import com.antimage.basemodule.event.RxBus;
import com.antimage.basemodule.utils.android.ToastUtils;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.functions.Consumer;
import retrofit2.HttpException;
import timber.log.Timber;

/**
 * Created by xuyuming on 2019/6/13.
 */

public class ApiExceptionProcess implements ExceptionProcess {

    private static final Consumer<ApiException> DEFAULT_CONSUMER = e -> ToastUtils.toastShort(e.getMessage());

    private static final int FORCE_LOGOUT = 10010; // 强制退出，可用于token过期或另一台设备登录
    private Consumer<ApiException> mConsumer;

    public ApiExceptionProcess() {
        this(DEFAULT_CONSUMER);
    }

    public ApiExceptionProcess(Consumer<ApiException> consumer) {
        mConsumer = consumer;
    }

    @Override
    public void process(Throwable t) {
        if (t instanceof ApiException) {
            ApiException e = (ApiException) t;
            if (FORCE_LOGOUT == e.getErrorCode()) {
                RxBus.post(new ForceLogoutStatusEvent(ForceLogoutStatusEvent.STATUS_TOKEN_FAIL));
            } else {
                try {
                    if (mConsumer != null) {
                        mConsumer.accept(e);
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
            Timber.e("else exception");
            ToastUtils.toastShort("网络异常，请稍后再试");
            t.printStackTrace();
        }
    }
}
