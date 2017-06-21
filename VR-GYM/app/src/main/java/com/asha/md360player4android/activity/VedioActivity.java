package com.asha.md360player4android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.asha.md360player4android.MorePopupWindow;
import com.asha.md360player4android.R;
import com.asha.md360player4android.RecommendListAdapter;
import com.asha.md360player4android.VedioInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZHENGYU on 2016/12/5.
 */
public class VedioActivity extends BaseActivity {
    private ImageView MoreMessage;
    private ImageView view;
    private ImageView Viedoview;//视频图片imageview
    private TextView VedioName;//视频名称
    private TextView VedioMessage;//视频简介
    private ListView listview;
    private List<VedioInfo> list;
    private RecommendListAdapter recommendListAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.vedio);
        view =(ImageView)findViewById(R.id.RealImage);
        view.setBackgroundResource(R.drawable.xianjian);
        listview = (ListView)findViewById(R.id.listView);
        //SharePopupWindow sharePopupWindow=new SharePopupWindow(this);
        initView();
        initListener();
        setListViewHeightBasedOnChildren();
    }
    private LinearLayout share,download;
    private void initView(){
      //  MoreMessage = (ImageView)findViewById(R.id.MoreMessage) ;
        share=(LinearLayout)findViewById(R.id.share);
        download=(LinearLayout)findViewById(R.id.download);
        Viedoview = (ImageView)findViewById(R.id.RealImage);
        VedioMessage = (TextView)findViewById(R.id.Real_Name);
        VedioName = (TextView)findViewById(R.id.Real_detail);
    }

    private void initListener(){
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(VedioActivity.this,DownloadActivity.class);
                startActivity(intent);
            }
        });

      /*  MoreMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MorePopupWindow sharePopupWindow=new MorePopupWindow(VedioActivity.this,R.layout.vedio_message);
                sharePopupWindow.showAsDropDown(view);
                sharePopupWindow.showWindow();
            }
        });
*/
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MorePopupWindow sharePopupWindow=new MorePopupWindow(VedioActivity.this,R.layout.popupwindow_share);
                sharePopupWindow.showWindow();
            }
        });

        list=new ArrayList<VedioInfo>();//推荐视频信息
        list.add(new VedioInfo(R.drawable.wor,"00:01:21","《魔兽世界》暴风城是一部休闲体验类型的全景视频，视频以狮鹫的视角飞跃过暴风城。","《魔兽世界》暴风城","http://aranciahub.oss-cn-shenzhen.aliyuncs.com/%E3%80%8A%E9%AD%94%E5%85%BD%E4%B8%96%E7%95%8C%E3%80%8B%E6%9A%B4%E9%A3%8E%E5%9F%8E.mp4"));
        list.add(new VedioInfo(R.drawable.self_defense,"00:01:30","《正当防卫3》（Just Cause 3）是一款由Avalanche Studios开发并于2015年发行的在pc、ps4、xboxone平台上的第三人称射击类游戏。","正当防卫3","http://aranciahub.oss-cn-shenzhen.aliyuncs.com/%E6%AD%A3%E5%BD%93%E9%98%B2%E5%8D%AB.mp4"));
        list.add(new VedioInfo(R.drawable.speed,"00:03:27","飙车的时间到啦！你驾驶着摩托车，以最快的速度在公路上狂飙！","速度与激情","http://aranciahub.oss-cn-shenzhen.aliyuncs.com/%E9%80%9F%E5%BA%A6%E4%B8%8E%E6%BF%80%E6%83%85.mp4"));
        list.add(new VedioInfo(R.drawable.seafloor,"00:04:35","海，真的海，同北方高原那片苍茫的土地一样，凝聚着一种无法言说的神秘的生命力，给人一种超越自然的深刻。","海底世界","http://aranciahub.oss-cn-shenzhen.aliyuncs.com/%E6%B5%B7%E5%BA%95%E4%B8%96%E7%95%8C.mp4"));
        recommendListAdapter=new RecommendListAdapter(this,list);
        listview.setAdapter(recommendListAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VedioInfo info=list.get(position);
                Viedoview.setImageResource(info.getPicId());
                VedioMessage.setText(info.getBrief());
                VedioName.setText(info.getName());
            }
        });
    }

    public void setListViewHeightBasedOnChildren() {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listview.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listview);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }
        if(listAdapter.getCount()%2 == 0){
            totalHeight=totalHeight/2;
        }
        else {
            View listItem = listAdapter.getView(0, null,listview);
            listItem.measure(0, 0);
            totalHeight=totalHeight/2+listItem.getMeasuredHeight();;
        }
        ViewGroup.LayoutParams params = listview.getLayoutParams();
        params.height = totalHeight;
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listview.setLayoutParams(params);
    }

}
