package com.simoncherry.faketantan.sp;

import com.baoyz.treasure.Preferences;
import com.simoncherry.faketantan.bean.PhotoItem;

import java.util.List;

/**
 * Created by Simon on 2016/10/22.
 */

@Preferences
public interface UserData {

    String getPhone();
    void setPhone(String phone);

    String getNickName();
    void setNickName(String nickName);

    String getSex();
    void setSex(String sex);

    String getBirthday();
    void setBirthday(String birthday);

    int getAge();
    void setAge(int age);

    String getStar();
    void setStar(String star);

    int getLikeCount();
    void setLikeCount(int count);

    String getIndustry();
    void setIndustry(String industry);

    String getJob();
    void setJob(String job);

    String getCompany();
    void setCompany(String company);

    String getFrom();
    void setFrom(String from);

    String getMarker();
    void setMarker(String marker);

    String getSign();
    void setSign(String sign);

    List<String> getTagSign();
    void setTagSign(List<String> tagSign);

    List<Integer> getIndexSign();
    void setIndexSign(List<Integer> indexSign);

    List<String> getTagSport();
    void setTagSport(List<String> tagSport);

    List<Integer> getIndexSport();
    void setIndexSport(List<Integer> indexSport);

    List<String> getTagMusic();
    void setTagMusic(List<String> tagMusic);

    List<Integer> getIndexMusic();
    void setIndexMusic(List<Integer> indexMusic);

    List<String> getTagFood();
    void setTagFood(List<String> tagFood);

    List<Integer> getIndexFood();
    void setIndexFood(List<Integer> indexFood);

    List<String> getTagMovie();
    void setTagMovie(List<String> tagMovie);

    List<Integer> getIndexMovie();
    void setIndexMovie(List<Integer> indexMovie);

    List<String> getTagBook();
    void setTagBook(List<String> tagBook);

    List<Integer> getIndexBook();
    void setIndexBook(List<Integer> indexBook);

    List<String> getTagTravel();
    void setTagTravel(List<String> tagTravel);

    List<Integer> getIndexTravel();
    void setIndexTravel(List<Integer> indexTravel);

    List<PhotoItem> getPhotoItem();
    void setPhotoItem(List<PhotoItem> photoItem);
}
