package com.caijia.recyclertablayout.indicator;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.caijia.recyclertablayout.R;

/**
 * Created by cai.jia on 2017/5/17 0017
 */

public class DefaultTabAdapter extends TabAdapter<DefaultTabAdapter.DefaultTabVH> {

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    public DefaultTabAdapter(@NonNull ViewPager viewPager) {
        this.viewPager = viewPager;
        checkAndInitAdapter(viewPager);
    }

    private void checkAndInitAdapter(@NonNull ViewPager viewPager) {
        if (viewPager.getAdapter() == null) {
            throw new RuntimeException("ViewPager Adapter is null");
        }
        pagerAdapter = viewPager.getAdapter();
    }

    private ViewGroup parent;

    @Override
    public DefaultTabVH onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = parent;
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_text_view, parent, false);
        return new DefaultTabVH(view);
    }

    @Override
    public void onBindViewHolder(DefaultTabVH holder, int position) {
        applyTabAttribute(holder);
        holder.textView.setText(pagerAdapter.getPageTitle(position));
    }

    @Override
    public int getItemCount() {
        return pagerAdapter.getCount();
    }

    @Override
    public void updatePageAdapter(ViewPager viewPager) {
        checkAndInitAdapter(viewPager);
    }

    @Override
    public ViewPager getViewPager() {
        return viewPager;
    }

    private TabAttribute tabAttribute;

    @Override
    public void setTabAttribute(TabAttribute tabAttribute) {
        this.tabAttribute = tabAttribute;
    }

    private void applyTabAttribute(DefaultTabVH holder) {
        if (tabAttribute == null) {
            return;
        }
        holder.itemView.setBackgroundResource(tabAttribute.getTabBackground());
        int paddingStart = tabAttribute.getTabPaddingStart();
        int paddingTop = tabAttribute.getTabPaddingTop();
        int paddingEnd = tabAttribute.getTabPaddingEnd();
        int paddingBottom = tabAttribute.getTabPaddingBottom();
        int tabMode = tabAttribute.getTabMode();
        float tabWidthPercent = tabAttribute.getTabWidthPercent();

        View tabView = ViewFinder.find(holder.itemView, tabAttribute.getTabId());
        if (tabView != null) {
            tabView.setPadding(paddingStart,paddingTop,paddingEnd,paddingBottom);

        }else{
            holder.itemView.setPadding(paddingStart,paddingTop,paddingEnd,paddingBottom);
        }

        holder.itemView.getLayoutParams().width = tabMode == TabAttribute.MODE_FIXED
                ? parent.getMeasuredWidth() / getItemCount() : ViewGroup.LayoutParams.WRAP_CONTENT;
        if (tabWidthPercent != 0) {
            holder.itemView.getLayoutParams().width = (int) (parent.getMeasuredWidth() / tabWidthPercent);
        }
    }

    static class DefaultTabVH extends RecyclerView.ViewHolder {

        private TextView textView;

        DefaultTabVH(View itemView) {
            super(itemView);
            textView = ViewFinder.find(itemView,R.id.text);
        }
    }
}
