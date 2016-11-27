package com.simoncherry.faketantan.realm;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Simon on 2016/11/25.
 */

public class ChatMessage extends RealmObject {

    @PrimaryKey
    private long id;
    @Required
    private String conversation;
    @Required
    private String textFrom;
    @Required
    private String textTo;
    @Required
    private String name;
    @Required
    private String content;
    @Required
    private Date textTime;

    private boolean isSend;

    private RealmList<Tutorial> tutorialList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getConversation() {
        return conversation;
    }

    public void setConversation(String conversation) {
        this.conversation = conversation;
    }

    public String getTextFrom() {
        return textFrom;
    }

    public void setTextFrom(String textFrom) {
        this.textFrom = textFrom;
    }

    public String getTextTo() {
        return textTo;
    }

    public void setTextTo(String textTo) {
        this.textTo = textTo;
    }

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

    public Date getTextTime() {
        return textTime;
    }

    public void setTextTime(Date textTime) {
        this.textTime = textTime;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    public RealmList<Tutorial> getTutorialList() {
        return tutorialList;
    }

    public void setTutorialList(RealmList<Tutorial> tutorialList) {
        this.tutorialList = tutorialList;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "id=" + id +
                ", conversation='" + conversation + '\'' +
                ", textFrom='" + textFrom + '\'' +
                ", textTo='" + textTo + '\'' +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", textTime=" + textTime +
                ", isSend=" + isSend +
                '}';
    }
}
