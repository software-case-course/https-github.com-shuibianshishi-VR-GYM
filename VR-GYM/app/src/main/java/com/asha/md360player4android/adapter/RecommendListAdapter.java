package com.asha.md360player4android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.asha.md360player4android.R;
import com.asha.md360player4android.VedioInfo;

import java.util.List;

/**
 * Created by kg on 2016/12/18.
 */
public class RecommendListAdapter extends BaseAdapter{//推荐视频适配器

    private Context parentContext;
    private List<VedioInfo> list;

    public class RecommendVedio{
        private ImageView real_image;//视频图片imageview
        private TextView real_name;//视频名称textview
        private TextView real_detail;//视频简介textview
        private TextView time;//视频时长textview
    }

    public RecommendListAdapter(Context context, List<VedioInfo> list){
        super();
        this.parentContext=context;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RecommendVedio vedio;
        if(convertView==null) {
            vedio = new RecommendVedio();
            LayoutInflater inflater = LayoutInflater.from(parentContext);
            convertView = inflater.inflate(R.layout.listviewitem, null);
            vedio.real_image = (ImageView) convertView.findViewById(R.id.RealImage);
            vedio.real_name = (TextView) convertView.findViewById(R.id.Real_Name);
            vedio.real_detail = (TextView) convertView.findViewById(R.id.Real_detail);
            vedio.time = (TextView) convertView.findViewById(R.id.time);

            VedioInfo info = list.get(position);
            vedio.real_image.setBackgroundResource(info.getPicId());
            vedio.real_name.setText(info.getName());
            vedio.real_detail.setText(info.getBrief());
            vedio.time.setText(info.getTime());
        }
        return convertView;
    }

}
