package com.antimage.baseframe.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.antimage.baseframe.R;
import com.antimage.baseframe.databinding.FragmentRecyclerBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuyuming on 2018/10/9.
 */

public class RecyclerFragment extends Fragment {

    FragmentRecyclerBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recycler, container, false);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("item: " + i);
        }
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerView.setAdapter(new Adapter(list));
        return mBinding.getRoot();
    }


    public class Adapter extends RecyclerView.Adapter {

        private List<String> list;

        public Adapter(List<String> list) {
            this.list = list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView textView = new TextView(getContext());
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(-1, 210);
            textView.setLayoutParams(params);
            TypedValue typedValue = new TypedValue();
            getActivity().getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
            textView.setBackgroundResource(typedValue.resourceId); // 增加列表默认点击效果
            textView.setClickable(true);
            return new ViewHolder(textView);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            TextView textView = (TextView) holder.itemView;
            textView.setText(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

}
