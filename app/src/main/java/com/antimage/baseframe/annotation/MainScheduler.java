package com.antimage.baseframe.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by xuyuming on 2018/10/15.
 */

@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface MainScheduler {
}
