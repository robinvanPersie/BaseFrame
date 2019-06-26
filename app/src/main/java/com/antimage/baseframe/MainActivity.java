package com.antimage.baseframe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.antimage.basemodule.ui.activity.BaseActivity;

/**
 * Created by xuyuming on 2019/6/25.
 */
@Route(path = "/app/mainTest")
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-2, -2);
        params.topMargin = 600;
        textView.setText("app module");
        textView.setBackgroundColor(0xff00bbdd);
        setContentView(textView, params);
        textView.setOnClickListener(v -> {
            ARouter.getInstance().build("/rt/routerTest")
                    .withString("str", "from main").navigation();
        });
    }
}
