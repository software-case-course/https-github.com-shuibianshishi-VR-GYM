package com.example.jim.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Jim斌 on 2017/2/17.
 */

public class SensorManager_Boating implements SensorEventListener {
    // 速度阈值，当摇晃速度达到这值后产生作用
    private static final int SPEED_SHRESHOLD = 4000;
    // 两次检测的时间间隔
    private static final int UPTATE_INTERVAL_TIME = 50;
    // 传感器管理器
    private SensorManager sensorManager;
    // 传感器
    private Sensor sensor;
    // 重力感应监听器
    private OnShakeListener onShakeListener;
    // 上下文对象context
    private Context context;
    // 手机上一个位置时重力感应坐标
    private float xValue;
    private float yValue;
    private float zValue;
    private int sign=0;
    private int count_boat = 0;
    // 上次检测时间
    private long lastUpdateTime;

    // 构造器
    public SensorManager_Boating(Context context) {
        // 获得监听对象
        this.context = context;
        start();
    }

    // 开始
    public void start() {
        // 获得传感器管理器
        sensorManager = (SensorManager) context
                .getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            // 获得重力传感器
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
        // 注册
        if (sensor != null) {
            sensorManager.registerListener(this, sensor,
                    SensorManager.SENSOR_DELAY_GAME);
        }
    }

    // 停止检测
    public void stop() {
        sensorManager.unregisterListener(this);
    }

    // 摇晃监听接口
    public interface OnShakeListener {
        public void onShake();
    }

    // 设置重力感应监听器
    public void setOnShakeListener(OnShakeListener listener) {
        onShakeListener = listener;
    }

    /*
     * (non-Javadoc)
     * android.hardware.SensorEventListener#onAccuracyChanged(android.hardware
     * .Sensor, int)
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
    }

    /*
     * 重力感应器感应获得变化数据
     * android.hardware.SensorEventListener#onSensorChanged(android.hardware
     * .SensorEvent)
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        xValue=Math.abs(event.values[0]);
        yValue=Math.abs(event.values[1]);
        zValue=Math.abs(event.values[2]);

        if (zValue>10&&sign==0){                //xValue>15
            sign=1;
        }
        if (zValue<3&&sign==1){                 //x<1
            sign=0;
            count_boat=count_boat+1;
            if (MainActivity.SPORT_TYPE==4){
            update_count();}
            else stop();
            Log.d("boat次数为：", count_boat+"次 ");
        }
        // TODO Auto-generated method stub
    }
    public void update_count(){
        Sports_Count bc=new Sports_Count();
        bc.setCount(count_boat);
        bc.update("747cb6292f", new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                }else{
                }
            }
        });
        Sports_Count sign = new Sports_Count();
        sign.setCount(4);
        sign.update("tnAv777H", new UpdateListener() {
            @Override
            public void done(BmobException e) {

            }
        });
    }
}
