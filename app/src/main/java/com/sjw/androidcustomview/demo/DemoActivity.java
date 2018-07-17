package com.sjw.androidcustomview.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sjw.androidcustomview.R;

public class DemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
    }

    public void demo1(View view) {

        startActivity(new Intent(this,QQStepActivity.class));

    }

    public void demo2(View view) {
        startActivity(new Intent(this,ColorTextviewActivity.class));
    }

    public void demo3(View view) {
        startActivity(new Intent(this,LetterSidebarActivity.class));
    }

    public void demo4(View view) {
        startActivity(new Intent(this,BannerActivity.class));
    }

    public void demo5(View view) {
        startActivity(new Intent(this,BackActivity.class));
    }
}
