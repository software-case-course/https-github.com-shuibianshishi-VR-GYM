package com.asha.md360player4android.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by SaiHor on 2016/10/23.
 */
public class GradientView extends View {

    // 颜色数组
    private static final int[] COLOR_BLUES = new int[] {
            0xff1db6ff, 0xff1db6ff, 0xff0b58c2, 0xff002a6d
    };

    // 颜色对应的位置数组
    private static final float[] COLOR_LOCATIONS = new float[] {
            0, 0.15f, 0.65f, 1f
    };

    private Paint mPaint;
    private int mTotalWidth,mTotalHeight,mHlafWidth,mHlafHeight;

    public GradientView(Context context){
        super(context);
        initPaint();
    }

    public GradientView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint(){
       mPaint=new Paint();
        mPaint.setShader( new RadialGradient(mHlafWidth, mTotalHeight ,
                mTotalHeight ,
                COLOR_BLUES, COLOR_LOCATIONS, Shader.TileMode.MIRROR));


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0,0,mTotalWidth,mTotalHeight,mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth=w;
        mHlafWidth=w/2;
        mTotalHeight=h;
        mHlafHeight=h/2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
