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

    private int orientation;

    public RectTabIndicator(Context context, int orientation, boolean hasIndicator,
                            int indicatorWidth, int indicatorHeight,
                            @ColorInt int indicatorColor) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setColor(indicatorColor);
        this.hasIndicator = hasIndicator;
        this.indicatorWidth = indicatorWidth;
        this.indicatorHeight = indicatorHeight;
        this.orientation = orientation;
    }

    @Override
    public void drawIndicator(Canvas canvas, RecyclerTabLayout tabLayout,
                              @Nullable RecyclerView.ViewHolder selectedViewHolder,
                              @Nullable RecyclerView.ViewHolder nextViewHolder,
                              int position, float positionOffset, @NonNull Rect drawBounds) {
        switch (orientation) {
            case RecyclerTabLayout.HORIZONTAL: {
                int halfRemaining = (drawBounds.width() - indicatorWidth) / 2;
                int left = indicatorWidth > 0 ? drawBounds.left + halfRemaining : drawBounds.left;
                int top = drawBounds.bottom - indicatorHeight;
                int right = indicatorWidth > 0 ? drawBounds.right - halfRemaining : drawBounds.right;
                canvas.drawRect(left, top, right, drawBounds.bottom, paint);
                break;
            }

            case RecyclerTabLayout.VERTICAL:

                break;
        }

    }

    @Override
    public boolean isDrawIndicator(RecyclerTabLayout tabLayout,
                                   @Nullable RecyclerView.ViewHolder selectedViewHolder,
                                   @Nullable RecyclerView.ViewHolder nextViewHolder,
                                   int position, float positionOffset) {
        return hasIndicator;
    }
}
