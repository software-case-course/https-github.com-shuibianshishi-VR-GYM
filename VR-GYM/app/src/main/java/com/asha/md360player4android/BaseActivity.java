package com.asha.md360player4android;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.bmob.v3.Bmob;


/**
 * Created by Jim斌 on 2017/3/23.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "80c583efe08701c08a2c323302339220");
//        changeSpeed();
    }


}
