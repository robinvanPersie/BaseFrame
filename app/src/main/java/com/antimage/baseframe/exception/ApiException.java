package com.antimage.baseframe.exception;

/**
 * Created by xuyuming on 2018/10/16.
 */

public class ApiException extends RuntimeException {

    private int code;

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getErrorCode() {
        return this.code;
    }
}
