package com.caijia.recyclertablayout.indicator;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by cai.jia on 2017/5/17 0017
 */

public abstract class SimpleTabAdapter extends TabAdapter<CacheViewHolder> {

    private ViewPager viewPager;
    private ViewGroup parent;
    private TabAttribute tabAttribute;

    public SimpleTabAdapter(@NonNull ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public PagerAdapter getPagerAdapter() {
        PagerAdapter adapter = viewPager.getAdapter();
        if (viewPager.getAdapter() == null) {
            throw new RuntimeException("ViewPager Adapter is null");
        }
        return adapter;
    }

    @Override
    public final CacheViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = parent;
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false);
        CacheViewHolder holder = new CacheViewHolder(view);
        setTabPadding(holder);
        return holder;
    }

    protected abstract
    @LayoutRes
    int getLayoutId();

    protected abstract void onBindTab(CacheViewHolder holder, int position);

    @Override
    public final void onBindViewHolder(CacheViewHolder holder, int position) {
        holder.itemView.getLayoutParams().width = getTabViewWidth();
        onBindTab(holder, position);
    }

    @Override
    public int getItemCount() {
        return getPagerAdapter().getCount();
    }

    @Override
    public ViewPager getViewPager() {
        return viewPager;
    }

    @Override
    public void setTabAttribute(TabAttribute tabAttribute) {
        this.tabAttribute = tabAttribute;
    }

    private void setTabPadding(CacheViewHolder holder) {
        if (tabAttribute == null) {
            return;
        }
        holder.itemView.setBackgroundResource(tabAttribute.getTabBackground());
        int paddingStart = tabAttribute.getTabPaddingStart();
        int paddingTop = tabAttribute.getTabPaddingTop();
        int paddingEnd = tabAttribute.getTabPaddingEnd();
        int paddingBottom = tabAttribute.getTabPaddingBottom();
        View tabView = holder.find(tabAttribute.getTabId());
        if (tabView != null) {
            tabView.setPadding(paddingStart, paddingTop, paddingEnd, paddingBottom);

        } else {
            holder.itemView.setPadding(paddingStart, paddingTop, paddingEnd, paddingBottom);
        }
    }

    private int getTabViewWidth() {
        if (tabAttribute == null) {
            return WRAP_CONTENT;
        }
        int tabMode = tabAttribute.getTabMode();
        float tabWidthPercent = tabAttribute.getTabWidthPercent();
        int perWidth = parent.getMeasuredWidth() / getItemCount();

        int itemWidth = tabMode == TabAttribute.MODE_FIXED ? perWidth : WRAP_CONTENT;
        if (tabWidthPercent != 0) {
            itemWidth = (int) (parent.getMeasuredWidth() / tabWidthPercent);
        }
        return itemWidth;
    }
}
