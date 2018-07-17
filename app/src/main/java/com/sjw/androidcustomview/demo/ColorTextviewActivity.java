package com.sjw.androidcustomview.demo;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sjw.androidcustomview.view.ColorChangeView;
import com.sjw.androidcustomview.R;

public class ColorTextviewActivity extends AppCompatActivity {
    private ColorChangeView colorChangeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_textview);
        colorChangeView = (ColorChangeView) findViewById(R.id.colorChangeView);
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
}
