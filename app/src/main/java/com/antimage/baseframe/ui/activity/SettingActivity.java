package com.antimage.baseframe.ui.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.antimage.baseframe.ui.base.BaseActivity;
import com.antimage.baseframe.ui.fragment.SettingFragment;


/**
 * Created by xuyuming on 2018/11/15.
 */

public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(android.R.id.content, new SettingFragment());
        transaction.commit();
    }
}
