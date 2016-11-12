package com.example.liujiachao.gank.entity;

/**
 * Created by liujiachao on 2016/11/8.
 */
public class Tab {

    private String title;
    private Class fragment;

    public Tab(String title, Class fragment) {
        this.title = title;
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class getFragment() {
        return fragment;
    }

    public void setFragment(Class fragment) {
        this.fragment = fragment;
    }
}
