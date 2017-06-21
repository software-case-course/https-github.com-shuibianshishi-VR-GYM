package com.asha.md360player4android.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.asha.md360player4android.MyAdapter;
import com.asha.md360player4android.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity{

    private EditText edit;
    private ImageButton back;
    private Button seach;
    private Button clear;
    private List<String> history;
    private MyAdapter myAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }



    public void init(){

        findViewById(R.id.mainlayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.mainlayout:
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        break;
                }
            }
        });

        Drawable drawable=getResources().getDrawable(R.drawable.search);
        drawable.setBounds(0,0,65,65);
        ListView listView=(ListView)findViewById(R.id.listView);
        edit=(EditText)findViewById(R.id.edit);
        edit.setCompoundDrawables(drawable,null,null,null);
        history=new ArrayList<String>();
        myAdapter=new MyAdapter(this,history);
        listView.setAdapter(myAdapter);
        clear=(Button)findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                history.clear();
                clear.setVisibility(View.GONE);
                myAdapter.notifyDataSetChanged();

            }
        });

        back=(ImageButton)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        seach=(Button)findViewById(R.id.search_button);
        seach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str=edit.getText().toString();
                if(!str.trim().equals("")){
                    if(history.contains(str))
                        history.remove(str);
                    history.add(0,str);
                    myAdapter.notifyDataSetChanged();
                }
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                clear.setVisibility(View.VISIBLE);
            }
        });
    }
}
