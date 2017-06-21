package com.asha.md360player4android.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.asha.md360player4android.ADInfo;
import com.asha.md360player4android.BannerAdapter;
import com.asha.md360player4android.R;
import com.asha.md360player4android.RealWorld;
import com.asha.md360player4android.RealWorldAdapter;

import java.util.ArrayList;
import java.util.List;


public class SportActivity  {

    String[] gameDetail = {"日本人似乎对九龙城有很大的兴趣，居然把九龙城搬到了日本神奈川县的川崎市，并取名为『電脳九龍城』。『電脳九龍城』是以香港九龙城为原型，打造的游戏中心。无论是外观还是内部设计，都能让你仿佛身处在旧时的九龙城",
            "城堡过山车估计是很多VR入门玩家所体验到的第一个游戏，现在，可以摆脱PC的限制，在移动端尽情体验城堡过山车这个经典VR游戏了。降低硬件需求，实现不同平台的VR体验，这就是360视频的魅力了！",
            "这部全景是取自游戏《正当防卫3》的其中一个片段，《正当防卫3》（Just Cause 3）是一款由Avalanche Studios开发并于2015年发行的在pc、ps4、xboxone平台上的第三人称射击类游戏。",
            "GTA(侠盗猎车手)高清全景视频，侠盗猎车手的粉丝千万别错过！",
            "这就是未来游戏的方式吗？这段视频是《英雄联盟》游戏的360录制视频，你可以从各个角度去观察和体验游戏。",
            "《魔兽世界》暴风城是一部休闲体验类型的全景视频，视频以狮鹫的视角飞跃过暴风城。狮鹫载着摄像头冲过重重雾气，俯瞰下去，前面就是暴风城了。",
            "玩过手游部落战争吗？这是一个部落战争游戏中的场景哦，快来看看平时为你打仗的士兵是如何在战场上表现的吧！",
            "《我的世界》里总有一群不一样的人，这个VR全景视频展现了堆积如山的TNT发生爆炸的画面，玩家是相当的无聊，画面却是相当有有趣！"
    };
    String[] gameName = {"电脑九龙城","城堡过山车","正当防卫3","GTA","英雄联盟","《魔兽世界》暴风城","部落战争","我的世界"};
    int[] gamePicture = {R.drawable.kowloon,R.drawable.castleroller,R.drawable.self_defense,
            R.drawable.gta,R.drawable.lol1, R.drawable.wor,R.drawable.tribe,R.drawable.myworld };
    String []gameUrl = {"http://aranciahub.oss-cn-shenzhen.aliyuncs.com/%E7%94%B5%E8%84%91%E4%B9%9D%E9%BE%99%E5%9F%8E.mp4",
            "http://aranciahub.oss-cn-shenzhen.aliyuncs.com/%E5%9F%8E%E5%A0%A1%E8%BF%87%E5%B1%B1%E8%BD%A6.mp4",
            "http://aranciahub.oss-cn-shenzhen.aliyuncs.com/%E6%AD%A3%E5%BD%93%E9%98%B2%E5%8D%AB.mp4",
            "http://aranciahub.oss-cn-shenzhen.aliyuncs.com/GTA%28%E4%BE%A0%E7%9B%97%E7%8C%8E%E8%BD%A6%E6%89%8B%EF%BC%89.mp4",
            "http://aranciahub.oss-cn-shenzhen.aliyuncs.com/%E8%8B%B1%E9%9B%84%E8%81%94%E7%9B%9F.mp4",
            "http://aranciahub.oss-cn-shenzhen.aliyuncs.com/%E3%80%8A%E9%AD%94%E5%85%BD%E4%B8%96%E7%95%8C%E3%80%8B%E6%9A%B4%E9%A3%8E%E5%9F%8E.mp4",
            "http://aranciahub.oss-cn-shenzhen.aliyuncs.com/%E9%83%A8%E8%90%BD%E6%88%98%E4%BA%89.mp4",
            "http://aranciahub.oss-cn-shenzhen.aliyuncs.com/%E6%88%91%E7%9A%84%E4%B8%96%E7%95%8C.mp4"
    };

