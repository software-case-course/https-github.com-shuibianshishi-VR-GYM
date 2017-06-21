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

public class SensorManager_Running implements SensorEventListener {
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
    private int count_run = 0;
    // 上次检测时间
    private long lastUpdateTime;

    // 构造器
    public SensorManager_Running(Context context) {
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


        // Log.d("x方向的加速度值为：", xValue+":");
//        Log.d("y方向的加速度值为：", yValue+":");
//        Log.d("z方向的加速度值为：", zValue+":");
        if (xValue>15&&sign==0){
            sign=1;
        }
        if (xValue<3&&sign==1){
            sign=0;
            count_run=count_run+1;
            if (MainActivity.SPORT_TYPE==1){
            update_count();}
            else stop();
            Log.d("次数为：", count_run+"次 ");
        }
        // TODO Auto-generated method stub
//        // 现在检测时间
//        long currentUpdateTime = System.currentTimeMillis();
//        // 两次检测的时间间隔
//        long timeInterval = currentUpdateTime - lastUpdateTime;
//        // 判断是否达到了检测时间间隔
//        if (timeInterval < UPTATE_INTERVAL_TIME) return;
//        // 现在的时间变成last时间
//        lastUpdateTime = currentUpdateTime;
//        // 获得x,y,z坐标
//        float x = event.values[0];
//        float y = event.values[1];
//        float z = event.values[2];
//        // 获得x,y,z的变化值
//        float deltaX = x - lastX;
//        float deltaY = y - lastY;
//        float deltaZ = z - lastZ;
//        // 将现在的坐标变成last坐标
//        lastX = x;
//        lastY = y;
//        lastZ = z;
//        double speed = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ
//                * deltaZ)
//                / timeInterval * 10000;
//        // 达到速度阀值，发出提示
//        if (speed >= SPEED_SHRESHOLD) {
//            count_run = count_run + 1;
//            Log.d(TAG, "你动了: " + count_run);
//        }
//        //onShakeListener.onShake();
    }
    public void update_count(){
        Sports_Count rc=new Sports_Count();
        rc.setCount(count_run);
        rc.update("586580b148", new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
//                    Toast.makeText(context,"添加数据成功",Toast.LENGTH_LONG).show();
                }else{
//                    Toast.makeText(context,"添加数据失败",Toast.LENGTH_LONG).show();
                }
            }
        });
        Sports_Count sign = new Sports_Count();
        sign.setCount(2);
        sign.update("tnAv777H", new UpdateListener() {
            @Override
            public void done(BmobException e) {

            }
        });
    }
}
