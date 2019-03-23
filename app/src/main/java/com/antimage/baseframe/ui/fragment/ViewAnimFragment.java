package com.antimage.baseframe.ui.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.PathInterpolator;

import com.antimage.baseframe.R;
import com.antimage.baseframe.databinding.FragmentViewAnimBinding;
import com.antimage.baseframe.image.ImageLoader;
import com.antimage.baseframe.ui.activity.AnimOpenActivity;

/**
 * Created by xuyuming on 2018/9/29.
 */

public class ViewAnimFragment extends Fragment {

    private FragmentViewAnimBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_anim, container, false);
        mBinding.toolBarId.setTitle("va标题");
        ImageLoader.loadImage(getContext(), R.mipmap.ic_launcher, mBinding.ivView);
        mBinding.btn1.setOnClickListener(v -> {
            int centerX = mBinding.ivView.getWidth() / 2;
            int centerY = mBinding.ivView.getHeight() / 2;
            int maxRadius = Math.max(mBinding.ivView.getWidth(), mBinding.ivView.getHeight());

            Animator anim = ViewAnimationUtils.createCircularReveal(mBinding.ivView, centerX, centerY, maxRadius, 0);
            anim.setDuration(1000);
            anim.start();

        });
        mBinding.btn2.setOnClickListener(v -> {
//            ActivityOptionsCompat compat = ActivityOptionsCompat.makeCustomAnimation(getContext(), 0, 0);
//            ActivityCompat.startActivity(getContext(), new Intent(getContext(), AnimOpenActivity.class), compat.toBundle());

//            ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(mBinding.ivView,
//                    mBinding.ivView.getWidth() / 2, mBinding.ivView.getHeight() / 2, 0, 0);
//            ActivityCompat.startActivity(getActivity(), new Intent(getActivity(), AnimOpenActivity.class), compat.toBundle());
            ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), v, "shareName");
            ActivityCompat.startActivity(getContext(), new Intent(getActivity(), AnimOpenActivity.class), compat.toBundle());
        });

        mBinding.btn3.setOnClickListener(v -> {
            Path path = new Path();
            path.lineTo(100, 100);
            ObjectAnimator animator = ObjectAnimator.ofFloat(v, View.X, View.Y, path);
            animator.setInterpolator(new PathInterpolator(0.4f, 0f, 1f, 1f));
            animator.start();
        });

        mBinding.btn4.setOnClickListener(v -> {

        });

        mBinding.btn5.setOnClickListener(v -> {
        });
        return mBinding.getRoot();
    }
}
