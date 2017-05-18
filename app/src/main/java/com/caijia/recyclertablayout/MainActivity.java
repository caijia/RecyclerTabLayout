package com.caijia.recyclertablayout;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;

import com.caijia.recyclertablayout.indicator.DefaultTabAdapter;
import com.caijia.recyclertablayout.indicator.RecyclerTabLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addBtn = (Button) findViewById(R.id.add_btn);
        Button removeBtn = (Button) findViewById(R.id.remove_btn);
        Button changeAdapterBtn = (Button) findViewById(R.id.change_adapter_btn);
        addBtn.setOnClickListener(this);
        removeBtn.setOnClickListener(this);
        changeAdapterBtn.setOnClickListener(this);

        RecyclerTabLayout tabLayout = (RecyclerTabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        tabLayout.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),4);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithAdapter(new DefaultTabAdapter(viewPager));
        viewPager.setCurrentItem(1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_btn:{
                viewPagerAdapter.addItem();
                break;
            }

            case R.id.remove_btn:{
                viewPagerAdapter.removeItem();
                break;
            }

            case R.id.change_adapter_btn:{
                viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),6);
                viewPager.setAdapter(viewPagerAdapter);
                break;
            }
        }
    }
}
