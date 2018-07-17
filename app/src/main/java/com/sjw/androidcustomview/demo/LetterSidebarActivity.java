package com.sjw.androidcustomview.demo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sjw.androidcustomview.view.LetterSideBarView;
import com.sjw.androidcustomview.R;

public class LetterSidebarActivity extends AppCompatActivity implements LetterSideBarView.LetterTouchListener {
    private TextView mLetterTv;
    private LetterSideBarView letter_SideBar_View;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter_sidebar);
        mLetterTv = (TextView) findViewById(R.id.mLetterTv);
        letter_SideBar_View = (LetterSideBarView) findViewById(R.id.letter_SideBar_View);
        letter_SideBar_View.setOnLetterTouchListener(this);
    }


    @Override
    public void touch(final CharSequence letter, boolean isTouch) {
        if (isTouch) {
            mLetterTv.setVisibility(View.VISIBLE);
            mLetterTv.setText(letter);
        } else {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mLetterTv.setVisibility(View.GONE);
                    mLetterTv.setText(letter);
                }
            }, 1000);

        }
    }
}
