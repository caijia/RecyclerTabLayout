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

class DefaultTab implements CustomTab<DefaultTab.DefaultTabViewHolder> {

    private List<? extends TabData> dataList;

    public DefaultTab(List<? extends TabData> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public DefaultTabViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_text_view, parent, false);
        return new DefaultTabViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DefaultTabViewHolder holder, int position) {
        TabData tabData = dataList.get(position);
        holder.textView.setText(tabData.getTabTitle());
        holder.itemView.setOnClickListener(holder);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    static class DefaultTabViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textView;

        DefaultTabViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
