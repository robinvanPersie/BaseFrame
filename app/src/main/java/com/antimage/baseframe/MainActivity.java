package com.antimage.baseframe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.antimage.basemodule.ui.activity.BaseActivity;

/**
 * Created by xuyuming on 2019/6/25.
 */

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-2, -2);
        params.topMargin = TypedValue.
    }
}
