package com.asha.md360player4android.activity;

import android.os.Bundle;
import android.view.Window;

import com.asha.md360player4android.R;

public class DownloadActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.downloadvedio);
    }
}
