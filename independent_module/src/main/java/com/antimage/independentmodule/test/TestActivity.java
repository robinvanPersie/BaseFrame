package com.antimage.independentmodule.test;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.antimage.basemodule.ui.activity.LifeCycleActivity;
import com.antimage.independentmodule.R;
import com.antimage.independentmodule.di.component.DaggerEasyActivityComponent;

public class TestActivity extends LifeCycleActivity<TestPresenter> implements TestView{

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.idpd_activity_login);
        textView = findViewById(R.id.tv);
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
        return null;
    }

    @Override
    public void success(String str) {
        textView.setText(str);
    }
}
