package com.asha.md360player4android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.asha.vrlib.MD360Director;
import com.asha.vrlib.MD360DirectorFactory;
import com.asha.vrlib.MDVRLibrary;
import com.asha.vrlib.model.BarrelDistortionConfig;
import com.asha.vrlib.model.MDPinchConfig;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.listener.ValueEventListener;
import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * Created by hzqiujiadi on 16/4/5.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class VideoPlayerActivity extends MD360PlayerActivity {

    private static final String TAG = "VideoPlayerActivity";
    private MediaPlayerWrapper mMediaPlayerWrapper = new MediaPlayerWrapper();
    private ImageView MoreMessage;
    private ImageView view;
    private TextView VedioName;//视频名称
    private TextView VedioMessage;//视频简介
    private ImageView Viedoview;//视频图片imageview
    private ListView listview;
    private List<VedioInfo> list;
    private RecommendListAdapter recommendListAdapter;

    private Button backOff;//增加的快进按钮    2017/3/15
    private Button goAhead;//增加的减速按钮    2017/3/15

    private float speed;//速度               2017/3/15

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



    }

    private void listen_Count(){
        final BmobRealTimeData bd=new BmobRealTimeData();
        bd.start(new ValueEventListener() {
            @Override
            public void onConnectCompleted(Exception e) {
                Log.d("bmob", "连接成功:"+bd.isConnected());
                if (bd.isConnected()){
                    bd.subRowUpdate("Running_Count","4b9eaa560a");
                }
            }

            @Override
            public void onDataChange(JSONObject jsonObject) {
                if (BmobRealTimeData.ACTION_UPDATEROW.equals(jsonObject.optString("updateRow"))){
//                        count=count+1;
                        Log.d("数据更新了：",": ");
                }
            }
        });
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

        Intent intent=getIntent();
//        Viedoview.setBackgroundResource(intent.getIntExtra("imageId",R.drawable.xianjian));
        VedioMessage.setText(intent.getStringExtra("detail"));
        VedioName.setText(intent.getStringExtra("name"));

        //backOff
        backOff=(Button) findViewById(R.id.backOff);//     2017/3/15
        //goAhead
        goAhead=(Button)findViewById(R.id.goAhead);//     2017/3/15

     //   speed=1.0f;
    }

    private void initListener(){
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
        backOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(speed>0.1f)
                    speed=speed-0.1f;
                else
                    speed=0.1f;
                Log.d("speed","speed"+speed);
                mMediaPlayerWrapper.setSpeed(speed);
            }
        });

        // 2017/3/15
        goAhead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speed=speed+0.1f;
                Log.d("speed","speed"+speed);
                mMediaPlayerWrapper.setSpeed(speed);
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMediaPlayerWrapper.resume();
    }
}
