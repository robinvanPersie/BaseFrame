package com.antimage.baseframe.ui.fragment.sub;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.antimage.baseframe.R;
import com.antimage.baseframe.databinding.FragmentLrSubBinding;
import com.antimage.baseframe.databinding.HeaderFooterViewBinding;
import com.github.jdsjlzx.ItemDecoration.LuGridItemDecoration;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuyuming on 2018/10/11.
 */

public class GridSubFragment extends Fragment {

    private FragmentLrSubBinding binding;

    Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lr_sub, container, false);


        List<String> list = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            list.add("item: " + i);
        }
        LuGridItemDecoration decoration = new LuGridItemDecoration.Builder(getContext())
                .setHorizontal(3f)
                .setVertical(3f)
                .setColor(0xff00bbdd)
                .build();
        binding.lRecyclerView.addItemDecoration(decoration);
        binding.lRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        LuRecyclerViewAdapter adapter = new LuRecyclerViewAdapter(new GridAdapter(list));
        HeaderFooterViewBinding headerBinding = DataBindingUtil.bind(getLayoutInflater().inflate(R.layout.header_footer_view, null));
        adapter.addHeaderView(headerBinding.getRoot());
        binding.lRecyclerView.setAdapter(adapter);
        binding.lRecyclerView.setLoadMoreEnabled(false);
        binding.lRecyclerView.setOnLoadMoreListener(() -> {
            handler.postDelayed(() -> {
                binding.lRecyclerView.refreshComplete(10);
            }, 2000);
        });

        headerBinding.hfIv.setOnClickListener(v -> {
            float y = binding.lRecyclerView.findViewHolderForAdapterPosition(0).itemView.getY();
            Log.w(getClass().getSimpleName(), "y: " + y);
            Log.d(getClass().getSimpleName(), "itemView height: " + binding.lRecyclerView.findViewHolderForAdapterPosition(0).itemView.getHeight());
            float y3 = binding.lRecyclerView.findViewHolderForAdapterPosition(2).itemView.getY();
            Log.w(getClass().getSimpleName(), "y3: " + y3);
        });
        return binding.getRoot();
    }

    private class GridAdapter extends RecyclerView.Adapter {

        List<String> list;
        public GridAdapter(List<String> list) {
            this.list = list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.grid_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            holder.itemView.setOnClickListener(v -> {});
        }

        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
