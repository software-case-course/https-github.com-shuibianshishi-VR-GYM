package com.asha.md360player4android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kg on 2016/12/3.
 */
public class MyAdapter extends BaseAdapter {
    private List<String> list;
    private Context context;
    private View pview;

    class ViewHolder{
        ImageView clock;
        TextView str;
        ImageButton del;
    }

    public MyAdapter(Context context,List<String> list){
        this.context=context;
        this.list=list;
        pview=LayoutInflater.from(context).inflate(R.layout.activity_main,null);
    }

    @Override
    public int getCount(){
        return list.size();
    }

    @Override
    public Object getItem(int position){
        return list.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        if(convertView==null||position<list.size()) {
            ViewHolder viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.search_history, null);
            viewHolder.clock = (ImageView) convertView.findViewById(R.id.history);
            viewHolder.str = (TextView) convertView.findViewById(R.id.text);
            viewHolder.str.setText(list.get(position));
            viewHolder.del = (ImageButton) convertView.findViewById(R.id.del);
            viewHolder.del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list.remove(position);
                    if(list.size()<=0)
                        ((Button)pview.findViewById(R.id.clear)).setVisibility(View.GONE);
                    InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    notifyDataSetChanged();
                }
            });
            convertView.setTag(viewHolder);
        }
        LinearLayout layout=(LinearLayout)convertView.findViewById(R.id.layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                String key=list.get(position);
                ((EditText)pview.findViewById(R.id.edit)).setText(key);
                list.remove(key);
                list.add(0,key);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
}
