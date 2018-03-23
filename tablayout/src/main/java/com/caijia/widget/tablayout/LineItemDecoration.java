package com.caijia.widget.tablayout;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

class LineItemDecoration extends RecyclerView.ItemDecoration {

    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;
    private final Rect mBounds = new Rect();

    private int mOrientation;
    private int dividerWidth;
    private int dividerHeight;
    private int padding;
    private Paint paint;

    /**
     * @param orientation  Divider orientation
     * @param dividerWidth Divider width
     * @param padding      Divider padding
     * @param color        Divider color
     */
    public LineItemDecoration(int orientation, int dividerWidth, int dividerHeight,int padding, @ColorInt int color) {
        setOrientation(orientation);
        this.dividerWidth = dividerWidth;
        this.dividerHeight = dividerHeight;
        this.padding = padding;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setColor(color);
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw new IllegalArgumentException(
                    "Invalid orientation. It should be either HORIZONTAL or VERTICAL");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null) {
            return;
        }
        if (mOrientation == VERTICAL) {
            drawHorizontalLine(c, parent, state);
        } else {
            drawVerticalLine(c, parent, state);
        }
    }

    @SuppressLint("NewApi")
    private void drawHorizontalLine(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        canvas.save();
        final int left;
        final int right;
        if (parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            canvas.clipRect(left, parent.getPaddingTop(), right,
                    parent.getHeight() - parent.getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth();
        }

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            parent.getDecoratedBoundsWithMargins(child, mBounds);
            final int bottom = mBounds.bottom + Math.round(child.getTranslationY());
            final int top = bottom - dividerWidth;
            if (dividerHeight == 0) {
                canvas.drawRect(left + padding, top, right - padding, bottom, paint);
                
            }else{
                int halfRemainingSpace = (mBounds.width() - dividerHeight) / 2;
                canvas.drawRect(left + halfRemainingSpace, top, right - halfRemainingSpace, bottom, paint);
            }
        }
        canvas.restore();
    }

    @SuppressLint("NewApi")
    private void drawVerticalLine(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        canvas.save();
        final int top;
        final int bottom;
        if (parent.getClipToPadding()) {
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
            canvas.clipRect(parent.getPaddingLeft(), top,
                    parent.getWidth() - parent.getPaddingRight(), bottom);
        } else {
            top = 0;
            bottom = parent.getHeight();
        }

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);
            final int right = mBounds.right + Math.round(child.getTranslationX());
            final int left = right - dividerWidth;
            if (dividerHeight == 0) {
                canvas.drawRect(left, top + padding, right, bottom - padding, paint);
                
            }else{
                int halfRemainingSpace = (mBounds.height() - dividerHeight) / 2;
                canvas.drawRect(left, top + halfRemainingSpace, right, bottom - halfRemainingSpace, paint);
            }
        }
        canvas.restore();
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        if (mOrientation == VERTICAL) {
            outRect.set(0, 0, 0, dividerWidth);
        } else {
            outRect.set(0, 0, dividerWidth, 0);
        }
    }
}