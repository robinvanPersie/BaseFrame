package com.antimage.basemodule.exception;

import com.antimage.basemodule.utils.android.ToastUtils;

import io.reactivex.functions.Consumer;

/**
 * Created by xuyuming on 2018/10/17.
 */

public class ApiExceptionHelper {

    static final ApiExceptionProcess PROCESS = new ApiExceptionProcess();
    static final Consumer<ApiException> DEFAULT_CONSUMER = e -> ToastUtils.toastShort(e.getMessage());

    private ApiExceptionHelper() {}

    public static ApiExceptionProcess normal() {
        return PROCESS.setConsumer(DEFAULT_CONSUMER);
    }

    public static ApiExceptionProcess normal(Consumer<ApiException> consumer) {
        return PROCESS.setConsumer(consumer);
    }
}
