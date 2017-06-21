package com.asha.md360player4android.entity;

/**
 * Created by ZHENGYU on 2016/11/27.
 */
public class RealWorld{
    private  String name;
    private  int imageId;
    private  String detail;
    private  String url;
    public RealWorld(String name,String detail,int imageId,String url){
        this.name = name;
        this.imageId = imageId;
        this.detail = detail;
        this.url=url;
    }

    public String getUrl() {
        return url;
    }

    public String getName(){
        return name;
    }

    public int getImageId(){
        return imageId;
    }

    public String getDetail(){
        return detail;
    }
}
