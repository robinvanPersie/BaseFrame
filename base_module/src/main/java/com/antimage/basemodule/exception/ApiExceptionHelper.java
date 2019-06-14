package com.antimage.basemodule.exception;

import com.antimage.basemodule.utils.android.ToastUtils;

import io.reactivex.functions.Consumer;

/**
 * Created by xuyuming on 2018/10/17.
 */

public class ApiExceptionHelper {

    static final ApiExceptionProcess PROCESS = new ApiExceptionProcess();

    private ApiExceptionHelper() {}

    public static ApiExceptionProcess normal() {
        return PROCESS;
    }

    public static ApiExceptionProcess normal(Consumer<ApiException> consumer) {
        return new ApiExceptionProcess(consumer);
    }
}
