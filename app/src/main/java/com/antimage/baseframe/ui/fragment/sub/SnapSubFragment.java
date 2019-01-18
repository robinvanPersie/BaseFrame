package com.antimage.baseframe.ui.fragment.sub;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.antimage.baseframe.R;
import com.antimage.baseframe.databinding.BottomSheetLayoutBinding;
import com.antimage.baseframe.databinding.FragmentLrSubBinding;
import com.antimage.baseframe.databinding.HeaderFooterViewBinding;
import com.antimage.baseframe.databinding.ItemStringBinding;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuyuming on 2019/1/18.
 */

public class SnapSubFragment extends Fragment {

    FragmentLrSubBinding binding;

    Handler handler = new Handler();
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lr_sub, container, false);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add("item: " + i);
        }
        DataAdapter adapter = new DataAdapter(list);
        LuRecyclerViewAdapter lAdapter = new LuRecyclerViewAdapter(adapter);

        binding.lRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.lRecyclerView.setAdapter(lAdapter);
        binding.lRecyclerView.setLoadMoreEnabled(true);
        binding.lRecyclerView.setOnLoadMoreListener(() -> {
            handler.postDelayed(() -> {
//                binding.lRecyclerView.refreshComplete(10);
                binding.lRecyclerView.setNoMore(true);
            }, 2000);
        });

        binding.lRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        binding.lRecyclerView.setFooterViewColor(android.R.color.black, android.R.color.black, android.R.color.white);
        binding.lRecyclerView.setFooterViewHint("拼命加载中","已经全部为你呈现了","网络不给力啊，点击再试一次吧");

        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(binding.lRecyclerView);
        return binding.getRoot();
    }

    private class DataAdapter extends RecyclerView.Adapter {

        private List<String> list;
        public DataAdapter(List<String> list) {
            this.list = list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_string, parent, false);
            ViewGroup.LayoutParams vp = view.getLayoutParams();
            vp.width = vp.width - 100;
            view.setLayoutParams(vp);
            view.setPadding(100, view.getPaddingTop(), 100, view.getPaddingBottom());
            return new DataAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            DataAdapter.ViewHolder viewHolder = (DataAdapter.ViewHolder) holder;
            ItemStringBinding bd = (ItemStringBinding) viewHolder.binding;
            bd.itemTv.setText(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ViewDataBinding binding;

            public ViewHolder(View itemView) {
                super(itemView);
                binding = DataBindingUtil.bind(itemView);
            }
        }
    }
}
