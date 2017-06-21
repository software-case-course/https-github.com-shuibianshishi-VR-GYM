package com.asha.md360player4android.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.asha.md360player4android.MediaPlayerWrapper;
import com.asha.md360player4android.RecommendListAdapter;
import com.asha.md360player4android.VedioInfo;
import com.asha.vrlib.MD360Director;
import com.asha.vrlib.MD360DirectorFactory;
import com.asha.vrlib.MDVRLibrary;
import com.asha.vrlib.model.BarrelDistortionConfig;
import com.asha.vrlib.model.MDPinchConfig;
import com.asha.vrlib.plugins.hotspot.IMDHotspot;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.listener.ValueEventListener;
import tv.danmaku.ijk.media.player.IMediaPlayer;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by hzqiujiadi on 16/4/5.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class VideoPlayerActivity extends MD360PlayerActivity {

    private static final String TAG = "VideoPlayerActivity";
    private static boolean ISPAUSE = false;
    private MediaPlayerWrapper mMediaPlayerWrapper = new MediaPlayerWrapper();
    private ImageView MoreMessage;

    private ImageView view;
    private TextView VedioName;//视频名称
    private TextView VedioMessage;//视频简介
    private ImageView Viedoview;//视频图片imageview
    private TextView video_tool_tvTime;//视频时间
    private SeekBar video_tool_Seekbar;//视频进度条
    private String videoTimeString = null;     // 时间长度文本
    private  boolean isplaying=true;//是否正在播放
    private boolean seekBarTouched;  //是否触动进度条
    private ListView listview;
    private List<VedioInfo> list;
    private RecommendListAdapter recommendListAdapter;

    /*private Button backOff;//增加的快进按钮    2017/3/15
    private Button goAhead;//增加的减速按钮    2017/3/15*/

    public float speed=1.0f;//速度               2017/3/15

    private long beforeTime, currentTime;
    public static int interval;       //时间间隔，通过这个判断数据变化速度,单位为s
    long dataChangeSpeed;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listview = (ListView)findViewById(R.id.listView);
        initView();
        initListener();
        mMediaPlayerWrapper.init();
        mMediaPlayerWrapper.setPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer mp) {
                cancelBusy();
                if (getVRLibrary() != null){
                    getVRLibrary().notifyPlayerChanged();
                }
                setInfo();
                new Thread() {
                    public void run() {
                        isplaying = true;
                        while (isplaying) {

                            updateCurrentPosition();
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }

                    };

                }.start();
            }
        });

        mMediaPlayerWrapper.getPlayer().setOnErrorListener(new IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(IMediaPlayer mp, int what, int extra) {
                String error = String.format("Play Error what=%d extra=%d",what,extra);
                Toast.makeText(VideoPlayerActivity.this, error, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        mMediaPlayerWrapper.getPlayer().setOnVideoSizeChangedListener(new IMediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sar_num, int sar_den) {
                getVRLibrary().onTextureResize(width, height);
            }
        });

        //Uri uri = getUri();
        String uri=getIntent().getStringExtra("url");
        if (uri != null){
            mMediaPlayerWrapper.openRemoteFile(uri);
            mMediaPlayerWrapper.prepare();
        }
        mMediaPlayerWrapper.setPlayerCallback(new MediaPlayerWrapper.PlayerCallback() {
            @Override
            public void updateProgress() {
                updateCurrentPosition();
            }

            @Override
            public void updateInfo() {
                setInfo();
            }

            @Override
            public void requestFinish() {

            }
        });
        changeSpeed();

    }
    //通过判断interval来改变速度
    private void dataChangeSpeed(int sign){
                    if (sign<3&&speed<5.0f){
                        if (ISPAUSE)
                            mMediaPlayerWrapper.resume();
                        else
                        {
                            speed=speed+1.0f;
                            mMediaPlayerWrapper.setSpeed(speed);
                            Log.d(TAG, "speed: "+speed);
                        }
                    } else if (sign>3&& sign<6&&speed>1.0f){
                        speed=speed-1.5f;
                        mMediaPlayerWrapper.setSpeed(speed);
                        Log.d(TAG, "speed: "+speed);
                    }else if (sign>10&&speed>1.0f){
                        speed=1.0f;
                        mMediaPlayerWrapper.setSpeed(speed);
                        mMediaPlayerWrapper.pause();
                        Log.d(TAG, "speed: "+speed);
                    }
    }

    //当后台运动数据改变时感应
    public void changeSpeed() {
        final BmobRealTimeData bmobRealTimeData = new BmobRealTimeData();
        bmobRealTimeData.start(new ValueEventListener() {

            @Override
            public void onConnectCompleted(Exception e) {
                if (bmobRealTimeData.isConnected()) {
                    bmobRealTimeData.subRowUpdate("Sports_Count", "tnAv777H");
                }
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
                            dataChangeSpeed(interval);
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
                            dataChangeSpeed(interval);
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
                            dataChangeSpeed(interval);
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
    private LinearLayout share,download;
    private void initView(){
       // view =(ImageView)findViewById(R.id.RealImage);
     //   view.setBackgroundResource(R.drawable.xianjian);
        MoreMessage = (ImageView)findViewById(R.id.MoreMessage) ;
        share=(LinearLayout)findViewById(R.id.share);
        download=(LinearLayout)findViewById(R.id.download);
        Viedoview = (ImageView)findViewById(R.id.RealImage);
        VedioMessage = (TextView)findViewById(R.id.Real_Name);
        VedioName = (TextView)findViewById(R.id.Real_detail);
        video_tool_tvTime=(TextView)findViewById(R.id.video_tool_tvTime);
        video_tool_Seekbar=(SeekBar)findViewById(R.id.video_tool_Seekbar);


        Intent intent=getIntent();
//        Viedoview.setBackgroundResource(intent.getIntExtra("imageId",R.drawable.xianjian));
        VedioMessage.setText(intent.getStringExtra("detail"));
        VedioName.setText(intent.getStringExtra("name"));

        //backOff
        /*backOff=(Button) findViewById(R.id.backOff);//     2017/3/15
        //goAhead
        goAhead=(Button)findViewById(R.id.goAhead);//     2017/3/15*/

     //   speed=1.0f;
    }

    private void initListener(){

        video_back_btn.setOnClickListener(new View.OnClickListener() {//返回键
            @Override
            public void onClick(View view) {
                if(!IsVertical)setRequestedOrientation(false);//原来为横屏，变为竖屏
                else finish();
            }
        });

        fullVedio.setOnClickListener(new View.OnClickListener() {//横屏
            @Override
            public void onClick(View v) {
                if(IsVertical) {
                    setRequestedOrientation(IsVertical);
                }
            }
        });


        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(VideoPlayerActivity.this,DownloadActivity.class);
                startActivity(intent);
            }
        });

       MoreMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MorePopupWindow sharePopupWindow=new MorePopupWindow(VideoPlayerActivity.this,R.layout.vedio_message);
                sharePopupWindow.showAsDropDown(view);
                sharePopupWindow.showWindow();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MorePopupWindow sharePopupWindow=new MorePopupWindow(VideoPlayerActivity.this,R.layout.popupwindow_share);
                sharePopupWindow.showWindow();
            }
        });

        video_tool_Seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
               if(mMediaPlayerWrapper.getPlayer()!=null)
                seekBarTouched=true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(mMediaPlayerWrapper.getPlayer()!=null) {
                    mMediaPlayerWrapper.getPlayer().seekTo(seekBar.getProgress());
                    seekBarTouched = false;
                }
               // startHideControllerTimer();
            }
        });

        video_tool_tbtnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(IsStart){
                    mMediaPlayerWrapper.pause();
                    IsStart=false;
                }else {
                    mMediaPlayerWrapper.start();
                    IsStart=true;
                }
            }
        });

        list=new ArrayList<VedioInfo>();//推荐视频信息
        list.add(new VedioInfo(R.drawable.wor,"00:01:21","《魔兽世界》暴风城","《魔兽世界》暴风城","http://aranciahub.oss-cn-shenzhen.aliyuncs.com/%E3%80%8A%E9%AD%94%E5%85%BD%E4%B8%96%E7%95%8C%E3%80%8B%E6%9A%B4%E9%A3%8E%E5%9F%8E.mp4"));
        list.add(new VedioInfo(R.drawable.seafloor,"00:04:35","海，真的海","海底世界","http://aranciahub.oss-cn-shenzhen.aliyuncs.com/%E6%B5%B7%E5%BA%95%E4%B8%96%E7%95%8C.mp4"));
        list.add(new VedioInfo(R.drawable.speed,"00:03:27","飙车的时间到啦！","速度与激情","http://aranciahub.oss-cn-shenzhen.aliyuncs.com/%E9%80%9F%E5%BA%A6%E4%B8%8E%E6%BF%80%E6%83%85.mp4"));
        list.add(new VedioInfo(R.drawable.self_defense,"00:01:30","《正当防卫3》","正当防卫3","http://aranciahub.oss-cn-shenzhen.aliyuncs.com/%E6%AD%A3%E5%BD%93%E9%98%B2%E5%8D%AB.mp4"));
        recommendListAdapter=new RecommendListAdapter(this,list);
        listview.setAdapter(recommendListAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VedioInfo info=list.get(position);
                VedioMessage.setText(info.getBrief());
                VedioName.setText(info.getName());
                mMediaPlayerWrapper.pause();
                mMediaPlayerWrapper.destroy();
                mMediaPlayerWrapper.init();
                mMediaPlayerWrapper.openRemoteFile(info.getUrl());
                mMediaPlayerWrapper.prepare();
             //   Viedoview.setImageResource(info.getPicId());

            }
        });

        //2017/3/15
       /* backOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(speed>0.1f)
                    speed=speed-0.1f;


                else
            { mMediaPlayerWrapper.pause(); ISPAUSE=true;}
                    speed=0.0f;
                Log.d("speed","speed"+speed);
                mMediaPlayerWrapper.setSpeed(speed);
            }
        });

        // 2017/3/15
        goAhead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ISPAUSE)
                    mMediaPlayerWrapper.resume();
                else
                    speed=speed+0.1f;
                Log.d("speed","speed"+speed);
                mMediaPlayerWrapper.setSpeed(speed);
            }
        });*/

        getVRLibrary().setEyePickChangedListener(new MDVRLibrary.IEyePickListener() {
            @Override
            public void onHotspotHit(IMDHotspot hotspot, long hitTimestamp) {
                String text = hotspot == null ? "nop" : String.format(Locale.CHINESE, "%s  %fs", hotspot.getTitle(), (System.currentTimeMillis() - hitTimestamp) / 1000.0f );
               // hotspotText.setText(text);
                String brief = getVRLibrary().getDirectorBrief().toString();
                //directorBriefText.setText(brief);

                if (System.currentTimeMillis() - hitTimestamp > 3000){
                    getVRLibrary().resetEyePick();
                    if(hotspot!=null) {
                        if(IsStart){
                            mMediaPlayerWrapper.pause();
                            IsStart=false;
                            video_tool_tbtnPlayPause.setChecked(false);
                        }else {
                            mMediaPlayerWrapper.start();
                            IsStart=true;
                            video_tool_tbtnPlayPause.setChecked(true);
                        }
                    }
                }
            }
        });

    }

    @Override
    protected MDVRLibrary createVRLibrary() {
        return MDVRLibrary.with(this)
                .displayMode(MDVRLibrary.DISPLAY_MODE_NORMAL)
                .interactiveMode(MDVRLibrary.INTERACTIVE_MODE_MOTION)
                .asVideo(new MDVRLibrary.IOnSurfaceReadyCallback() {
                    @Override
                    public void onSurfaceReady(Surface surface) {
                        mMediaPlayerWrapper.setSurface(surface);
                    }
                })
                .ifNotSupport(new MDVRLibrary.INotSupportCallback() {
                    @Override
                    public void onNotSupport(int mode) {
                        String tip = mode == MDVRLibrary.INTERACTIVE_MODE_MOTION
                                ? "onNotSupport:MOTION" : "onNotSupport:" + String.valueOf(mode);
                        Toast.makeText(VideoPlayerActivity.this, tip, Toast.LENGTH_SHORT).show();
                    }
                })
                .pinchConfig(new MDPinchConfig().setMin(1.0f).setMax(8.0f).setDefaultValue(0.1f))
                .pinchEnabled(true)
                .directorFactory(new MD360DirectorFactory() {
                    @Override
                    public MD360Director createDirector(int index) {
                        return MD360Director.builder().setPitch(90).build();
                    }
                })
                .projectionFactory(new CustomProjectionFactory())
                .barrelDistortionConfig(new BarrelDistortionConfig().setDefaultEnabled(false).setScale(0.95f))
                .build(R.id.gl_view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaPlayerWrapper.pause();
        mMediaPlayerWrapper.destroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMediaPlayerWrapper.pause();
        ISPAUSE=true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMediaPlayerWrapper.resume();
        ISPAUSE=false;
    }

    private void dataChange(){
        BmobRealTimeData bdata=new BmobRealTimeData();
        bdata.start(new ValueEventListener() {
            @Override
            public void onConnectCompleted(Exception e) {
            }

            @Override
            public void onDataChange(JSONObject jsonObject) {
                if (BmobRealTimeData.ACTION_UPDATEROW.equals(jsonObject.optString("action"))){
                }
            }
        });
    }

    public void updateCurrentPosition()
    {
        // 发出更新进度条的message
        handleProgress.sendEmptyMessage(0);
    }

    /**
     * 设置时间和进度条初始信息
     */
    public void setInfo() {
        Log.v("setInfo()","1");
        int duration = 0;
        if (mMediaPlayerWrapper.getPlayer() != null)
        {
            duration = (int)mMediaPlayerWrapper.getPlayer().getDuration();
            Log.v("setInfo()","duration");
        }
        if (duration == video_tool_Seekbar.getMax())
        {
            return;
        }
        // 设置控制条,放在加载完成以后设置，防止获取getDuration()错误
        video_tool_Seekbar.setProgress(0);
        video_tool_Seekbar.setMax(duration);
        // 设置播放时间
        Log.v("setInfo()",String.valueOf(duration));
        videoTimeString = Utils.getShowTime(duration);
        Log.v("setInfo()",videoTimeString);
        video_tool_tvTime.setText("00:00:00/" + videoTimeString);
    }

    /*******************************************************
     * 通过Handler来更新进度条
     ******************************************************/
    private Handler handleProgress = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 0://更新进度条
                    if (mMediaPlayerWrapper.getPlayer() != null)
                    {
                        int position = (int) mMediaPlayerWrapper.getPlayer().getCurrentPosition();
                        Log.v("setInfo()",String.valueOf(position)+"  "+videoTimeString);
                        if (position >= 0 && videoTimeString != null)
                        {
                            video_tool_Seekbar.setProgress(position);
                            // 设置播放时间
                            String cur = Utils.getShowTime(position);
                            video_tool_tvTime.setText(cur + "/" + videoTimeString);
                            Log.v("setInfo()",cur);
                        }
                    }
                    break;
            }

        };
    };

    public boolean onKeyDown(int keyCode, KeyEvent event) { // 监听HOME和返回键
        // TODO Auto-generated method stub
        super.onKeyDown(keyCode, event);
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if(!IsVertical)setRequestedOrientation(false);//原来为横屏，变为竖屏
                return true;
            case KeyEvent.KEYCODE_HOME:
                break;
            default:
                break;
        }
        return false;
    }


    public void setRequestedOrientation( boolean type){// true 则变为横屏，false 则变为竖屏
        LinearLayout.LayoutParams linearParams =  (LinearLayout.LayoutParams)vedio_view.getLayoutParams();
        RelativeLayout.LayoutParams playParams = (RelativeLayout.LayoutParams) video_tool_rlPlayProg.getLayoutParams();
        if (type == false) {
            linearParams.height = vedio_view_height;
            vedio_view.setLayoutParams(linearParams);
            IsVertical = true;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
            video_tool_tbtnVR.setVisibility(View.GONE);
            video_tool_tbtnGyro.setVisibility(View.GONE);
            fullVedio = (ImageView) findViewById(R.id.video_tool_imgFullscreen);
            fullVedio.setVisibility(View.VISIBLE);
            playParams.addRule(RelativeLayout.LEFT_OF, R.id.video_tool_imgFullscreen);
            video_tool_rlPlayProg.setLayoutParams(playParams);

        } else if (type == true) {//LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) vedio_view.getLayoutParams();
            linearParams.height = MATCH_PARENT;
            vedio_view_height = vedio_view.getHeight();
            vedio_view.setLayoutParams(linearParams);
            IsVertical = false;
            fullVedio.setVisibility(View.GONE);
            video_tool_tbtnVR.setVisibility(View.VISIBLE);
            video_tool_tbtnGyro.setVisibility(View.VISIBLE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
            playParams = (RelativeLayout.LayoutParams) video_tool_rlPlayProg.getLayoutParams();
            playParams.addRule(RelativeLayout.LEFT_OF, R.id.video_tool_tbtnGyro);
            video_tool_rlPlayProg.setLayoutParams(playParams);

        }
    }

}
