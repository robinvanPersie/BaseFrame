package com.antimage.baseframe.ui.fragment.sub;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.antimage.baseframe.R;
import com.antimage.baseframe.databinding.FragmentLrSubBinding;
import com.github.jdsjlzx.ItemDecoration.LuSpacesItemDecoration;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuyuming on 2018/10/11.
 */

public class StaggerSubFragment extends Fragment {

    FragmentLrSubBinding binding;

    List<String> list;

    int dip180, dip120;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lr_sub, container, false);

        dip180 = getResources().getDimensionPixelSize(R.dimen.dp_60) * 3;
        dip120 = getResources().getDimensionPixelSize(R.dimen.dp_40) * 3;
        list = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            list.add("item: " + i);
        }
        LuSpacesItemDecoration itemDecoration = LuSpacesItemDecoration.newInstance(3, 3, 3, 0xff00bbdd);
        binding.lRecyclerView.addItemDecoration(itemDecoration);

        binding.lRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        binding.lRecyclerView.setAdapter(new LuRecyclerViewAdapter(new StaggerAdapter()));
        return binding.getRoot();
    }

    class StaggerAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.card_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            int r = (int) (Math.random() * dip180);
            int h = r < dip120 ? dip120 : r;

            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            params.height = h;
            holder.itemView.setLayoutParams(params);
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
