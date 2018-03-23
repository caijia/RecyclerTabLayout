package com.caijia.widget.tablayout;

import android.content.res.ColorStateList;
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

class DefaultTabAdapter extends RecyclerView.Adapter<DefaultTabAdapter.DefaultTabViewHolder> {

    private List<? extends TabData> dataList;

    private int tabWidth;
    private int tabBackground;
    private ColorStateList colorStateList;
    private int scrollMode;
    private int tabPaddingSpace;
    private int orientation;
    private int dividerWidth;

    public DefaultTabAdapter(List<? extends TabData> dataList, int tabWidth, int tabBackground,
                             ColorStateList colorStateList, int scrollMode, int tabPaddingSpace,
                             int orientation, int dividerWidth) {
        this.dataList = dataList;
        this.tabWidth = tabWidth;
        this.tabBackground = tabBackground;
        this.colorStateList = colorStateList;
        this.scrollMode = scrollMode;
        this.tabPaddingSpace = tabPaddingSpace;
        this.orientation = orientation;
        this.dividerWidth = dividerWidth;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @NonNull
    @Override
    public DefaultTabViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_text_view, parent, false);
        if (scrollMode == RecyclerTabLayout.MODE_FIXED) {
            tabWidth = (parent.getMeasuredWidth() - dividerWidth * dataList.size() - 1) / dataList.size();
        }
        return new DefaultTabViewHolder(view,tabPaddingSpace);
    }

    @Override
    public void onBindViewHolder(@NonNull DefaultTabViewHolder holder, int position) {
        TabData tabData = dataList.get(position);
        holder.textView.setText(tabData.getTabTitle());
        if (colorStateList != null) {
            holder.textView.setTextColor(colorStateList);
        }

        if (tabWidth > 0) {
            holder.itemView.getLayoutParams().width = tabWidth;
        }

        if (tabBackground > 0) {
            holder.itemView.setBackgroundResource(tabBackground);
        }
    }

    static class DefaultTabViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        DefaultTabViewHolder(View itemView, int tabPaddingSpace) {
            super(itemView);
            itemView.setPadding(tabPaddingSpace, 0, tabPaddingSpace, 0);
            textView = itemView.findViewById(R.id.text);
        }
    }
}
