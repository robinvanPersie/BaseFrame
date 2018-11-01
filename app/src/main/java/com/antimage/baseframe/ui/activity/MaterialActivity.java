package com.antimage.baseframe.ui.activity;

import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.MenuItem;

import com.antimage.baseframe.R;
import com.antimage.baseframe.databinding.ActivityMaterialBinding;
import com.antimage.baseframe.ui.base.BaseActivity;
import com.antimage.baseframe.ui.fragment.DaoFragment;
import com.antimage.baseframe.ui.fragment.HomeFragment;
import com.antimage.baseframe.ui.fragment.LRecyclerFragment;
import com.antimage.baseframe.ui.fragment.RecyclerFragment;
import com.antimage.baseframe.ui.fragment.ViewAnimFragment;
import com.antimage.baseframe.utils.android.BottomNavigationHelper;
import com.antimage.baseframe.utils.android.FragmentUtils;

/**
 * Created by xuyuming on 2018/9/28.
 */

public class MaterialActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMaterialBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_material);

        BottomNavigationHelper.disableShiftMode(mBinding.bottomNav);

        MenuItem menuItem = mBinding.bottomNav.getMenu().findItem(R.id.home_item);
        Drawable icon = menuItem.getIcon();
        if (icon != null) {
            Drawable.ConstantState state = icon.getConstantState();
            icon = DrawableCompat.wrap(state == null ? icon : state.newDrawable()).mutate();
            DrawableCompat.setTintMode(icon, PorterDuff.Mode.DST);
        }
        menuItem.setIcon(icon);

        FragmentUtils.replace(getSupportFragmentManager(), R.id.container_id, new HomeFragment());

//        mBinding.bottomNav.setItemIconTintList(null);
        mBinding.bottomNav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home_item:
                    FragmentUtils.replace(getSupportFragmentManager(), R.id.container_id, new HomeFragment());
                    return true;
                case R.id.anim_item:
                    FragmentUtils.replace(getSupportFragmentManager(), R.id.container_id, new ViewAnimFragment());
                    return true;
                case R.id.other_item:
                    FragmentUtils.replace(getSupportFragmentManager(), R.id.container_id, new RecyclerFragment());
                    return true;
                case R.id.lrecycler_item:
                    FragmentUtils.replace(getSupportFragmentManager(), R.id.container_id, new LRecyclerFragment());
                    return true;
                case R.id.dao_item:
                    FragmentUtils.replace(getSupportFragmentManager(), R.id.container_id, new DaoFragment());
                    return true;
            }
            return false;
        });
        mBinding.bottomNav.getMenu().findItem(R.id.home_item).setChecked(true);
        mBinding.bottomNav.setOnNavigationItemReselectedListener(item -> {

        });

//        mBinding.viewPager.setAdapter(new Adapter(getSupportFragmentManager()));
    }

    private class Adapter extends FragmentPagerAdapter {

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new HomeFragment();
                case 1:
                    return new ViewAnimFragment();
                case 2:
                    return new RecyclerFragment();
                case 3:
                    return new LRecyclerFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
