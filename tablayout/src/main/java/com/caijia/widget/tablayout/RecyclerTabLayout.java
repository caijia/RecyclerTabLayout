package com.caijia.widget.tablayout;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cai.jia on 2017/5/16 0016
 */

public class RecyclerTabLayout extends RecyclerView implements OnTabClickListener {

    private static final int MODE_FIXED = 1;
    private static final int MODE_SCROLL = 2;
    private ViewPager viewPager;
    private int position;
    private float positionOffset;
    private OrientationHelper horizontalHelper;
    private OrientationHelper verticalHelper;
    private PageDataObserver pageDataObserver;
    private PageAdapterChangeListener pageAdapterChangeListener;
    private PageChangeListener pageChangeListener;
    private TabIndicator tabIndicator;

    private int tabWidth;
    private int tabBackground;
    private int scrollMode;
    private ColorStateList textColorStateList;
    private int customIndicatorId;
    private LineItemDecoration itemDecoration;
    private RecyclerAdapterWrapper tabAdapter;

    public RecyclerTabLayout(Context context) {
        this(context, null);
    }

    public RecyclerTabLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerTabLayout(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        pageAdapterChangeListener = new PageAdapterChangeListener();
        pageChangeListener = new PageChangeListener(this);
        getViewAttribute(context, attrs);
    }

    private static ColorStateList createColorStateList(int defaultColor, int selectedColor) {
        final int[][] states = new int[2][];
        final int[] colors = new int[2];
        int i = 0;

        states[i] = View.SELECTED_STATE_SET;
        colors[i] = selectedColor;
        i++;

        // Default enabled state
        states[i] = View.EMPTY_STATE_SET;
        colors[i] = defaultColor;
        return new ColorStateList(states, colors);
    }

    private void getViewAttribute(Context context, AttributeSet attrs) {
        TypedArray a = null;
        try {
            a = context.obtainStyledAttributes(attrs, R.styleable.RecyclerTabLayout);
            boolean hasDivider = a.getBoolean(R.styleable.RecyclerTabLayout_rTabHasDivider, true);
            int dividerWidth = a.getDimensionPixelOffset(R.styleable.RecyclerTabLayout_rTabDividerWidth, 0);
            int dividerPadding = a.getDimensionPixelOffset(R.styleable.RecyclerTabLayout_rTabDividerPadding, 0);
            int dividerColor = a.getColor(R.styleable.RecyclerTabLayout_rTabDividerColor, Color.BLACK);
            tabWidth = a.getDimensionPixelOffset(R.styleable.RecyclerTabLayout_rTabWidth, 0);
            boolean hasIndicator = a.getBoolean(R.styleable.RecyclerTabLayout_rTabHasIndicator, true);
            int indicatorColor = a.getColor(R.styleable.RecyclerTabLayout_rTabIndicatorColor, Color.RED);
            int indicatorWidth = a.getDimensionPixelOffset(R.styleable.RecyclerTabLayout_rTabIndicatorWidth, dpToPx(2));
            int indicatorHeight = a.getDimensionPixelOffset(R.styleable.RecyclerTabLayout_rTabIndicatorHeight, dpToPx(2));
            tabBackground = a.getResourceId(R.styleable.RecyclerTabLayout_rTabBackground, 0);
            customIndicatorId = a.getResourceId(R.styleable.RecyclerTabLayout_rTabCustomIndicatorId, -1);
            scrollMode = a.getInt(R.styleable.RecyclerTabLayout_rTabScrollMode, MODE_SCROLL);
            int selectedColor = a.getColor(R.styleable.RecyclerTabLayout_rTabSelectColor, Color.RED);
            int normalColor = a.getColor(R.styleable.RecyclerTabLayout_rTabNormalColor, Color.BLACK);
            textColorStateList = createColorStateList(normalColor, selectedColor);

            if (hasDivider) {
                itemDecoration = new LineItemDecoration(LineItemDecoration.HORIZONTAL,
                        dividerWidth, dividerPadding, dividerColor);
            }

            if (customIndicatorId == -1) {
                tabIndicator = new RectTabIndicator(context, hasIndicator, indicatorWidth,
                        indicatorHeight, 0, indicatorColor);
            }

        } finally {
            if (a != null) {
                a.recycle();
            }
        }
    }

