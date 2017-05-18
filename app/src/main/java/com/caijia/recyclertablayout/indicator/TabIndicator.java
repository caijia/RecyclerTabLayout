package com.caijia.recyclertablayout.indicator;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

public interface TabIndicator<VH extends RecyclerView.ViewHolder> {

    /**
     * 画tab指示器
     *
     * @param canvas         c
     * @param helper         帮助计算View的大小
     * @param selectedVH     选中的Item
     * @param nextVH         选中Item的下一个Item
     * @param position       当前选中的position
     * @param positionOffset ViewPager偏移量[0,1)
     * @param drawBounds     能画的范围
     */
    void drawIndicator(Canvas canvas, ViewMeasureHelper helper, @Nullable VH selectedVH,
                       @Nullable VH nextVH, int position, float positionOffset,
                       @NonNull Rect drawBounds);

}