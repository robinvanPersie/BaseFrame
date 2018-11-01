package com.antimage.baseframe.ui.fragment.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.antimage.baseframe.R;
import com.antimage.baseframe.databinding.FragmentDialogUpdateBinding;
import com.antimage.baseframe.ui.base.BaseDialogFragment;

/**
 * Created by xuyuming on 2018/10/24.
 */

public class AppUpdateDialogFragment extends BaseDialogFragment {

    private FragmentDialogUpdateBinding binding;
    private Builder builder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dialog_update, container, false);
        binding.titleTv.setText(builder.title);
        binding.contentTv.setText(builder.content);
        binding.cancelBtn.setText(builder.negativeText);
        binding.confirmBtn.setText(builder.positiveText);
        binding.cancelBtn.setOnClickListener(v -> {
            if (builder.negativeListener != null) {
                builder.negativeListener.onClick(v);
            }
            dismiss();
        });
        binding.confirmBtn.setOnClickListener(v -> {
            if (builder.positiveListener != null) {
                builder.positiveListener.onClick(v);
            }
        });
        setCancelable(builder.cancelable);
        return binding.getRoot();
    }

    @Override
    protected void initializeInjector() {}

    public static class Builder {

        private Context context;
        private String title;
        private String content;
        private String negativeText;
        private String positiveText;
        private View.OnClickListener negativeListener;
        private View.OnClickListener positiveListener;
        private boolean cancelable;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setTitle(@StringRes int titleId) {
            this.title = context.getString(titleId);
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setnegativeText(String text) {
            this.negativeText = text;
            return this;
        }

        public Builder setnegativeText(@StringRes int textId) {
            this.negativeText = context.getString(textId);
            return this;
        }

        public Builder setPositiveText(String text) {
            this.positiveText = text;
            return this;
        }

        public Builder setPositiveText(@StringRes int textId) {
            this.positiveText = context.getString(textId);
            return this;
        }

        public Builder setnegativeListener(View.OnClickListener listener) {
            this.negativeListener = listener;
            return this;
        }

        public Builder setPositiveListener(View.OnClickListener listener) {
            this.positiveListener = listener;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public AppUpdateDialogFragment build() {
            AppUpdateDialogFragment fragment = new AppUpdateDialogFragment();
            fragment.setRetainInstance(true);
            fragment.builder = this;
            return fragment;
        }
    }
}
