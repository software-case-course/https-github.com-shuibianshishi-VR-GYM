package com.asha.md360player4android;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONObject;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.listener.ValueEventListener;


/**
 * Created by Jim斌 on 2017/3/23.
 */

public class BaseActivity extends AppCompatActivity {
    private long beforeTime, currentTime;
    public static int interval;       //时间间隔，通过这个判断数据变化速度,单位为s
    long dataChangeSpeed;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "80c583efe08701c08a2c323302339220");
        changeSpeed();
    }

    //当后台运动数据改变时感应
    public void changeSpeed() {
        final BmobRealTimeData bmobRealTimeData = new BmobRealTimeData();
        bmobRealTimeData.start(new ValueEventListener() {

            @Override
            public void onConnectCompleted(Exception e) {
                if (bmobRealTimeData.isConnected()) {
                    bmobRealTimeData.subRowUpdate("Sports_Count", "tnAv777H");
//                    bmobRealTimeData.subRowUpdate("Sports_Count", "586580b148");
                }
                Log.d("后台", "连接了: ");
            }

            @Override
            public void onDataChange(JSONObject jsonObject) {
                if (BmobRealTimeData.ACTION_UPDATEROW.equals(jsonObject.optString("action"))) {
                    final JSONObject jobject = jsonObject.optJSONObject("data");
                    Log.d("输出sign的值是：", ": "+jobject.optInt("count"));
                    sportSign(jobject.optInt("count"));
//                    bmobRealTimeData.unsubRowUpdate("Sports_Count","tnAv777H");
                }
            }
        });

    }
    public void sportSign(int i){
        final BmobRealTimeData bdata1=new BmobRealTimeData();
        switch (i){
            case 1:
                bdata1.start(new ValueEventListener() {
                    @Override
                    public void onConnectCompleted(Exception e) {
                        bdata1.subRowUpdate("Sports_Count", "b3ee015f95");//deepsquat
                    }
                    @Override
                    public void onDataChange(JSONObject jsonObject) {
                        if (BmobRealTimeData.ACTION_UPDATEROW.equals(jsonObject.optString("action"))){
                        JSONObject jobject = jsonObject.optJSONObject("data");
                        if (jobject.optInt("count") == 0) {
                            beforeTime = System.currentTimeMillis();
                        }
                        currentTime = System.currentTimeMillis();
                        dataChangeSpeed=currentTime-beforeTime;
                        beforeTime=currentTime;
                            interval= (int) (dataChangeSpeed/1000);
                        Log.d("deepsquat取出来个数为", " " + jobject.optInt("count"));
                        Log.d("deepsquat数据变化的时间为", " " + dataChangeSpeed);
                            bdata1.unsubRowUpdate("Sports_Count","b3ee015f95");

                        }
                    }
                });
                break;
            case 2:
                bdata1.start(new ValueEventListener() {
                    @Override
                    public void onConnectCompleted(Exception e) {
                        bdata1.subRowUpdate("Sports_Count", "586580b148");//run
                    }

                    @Override
                    public void onDataChange(JSONObject jsonObject) {
                        if (BmobRealTimeData.ACTION_UPDATEROW.equals(jsonObject.optString("action"))) {
                            JSONObject jobject = jsonObject.optJSONObject("data");
                            if (jobject.optInt("count") == 0) {
                                beforeTime = System.currentTimeMillis();
                            }
                            currentTime = System.currentTimeMillis();
                            dataChangeSpeed = currentTime - beforeTime;
                            beforeTime = currentTime;
                            interval= (int) (dataChangeSpeed/1000);
                            Log.d("run取出来了", " " + jobject.optInt("count"));
                            Log.d("run数据变化的时间为", " " + dataChangeSpeed);
                            bdata1.unsubRowUpdate("Sports_Count","586580b148");

                        }
                    }
                });

                break;
            case 3:
                bdata1.start(new ValueEventListener() {
                    @Override
                    public void onConnectCompleted(Exception e) {
                        bdata1.subRowUpdate("Sports_Count", "4b9eaa560a");//dumbble

                    }

                    @Override
                    public void onDataChange(JSONObject jsonObject) {
                        if (BmobRealTimeData.ACTION_UPDATEROW.equals(jsonObject.optString("action"))) {
                            JSONObject jobject = jsonObject.optJSONObject("data");
                            if (jobject.optInt("count") == 0) {
                                beforeTime = System.currentTimeMillis();
                            }
                            currentTime = System.currentTimeMillis();
                            dataChangeSpeed = currentTime - beforeTime;
                            beforeTime = currentTime;
                            interval= (int) (dataChangeSpeed/1000);
                            Log.d("interval", " " +interval);
                            Log.d("dumbble取出来了", " " + jobject.optInt("count"));
                            Log.d("dummble数据变化的时间为", " " + dataChangeSpeed+"ms");
                            bdata1.unsubRowUpdate("Sports_Count","4b9eaa560a");

                        }
                    }
                });

                break;
            default:
                break;

    }
}
}
