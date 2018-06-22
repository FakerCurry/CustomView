package com.sjw.androidcustomview.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sjw.androidcustomview.R;

/**
 * Created by pc on 2018/6/21.
 */

public class CustomBarView extends RelativeLayout {
    private ImageView custom_bar_back;
    private TextView custom_bar_mid;
    private TextView custom_bar_right;

    //回调接口
    private BackListener backListener;

    public CustomBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //就是要这么写
        LayoutInflater.from(context).inflate(R.layout.custom_bar_layout, this);
        initViews();

    }

    private void initViews() {

        custom_bar_back = (ImageView) findViewById(R.id.custom_bar_back);
        custom_bar_mid = (TextView) findViewById(R.id.custom_bar_mid);
        custom_bar_right = (TextView) findViewById(R.id.custom_bar_right);
        custom_bar_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                backListener.setBack();
            }
        });

    }

    public void setBackListener(BackListener backListener) {

        this.backListener = backListener;
    }

    public interface BackListener {

        void setBack();


    }


}
