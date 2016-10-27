package com.givemepass.allenwu.firenews;

/**
 * Created by Admin on 2016/8/22.
 */
public class News {
    String date;
    String title;
    String url;
    String png;
public News(){


}
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPng() {
        return png;
    }

    public void setPng(String png) {
        this.png = png;
    }


    // not used
    @Override
    public String toString() {
        return super.toString();
    }
}
