package com.antimage.baseframe.event;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by xuyuming on 2018/10/25.
 */

public class ForceLogoutStatusEvent {

    public static final int STATUS_TOKEN_FAIL = 0;
    public static final int STATUS_MODIFY_PWD = 1;

    private int status = STATUS_TOKEN_FAIL;

    public ForceLogoutStatusEvent(@Status int status) {
        this.status = status;
    }

    @IntDef({STATUS_TOKEN_FAIL, STATUS_MODIFY_PWD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Status {}

    public int getStatus() {
        return status;
    }
}
