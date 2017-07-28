package com.caijia.recyclertablayout.indicator.utils;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by cai.jia on 2017/5/23 0023
 */

public class CacheViewHolder extends RecyclerView.ViewHolder {

    public CacheViewHolder(View itemView) {
        super(itemView);
    }

    public <T extends View> T find(@IdRes int viewId) {
        return ViewFinder.find(itemView, viewId);
    }
}
