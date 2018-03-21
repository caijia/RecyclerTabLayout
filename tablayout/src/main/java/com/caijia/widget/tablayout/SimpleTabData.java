package com.caijia.widget.tablayout;

/**
 * Created by cai.jia on 2018/3/16.
 */

public class SimpleTabData implements TabData {

    private String title;
    private int tabIcon;

    public SimpleTabData(String title, int tabIcon) {
        this.title = title;
        this.tabIcon = tabIcon;
    }

    public SimpleTabData(String title) {
        this.title = title;
    }

    public SimpleTabData() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabIcon() {
        return tabIcon;
    }

    public void setTabIcon(int tabIcon) {
        this.tabIcon = tabIcon;
    }
}
