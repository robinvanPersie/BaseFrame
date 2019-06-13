package com.antimage.basemodule.exception;

/**
 * Created by xuyuming on 2018/10/16.
 */

public class ApiException extends RuntimeException {

    private static final int DEFAULT_CODE = -1;

    private int code;

    public ApiException(String message) {
        super(message);
        this.code = DEFAULT_CODE;
    }

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getErrorCode() {
        return this.code;
    }
}
