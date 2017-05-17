package com.asha.md360player4android;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ZHENGYU on 2016/12/2.
 */
public class ADInfo {
    String ulr;
int test;
    ImageView view ;
    static String TAG;
    public void setUrl(String ulr){
        this.ulr=ulr;
    }
    public String getUlr(){
        return ulr;
    }

    public Bitmap getBitmap(){
        Bitmap bitmap = getHttpBitmap(ulr);
        return bitmap;
    }

    public ImageView getView(){
        Bitmap bitmap = getHttpBitmap(ulr);
        view.setImageBitmap(bitmap);	//设置Bitmap
        return view;
    }

    public static Bitmap getHttpBitmap(String url){
        Bitmap bitmap = null;
        try {
            URL URL = new URL(url);
               HttpURLConnection conn = (HttpURLConnection) URL.openConnection();
               conn.setConnectTimeout(20000);
               conn.setRequestMethod("GET");
               conn.setDoInput(true);
               int responseCode = conn.getResponseCode();
               if (responseCode == HttpURLConnection.HTTP_OK) {
                   InputStream inputStream = conn.getInputStream();
                   bitmap = BitmapFactory.decodeStream(inputStream);
                   inputStream.close();

               }else{
                   Log.d("sdjfhksd","jsldfkjelk");
               }
        }
        catch (Exception e){

        }
        return bitmap;
    }
}

