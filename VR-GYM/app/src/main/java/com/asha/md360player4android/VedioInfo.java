package com.asha.md360player4android;

/**
 * Created by kg on 2016/12/18.
 */
public class VedioInfo {//视频信息类
    private int picId;
    private String name;
    private String brief;
    private String time;
    private String url;
    public VedioInfo(int picId, String time, String brief, String name,String url) {
        this.picId = picId;
        this.time = time;
        this.brief = brief;
        this.name = name;
        this.url=url;
    }

    public String getUrl() {
        return url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId= picId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }




}
