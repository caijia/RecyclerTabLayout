package com.caijia.recyclertablayout.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IdRes;
import android.support.annotation.IntDef;
import android.util.AttributeSet;

import com.caijia.recyclertablayout.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by cai.jia on 2017/5/22 0022
 */

public class TabAttribute {

    /**
     * 指示器宽度由整个Item的宽度决定
     */
    public static final int MODE_MATCH_PARENT = 0;

    /**
     * 指示器宽度会由recyclerTabId的View的宽度决定
     */
    public static final int MODE_WRAP_CONTENT = 1;

    /**
     * Tab的宽度平分
     */
    public static final int MODE_FIXED = 0;

    /**
     * Tab宽度有内容决定
     */
    public static final int MODE_SCROLLABLE = 1;
    private int tabPaddingStart;
    private int tabPaddingTop;
    private int tabPaddingEnd;
    private int tabPaddingBottom;
    private int tabBackground;
    private int tabId;
    private int tabMode;
    private int tabIndicatorMode;
    private float tabWidthPercent;

    TabAttribute(Context context, AttributeSet attrs) {
        TypedArray a = null;
        try {
            a = context.obtainStyledAttributes(attrs, R.styleable.RecyclerTabContent);
            tabPaddingStart = tabPaddingTop = tabPaddingEnd = tabPaddingBottom = a
                    .getDimensionPixelOffset(R.styleable.RecyclerTabContent_recyclerTabPadding, 0);
            tabPaddingStart = a.getDimensionPixelOffset(
                    R.styleable.RecyclerTabContent_recyclerTabPaddingStart, 0);
            tabPaddingTop = a.getDimensionPixelOffset(
                    R.styleable.RecyclerTabContent_recyclerTabPaddingTop, 0);
            tabPaddingEnd = a.getDimensionPixelOffset(
                    R.styleable.RecyclerTabContent_recyclerTabPaddingEnd, 0);
            tabPaddingBottom = a.getDimensionPixelOffset(
                    R.styleable.RecyclerTabContent_recyclerTabPaddingBottom, 0);
            tabWidthPercent = a.getFloat(
                    R.styleable.RecyclerTabContent_recyclerTabWidthPercent, 0f);
            tabBackground = a.getResourceId(
                    R.styleable.RecyclerTabContent_recyclerTabBackground, 0);
            tabId = a.getResourceId(R.styleable.RecyclerTabContent_recyclerTabId, 0);
            tabMode = a.getInt(R.styleable.RecyclerTabContent_recyclerTabWidthMode, MODE_FIXED);
            tabIndicatorMode = a.getInt(
                    R.styleable.RecyclerTabContent_recyclerTabIndicatorWidthMode, MODE_MATCH_PARENT);
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
    }

    public int getTabPaddingStart() {
        return tabPaddingStart;
    }

    public int getTabPaddingTop() {
        return tabPaddingTop;
    }

    public int getTabPaddingEnd() {
        return tabPaddingEnd;
    }

    public int getTabPaddingBottom() {
        return tabPaddingBottom;
    }

    public int getTabBackground() {
        return tabBackground;
    }

    public
    @ModeTabWidth
    int getTabMode() {
        return tabMode;
    }

    public float getTabWidthPercent() {
        return tabWidthPercent;
    }

    public
    @IdRes
    int getTabId() {
        return tabId;
    }

    public
    @ModeIndicatorWidth
    int getTabIndicatorMode() {
        return tabIndicatorMode;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({MODE_FIXED, MODE_SCROLLABLE})
    public @interface ModeTabWidth {

    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({MODE_MATCH_PARENT, MODE_WRAP_CONTENT})
    public @interface ModeIndicatorWidth {

    }
}