    String[] realDetail = {"你玩过飞行衣吗？快来《飞翼体验》感受飞行快感！",
            "喜马拉雅山脉 （梵语：hima alaya，意为雪域），藏语意为“雪的故乡”。位于青藏高原南巅边缘，是世界海拔最高的山脉,其中有110多座山峰高达或超过海拔7350米。这部全景视频可以让玩家领略“雪的故乡”的魅力。",
            "海，真的海，同北方高原那片苍茫的土地一样，凝聚着一种无法言说的神秘的生命力，给人一种超越自然的深刻。",
            "《全景过山车》是一部虚拟现实全景视频，看名字也应该了解，这是一部过山车题材的视频。视频只有不到一分钟的时间，不过就和你现实生活坐过山车的感觉一样，很快却很刺激。视频中的场景简单却高潮起伏，喜欢玩过山车的玩家可以体验一番，保准你体验过之后尖叫不断！！",
            "你有想过夜观星象么？你有过夜晚独自仰望星空么？看着一望无际的天空，在漫天的星辰下享受着夜晚的安静，也许你曾经幻想过有这样的一次旅行，不过却一直没有实现，那么现在下载这部全景视频就可以感受到啦！喜欢的话快体验吧！",
            "这个视频也许会颠覆你对鲨鱼的印象，鲨鱼看起来很温顺可爱，反倒是潜水员的行头显得面目狰狞....当然，海洋水面下的奇异景色也值得您来体验这个视频。",
            "飙车的时间到啦！你驾驶着摩托车，以最快的速度在公路上狂飙！真人版的极速摩托，快戴上VR眼镜来体验吧，不会骑？这点根本不重要，瞬间让你拥有超刺激的速度，然而不会有任何安全风险，一遍玩不够，还可以不停的玩，根本停不下来！！",
            "是否都有过被气球带上高空的野望？这个视频就可以帮你实现这种幻想，从地面起飞、穿过云层、迎接阳光。最后的彩蛋当然是气球炸裂...",
            "《滑雪技巧》是一部虚拟现实全景视频。冬天到了，寒假也要快来临了，是不是有很多同学已经开始准备想要去滑雪了呢？如果各位同学还是滑雪小白的话不妨下载这部全景视频学习一下技巧呢？这部全景视频是一个老外在滑雪场里，手持全景摄像机拍摄的全景视频，分别有5个初级滑雪技巧来教大家如何进行滑雪。如果你有这个打算那么快快下载体验吧！"
    };
    String[] realName = {"飞翼体验","喜马拉雅山","海底世界","全景过山车","午夜星空","深海鲨鱼","速度与激情","高空之旅","滑雪技巧"};
    int[] realPicture = {R.drawable.fly,R.drawable.himalaya,R.drawable.seafloor,R.drawable.roller,
            R.drawable.starrysky, R.drawable.shark,R.drawable.speed ,R.drawable.highsky,R.drawable.skiing};
    String [] realUrl={"http://aranciahub.oss-cn-shenzhen.aliyuncs.com/%E9%A3%9E%E7%BF%BC%E4%BD%93%E9%AA%8C.mp4",
            "http://aranciahub.oss-cn-shenzhen.aliyuncs.com/%E5%96%9C%E9%A9%AC%E6%8B%89%E9%9B%85.mp4",
            "http://aranciahub.oss-cn-shenzhen.aliyuncs.com/%E6%B5%B7%E5%BA%95%E4%B8%96%E7%95%8C.mp4",
            "http://aranciahub.oss-cn-shenzhen.aliyuncs.com/%E5%85%A8%E6%99%AF%E8%BF%87%E5%B1%B1%E8%BD%A6.mp4",
            "http://aranciahub.oss-cn-shenzhen.aliyuncs.com/%E6%98%9F%E7%A9%BA.mp4",
            "http://aranciahub.oss-cn-shenzhen.aliyuncs.com/%E6%B7%B1%E6%B5%B7%E9%B2%A8%E9%B1%BC.mp4",
            "http://aranciahub.oss-cn-shenzhen.aliyuncs.com/%E9%80%9F%E5%BA%A6%E4%B8%8E%E6%BF%80%E6%83%85.mp4",
            "http://aranciahub.oss-cn-shenzhen.aliyuncs.com/%E9%AB%98%E7%A9%BA%E4%B9%8B%E6%97%85.mp4",
            "http://aranciahub.oss-cn-shenzhen.aliyuncs.com/%E6%BB%91%E9%9B%AA%E6%8A%80%E5%B7%A7.mp4"
    };

    private String[] imageUrls = {"http://img.pconline.com.cn/images/upload/upc/tx/pcdlc/1606/13/c31/22752787_1465787745476.jpg",
            "http://img.pconline.com.cn/images/upload/upc/tx/pcdlc/1606/13/c10/22742379_1465783168087.jpg",
            "http://img.pconline.com.cn/images/upload/upc/tx/pcdlc/1606/29/c0/23491498_1467140664897.jpg",
            "http://img.pconline.com.cn/images/upload/upc/tx/pcdlc/1606/13/c32/22752969_1465787844784.jpg",
            "http://img.pconline.com.cn/images/upload/upc/tx/pcdlc/1606/13/c31/22752338_1465787567678.jpg"
    };

