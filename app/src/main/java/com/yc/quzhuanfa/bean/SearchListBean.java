package com.yc.quzhuanfa.bean;


import org.litepal.crud.LitePalSupport;

public class SearchListBean extends LitePalSupport {

     private String title;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
