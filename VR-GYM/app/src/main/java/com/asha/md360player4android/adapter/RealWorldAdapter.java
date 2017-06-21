package com.asha.md360player4android.adapter;

/**
 * Created by ZHENGYU on 2016/11/27.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.asha.md360player4android.R;
import com.asha.md360player4android.RealWorld;
import com.asha.md360player4android.activity.MD360PlayerActivity;

import java.util.List;

public class RealWorldAdapter extends ArrayAdapter<RealWorld>{
    private int resoutceId;
    private Context context;
    private List<RealWorld>list;
    private Activity activity;

    public RealWorldAdapter(Context context,Activity activity,int textViewResourceId, List<RealWorld> objects){
        super(context,textViewResourceId,objects);
        this.context=context;
        resoutceId = textViewResourceId;
        this.list=objects;
        this.activity = activity;
    }

    public View getView(int position, View converView,ViewGroup parent){
        final RealWorld realworld = list.get(position);
        View view = LayoutInflater.from(getContext()).inflate(resoutceId,null);
        ImageView RealImage = (ImageView) view.findViewById(R.id.RealImage);
        TextView RealName = (TextView) view.findViewById(R.id.Real_Name);
        TextView RealDetail = (TextView) view.findViewById(R.id.Real_detail);
        RealImage.setImageResource(realworld.getImageId());
        RealName.setText(realworld.getName());
        RealDetail.setText(realworld.getDetail());
        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MD360PlayerActivity.startVideo(context, realworld);
                int version = Integer.valueOf(android.os.Build.VERSION.SDK);
                if(version >= 5)
                {
                    activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                }

            }
        });
        return view;
    }

}
