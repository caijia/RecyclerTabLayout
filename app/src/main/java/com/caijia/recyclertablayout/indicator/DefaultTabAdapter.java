package com.caijia.recyclertablayout.indicator;

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

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.caijia.recyclertablayout.indicator.TabLayoutAttribute.MODE_EQ_MAX_WIDTH;
import static com.caijia.recyclertablayout.indicator.TabLayoutAttribute.MODE_FIXED;
import static com.caijia.recyclertablayout.indicator.TabLayoutAttribute.MODE_MATCH_PARENT;
import static com.caijia.recyclertablayout.indicator.TabLayoutAttribute.MODE_SCROLLABLE;
import static com.caijia.recyclertablayout.indicator.TabLayoutAttribute.MODE_WRAP_CONTENT;

/**
 * Created by cai.jia on 2017/5/17 0017
 */

public class DefaultTabAdapter extends TabAdapter<DefaultTabAdapter.DefaultTabVH> {

    private int tabIndicatorHeight;
    private int tabIndicatorColor;
    private int tabPaddingStart;
    private int tabPaddingTop;
    private int tabPaddingEnd;
    private int tabPaddingBottom;
    private int tabTextColor;
    private int tabTextSize;
    private int tabSelectedTextColor;
    private int tabBackground;
    private int tabMode;
    private int tabIndicatorWidthMode;
    private float tabWidthPercent;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private Paint paint;
    private ViewGroup parent;

    public DefaultTabAdapter(@NonNull ViewPager viewPager) {
        this.viewPager = viewPager;
        checkAndInitAdapter(viewPager);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
    }

    private void checkAndInitAdapter(@NonNull ViewPager viewPager) {
        if (viewPager.getAdapter() == null) {
            throw new RuntimeException("ViewPager Adapter is null");
        }
        pagerAdapter = viewPager.getAdapter();
    }

    @Override
    public DefaultTabVH onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = parent;
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_text_view, parent, false);
        return new DefaultTabVH(view);
    }

    @Override
    public void onBindViewHolder(DefaultTabVH holder, int position) {
        holder.itemView.getLayoutParams().width = tabMode == MODE_FIXED
                ? parent.getMeasuredWidth() / getItemCount() : WRAP_CONTENT;
        if (tabWidthPercent != 0) {
            holder.itemView.getLayoutParams().width = (int) (parent.getMeasuredWidth() / tabWidthPercent);
        }
        holder.textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,tabTextSize);
        holder.textView.setTextColor(tabTextColor);
        holder.textView.setPadding(tabPaddingStart, tabPaddingTop, tabPaddingEnd, tabPaddingBottom);
        holder.itemView.setBackgroundResource(tabBackground);
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
    public void onDrawTabContent(Canvas canvas, ViewSizeHelper helper,
                                 @Nullable DefaultTabVH selectedVH, @Nullable DefaultTabVH nextVH,
                                 int position, float positionOffset, @NonNull Rect drawBounds) {
        switch (tabIndicatorWidthMode) {
            case MODE_MATCH_PARENT: {
                canvas.drawRect(
                        drawBounds.left,
                        drawBounds.bottom - tabIndicatorHeight,
                        drawBounds.right,
                        drawBounds.bottom, paint);
                break;
            }

            case MODE_WRAP_CONTENT: {
                View parentSelectedView = selectedVH == null ? null : selectedVH.itemView;
                View parentNextView = nextVH == null ? null : nextVH.itemView;
                View childSelectedView = selectedVH == null ? null : selectedVH.textView;
                View childNextView = nextVH == null ? null : nextVH.textView;

                Rect rect = helper.getChildDrawBounds(parentSelectedView, parentNextView,
                        childSelectedView, childNextView, positionOffset);
                canvas.drawRect(
                        rect.left,
                        rect.bottom - tabIndicatorHeight,
                        rect.right,
                        rect.bottom, paint);
                break;
            }

            case MODE_EQ_MAX_WIDTH: {
                break;
            }
        }
    }

    public void setAttribute(TabLayoutAttribute attribute) {
        tabIndicatorHeight = attribute.tabIndicatorHeight;
        tabIndicatorColor = attribute.tabIndicatorColor;
        tabPaddingStart = attribute.tabPaddingStart;
        tabPaddingTop = attribute.tabPaddingTop;
        tabPaddingEnd = attribute.tabPaddingEnd;
        tabPaddingBottom = attribute.tabPaddingBottom;
        tabTextColor = attribute.tabTextColor;
        tabSelectedTextColor = attribute.tabSelectedTextColor;
        tabBackground = attribute.tabBackground;
        tabTextSize = attribute.tabTextSize;
        tabMode = attribute.tabMode;
        tabIndicatorWidthMode = attribute.tabIndicatorWidthMode;
        tabWidthPercent = attribute.tabWidthPercent;
        paint.setColor(tabIndicatorColor);
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({MODE_MATCH_PARENT, MODE_WRAP_CONTENT, MODE_EQ_MAX_WIDTH})
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
