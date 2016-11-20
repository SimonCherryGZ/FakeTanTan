package com.simoncherry.faketantan.event;

/**
 * Created by Simon on 2016/10/4.
 */

public class ChangePhotoEvent {

    private String url;

    public ChangePhotoEvent(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
