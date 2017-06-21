package com.example.jim.sensor;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.ValueEventListener;

public class SensorActivity extends AppCompatActivity {
    private ArrayList<String> namelist = new ArrayList<String>();
    private SensorManager sensorManager;
    private int sign = 0;
    private int count = 0;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        //第一：默认初始化
        Bmob.initialize(this, "80c583efe08701c08a2c323302339220");
        mUpdate();

        Running_Count running_count=new Running_Count();
        running_count.setCount(0);
        running_count.update("98ac203ffb", new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(SensorActivity.this,"更新数据成功",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(SensorActivity.this,"更新数据失败",Toast.LENGTH_LONG).show();
                }
            }
        });


//        running_count.save(new SaveListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//                if(e==null){
//                    Toast.makeText(SensorActivity.this,"添加数据成功",Toast.LENGTH_LONG).show();
//                }else{
//                    Toast.makeText(SensorActivity.this,"添加数据失败",Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//        /*sensorManager= (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//        Sensor magneticSensor=sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
//        Sensor accelerometerSensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        sensorManager.registerListener(listener,magneticSensor,SensorManager.SENSOR_DELAY_GAME);
//        sensorManager.registerListener(listener,accelerometerSensor,SensorManager.SENSOR_DELAY_GAME);
//*/
        namelist.add("哑铃");
        namelist.add("跑步");
        namelist.add("深蹲");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SensorActivity.this,
                R.layout.support_simple_spinner_dropdown_item, namelist);
        ListView lv_sensor = (ListView) findViewById(R.id.lv_sensor);
        lv_sensor.setAdapter(adapter);
        lv_sensor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (namelist.get(position).equals("哑铃")) {
                    Toast.makeText(SensorActivity.this, "开始感应哑铃动作", Toast.LENGTH_SHORT).show();
                    //SensorManager_Dumbbel sensorManager_dumbbel=new SensorManager_Dumbbel(SensorActivity.this);
                    Dumbbell();
                }
                if (namelist.get(position).equals("跑步")) {
                    Toast.makeText(SensorActivity.this, "开始感应跑步动作", Toast.LENGTH_SHORT).show();
                    Running();
                }
                if (namelist.get(position).equals("深蹲")) {
                    Toast.makeText(SensorActivity.this, "开始感应深蹲动作", Toast.LENGTH_SHORT).show();
                    DeepSquat();
                }
            }
        });
    }

    public void mUpdate(){
        final BmobRealTimeData rtd = new BmobRealTimeData();
        rtd.start(new ValueEventListener() {
            @Override
            public void onConnectCompleted(Exception e) {
                if(rtd.isConnected()){
                    // 监听表更新
                    rtd.subRowUpdate("Running_Count","98ac203ffb");
                }
            }

            @Override
            public void onDataChange(JSONObject jsonObject) {
                if(BmobRealTimeData.ACTION_UPDATEROW.equals(jsonObject.optString("action"))){
                    Log.d("发生了变化", "changed: ");
                }
            }
        });
    }

    public void Dumbbell() {
        SensorManager_Dumbbel sensorHelper = new SensorManager_Dumbbel(this);
    }

    public void DeepSquat() {
        SensorManager_DeepSquat deepSquatmanager = new SensorManager_DeepSquat(this);
    }

    public void Running() {
        SensorManager_Running runningmanager = new SensorManager_Running(this);
    }public void boat() {
        SensorManager_Boating boatingmanager = new SensorManager_Boating(this);
    }
}
