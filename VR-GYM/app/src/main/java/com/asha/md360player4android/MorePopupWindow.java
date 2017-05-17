package com.asha.md360player4android;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

/**
 * Created by SaiHor on 2016/12/5.
 */
public class MorePopupWindow extends PopupWindow {
    private Activity context;
    private View view;
    private int IDResould;
    public MorePopupWindow(Activity context,int IDResould){
        this.context=context;
        this.IDResould = IDResould;
        initView();
        initData();
    }

    private void initView(){
        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setFocusable(true);
        // 设置外部可点击
       /* this.view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = view.findViewById(IDResould).getTop();

                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });*/
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.take_photo_anim);
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=layoutInflater.inflate(IDResould,null);

    }


    private void initData() {
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        this.setContentView(view);
        this.setBackgroundDrawable(null);
        this.setWidth(w);
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            dismiss();
            return true;
        }
        return false;
    }

    public void showWindow() {

        if (!this.isShowing()) {
            this.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        }

    }

    // 实例化一个ColorDrawable颜色为半透明
    ColorDrawable dw = new ColorDrawable(0xb0000000);


}
