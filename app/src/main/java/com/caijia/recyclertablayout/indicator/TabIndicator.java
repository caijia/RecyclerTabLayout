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
     * @param mode           {@link TabAttribute#MODE_MATCH_PARENT,TabAttribute#MODE_WRAP_CONTENT},
     *                       当mode == {@link TabAttribute#MODE_WRAP_CONTENT}时,确保设置tabId
     * @param tabId          Item里面内部的tabId
     * @param selectedView   选中的Item,当找内部View时,建议使用{@link ViewFinder#find(View, int)}来获取,
     *                       内部使用缓存
     * @param nextView       选中Item的下一个Item 当找内部View时,
     *                       建议使用{@link ViewFinder#find(View, int)}来获取，内部使用缓存
     * @param position       当前选中的position
     * @param positionOffset ViewPager偏移量[0,1)
     */
    void onDrawIndicator(ViewSizeHelper helper, @TabAttribute.ModeIndicatorWidth int mode,
                         @IdRes int tabId, @Nullable View selectedView, @Nullable View nextView,
                         int position, float positionOffset);

}
