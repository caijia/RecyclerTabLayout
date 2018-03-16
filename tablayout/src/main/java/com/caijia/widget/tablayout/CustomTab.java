package com.caijia.widget.tablayout;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by cai.jia on 2018/3/16.
 */

public interface CustomTab<VH extends RecyclerView.ViewHolder> {

    VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    void onBindViewHolder(@NonNull VH holder, int position);

    int getCount();
}
