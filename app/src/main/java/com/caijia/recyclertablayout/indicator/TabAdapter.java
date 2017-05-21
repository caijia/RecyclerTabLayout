package com.caijia.recyclertablayout.indicator;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by cai.jia on 2017/5/18 0018
 */

public abstract class TabAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> implements TabContent<VH>{

    public abstract ViewPager getViewPager();

    public abstract void updatePageAdapter(ViewPager viewPager);
}
