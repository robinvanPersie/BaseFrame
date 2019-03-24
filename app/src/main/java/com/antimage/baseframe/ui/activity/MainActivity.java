package com.antimage.baseframe.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.antimage.baseframe.R;
import com.antimage.baseframe.databinding.ActivityMainBinding;
import com.antimage.baseframe.presenter.MainPresenter;
import com.antimage.baseframe.ui.base.LifeCycleActivity;
import com.antimage.baseframe.ui.view.MainView;

/**
 * Created by xuyuming on 2019/3/24.
 */

public class MainActivity extends LifeCycleActivity<MainPresenter> implements MainView {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.rg.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {}
        });
    }

    @Override
    protected Toolbar buildToolbar() {
        return binding.toolbarLayout.toolbar;
    }

    @Override
    protected boolean showBackBtn() {
        return false;
    }

    private class MainPagerAdapter extends PagerAdapter {

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            switch (position) {

            }
            return null;
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
