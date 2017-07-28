package com.caijia.recyclertablayout.indicator.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.caijia.recyclertablayout.R;
import com.caijia.recyclertablayout.indicator.utils.CacheViewHolder;

/**
 * Created by cai.jia on 2017/5/23 0023
 */

public class RectTabAdapter extends SimpleTabAdapter {

    public RectTabAdapter(@NonNull ViewPager viewPager) {
        super(viewPager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.default_tab_item;
    }

    @Override
    protected void onBindTab(CacheViewHolder holder, int position) {
        TextView textView = holder.find(R.id.default_tab_text);
        textView.setText(getPagerAdapter().getPageTitle(position));
    }
}
