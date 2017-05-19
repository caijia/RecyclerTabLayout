package com.caijia.recyclertablayout.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.caijia.recyclertablayout.R;

public class TabLayoutAttribute {

    public static final int MODE_MATCH_PARENT = 0;
    public static final int MODE_WRAP_CONTENT = 1;
    public static final int MODE_EQ_MAX_WIDTH = 2;

    public static final int MODE_FIXED = 0;
    public static final int MODE_SCROLLABLE = 1;

    private static final int D_INDICATOR_HEIGHT = 2;

    public int tabIndicatorHeight;
    public int tabIndicatorColor;
    public int tabPaddingStart;
    public int tabPaddingTop;
    public int tabPaddingEnd;
    public int tabPaddingBottom;
    public int tabTextColor;
    public int tabSelectedTextColor;
    public int tabBackground;
    public int tabMode;
    public int tabIndicatorWidthMode;
    public int tabTextSize;
    public float tabWidthPercent;

    TabLayoutAttribute(Context context, AttributeSet attrs) {
        TypedArray a = null;
        try {
            a = context.obtainStyledAttributes(attrs, R.styleable.RecyclerTabLayout);
            tabIndicatorHeight = a.getDimensionPixelOffset(
                    R.styleable.RecyclerTabLayout_recyclerTabIndicatorHeight,
                    dpToPx(context, D_INDICATOR_HEIGHT));
            tabIndicatorColor = a.getColor(
                    R.styleable.RecyclerTabLayout_recyclerTabIndicatorColor,
                    Color.CYAN);
            tabTextSize = a.getDimensionPixelSize(
                    R.styleable.RecyclerTabLayout_recyclerTabTextSize, spToPx(context,14));
            tabPaddingStart = tabPaddingTop = tabPaddingEnd = tabPaddingBottom = a
                    .getDimensionPixelOffset(R.styleable.RecyclerTabLayout_recyclerTabPadding, 0);
            tabPaddingStart = a.getDimensionPixelOffset(
                    R.styleable.RecyclerTabLayout_recyclerTabPaddingStart, 0);
            tabPaddingTop = a.getDimensionPixelOffset(
                    R.styleable.RecyclerTabLayout_recyclerTabPaddingTop, 0);
            tabPaddingEnd = a.getDimensionPixelOffset(
                    R.styleable.RecyclerTabLayout_recyclerTabPaddingEnd, 0);
            tabPaddingBottom = a.getDimensionPixelOffset(
                    R.styleable.RecyclerTabLayout_recyclerTabPaddingBottom, 0);
            tabWidthPercent = a.getFloat(
                    R.styleable.RecyclerTabLayout_recyclerTabWidthPercent, 0f);
            tabTextColor = a.getColor(
                    R.styleable.RecyclerTabLayout_recyclerTabTextColor, Color.GRAY);
            tabSelectedTextColor = a.getColor(
                    R.styleable.RecyclerTabLayout_recyclerTabSelectedTextColor, Color.BLACK);
            tabBackground = a.getResourceId(
                    R.styleable.RecyclerTabLayout_recyclerTabBackground, 0);
            tabMode = a.getInt(R.styleable.RecyclerTabLayout_recyclerTabWidthMode, MODE_FIXED);
            tabIndicatorWidthMode = a.getInt(
                    R.styleable.RecyclerTabLayout_recyclerTabIndicatorWidthMode, MODE_MATCH_PARENT);

        } finally {
            if (a != null) {
                a.recycle();
            }
        }
    }

    private int dpToPx(Context context, float dp) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics()));
    }

    private int spToPx(Context context,float sp) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                context.getResources().getDisplayMetrics()));
    }
}