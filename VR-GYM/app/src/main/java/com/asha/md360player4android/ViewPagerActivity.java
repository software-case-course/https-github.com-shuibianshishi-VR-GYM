package com.asha.md360player4android;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;

public class ViewPagerActivity extends BaseActivity implements View.OnClickListener ,RadioGroup.OnCheckedChangeListener{


    private ImageView Dss_ImageView;
    private RadioButton test2,test3,test4;
    private RadioGroup DssGrounp;
    private ViewPager Dss_viewPager;
    private List<View> viewList;
    private View Test2,Test3,Test4,MainView;
    private MyViewPagerAdapter myViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        getSupportActionBar().hide();
       // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        Bmob.initialize(this, "80c583efe08701c08a2c323302339220");

        initView();
        initViewList();
        initListAdapter();
        initListContent();
        test2.setChecked(true);
        Dss_viewPager.setCurrentItem(0);



        //DssGrounp.setLayoutParams(linearParams);
        DssGrounp.setOnCheckedChangeListener(this);
        Dss_viewPager.setOnPageChangeListener(new MyPagerOnPageChangeListener());

    }

    private void initListContent(){
        SportActivity gameSport=new SportActivity(Test2,this,ViewPagerActivity.this,0);
        SportActivity realSport=new SportActivity(Test3,this,this,1);
        Demo demo=new Demo(Test4,this);
    }

    private void initView(){
        DssGrounp=(RadioGroup)findViewById(R.id.DssGrounp);

        test2=(RadioButton)findViewById(R.id.SelectBook);
        test3=(RadioButton)findViewById(R.id.DonateBook);
        test4=(RadioButton)findViewById(R.id.SellBook);
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) test2.getLayoutParams();
        linearParams.width=dm.widthPixels/3;
        test2.setLayoutParams(linearParams);

        LinearLayout.LayoutParams linearParams2 = (LinearLayout.LayoutParams) test3.getLayoutParams();
        linearParams.width=dm.widthPixels/3;
        test3.setLayoutParams(linearParams);

        LinearLayout.LayoutParams linearParams3 = (LinearLayout.LayoutParams) test4.getLayoutParams();
        linearParams.width=dm.widthPixels/3;
        test4.setLayoutParams(linearParams);


        Dss_ImageView=(ImageView)findViewById(R.id.Dss_ImageView);
        RelativeLayout.LayoutParams linearParams4 = (RelativeLayout.LayoutParams) Dss_ImageView.getLayoutParams();
        linearParams4.width=dm.widthPixels/3;
        Dss_ImageView.setLayoutParams(linearParams4);

        Dss_viewPager=(ViewPager)findViewById(R.id.Dss_viewPager);

        Test2= LayoutInflater.from(this).inflate(R.layout.main_view,null);
        Test3=LayoutInflater.from(this).inflate(R.layout.main_view,null);
        Test4=LayoutInflater.from(this).inflate(R.layout.activity_demo,null);

    }

    private void initViewList(){
        viewList=new ArrayList<View>();
        viewList.add(Test2);
        viewList.add(Test3);
        viewList.add(Test4);

    }

    private void initListAdapter(){
        myViewPagerAdapter=new MyViewPagerAdapter();
        myViewPagerAdapter.setViews(viewList);
        Dss_viewPager.setAdapter(myViewPagerAdapter);
    }



    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int i) {
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        switch (i){
            case R.id.SelectBook:
                Dss_viewPager.setCurrentItem(0);
                Dss_ImageView.setX(0);
                break;
            case R.id.DonateBook:
                Dss_viewPager.setCurrentItem(1);
                Dss_ImageView.setX((dm.widthPixels)/3);
                break;
            case R.id.SellBook:
                Dss_viewPager.setCurrentItem(2);
                Dss_ImageView.setX((dm.widthPixels*2)/3);
                break;
        }
    }


    public class MyViewPagerAdapter extends PagerAdapter {

        private List<View>views;
        public void setViews(List<View>views){
            this.views=views;
        }

        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
        public Object instantiateItem(View container,int i){
            ((ViewPager)container).addView(views.get(i));

            return views.get(i);
        }

        public void destroyItem(View container,int position,Object object){
            ((ViewPager)container).removeView(views.get(position));
        }
    }


    public class MyPagerOnPageChangeListener implements  ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            Dss_viewPager.setPageTransformer(true,new ViewPagerAnimation());
        }

        @Override
        public void onPageSelected(int position) {
            DisplayMetrics dm=new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            switch (position){
                case 0:
                    test2.setChecked(true);
                    Test2.performClick();
                    Dss_ImageView.setX(0);
                    break;
                case 1:
                    test3.setChecked(true);
                    Test3.performClick();
                    Dss_ImageView.setX((dm.widthPixels) / 3);
                    break;
                case 2:
                    test4.setChecked(true);
                    Test4.performClick();
                    Dss_ImageView.setX((dm.widthPixels * 2) / 3);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
