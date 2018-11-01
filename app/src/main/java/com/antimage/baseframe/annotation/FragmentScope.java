package com.antimage.baseframe.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by xuyuming on 2018/10/15.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface FragmentScope {
}
