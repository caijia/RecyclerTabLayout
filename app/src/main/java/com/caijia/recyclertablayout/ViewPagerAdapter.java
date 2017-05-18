package com.caijia.recyclertablayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cai.jia on 2017/5/11 0011
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<TestFragment> list;

    public ViewPagerAdapter(FragmentManager fm,int count) {
        super(fm);
        list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(TestFragment.getInstance(i+""));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "item"+position;
    }

    public void addItem() {
        int index = (int) (Math.random()*getCount());
        list.add(index,TestFragment.getInstance("add"));
        notifyDataSetChanged();
    }

    public void removeItem() {
        if (list.isEmpty()) {
            return;
        }
        int index = (int) (Math.random()*getCount());
        list.remove(index);
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
