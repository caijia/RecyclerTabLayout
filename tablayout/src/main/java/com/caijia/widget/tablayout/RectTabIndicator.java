package com.caijia.widget.tablayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

/**
 * Created by cai.jia on 2017/5/17 0017
 */

public class RectTabIndicator implements TabIndicator {

    private Paint paint;

    private boolean hasIndicator;

    /**
     * 指示器高度
     */
    private int indicatorHeight;

    private int indicatorWidth;

    /**
     * 指示器左右padding
     */
    private int paddingLR;

    public RectTabIndicator(Context context, boolean hasIndicator,int indicatorWidth,
                            int indicatorHeight, int paddingLR, @ColorInt int indicatorColor) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setColor(indicatorColor);
        this.hasIndicator = hasIndicator;
        this.paddingLR = paddingLR;
        this.indicatorWidth = indicatorWidth;
        this.indicatorHeight = indicatorHeight;
    }

    @Override
    public void drawIndicator(Canvas canvas, RecyclerTabLayout tabLayout,
                              @Nullable RecyclerView.ViewHolder selectedViewHolder,
                              @Nullable RecyclerView.ViewHolder nextViewHolder,
                              int position, float positionOffset, @NonNull Rect drawBounds) {
        canvas.drawRect(
                drawBounds.left + paddingLR,
                drawBounds.bottom - indicatorHeight,
                drawBounds.right - paddingLR,
                drawBounds.bottom, paint);
    }

    @Override
    public boolean isDrawIndicator(RecyclerTabLayout tabLayout,
                                   @Nullable RecyclerView.ViewHolder selectedViewHolder,
                                   @Nullable RecyclerView.ViewHolder nextViewHolder,
                                   int position, float positionOffset) {
        return hasIndicator;
    }
}
