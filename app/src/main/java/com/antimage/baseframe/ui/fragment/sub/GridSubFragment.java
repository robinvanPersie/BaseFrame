package com.antimage.baseframe.ui.fragment.sub;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
import java.util.Collections;
import java.util.List;

import timber.log.Timber;

/**
 * Created by xuyuming on 2018/10/11.
 */

public class GridSubFragment extends Fragment {

    private FragmentLrSubBinding binding;

    Handler handler = new Handler();

    List<String> list;
    GridAdapter gridAdapter;
    LuRecyclerViewAdapter luRecyclerViewAdapter;
    ItemTouchHelper itemTouchHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lr_sub, container, false);


        list = new ArrayList<>();
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
        gridAdapter = new GridAdapter(list);
        luRecyclerViewAdapter = new LuRecyclerViewAdapter(gridAdapter);
        HeaderFooterViewBinding headerBinding = DataBindingUtil.bind(getLayoutInflater().inflate(R.layout.header_footer_view, null));
        luRecyclerViewAdapter.addHeaderView(headerBinding.getRoot());
        binding.lRecyclerView.setAdapter(luRecyclerViewAdapter);
        binding.lRecyclerView.setLoadMoreEnabled(false);
        binding.lRecyclerView.setOnLoadMoreListener(() -> {
            handler.postDelayed(() -> {
                binding.lRecyclerView.refreshComplete(10);
            }, 2000);
        });

        itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(binding.lRecyclerView);

        headerBinding.hfIv.setOnClickListener(v -> {
            float y = binding.lRecyclerView.findViewHolderForAdapterPosition(0).itemView.getY();
            Log.w(getClass().getSimpleName(), "y: " + y);
            Log.d(getClass().getSimpleName(), "itemView height: " + binding.lRecyclerView.findViewHolderForAdapterPosition(0).itemView.getHeight());
            float y3 = binding.lRecyclerView.findViewHolderForAdapterPosition(2).itemView.getY();
            Log.w(getClass().getSimpleName(), "y3: " + y3);
        });
        return binding.getRoot();
    }

    private ItemTouchHelper.Callback itemTouchCallback = new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN
                        | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                int swipeFlag = 0;
                return makeMovementFlags(dragFlag, swipeFlag);
            } else {
                int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlag = 0;
                return makeMovementFlags(dragFlag, swipeFlag);
            }
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            int fromIndex = viewHolder.getAdapterPosition();
            int toIndex = target.getAdapterPosition();
            Timber.w("anti-mage, fromIndex:" + fromIndex + " , count: " + luRecyclerViewAdapter.getItemCount());
            Timber.w("anti-mage, toIndex:" + toIndex + " , gridCount: " + gridAdapter.getItemCount());

            int headerCount = luRecyclerViewAdapter.getHeaderViewsCount();
            int footerCount = luRecyclerViewAdapter.getFooterViewsCount();

            if (headerCount > 0 && (fromIndex == 0 || toIndex == 0)) return false;
            if (footerCount > 0
                    && (fromIndex - headerCount > list.size() - 1 || toIndex - headerCount > list.size() - 1)) return false;


            if (fromIndex < toIndex) {
                for (int i = fromIndex - headerCount; i < toIndex - headerCount; i++) {
                    Collections.swap(list, i, i + 1);
                }
            } else {
                for (int i = fromIndex - headerCount; i > toIndex - headerCount; i--) {
                    Collections.swap(list, i, i - 1);
                }
            }
            luRecyclerViewAdapter.notifyItemMoved(fromIndex, toIndex);
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        }


    };

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
//            holder.itemView.setOnLongClickListener(v -> {
//                itemTouchHelper.startDrag(holder);
//                return false;
//            });
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
