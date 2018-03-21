package com.caijia.widget.tablayout;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 对RecyclerView.Adapter包装点击事件
 * Created by cai.jia on 2017/5/17 0017
 */

class RecyclerAdapterWrapper<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private RecyclerView.Adapter<VH> adapter;
    private OnTabClickListener tabClickListener;
    private int selectPosition;
    private int previousSelectPosition;

    public RecyclerAdapterWrapper(RecyclerView.Adapter<VH> adapter) {
        this.adapter = adapter;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return adapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        adapter.onBindViewHolder(holder, position);

        SimpleViewClickListener listener = (SimpleViewClickListener) holder.itemView.getTag();
        if (listener == null) {
            listener = new SimpleViewClickListener();
            holder.itemView.setTag(listener);
        }
        holder.itemView.setSelected(selectPosition == position);
        listener.setExtra(holder, tabClickListener);
        holder.itemView.setOnClickListener(listener);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);

        } else {
            boolean selected = selectPosition == position;
            if (selected) {
                previousSelectPosition = selectPosition;
            }
            holder.itemView.setSelected(selected);
        }
    }

    public void setSelectPosition(int selectPosition) {
        if (this.selectPosition == selectPosition) {
            return;
        }
        this.selectPosition = selectPosition;
        notifyItemChanged(previousSelectPosition, Boolean.TRUE);
        notifyItemChanged(selectPosition, Boolean.TRUE);
    }

    @Override
    public int getItemCount() {
        return adapter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return adapter.getItemViewType(position);
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        adapter.setHasStableIds(hasStableIds);
    }

    @Override
    public long getItemId(int position) {
        return adapter.getItemId(position);
    }

    @Override
    public void onViewRecycled(@NonNull VH holder) {
        adapter.onViewRecycled(holder);
    }

    @Override
    public boolean onFailedToRecycleView(@NonNull VH holder) {
        return adapter.onFailedToRecycleView(holder);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull VH holder) {
        adapter.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull VH holder) {
        adapter.onViewDetachedFromWindow(holder);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        adapter.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        adapter.onDetachedFromRecyclerView(recyclerView);
    }

    public void setTabClickListener(OnTabClickListener tabClickListener) {
        this.tabClickListener = tabClickListener;
    }

    private static class SimpleViewClickListener implements View.OnClickListener {

        private RecyclerView.ViewHolder holder;
        private OnTabClickListener tabClickListener;

        @Override
        public void onClick(View v) {
            tabClickListener.onTabClick(v, holder.getAdapterPosition());
        }

        void setExtra(RecyclerView.ViewHolder holder,
                      OnTabClickListener tabClickListener) {
            this.holder = holder;
            this.tabClickListener = tabClickListener;
        }
    }
}
