package com.antimage.baseframe.core;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by xuyuming on 2018/11/14.
 */

public class G {

    // host
    public static final int ENVIRONMENT_RELEASE = 0;
    public static final int ENVIRONMENT_TEST = 1;

    @IntDef({ENVIRONMENT_RELEASE, ENVIRONMENT_TEST})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Environments {}

}
