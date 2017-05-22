package com.caijia.recyclertablayout.indicator;

import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by cai.jia on 2017/5/22 0022
 */

public interface TabIndicator {

    /**
     * 画tab内容
     *
     * @param helper         帮助计算View的大小,当mode == {@link TabAttribute#MODE_WRAP_CONTENT}时,注意
     *                       使用{@link ViewSizeHelper#getChildDrawBounds(View, View, View, View, float)}
     *                       这个方法来得到画的边界
     * @param mode           {@link TabAttribute#MODE_MATCH_PARENT,TabAttribute#MODE_WRAP_CONTENT}
     * @param tabId          Item里面内部的tabId
     * @param selectedView   选中的Item
     * @param nextView       选中Item的下一个Item
     * @param position       当前选中的position
     * @param positionOffset ViewPager偏移量[0,1)
     */
    void onDrawIndicator(ViewSizeHelper helper, @TabAttribute.ModeIndicatorWidth int mode,
                         @IdRes int tabId, @Nullable View selectedView, @Nullable View nextView,
                         int position, float positionOffset);

}
