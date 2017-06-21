package com.asha.md360player4android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.asha.md360player4android.R;
import com.asha.md360player4android.StringAndInt;

import java.util.List;

/**
 * Created by SaiHor on 2016/10/23.
 */
public class ItemListAdapter extends BaseAdapter {

    private List<StringAndInt> list;
    private LayoutInflater mInflater;

    public ItemListAdapter(Context context, List<StringAndInt>list){
        mInflater=LayoutInflater.from(context);
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
        ViewHolder viewHolder;
        if (convertView==null){
            convertView=mInflater.inflate(R.layout.setting_list_item,null);
            viewHolder=new ViewHolder();
            viewHolder.viewIcon=(ImageView)convertView.findViewById(R.id.icon);
            viewHolder.viewItem_Name=(TextView)convertView.findViewById(R.id.item_name);
            viewHolder.viewArrow=(ImageView)convertView.findViewById(R.id.arrow);
            viewHolder.gap_view=(View)convertView.findViewById(R.id.gap_view);
            viewHolder.small_gap_view=(View)convertView.findViewById(R.id.small_gap_view);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.viewItem_Name.setText(list.get(position).getName());
        viewHolder.viewIcon.setImageResource(list.get(position).getID());
        if(!list.get(position).isView_flag()){
            viewHolder.gap_view.setVisibility(View.GONE);
        }
        if(viewHolder.gap_view.getVisibility()==View.VISIBLE){
            viewHolder.small_gap_view.setVisibility(View.GONE);
        }
        return convertView;
    }

    public final class ViewHolder{
        public ImageView viewIcon;
        public TextView viewItem_Name;
        public ImageView viewArrow;
        public View gap_view;
        public View small_gap_view;
    }


}