    private int [] image = {R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.four,R.drawable.five};
    private List<ADInfo> infos = new ArrayList<ADInfo>();
    private ViewPager mViewPager;
    private List<ImageView> mlist;
    private TextView mTextView;
    private LinearLayout mLinearLayout;
    private View view;
    private Context context;
    private Activity activity;
    private GridView gridView;

    // ViewPager适配器与监听器
    private BannerAdapter mAdapter;
    private BannerListener bannerListener;

    // 广告语
    private String[] bannerTexts = {};
    // 圆圈标志位
    private int pointIndex = 0;
    // 线程标志
    private boolean isStop = false;
    private int i;

    private List<RealWorld> realWorldList = new ArrayList<RealWorld>();
    private List<ImageView> views = new ArrayList<ImageView>();

    private int type;

    public SportActivity(View view,Context context,Activity activity,int type){
        this.view=view;
        this.context=context;
        this.activity=activity;
        isStop = false;
        i=0;
        this.type=type;
        init();
        setListViewHeightBasedOnChildren();
    }


    public void init() {
        initiViewPager();
        thread();
        RealWorldAdapter adapter = new RealWorldAdapter(context,activity,R.layout.viewitem,realWorldList);
         gridView = (GridView)view.findViewById(R.id.gridView);
        gridView.setAdapter(adapter);
        // 开启新线程，2秒一次更新Banner
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isStop) {
                    SystemClock.sleep(2000);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                        }
                    });
                }
            }
        }).start();
        mViewPager.setCurrentItem( 1);

    }

    public void thread(){
        mViewPager = (ViewPager) view.findViewById(R.id.CycleViewPager);
        mTextView = (TextView) view.findViewById(R.id.tv_bannertext);
        mLinearLayout = (LinearLayout) view.findViewById(R.id.points);
        bannerListener = new BannerListener();
        mViewPager.setOnPageChangeListener(bannerListener);

        view.findViewById(R.id.head_title).findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,MainActivity.class);
                context.startActivity(intent);
                int version = Integer.valueOf(android.os.Build.VERSION.SDK);
                if(version >= 5)
                {
                    activity.overridePendingTransition(R.anim.in,R.anim.out);
                }
            }
        });

        View view;
        LayoutParams params;

        for (int i = 0; i < imageUrls.length; i++) {
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls[i]);
            infos.add(info);//获取网络图片
        }
        for (int i = 0; i <infos.size(); i++) {
            // 设置广告图
            ImageView imageView = new ImageView(activity);
            imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
             imageView.setImageBitmap(infos.get(i).getBitmap());
            imageView.setBackgroundResource(image[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mlist.add(imageView);
            // 设置圆圈点
            view = new View(activity);
            params = new LayoutParams(5, 5);
            params.leftMargin = 10;
            //view.setBackgroundResource(R.drawable.point_background);
            view.setLayoutParams(params);
            view.setEnabled(false);
            mLinearLayout.addView(view);
        }

        //取中间数来作为起始位置
        int index = (Integer.MAX_VALUE / 2) - (Integer.MAX_VALUE / 2 % mlist.size());
        //用来出发监听器
        mViewPager.setCurrentItem(index);
        //  mLinearLayout.getChildAt(pointIndex).setEnabled(true);
        mAdapter = new BannerAdapter(mlist);
        mViewPager.setAdapter(mAdapter);
    }
    public void initiViewPager() {

        mlist = new ArrayList<ImageView>();

        if(type==0) {
            for (int i = 0; i < gameName.length; i++) {
                RealWorld world=new RealWorld(gameName[i], gameDetail[i], gamePicture[i],gameUrl[i]);//game
                realWorldList.add(world);
            }
        }
        else {
            for (int i = 0; i < realName.length; i++) {
                RealWorld world = new RealWorld(realName[i], realDetail[i], realPicture[i],realUrl[i]);//real
                realWorldList.add(world);
            }
        }
    }

    public void setListViewHeightBasedOnChildren() {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, gridView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }
        if(listAdapter.getCount()%2 == 0){
            totalHeight=totalHeight/2;
        }
        else {
            View listItem = listAdapter.getView(0, null, gridView);
            listItem.measure(0, 0);
            totalHeight=totalHeight/2+listItem.getMeasuredHeight();;
        }
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        gridView.setLayoutParams(params);
    }

    class BannerListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            int newPosition = position % infos.size();
            // mTextView.setText(bannerTexts[newPosition]);
            mLinearLayout.getChildAt(newPosition).setEnabled(true);
            mLinearLayout.getChildAt(pointIndex).setEnabled(false);
            // 更新标志位
            pointIndex = newPosition;

        }
        protected void onDestroy() {
            // 关闭定时器
            isStop = true;
            // super.onDestroy();
        }
    }









}
