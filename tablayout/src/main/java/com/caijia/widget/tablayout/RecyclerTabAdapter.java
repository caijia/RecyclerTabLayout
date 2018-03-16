package com.caijia.widget.tablayout;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by cai.jia on 2017/5/17 0017
 */

class RecyclerTabAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private CustomTab<VH> customTab;

    public RecyclerTabAdapter(CustomTab<VH> customTab) {
        this.customTab = customTab;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return customTab.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        customTab.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return customTab.getCount();
    }
}
