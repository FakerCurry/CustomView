package com.sjw.androidcustomview.demo;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;

import com.sjw.androidcustomview.view.QQStepyView;
import com.sjw.androidcustomview.R;

public class QQStepActivity extends AppCompatActivity {
    //属性动画
    ValueAnimator valueAnimator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqstep);
        final QQStepyView qqStepyView = (QQStepyView) findViewById(R.id.step_view);
        qqStepyView.setmMaxStep(4000);
        valueAnimator= ObjectAnimator.ofFloat(0, 3000);
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
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        valueAnimator.cancel();
    }
}
