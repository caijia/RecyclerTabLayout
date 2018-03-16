package com.caijia.widget.tablayout;

import android.support.annotation.DrawableRes;

/**
 * Created by cai.jia on 2018/3/16.
 */

public interface TabData {

    String getTabTitle();

    @DrawableRes
    int getTabIcon();
}
