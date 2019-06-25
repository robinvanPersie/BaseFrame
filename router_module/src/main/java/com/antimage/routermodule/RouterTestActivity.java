package com.antimage.routermodule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.antimage.basemodule.ui.activity.BaseActivity;

/**
 * Created by xuyuming on 2019/6/24.
 */
@Route(path = "/rt/routerTest")
public class RouterTestActivity extends BaseActivity {

    @Autowired
    String str;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-2, -2);
        params.gravity = Gravity.CENTER;
        setContentView(textView, params);
        ARouter.getInstance().inject(this);

        textView.setText("上一个页面来自：" + str);
    }
}
