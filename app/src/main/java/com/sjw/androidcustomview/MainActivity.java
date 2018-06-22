package com.sjw.androidcustomview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LetterSideBarView.LetterTouchListener {

    private ColorChangeView colorChangeView;
    private TextView mLetterTv;
    private LetterSideBarView letter_SideBar_View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final QQStepyView qqStepyView = (QQStepyView) findViewById(R.id.step_view);
        colorChangeView = (ColorChangeView) findViewById(R.id.colorChangeView);
        mLetterTv = (TextView) findViewById(R.id.mLetterTv);
        letter_SideBar_View = (LetterSideBarView) findViewById(R.id.letter_SideBar_View);

        qqStepyView.setmMaxStep(4000);
        //属性动画
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 3000);
        valueAnimator.setDuration(1000);
        //给一个插值器，这里可以达到动画效果从快到慢
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float step = (float) animation.getAnimatedValue();
                qqStepyView.setmCurrentStep((int) step);
            }
        });
        valueAnimator.start();

        letter_SideBar_View.setOnLetterTouchListener(this);


    }

    public void leftToRight(View view) {
        colorChangeView.setmDirection(ColorChangeView.Direction.LEFT_TO_RIGHT);
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                colorChangeView.setmCurrentProgress(value);

            }
        });
        valueAnimator.start();

    }

    public void rightToLeft(View view) {

        colorChangeView.setmDirection(ColorChangeView.Direction.RIGHT_TO_LEFT);
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                colorChangeView.setmCurrentProgress(value);

            }
        });
        valueAnimator.start();
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
