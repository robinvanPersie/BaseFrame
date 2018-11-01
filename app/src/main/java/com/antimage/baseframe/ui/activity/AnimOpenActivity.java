package com.antimage.baseframe.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Slide;

import com.antimage.baseframe.R;

/**
 * Created by xuyuming on 2018/9/29.
 */

public class AnimOpenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_anim_open);
        getWindow().setEnterTransition(new Slide());
        getWindow().getDecorView().setBackgroundColor(0xff00bbdd);
    }

    @Override
    public void onBackPressed() {
        getWindow().setExitTransition(new Fade());
        super.onBackPressed();
//        ActivityCompat.finishAfterTransition(this);
    }
}
