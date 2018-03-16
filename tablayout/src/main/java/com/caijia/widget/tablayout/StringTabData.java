package com.caijia.widget.tablayout;

/**
 * Created by cai.jia on 2018/3/16.
 */

public class StringTabData implements TabData {

    private String title;

    public StringTabData(String title) {
        this.title = title;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabIcon() {
        return 0;
    }
}
