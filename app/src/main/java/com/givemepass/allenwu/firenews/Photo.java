package com.givemepass.allenwu.firenews;

/**
 * Created by Admin on 2016/8/31.
 */
public class Photo {
    public Photo(){


    }
    String title;
    String content;
    String imgvUrl;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImgvUrl(String imgvUrl) {
        this.imgvUrl = imgvUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getImgvUrl() {
        return imgvUrl;
    }
}
