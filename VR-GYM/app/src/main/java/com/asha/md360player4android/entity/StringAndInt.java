package com.asha.md360player4android.entity;

/**
 * Created by SaiHor on 2016/10/23.
 */
public class StringAndInt {
    public String Name;
    public int ID;
    public boolean view_flag;

    public StringAndInt(String Name,int ID, boolean view_flag){
        this.Name=Name;
        this.ID=ID;
        this.view_flag=view_flag;
    }

    public boolean isView_flag() {
        return view_flag;
    }

    public void setView_flag(boolean view_flag) {
        this.view_flag = view_flag;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
