package com.simoncherry.faketantan.bean;

/**
 * Created by Simon on 2016/10/22.
 */

public class PhotoItem {
    public int id;
    public int sort;
    public String hyperlink;

    @Override
    public String toString() {
        return "PhotoItem{" +
                "id=" + id +
                ", sort=" + sort +
                ", hyperlink='" + hyperlink + '\'' +
                '}';
    }
}
