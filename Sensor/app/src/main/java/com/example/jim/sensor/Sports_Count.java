package com.example.jim.sensor;

import cn.bmob.v3.BmobObject;

/**
 * Created by Jim斌 on 2017/3/19.
 */

public class Sports_Count extends BmobObject {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
