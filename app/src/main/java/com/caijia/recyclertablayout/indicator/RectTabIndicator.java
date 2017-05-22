package com.caijia.recyclertablayout.indicator;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.caijia.recyclertablayout.R;

/**
 * Created by cai.jia on 2017/5/22 0022
 */

public class RectTabIndicator extends View implements TabIndicator {

    public int tabIndicatorHeight;
    public int tabIndicatorColor;

    private Paint paint;
    private ViewSizeHelper viewSizeHelper;
    private View selectedView;
    private View nextView;
    private float positionOffset;
    private int indicatorMode;
    private int tabId;

    public RectTabIndicator(Context context) {
        this(context, null);
    }

    public RectTabIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RectTabIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RectTabIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);

        TypedArray a = null;
        try {
            a = context.obtainStyledAttributes(attrs, R.styleable.RectTabIndicator);
            tabIndicatorHeight = a.getDimensionPixelOffset(
                    R.styleable.RectTabIndicator_recyclerTabIndicatorHeight,
                    dpToPx(context, 2));
            tabIndicatorColor = a.getColor(
                    R.styleable.RectTabIndicator_recyclerTabIndicatorColor,
                    Color.CYAN);
            paint.setColor(tabIndicatorColor);
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
    }

    @Override
    public void onDrawIndicator(ViewSizeHelper helper, @TabAttribute.ModeIndicatorWidth int mode,
                                @IdRes int tabId, @Nullable View selectedView,
                                @Nullable View nextView, int position, float positionOffset) {
        this.viewSizeHelper = helper;
        this.selectedView = selectedView;
        this.nextView = nextView;
        this.indicatorMode = mode;
        this.tabId = tabId;
        this.positionOffset = positionOffset;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (indicatorMode) {
            case TabAttribute.MODE_MATCH_PARENT: {
                Rect bounds = viewSizeHelper.getDrawBounds(selectedView, nextView, positionOffset);
                drawRect(canvas, bounds);
                break;
            }

            case TabAttribute.MODE_WRAP_CONTENT: {
                View childSelectedView = ViewFinder.find(selectedView, tabId);
                View childNextView = ViewFinder.find(nextView, tabId);
                Rect bounds = viewSizeHelper.getChildDrawBounds(selectedView,nextView,
                        childSelectedView, childNextView, positionOffset);
                drawRect(canvas, bounds);
                break;
            }
        }
    }

    private void drawRect(Canvas canvas, Rect bounds) {
        canvas.drawRect(
                bounds.left,
                bounds.bottom - tabIndicatorHeight,
                bounds.right,
                bounds.bottom, paint);
    }

    private int dpToPx(Context context, float dp) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics()));
    }
}
