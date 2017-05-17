package com.asha.md360player4android;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * Created by SaiHor on 2016/10/23.
 */
public class RoundImageDrawable extends Drawable {

    private Paint paint;
    private Bitmap bitmap;
    private int width;

    public RoundImageDrawable(Bitmap bitmap){
        this.bitmap=bitmap;
        BitmapShader bitmapShader=new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setShader(bitmapShader);
        width=Math.min(bitmap.getHeight(),bitmap.getHeight());

    }

    @Override
    public int getIntrinsicWidth() {
        return width;
    }

    @Override
    public int getIntrinsicHeight() {
        return width;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(width/2,width/2,width/2,paint);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
