package com.caijia.recyclertablayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cai.jia on 2017/5/11 0011
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<TestFragment> list;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add(TestFragment.getInstance(i));
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
}
