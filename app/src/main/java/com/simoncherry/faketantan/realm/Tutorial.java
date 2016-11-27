package com.simoncherry.faketantan.realm;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by Simon on 2016/11/27.
 */

public class Tutorial extends RealmObject {

    private int index;
    @Required
    private String content;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Tutorial{" +
                "index=" + index +
                ", content='" + content + '\'' +
                '}';
    }
}
