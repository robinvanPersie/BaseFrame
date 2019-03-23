package com.antimage.baseframe.ui.view.base;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;

/**
 * Created by xuyuming on 2018/10/19.
 */

public interface IToolbar {

    /**
     * 是否设置好了toolbar (setSupportActionBar())
     */
    boolean isToolbarReady();

    void setTitle(CharSequence title);

    void setTitle(@StringRes int titleId);

    void setBackgroundColor(@ColorRes int resId);

    void setBackgroundDrawable(@DrawableRes int drawableId);

    /**
     * 是否显示android.R.id.home
     */
    void showHomeAsUpEnable(boolean enable);

    CharSequence getTitle();

    Toolbar getToolbar();


    /**
     * 界面没有导航栏的默认实现类
     */
    class NoneToolbarImpl implements IToolbar {

        @Override
        public boolean isToolbarReady() {
            return false;
        }

        @Override
        public void setTitle(CharSequence title) {

        }

        @Override
        public void setTitle(int titleId) {

        }

        @Override
        public void setBackgroundColor(int resId) {

        }

        @Override
        public void setBackgroundDrawable(int drawableId) {

        }

        @Override
        public void showHomeAsUpEnable(boolean enable) {

        }

        @Override
        public CharSequence getTitle() {
            return "";
        }

        @Override
        public Toolbar getToolbar() {
            return null;
        }
    }
}
