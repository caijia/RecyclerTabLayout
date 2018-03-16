package com.caijia.recyclertablayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.caijia.widget.tablayout.RecyclerTabLayout;
import com.caijia.widget.tablayout.TabData;
import com.caijia.widget.tablayout.TabDataFactory;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerTabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter implements TabDataFactory{

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
        public TabData getTabData(final int position) {
            return new TabData() {
                @Override
                public String getTabTitle() {
                    return "item" + position;
                }

                @Override
                public int getTabIcon() {
                    return 0;
                }
            };
        }

//        @Override
//        public CharSequence getPageTitle(int position) {
//            return "item" + position;
//        }

    }
}


