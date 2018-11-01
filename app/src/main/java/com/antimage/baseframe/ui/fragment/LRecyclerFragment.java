package com.antimage.baseframe.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.antimage.baseframe.R;
import com.antimage.baseframe.databinding.FragmentLrecyclerBinding;
import com.antimage.baseframe.ui.fragment.sub.GridSubFragment;
import com.antimage.baseframe.ui.fragment.sub.LinearSubFragment;
import com.antimage.baseframe.ui.fragment.sub.StaggerSubFragment;

/**
 * Created by xuyuming on 2018/10/10.
 */

public class LRecyclerFragment extends Fragment {

    FragmentLrecyclerBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lrecycler, container, false);


        binding.tabLayout.addTab(binding.tabLayout.newTab());
        binding.tabLayout.addTab(binding.tabLayout.newTab());
        binding.tabLayout.addTab(binding.tabLayout.newTab());

//        binding.viewPager.setAdapter(new PagerAdapter(getActivity().getSupportFragmentManager()));
        binding.viewPager.setAdapter(new PagerAdapter(getChildFragmentManager()));
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                binding.toolBarId.setTitle(titles[position]);
                binding.collapseLayout.setTitle(titles[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        binding.appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
//            Log.w(getClass().getSimpleName(), "offset: " + verticalOffset + " , totalScrollRange: " + appBarLayout.getTotalScrollRange());
            int offset = Math.abs(verticalOffset);
            int halfRange = appBarLayout.getTotalScrollRange() / 2;
            if (offset <= halfRange) {
                float a = 1 - (offset * 1f / halfRange);
                Log.d(getClass().getSimpleName(), "image a: " + a);
                binding.imageView.setAlpha(a);
            } else {
                float a = (offset - halfRange) * 1f / halfRange;
                Log.w(getClass().getSimpleName(), "toolbar a: " + a);
                binding.toolBarId.setAlpha(a);
            }

        });
        return binding.getRoot();
    }

    String[] titles = {"linear", "grid", "stagger", "hint", "hint", "hint", "hint", "hint", "hint"};
    Fragment[] fragments = {new LinearSubFragment(), new GridSubFragment(),
            new StaggerSubFragment(), new LinearSubFragment(), new LinearSubFragment(), new LinearSubFragment(),
            new LinearSubFragment(), new LinearSubFragment(), new LinearSubFragment() };

    class PagerAdapter extends FragmentPagerAdapter {



        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = fragments[position];
            return fragment;
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }
}
