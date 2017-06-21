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

public class SensorManager_Dumbbel implements SensorEventListener {

    private int sign = 0;
    private int count_dumbbel = 0;
    float[] accelerometerValues = new float[3];
    float[] magneticValues = new float[3];
    // 传感器管理器
    private SensorManager sensorManager;
    // 传感器
    private Sensor magneticSensor, accelerometerSensor;
    // 重力感应监听器
    private OnShakeListener onShakeListener;
    // 上下文对象context
    private Context context;
    // 手机上一个位置时重力感应坐标

    public SensorManager_Dumbbel(Context context) {
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
            magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
        // 注册
        if (magneticSensor != null) {
            sensorManager.registerListener(this, magneticSensor,
                    SensorManager.SENSOR_DELAY_GAME);
        }
        if (accelerometerSensor != null) {
            sensorManager.registerListener(this, accelerometerSensor,
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
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelerometerValues = event.values.clone();
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            magneticValues = event.values.clone();
        }
        float[] R = new float[9];
        float[] values = new float[3];
        SensorManager.getRotationMatrix(R, null, accelerometerValues, magneticValues);
        SensorManager.getOrientation(R, values);

        if (Math.toDegrees(values[1]) > 40 && sign == 0) {
            sign = 1;
            // Log.d("SensorActivity", "sign: "+sign+Math.toDegrees(values[1]));

        }
        if (sign == 1 && Math.toDegrees(values[1]) < 2) {
            sign = 0;
            count_dumbbel = count_dumbbel + 1;
//            onShakeListener.onShake();
            if (MainActivity.SPORT_TYPE==2){
            update_count();}
            else stop();
            Log.d("次数：", count_dumbbel + "次");
            // Log.d("SensorActivity", "sign: "+sign+"count"+count+Math.toDegrees(values[1]));
            // Toast.makeText(context,count,Toast.LENGTH_SHORT).show();

        }
    }
    public void update_count(){
        Sports_Count dc=new Sports_Count();
        dc.setCount(count_dumbbel);
        dc.update("4b9eaa560a", new UpdateListener() {
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
        sign.setCount(3);
        sign.update("tnAv777H", new UpdateListener() {
            @Override
            public void done(BmobException e) {

            }
        });
    }
}
