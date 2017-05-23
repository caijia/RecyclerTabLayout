package com.caijia.recyclertablayout.indicator;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by cai.jia on 2017/5/22 0022
 */

public class RecyclerTabLayout extends FrameLayout implements RecyclerTabContent.OnDrawListener {

    private TabIndicator tabIndicator;
    private RecyclerTabContent tabContent;
    private TabAttribute tabAttribute;

    public RecyclerTabLayout(@NonNull Context context) {
        this(context, null);
    }

    public RecyclerTabLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerTabLayout(@NonNull Context context, @Nullable AttributeSet attrs,
                             @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RecyclerTabLayout(@NonNull Context context, @Nullable AttributeSet attrs,
                             @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        tabContent = new RecyclerTabContent(getContext());
        tabAttribute = new TabAttribute(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount > 1) {
            throw new RuntimeException("only one child");
        }

        if (childCount == 1) {
            View indicator = getChildAt(0);
            if (!(indicator instanceof TabIndicator)) {
                throw new RuntimeException("child is not implements TabIndicator");
            }
            tabIndicator = (TabIndicator) indicator;
        }

        addView(tabContent, 0, new FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        tabContent.setOnDrawListener(this);
    }

    public void setupWithViewPager(@NonNull ViewPager viewPager) {
        RectTabAdapter adapter = new RectTabAdapter(viewPager);
        setupWithAdapter(adapter);
    }

    public void setupWithAdapter(@NonNull TabAdapter tabAdapter) {
        int tabAdapterItemCount = tabAdapter.getItemCount();
        int pageAdapterItemCount = tabAdapter.getViewPager().getAdapter().getCount();
        if (tabAdapterItemCount != pageAdapterItemCount) {
            throw new RuntimeException("tabAdapterItemCount is not equals pageAdapterItemCount");
        }
        tabAdapter.setTabAttribute(tabAttribute);
        tabContent.setupWithAdapter(tabAdapter);
    }

    public void setLayoutManager(LinearLayoutManager layoutManager) {
        this.tabContent.setLayoutManager(layoutManager);
    }

    @Override
    public void onDraw(ViewSizeHelper helper, @Nullable View selectedView, @Nullable View nextView,
                       int position, float positionOffset) {
        int tabId = 0;
        int indicatorMode = TabAttribute.MODE_MATCH_PARENT;
        if (tabAttribute != null) {
            tabId = tabAttribute.getTabId();
            indicatorMode = tabAttribute.getTabIndicatorMode();
        }

        if (tabIndicator != null) {
            tabIndicator.onDrawIndicator(helper, indicatorMode, tabId, selectedView, nextView,
                    position, positionOffset);
        }
    }
}
