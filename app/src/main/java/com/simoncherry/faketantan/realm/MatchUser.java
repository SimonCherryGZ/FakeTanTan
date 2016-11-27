package com.simoncherry.faketantan.realm;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Simon on 2016/11/27.
 */

public class MatchUser extends RealmObject {

    @PrimaryKey
    private long id;
    @Required
    private String name;
    @Required
    private String word;
    @Required
    private String identify;
    @Required
    private String avatarUrl;
    @Required
    private Date matchTime;

    private boolean isHelper = false;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Date getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(Date matchTime) {
        this.matchTime = matchTime;
    }

    public boolean isHelper() {
        return isHelper;
    }

    public void setHelper(boolean helper) {
        isHelper = helper;
    }

    @Override
    public String toString() {
        return "MatchUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", word='" + word + '\'' +
                ", identify='" + identify + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", matchTime=" + matchTime +
                ", isHelper=" + isHelper +
                '}';
    }
}
