package com.asha.md360player4android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Demo  {

    private ImageView head;
    private TextView nick;
    private Bitmap headBitmap;
    private ListView item_list;
    private List<StringAndInt> list;
    private RelativeLayout bg;
    private Bitmap bitmap;
    // 颜色数组
    private static final int[] COLOR_BLUES = new int[] {
            0xFF87D8D2, 0xFF59C7E8,   0xFF2FB5FC
    };
    // 0xff1db6ff, 0xff0b58c2, 0xff002a6d

    // 颜色对应的位置数组
    private static final float[] COLOR_LOCATIONS = new float[] {
            0, 0.55f, 1f
    };
    private Context context;
    private View view;

    public Demo(View view, Context context){
        this.view=view;
        this.context=context;
        initView();
        initData();
        initList();
    }

    private void initView(){
        item_list=(ListView)view.findViewById(R.id.item_list);
        head=(ImageView)view.findViewById(R.id.head);
        nick=(TextView)view.findViewById(R.id.nick);
        bg=(RelativeLayout)view.findViewById(R.id.bg);
    }

    private void initData(){
        headBitmap= BitmapFactory.decodeResource(view.getResources(),R.drawable.ss1);
        head.setImageDrawable(new RoundImageDrawable(headBitmap));

        WindowManager wm=(WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
      int  mHalfWidth=wm.getDefaultDisplay().getWidth()/2;
      int  mTotalHeight=wm.getDefaultDisplay().getHeight();
      int  mTotalWidth=wm.getDefaultDisplay().getWidth();
        bitmap=Bitmap.createBitmap(mTotalWidth,mTotalHeight, Bitmap.Config.ARGB_8888);
        Canvas newCanvas=new Canvas(bitmap);
        Paint newPaint=new Paint();
        newPaint.setShader( new RadialGradient(mHalfWidth, mTotalHeight ,
                mTotalHeight ,
                COLOR_BLUES, COLOR_LOCATIONS, Shader.TileMode.MIRROR));
        newCanvas.drawRect(0,0,mTotalWidth,mTotalHeight,newPaint);
        Drawable drawable =new BitmapDrawable(bitmap);
        bg.setBackground(drawable);
    }

    private void initList(){
        list=new ArrayList<StringAndInt>();
        list.add(new StringAndInt("离线缓存",R.drawable.download,true));
        list.add(new StringAndInt("商务合作",R.drawable.business,false));
        list.add(new StringAndInt("VR眼镜",R.drawable.glasses,true));
        list.add(new StringAndInt("使用帮助",R.drawable.help,false));
        list.add(new StringAndInt("用户反馈",R.drawable.feedback,false));
        list.add(new StringAndInt("关于我们",R.drawable.about_us,true));
        list.add(new StringAndInt("设置",R.drawable.setting_,true));
        ItemListAdapter itemListAdapter=new ItemListAdapter(context,list);
        item_list.setAdapter(itemListAdapter);
    }
}
