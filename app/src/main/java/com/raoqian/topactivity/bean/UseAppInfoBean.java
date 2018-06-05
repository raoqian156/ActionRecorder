package com.raoqian.topactivity.bean;

import com.google.gson.Gson;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by raoqian on 2017/7/22.
 */

@Entity
public class UseAppInfoBean {

    @Id
    private Long id;

    @NotNull
    @Property(nameInDb = "page_name")
    private String pageName;
    private String nickName;
    private long startTime;
    private long endTime;
    private String showLable;
    private String tag;
    private int openWay;
    private int closeWay;

    public UseAppInfoBean(String pageName, String nickName, String longTime) {
        this.pageName = pageName;
        this.nickName = nickName;
        try {
            this.startTime = Long.parseLong(longTime);
        } catch (Exception e) {
            this.startTime = 0L;
        }
    }

    public UseAppInfoBean(String pageName, String nickName, long longTime) {
        this.pageName = pageName;
        this.nickName = nickName;
        this.startTime = longTime;
    }

    public UseAppInfoBean(String pageName, String nickName, int i, long longTime) {
        this.pageName = pageName;
        this.nickName = nickName;
        this.endTime = longTime;
    }

    @Generated(hash = 1893248211)
    public UseAppInfoBean(Long id, @NotNull String pageName, String nickName, long startTime,
                          long endTime, String showLable, String tag, int openWay, int closeWay) {
        this.id = id;
        this.pageName = pageName;
        this.nickName = nickName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.showLable = showLable;
        this.tag = tag;
        this.openWay = openWay;
        this.closeWay = closeWay;
    }

    @Generated(hash = 2090323655)
    public UseAppInfoBean() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getShowLable() {
        return showLable;
    }

    public void setShowLable(String showLable) {
        this.showLable = showLable;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getOpenWay() {
        return openWay;
    }

    public void setOpenWay(int openWay) {
        this.openWay = openWay;
    }

    public int getCloseWay() {
        return closeWay;
    }

    public void setCloseWay(int closeWay) {
        this.closeWay = closeWay;
    }

    public String toString() {
        return new Gson().toJson(UseAppInfoBean.this);
    }

}
