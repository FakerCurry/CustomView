package com.sjw.androidcustomview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.sjw.androidcustomview.viewgroup.CustomBarView;

public class Main2Activity extends Activity implements CustomBarView.BackListener {
    private CustomBarView custom_bar_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        custom_bar_view = (CustomBarView) findViewById(R.id.custom_bar_view);

        custom_bar_view.setBackListener(this);

    }

    @Override
    public void setBack() {
        finish();
    }






}
