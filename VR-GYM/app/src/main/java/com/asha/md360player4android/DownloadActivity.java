package com.asha.md360player4android;

import android.os.Bundle;
import android.view.Window;

public class DownloadActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.downloadvedio);
    }
}
