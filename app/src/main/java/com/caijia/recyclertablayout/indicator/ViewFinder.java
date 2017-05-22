package com.caijia.recyclertablayout.indicator;

import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.view.View;

/**
 * Created by cai.jia on 2017/5/22 0022
 */

public class ViewFinder {

    public static <T extends View> T find(@Nullable View rootView, @IdRes int viewId) {
        if (rootView == null) {
            return null;
        }

        Object tag = rootView.getTag();
        SparseArrayCompat<T> viewCaches;
        if (tag == null || !(tag instanceof SparseArrayCompat)) {
            viewCaches = new SparseArrayCompat<>();
            rootView.setTag(tag);

        }else {
            viewCaches = (SparseArrayCompat<T>) tag;
        }

        T cacheView = viewCaches.get(viewId,null);
        if (cacheView == null) {
            cacheView = (T) rootView.findViewById(viewId);
            viewCaches.put(viewId, cacheView);
        }
        return cacheView;
    }
}
