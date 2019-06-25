package com.antimage.independentmodule.test;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.alibaba.android.arouter.launcher.ARouter;
import com.antimage.basemodule.ui.activity.LifeCycleActivity;
import com.antimage.independentmodule.R;
import com.antimage.independentmodule.databinding.IdpdActivityTestBinding;
import com.antimage.independentmodule.di.component.DaggerEasyActivityComponent;

public class TestActivity extends LifeCycleActivity<TestPresenter> implements TestView{

    private IdpdActivityTestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.idpd_activity_test);
        binding.tv.setOnClickListener(v -> {
            getPresenter().test();
        });
        binding.btnGoRt.setOnClickListener(v -> {
            ARouter.getInstance().build("/rt/routerTest")
                    .withString("str", "independentModule")
                    .navigation();
        });
    }

    @Override
    protected void initializeInjector() {
        DaggerEasyActivityComponent.builder()
                .activityModule(getActivityModule())
                .appComponent(getAppComponent())
                .build().inject(this);
    }

    @Override
    protected Toolbar buildToolbar() {
        return binding.toolbarId.toolbar;
    }

    @Override
    public void success(String str) {
        binding.tv.setText(str);
    }
}
