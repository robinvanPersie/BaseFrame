package com.antimage.baseframe.ui.fragment.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.antimage.baseframe.R;
import com.antimage.baseframe.utils.android.FragmentUtils;

import java.lang.reflect.Field;

import timber.log.Timber;

/**
 * Created by xuyuming on 2018/10/16.
 */

public class LoadingDialogFragment extends DialogFragment {

    public static final String MSG_ID = "msgId";
    public static final String MSG_STR = "msgStr";

    TextView msgView;

    public static LoadingDialogFragment newInstance(@StringRes int textId) {
        Bundle arguments = new Bundle();
        arguments.putInt(MSG_ID, textId);
        LoadingDialogFragment fragment = new LoadingDialogFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    public static LoadingDialogFragment newInstance(String text) {
        Bundle arguments = new Bundle();
        arguments.putString(MSG_STR, text);
        LoadingDialogFragment fragment = new LoadingDialogFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    public LoadingDialogFragment() {

    }

    public void updateMessage(String msg) {
        msgView.setText(msg);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        if(dialog.getWindow() != null) {
            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        dialog.setCanceledOnTouchOutside(false);
        Timber.w("onCreateDialog()");
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loading, container, false);
        int resId = -1;
        String message = null;
        if (getArguments() != null) {
            Bundle arguments = getArguments();
            resId = arguments.getInt(MSG_ID, -1);
            message = arguments.getString(MSG_STR, "");
        }
        msgView = view.findViewById(R.id.text_message);
        if (resId != -1) {
            msgView.setText(resId);
        } else {
            msgView.setText(message);
        }
        Timber.w("onCreateView()");
        return view;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        Class clazz = DialogFragment.class;
        try {
            Field mDismissed = clazz.getDeclaredField("mDismissed");
            Field mShownByMe = clazz.getDeclaredField("mShownByMe");
            mDismissed.setAccessible(true);
            mShownByMe.setAccessible(true);
            mDismissed.setBoolean(this, false);
            mShownByMe.setBoolean(this,true);
            FragmentUtils.add(manager,0,this,tag,false);
        } catch (Exception e) {
            super.show(manager,tag);
        }
    }
}
