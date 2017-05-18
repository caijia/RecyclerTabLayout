package com.caijia.recyclertablayout.indicator;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.View;

/**
 * Created by cai.jia on 2017/5/18 0018
 */

public class ViewMeasureHelper {

    private OrientationHelper horizontalHelper;
    private OrientationHelper verticalHelper;
    private RecyclerView recyclerView;

    ViewMeasureHelper(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public OrientationHelper getOrientationHelper(LayoutManager layoutManager) {
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

    public OrientationHelper getVerticalHelper(@NonNull LayoutManager layoutManager) {
        if (verticalHelper == null) {
            verticalHelper = OrientationHelper
                    .createOrientationHelper(layoutManager, OrientationHelper.VERTICAL);
        }
        return verticalHelper;
    }

    public OrientationHelper getHorizontalHelper(@NonNull LayoutManager layoutManager) {
        if (horizontalHelper == null) {
            horizontalHelper = OrientationHelper
                    .createOrientationHelper(layoutManager, OrientationHelper.HORIZONTAL);
        }
        return horizontalHelper;
    }

    public int getOrientationHeight() {
        LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager == null) {
            return 0;
        }
        return layoutManager.canScrollHorizontally() ? recyclerView.getMeasuredHeight() :
                layoutManager.canScrollVertically() ? recyclerView.getMeasuredWidth() : 0;
    }

    public int getViewWidth(View view) {
        OrientationHelper helper = getOrientationHelper(recyclerView.getLayoutManager());
        return view != null && helper != null ? helper.getDecoratedMeasurement(view) : 0;
    }

    public int getViewStart(View view) {
        OrientationHelper helper = getOrientationHelper(recyclerView.getLayoutManager());
        return view != null && helper != null ? helper.getDecoratedStart(view) : 0;
    }

    public int getViewEnd(View view) {
        OrientationHelper helper = getOrientationHelper(recyclerView.getLayoutManager());
        return view != null && helper != null ? helper.getDecoratedEnd(view) : 0;
    }

    public Rect getDrawBounds(View selectedView, View nextView, float positionOffset) {
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

    public Rect getInternalBounds(View parentSelectedView, View parentNextView, View selectedView,
                                  View nextView, float positionOffset) {

        int selectedChildLeft = selectedView == null ? 0 : selectedView.getLeft();
        int nextChildLeft = nextView == null ? 0 : nextView.getLeft();
        int selectedChildWidth = selectedView == null ? 0 : selectedView.getMeasuredWidth();
        int nextChildWidth = nextView == null ? 0 : nextView.getMeasuredWidth();

        int selectViewStart = getViewStart(parentSelectedView) + selectedChildLeft;
        int selectViewEnd = selectViewStart + selectedChildWidth;

        int nextViewStart = getViewStart(parentNextView) + nextChildLeft;
        int nextViewEnd = nextViewStart + nextChildWidth;

        int bottom = getOrientationHeight();
        int top = 0;
        int left = (int) (nextViewStart * positionOffset + (1f - positionOffset) * selectViewStart);
        int right = (int) (nextViewEnd * positionOffset + (1f - positionOffset) * selectViewEnd);
        return new Rect(left, top, right, bottom);
    }
}
