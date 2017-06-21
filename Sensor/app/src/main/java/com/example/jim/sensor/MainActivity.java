package com.example.jim.sensor;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;


public class MainActivity extends Activity implements View.OnClickListener{

    private int[] res = {R.id.btn_squat,R.id.btn_Running,R.id.btn_Dumbbell,R.id.btn_boat,R.id.btn_Add};
    private List<ImageButton> buttonList = new ArrayList<ImageButton>();
    private boolean flag;
    private ImageButton btn=null;
    public static int SPORT_TYPE=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this, "80c583efe08701c08a2c323302339220");
        for (int i = 0; i < res.length; i++){
            if (i==4){
                btn=(ImageButton) findViewById(res[i]);
                btn.setOnClickListener(this);
                buttonList.add(btn);
            }else {
                ImageButton button = (ImageButton) findViewById(res[i]);
            button.setOnClickListener(this);
            buttonList.add(button);}
        }
//        Sports_Count count=new Sports_Count();
//        count.setName("boat");
//        count.save(new SaveListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_Add:
                if(!flag){
                    startAnimation();
                }else {
                    endAnimation();
                    btn.setImageResource(R.mipmap.add);
                }
                break;
            case R.id.btn_Running:
                SPORT_TYPE=1;
                running();
                Toast.makeText(MainActivity.this, "开始跑步体验，请将手机握在手中。", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_Dumbbell:
                SPORT_TYPE=2;
                dumbbell();
                Toast.makeText(MainActivity.this, "开始哑铃体验，请将手机固定在小臂处。", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_squat:
                SPORT_TYPE=3;
                deepSquat();
                Toast.makeText(MainActivity.this, "开始深蹲体验，请将手机固定在大腿处", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_boat:
                SPORT_TYPE=4;
                boat();
                Toast.makeText(MainActivity.this, "开始划船体验，请开始划船动作", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    private void startAnimation() {
        flag = true;
        for (int i = 0 ; i < res.length; i++){
            ObjectAnimator animator = ObjectAnimator.ofFloat(buttonList.get(i), "translationY",0f,i*200f);
            animator.setDuration(i*500);
            animator.setInterpolator(new BounceInterpolator());
            animator.start();
            if (i==4){
                btn=buttonList.get(i);
                btn.setImageResource(R.mipmap.minus);
            }
        }
    }

    private void endAnimation() {
        for (int i = 0 ; i < res.length; i++){
            ObjectAnimator animator = ObjectAnimator.ofFloat(buttonList.get(i), "translationY",i*200f,0f);
            animator.setDuration(i * 500);
            animator.start();

        }

        flag = false;
    }
//调用感应跑步、深蹲、哑铃等动作的功能函数 03\19
    public void dumbbell() {
        SensorManager_Dumbbel sensorHelper = new SensorManager_Dumbbel(this);
    }

    public void deepSquat() {
        SensorManager_DeepSquat deepSquatmanager = new SensorManager_DeepSquat(this);
    }

    public void running() {
        SensorManager_Running runningmanager = new SensorManager_Running(this);
    }
    public void boat() {
        SensorManager_Boating boatingmanager = new SensorManager_Boating(this);
    }

    //退出时初始化Bmob后台SportCount表里的数据  03\19
    public void init(){
        Sports_Count scount=new Sports_Count();
        scount.setCount(0);
        scount.update("586580b148", new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                }else{
                }
            }
        });scount.update("b3ee015f95", new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                }else{
                }
            }
        });scount.update("4b9eaa560a", new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                }else{
                }
            }
        });scount.update("tnAv777H", new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                }else{
                }
            }
        });scount.update("747cb6292f", new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                }else{
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        init();
    }
    //    public void addCount(){
//        Sports_Count sports_count=new Sports_Count();
//        sports_count.setCount(0);
//        sports_count.save(new SaveListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//
//            }
//        });
//    }
}
