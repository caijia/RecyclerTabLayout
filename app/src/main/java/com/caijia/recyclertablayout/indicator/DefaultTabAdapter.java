package com.caijia.recyclertablayout.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.caijia.recyclertablayout.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by cai.jia on 2017/5/17 0017
 */

public class DefaultTabAdapter extends TabAdapter<DefaultTabAdapter.DefaultTabVH> {

    public static final int MATCH_PARENT = 1;
    public static final int WRAP_CONTENT = 2;
    public static final int EQ_MAX_WIDTH = 3;

    public static final int MODE_SCROLLABLE = 4;
    public static final int MODE_FIXED = 5;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private Paint paint;

    private int indicatorHeight;
    private int paddingLR;
    private int widthMode;
    private int scrollMode;
    private int selectedColor;
    private int normalColor;
    private float textSize;
    private int tabBackground;
    private ViewGroup parent;

    public DefaultTabAdapter(@NonNull ViewPager viewPager) {
        this.viewPager = viewPager;
        checkAndInitAdapter(viewPager);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        initDefaultValue(viewPager.getContext());
    }

    private void checkAndInitAdapter(@NonNull ViewPager viewPager) {
        if (viewPager.getAdapter() == null) {
            throw new RuntimeException("ViewPager Adapter is null");
        }
        pagerAdapter = viewPager.getAdapter();
    }

    private void initDefaultValue(Context context) {
        indicatorHeight = dpToPx(context, 2);
        widthMode = WRAP_CONTENT;
        scrollMode = MODE_FIXED;
        textSize = 14;
    }

    @Override
    public DefaultTabVH onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = parent;
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_text_view, parent, false);
        DefaultTabVH holder = new DefaultTabVH(view);
        holder.itemView.setPadding(paddingLR, 0, paddingLR, 0);
        holder.textView.setTextSize(textSize);
        holder.itemView.setBackgroundResource(tabBackground);
        return holder;
    }

    @Override
    public void onBindViewHolder(DefaultTabVH holder, int position) {
        if (scrollMode == MODE_FIXED) {
            holder.itemView.getLayoutParams().width = parent.getMeasuredWidth() / getItemCount();
        }
        holder.textView.setText(pagerAdapter.getPageTitle(position));
    }

    @Override
    public int getItemCount() {
        return pagerAdapter.getCount();
    }

    @Override
    public void updatePageAdapter(ViewPager viewPager) {
        checkAndInitAdapter(viewPager);
    }

    @Override
    public ViewPager getViewPager() {
        return viewPager;
    }

    @Override
    public void drawIndicator(Canvas canvas, ViewMeasureHelper helper,
                              @Nullable DefaultTabVH selectedVH, @Nullable DefaultTabVH nextVH,
                              int position, float positionOffset, @NonNull Rect drawBounds) {
        switch (widthMode) {
            case MATCH_PARENT: {
                canvas.drawRect(
                        drawBounds.left,
                        drawBounds.bottom - indicatorHeight,
                        drawBounds.right,
                        drawBounds.bottom, paint);
                break;
            }

            case WRAP_CONTENT: {
                View parentSelectedView = selectedVH == null ? null : selectedVH.itemView;
                View parentNextView = nextVH == null ? null : nextVH.itemView;
                View childSelectedView = selectedVH == null ? null : selectedVH.textView;
                View childNextView = nextVH == null ? null : nextVH.textView;

                Rect rect = helper.getInternalBounds(parentSelectedView, parentNextView,
                        childSelectedView, childNextView, positionOffset);
                canvas.drawRect(
                        rect.left,
                        rect.bottom - indicatorHeight,
                        rect.right,
                        rect.bottom, paint);
                break;
            }

            case EQ_MAX_WIDTH: {
                break;
            }
        }
    }

    public void setIndicatorHeight(int indicatorHeight) {
        this.indicatorHeight = indicatorHeight;
    }

    public void setPaddingLR(int paddingLR) {
        this.paddingLR = paddingLR;
    }

    public void setWidthMode(@WidthMode int widthMode) {
        this.widthMode = widthMode;
    }

    public void setSelectedColor(int selectedColor) {
        this.selectedColor = selectedColor;
    }

    public void setNormalColor(int normalColor) {
        this.normalColor = normalColor;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public void setTabBackground(int tabBackground) {
        this.tabBackground = tabBackground;
    }

    public void setScrollMode(@ScrollMode int scrollMode) {
        this.scrollMode = scrollMode;
    }

    private int dpToPx(Context context, float dip) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip,
                context.getResources().getDisplayMetrics()));
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({MATCH_PARENT, WRAP_CONTENT, EQ_MAX_WIDTH})
    public @interface WidthMode {

    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({MODE_SCROLLABLE, MODE_FIXED})
    public @interface ScrollMode {

    }

    static class DefaultTabVH extends RecyclerView.ViewHolder {

        private TextView textView;

        DefaultTabVH(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }


}