    private int dpToPx(int value) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,
                getContext().getResources().getDisplayMetrics()));
    }

    private int spToPx(int sp) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                sp, getResources().getDisplayMetrics()));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (customIndicatorId != -1) {
            View customIndicatorView = LayoutInflater.from(getContext())
                    .inflate(customIndicatorId, this, false);

            if (customIndicatorView != null && customIndicatorView instanceof TabIndicator)
                tabIndicator = (TabIndicator) customIndicatorView;
        }
    }

    public void setupWithViewPager(ViewPager viewPager) {
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

        PagerAdapter adapter = viewPager.getAdapter();
        if (adapter != null && pageDataObserver != null) {
            adapter.unregisterDataSetObserver(pageDataObserver);
            pageDataObserver = null;
        }
    }

    private void setupViewPagerCallback(ViewPager viewPager) {
        if (viewPager == null) {
            return;
        }
        viewPager.addOnPageChangeListener(pageChangeListener);
        viewPager.addOnAdapterChangeListener(pageAdapterChangeListener);

        PagerAdapter adapter = viewPager.getAdapter();
        if (adapter != null && pageDataObserver == null) {
            pageDataObserver = new PageDataObserver();
            adapter.registerDataSetObserver(pageDataObserver);
        }

        createOrRecycleTab(adapter);
    }

    private void updateTab() {
        if (viewPager != null) {
            createOrRecycleTab(viewPager.getAdapter());
        }
    }

    private void createOrRecycleTab(PagerAdapter adapter) {
        if (adapter == null) {
            return;
        }

        tabAdapter = new RecyclerAdapterWrapper<>(pageAdapterToRecyclerAdapter(adapter));
        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        if (itemDecoration != null) {
            addItemDecoration(itemDecoration);
        }
        tabAdapter.setTabClickListener(this);
        swapAdapter(tabAdapter, true);
    }

    private RecyclerView.Adapter<DefaultTabAdapter.DefaultTabViewHolder> pageAdapterToRecyclerAdapter(PagerAdapter pagerAdapter) {
        List<TabData> dataList = new ArrayList<>();
        int count = pagerAdapter.getCount();

        TabDataFactory factory = null;
        if (pagerAdapter instanceof TabDataFactory) {
            factory = (TabDataFactory) pagerAdapter;
        }

        for (int i = 0; i < count; i++) {
            CharSequence pageTitle = pagerAdapter.getPageTitle(i);
            TabData tabData = factory != null
                    ? factory.getTabData(i)
                    : new SimpleTabData(pageTitle == null ? "" : pageTitle.toString());
            dataList.add(tabData);
        }
        return new DefaultTabAdapter(dataList, tabWidth, tabBackground, textColorStateList);
    }

    private OrientationHelper getOrientationHelper(LayoutManager layoutManager) {
        if (layoutManager == null || !(layoutManager instanceof LinearLayoutManager)) {
            return null;
        }

        int orientation = layoutManager.canScrollHorizontally() ? OrientationHelper.HORIZONTAL :
                layoutManager.canScrollVertically() ? OrientationHelper.VERTICAL : 0;

        switch (orientation) {
            case OrientationHelper.HORIZONTAL: {
                return getHorizontalHelper(layoutManager);
            }

            case OrientationHelper.VERTICAL: {
                return getVerticalHelper(layoutManager);
            }
        }
        return null;
    }

    private OrientationHelper getVerticalHelper(@NonNull LayoutManager layoutManager) {
        if (verticalHelper == null) {
            verticalHelper = OrientationHelper
                    .createOrientationHelper(layoutManager, OrientationHelper.VERTICAL);
        }
        return verticalHelper;
    }

    private OrientationHelper getHorizontalHelper(@NonNull LayoutManager layoutManager) {
        if (horizontalHelper == null) {
            horizontalHelper = OrientationHelper
                    .createOrientationHelper(layoutManager, OrientationHelper.HORIZONTAL);
        }
        return horizontalHelper;
    }

    private int getOrientationHeight() {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager == null) {
            return 0;
        }
        return layoutManager.canScrollHorizontally() ? getMeasuredHeight() :
                layoutManager.canScrollVertically() ? getMeasuredWidth() : 0;
    }

    private int getViewWidth(View view) {
        OrientationHelper helper = getOrientationHelper(getLayoutManager());
        return view != null && helper != null ? helper.getDecoratedMeasurement(view) : 0;
    }

    private int getViewStart(View view) {
        OrientationHelper helper = getOrientationHelper(getLayoutManager());
        return view != null && helper != null ? helper.getDecoratedStart(view) : 0;
    }

    private int getViewEnd(View view) {
        OrientationHelper helper = getOrientationHelper(getLayoutManager());
        return view != null && helper != null ? helper.getDecoratedEnd(view) : 0;
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

        final int selectedWidth = getViewWidth(selectedView);
        final int nextWidth = getViewWidth(nextView);

        int centerOffset = getWidth() / 2 - selectedWidth / 2;
        int scrollOffset = (int) ((selectedWidth + nextWidth) * 0.5f * positionOffset);

        stopScroll();
        llManager.scrollToPositionWithOffset(position, centerOffset - scrollOffset);
    }

    public void setTabIndicator(TabIndicator tabIndicator) {
        this.tabIndicator = tabIndicator;
    }

    @Override
    public void draw(Canvas c) {
        super.draw(c);
        if (tabIndicator == null) {
            return;
        }

        LayoutManager layoutManager = checkLayoutManager();
        ViewHolder selectedViewHolder = findViewHolderForLayoutPosition(position);
        ViewHolder nextViewHolder = findViewHolderForLayoutPosition(position + 1);

        if (!tabIndicator.isDrawIndicator(this, selectedViewHolder, nextViewHolder,
                position, positionOffset)) {
            return;
        }

        View selectedView = layoutManager.findViewByPosition(position);
        View nextView = layoutManager.findViewByPosition(position + 1);
        Rect bounds = getDrawBounds(selectedView, nextView);
        tabIndicator.drawIndicator(c, this, selectedViewHolder, nextViewHolder,
                position, positionOffset, bounds);
    }

    public Rect getDrawBounds(View selectedView, View nextView) {
        int selectViewStart = getViewStart(selectedView);
        int nextViewStart = getViewStart(nextView);

        int selectViewEnd = getViewEnd(selectedView);
        int nextViewEnd = getViewEnd(nextView);

        int bottom = getOrientationHeight();
        int top = 0;
        int left = (int) (nextViewStart * positionOffset + (1f - positionOffset) * selectViewStart);
        int right = (int) (nextViewEnd * positionOffset + (1f - positionOffset) * selectViewEnd);
        return new Rect(left, top, right, bottom);
    }

    @Override
    public void onTabClick(View view, int position) {
        if (viewPager != null) {
            viewPager.setCurrentItem(position);
        }
    }

    private void setTabSelectedItem(int position) {
        if (tabAdapter != null) {
            tabAdapter.setSelectPosition(position);
        }
    }

    private static class PageChangeListener implements ViewPager.OnPageChangeListener {

        private WeakReference<RecyclerTabLayout> ref;
        private int scrollState;

        public PageChangeListener(RecyclerTabLayout tabLayout) {
            ref = new WeakReference<>(tabLayout);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            RecyclerTabLayout tabLayout = ref.get();
            if (tabLayout == null) {
                return;
            }
            tabLayout.scrollToTab(position, positionOffset);
        }

        @Override
        public void onPageSelected(int position) {
            RecyclerTabLayout tabLayout = ref.get();
            if (tabLayout == null) {
                return;
            }

            tabLayout.setTabSelectedItem(position);
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
            updateTab();
        }

        @Override
        public void onInvalidated() {
            updateTab();
        }
    }

    private class PageAdapterChangeListener implements ViewPager.OnAdapterChangeListener {

        @Override
        public void onAdapterChanged(@NonNull ViewPager viewPager,
                                     @Nullable PagerAdapter oldAdapter,
                                     @Nullable PagerAdapter newAdapter) {
            if (RecyclerTabLayout.this.viewPager == viewPager) {
                updateTab();
            }
        }
    }
}
