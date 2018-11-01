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
 * Created by xuyuming on 2018/10/11.
 * LRecyclerFragment的tab
 */

public class LinearSubFragment extends Fragment {

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

        binding.lRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.lRecyclerView.setAdapter(lAdapter);
        binding.lRecyclerView.setLoadMoreEnabled(true);
        binding.lRecyclerView.setOnLoadMoreListener(() -> {
            handler.postDelayed(() -> {
//                binding.lRecyclerView.refreshComplete(10);
                binding.lRecyclerView.setNoMore(true);
            }, 2000);
        });

        HeaderFooterViewBinding headerBinding = DataBindingUtil.bind(getLayoutInflater().inflate(R.layout.header_footer_view, null));
        lAdapter.addHeaderView(headerBinding.getRoot());

        headerBinding.hfIv.setOnClickListener(v -> {
            BottomSheetDialog sheetDialog = new BottomSheetDialog(getContext());
                    sheetDialog.setTitle("tai tou");
            BottomSheetLayoutBinding sheetBinding = DataBindingUtil.bind(getLayoutInflater().inflate(R.layout.bottom_sheet_layout, null));
            sheetDialog.setContentView(sheetBinding.getRoot());
            sheetBinding.listView.setAdapter(new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_list_item_1, android.R.id.text1, new String[]{"1", "2", "3", "4", "5"}));
            sheetBinding.listView.setOnItemClickListener((parent, view, position, id) -> {
                sheetDialog.dismiss();
            });
            sheetDialog.show();
        });

//        ViewDataBinding footerBinding = DataBindingUtil.bind(getLayoutInflater().inflate(R.layout.header_footer_view, null));
//        lAdapter.addFooterView(footerBinding.getRoot());

        binding.lRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        binding.lRecyclerView.setFooterViewColor(android.R.color.black, android.R.color.black, android.R.color.white);
        binding.lRecyclerView.setFooterViewHint("拼命加载中","已经全部为你呈现了","网络不给力啊，点击再试一次吧");


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
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolder viewHolder = (ViewHolder) holder;
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
