package com.simoncherry.faketantan.bean;

import java.util.List;

/**
 * Created by Simon on 2016/11/7.
 */

public class ChatMsgBean {

    private String name;
    private String content;
    private boolean isSend;
    private List<TutorialBean> tutorialList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    public List<TutorialBean> getTutorialList() {
        return tutorialList;
    }

    public void setTutorialList(List<TutorialBean> tutorialList) {
        this.tutorialList = tutorialList;
    }
}
