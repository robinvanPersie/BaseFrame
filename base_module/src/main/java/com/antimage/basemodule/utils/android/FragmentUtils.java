package com.antimage.basemodule.utils.android;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import timber.log.Timber;

/**
 * Created by xuyuming on 2018/10/16.
 */

public class FragmentUtils {

    public static void replace(FragmentManager manager, int containerId, Fragment fragment) {
        replace(manager, containerId, fragment, null, false, null, false);
    }

    public static void replace(FragmentManager manager, int containerId, Fragment fragment, String tag) {
        replace(manager, containerId, fragment, tag, false, null, false);
    }

    public static void replace(FragmentManager manager, int containerId, Fragment fragment, String tag, boolean addToBackStack, String backStackTag, boolean ignoreState) {
        if (fragment == null || fragment.isAdded()) {
            //不可用的Fragment
            Timber.e("fragment null or added");
            return;
        }
        FragmentTransaction transaction = manager.beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(backStackTag);
        }
        if (TextUtils.isEmpty(tag)) {
            transaction.replace(containerId, fragment);
        } else {
            transaction.replace(containerId, fragment, tag);
        }
        if (ignoreState) {
            transaction.commitAllowingStateLoss();
        } else {
            transaction.commit();
        }
    }

    public static void add(FragmentManager manager, int containerId, Fragment fragment, String tag) {
        add(manager, containerId, fragment, tag, false, null, false);
    }

    public static void add(FragmentManager manager, int containerId, Fragment fragment, String tag, boolean addToBackStack, String backStackTag) {
        add(manager, containerId, fragment, tag, addToBackStack, backStackTag, false);
    }

    public static void add(FragmentManager manager, int containerId, Fragment fragment, String tag, boolean ignoreState) {
        add(manager, containerId, fragment, tag, false, null, ignoreState);
    }

    /**
     *
     * @param manager        getFragmentManager()
     * @param containerId    R.id.container
     * @param fragment
     * @param tag
     * @param addToBackStack 是否拦截返回键
     * @param backStackTag
     * @param ignoreState
     */
    public static void add(FragmentManager manager, int containerId, Fragment fragment, String tag, boolean addToBackStack, String backStackTag, boolean ignoreState) {
        if (fragment == null || fragment.isAdded()) {
            // Fragment null or added
            Timber.e("fragment null or added");
            return;
        }
        FragmentTransaction transaction = manager.beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(backStackTag);
        }
        if (TextUtils.isEmpty(tag)) {
            transaction.add(containerId, fragment);
        } else {
            transaction.add(containerId, fragment, tag);
        }
        if (ignoreState) {
            transaction.commitAllowingStateLoss();
        } else {
            transaction.commit();
        }
    }
}
