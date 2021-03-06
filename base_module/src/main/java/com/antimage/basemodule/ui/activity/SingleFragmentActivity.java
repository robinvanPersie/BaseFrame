package com.antimage.basemodule.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.antimage.basemodule.R;
import com.antimage.basemodule.databinding.BaseActivityFragmentBinding;
import com.antimage.basemodule.ui.view.IToolbar;
import com.antimage.basemodule.utils.android.FragmentUtils;

/**
 * Created by xuyuming on 2018/10/19.
 */

public abstract class SingleFragmentActivity extends BaseActivity implements IToolbar {

    private BaseActivityFragmentBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.base_activity_fragment);
        setSupportActionBar(mBinding.toolBarId);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (createDefaultFragment()) {
            FragmentUtils.replace(getSupportFragmentManager(), getFragmentContainerId(), createNewFragment());
        }
    }

    @Override
    public boolean isToolbarReady() {
        return getSupportActionBar() != null;
    }

    @Override
    public void setBackgroundColor(int resId) {
        mBinding.toolBarId.setBackgroundResource(resId);
    }

    @Override
    public void setBackgroundDrawable(int drawableId) {
        mBinding.toolBarId.setBackgroundResource(drawableId);
    }

    @Override
    public void showHomeAsUpEnable(boolean enable) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(enable);
    }

    @Override
    public Toolbar getToolbar() {
        return mBinding.toolBarId;
    }

    /**
     * 创建默认fragment
     */
    protected boolean createDefaultFragment() {
        return true;
    }

    public int getFragmentContainerId() {
        return R.id.container_id;
    }

    /**
     * 默认fragment
     */
    protected abstract Fragment createNewFragment();
}
