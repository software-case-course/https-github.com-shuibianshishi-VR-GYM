package com.asha.md360player4android;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignInActivity extends BaseActivity {

    Button sign_in;
    EditText User_id,User_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
         getSupportActionBar().hide();
        initView();
        initData();
        initListener();
    }

    private void initView(){
        sign_in=(Button)findViewById(R.id.sign_in);
        User_id=(EditText)findViewById(R.id.User_id);
        User_password=(EditText)findViewById(R.id.User_password);

    }

    private void initData(){
        Drawable drawable=getResources().getDrawable(R.drawable.user);
        drawable.setBounds(0,0,63,63);
        User_id.setCompoundDrawables(drawable,null,null,null);

        Drawable drawable2=getResources().getDrawable(R.drawable.lock);
        drawable2.setBounds(0,0,50,63);
        User_password.setCompoundDrawables(drawable2,null,null,null);

    }

    private void initListener(){
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SignInActivity.this,ViewPagerActivity.class);
                if(User_id.getText().toString().equals("test")
                        &&User_password.getText().toString().equals("123456")){
                    startActivity(intent);
                }else{
                    Toast.makeText(SignInActivity.this,"请检查账号密码!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
