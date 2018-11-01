package com.antimage.baseframe.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;

import com.antimage.baseframe.R;
import com.antimage.baseframe.databinding.FragmentHomeBinding;

/**
 * Created by xuyuming on 2018/9/28.
 */

public class HomeFragment extends Fragment {

    private FragmentHomeBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        mBinding.toolBarId.setTitle("标题");

//        mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText("tab 1"));
//        mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText("tab 2"));

        mBinding.faBtn.setOnClickListener(v -> {});
        initDraw();
        return mBinding.getRoot();
    }

    private void initDraw() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), mBinding.drawerLayout,
                mBinding.toolBarId, R.string.open_toggle, R.string.close_toggle);
        mBinding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        for (int i = 0; i < mBinding.toolBarId.getChildCount(); i++) {

            Toolbar.LayoutParams lp = (Toolbar.LayoutParams) mBinding.toolBarId.getChildAt(i).getLayoutParams();
            lp.gravity = Gravity.CENTER_VERTICAL;
            mBinding.toolBarId.getChildAt(i).setLayoutParams(lp);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
}
