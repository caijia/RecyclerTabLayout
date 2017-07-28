package com.caijia.recyclertablayout.indicator;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.caijia.recyclertablayout.indicator.adapter.TabAdapter;
import com.caijia.recyclertablayout.indicator.utils.ViewSizeHelper;

import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * Created by cai.jia on 2017/5/16 0016
 */

public class RecyclerTabContent extends RecyclerView {

    private ViewPager viewPager;
    private int position;
    private float positionOffset;
    private ViewSizeHelper measureHelper;
    private PageAdapterChangeListener pageAdapterChangeListener;
    private PageChangeListener pageChangeListener;
    private TabAdapter tabAdapter;
    private ArrayMap<PagerAdapter, DataSetObserver> pageDataObserverMap;
    private OnDrawListener onDrawListener;

    public RecyclerTabContent(Context context) {
        this(context, null);
    }

    public RecyclerTabContent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerTabContent(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        pageDataObserverMap = new ArrayMap<>();
        measureHelper = new ViewSizeHelper(this);
        pageAdapterChangeListener = new PageAdapterChangeListener();
        pageChangeListener = new PageChangeListener(this);
    }

    public void setupWithAdapter(@NonNull TabAdapter tabAdapter) {
        ViewPager viewPager = tabAdapter.getViewPager();
        if (viewPager == null) {
            throw new RuntimeException("ViewPager is null ");
        }
        this.viewPager = viewPager;
        this.tabAdapter = tabAdapter;
        setAdapter(tabAdapter);
        resetCallback(viewPager);
    }

    private void resetCallback(ViewPager viewPager) {
        destroyViewPagerCallback(viewPager);
        setupViewPagerCallback(viewPager);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        destroyViewPagerCallback(viewPager);
    }

    private void destroyViewPagerCallback(ViewPager viewPager) {
        if (viewPager == null) {
            return;
        }
        this.viewPager = viewPager;
        viewPager.removeOnPageChangeListener(pageChangeListener);
        viewPager.removeOnAdapterChangeListener(pageAdapterChangeListener);

        for (Map.Entry<PagerAdapter, DataSetObserver> entry : pageDataObserverMap.entrySet()) {
            PagerAdapter adapter = entry.getKey();
            DataSetObserver observer = entry.getValue();
            if (adapter != null && observer != null) {
                adapter.unregisterDataSetObserver(observer);
            }
        }
        pageDataObserverMap.clear();
    }

    private void setupViewPagerCallback(ViewPager viewPager) {
        if (viewPager == null) {
            return;
        }
        viewPager.addOnPageChangeListener(pageChangeListener);
        viewPager.addOnAdapterChangeListener(pageAdapterChangeListener);

        PagerAdapter adapter = viewPager.getAdapter();
        if (adapter != null && pageDataObserverMap.get(adapter) == null) {
            PageDataObserver pageDataObserver = new PageDataObserver();
            pageDataObserverMap.put(adapter, pageDataObserver);
            adapter.registerDataSetObserver(pageDataObserver);
        }
    }

    @NonNull
    private LayoutManager checkLayoutManager() {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager == null || !(layoutManager instanceof LinearLayoutManager)) {
            throw new RuntimeException("layoutManager is null or layout is not LinearLayoutManager");
        }
        return layoutManager;
    }

    private void scrollToTab(int position, float positionOffset) {
        this.position = position;
        this.positionOffset = positionOffset;

        LayoutManager layoutManager = checkLayoutManager();
        LinearLayoutManager llManager = (LinearLayoutManager) layoutManager;

        View selectedView = layoutManager.findViewByPosition(position);
        View nextView = layoutManager.findViewByPosition(position + 1);

        final int selectedWidth = measureHelper.getViewWidth(selectedView);
        final int nextWidth = measureHelper.getViewWidth(nextView);

        int centerOffset = Math.round((getWidth() - selectedWidth) * 0.5f);
        int scrollOffset = (int) ((selectedWidth + nextWidth) * 0.5f * positionOffset);

        stopScroll();
        llManager.scrollToPositionWithOffset(position, centerOffset - scrollOffset);
    }

    @Override
    public void draw(Canvas c) {
        super.draw(c);
        if (tabAdapter == null || onDrawListener == null) {
            return;
        }

        LayoutManager layoutManager = checkLayoutManager();
        View selectedView = layoutManager.findViewByPosition(position);
        View nextView = layoutManager.findViewByPosition(position + 1);
        onDrawListener.onDraw(measureHelper, selectedView, nextView, position, positionOffset);
    }

    public void setOnDrawListener(OnDrawListener onDrawListener) {
        this.onDrawListener = onDrawListener;
    }

    public interface OnDrawListener {

        void onDraw(ViewSizeHelper helper, @Nullable View selectedView,
                    @Nullable View nextView, int position, float positionOffset);
    }

    private static class PageChangeListener implements ViewPager.OnPageChangeListener {

        private WeakReference<RecyclerTabContent> ref;
        private int scrollState;

        PageChangeListener(RecyclerTabContent tabLayout) {
            ref = new WeakReference<>(tabLayout);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            RecyclerTabContent tabLayout = ref.get();
            if (tabLayout == null) {
                return;
            }
            tabLayout.scrollToTab(position, positionOffset);
        }

        @Override
        public void onPageSelected(int position) {
            RecyclerTabContent tabLayout = ref.get();
            if (tabLayout == null) {
                return;
            }

            if (scrollState == RecyclerView.SCROLL_STATE_IDLE) {
                tabLayout.scrollToTab(position, 0);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            scrollState = state;
        }
    }

    private class PageDataObserver extends DataSetObserver {

        @Override
        public void onChanged() {
            getAdapter().notifyDataSetChanged();
        }

        @Override
        public void onInvalidated() {
            getAdapter().notifyDataSetChanged();
        }
    }

    private class PageAdapterChangeListener implements ViewPager.OnAdapterChangeListener {

        @Override
        public void onAdapterChanged(@NonNull ViewPager viewPager,
                                     @Nullable PagerAdapter oldAdapter,
                                     @Nullable PagerAdapter newAdapter) {
            if (RecyclerTabContent.this.viewPager == viewPager) {
                if (tabAdapter != null) {
                    RecyclerTabContent.this.resetCallback(viewPager);
                }
                getAdapter().notifyDataSetChanged();
            }
        }
    }
}
