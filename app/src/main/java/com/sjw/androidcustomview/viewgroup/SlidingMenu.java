package com.sjw.androidcustomview.viewgroup;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by pc on 2018/6/21.
 */

public class SlidingMenu extends LinearLayout{
    GestureDetector gestureDetector;

    public SlidingMenu(Context context) {
        super(context);
    }

    public SlidingMenu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    //事件拉拦截  返回true表示不执行
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    private void init(Context context) {
        //可以只调用一个
        gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){


            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
//        gestureDetector=new GestureDetector(context, new GestureDetector.OnGestureListener() {
//            @Override
//            public boolean onDown(MotionEvent e) {
//                return false;
//            }
//
//            @Override
//            public void onShowPress(MotionEvent e) {
//
//            }
//
//            @Override
//            public boolean onSingleTapUp(MotionEvent e) {
//                return false;
//            }
//
//            @Override
//            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//                return false;
//            }
//
//            @Override
//            public void onLongPress(MotionEvent e) {
//
//            }
//
//            @Override
//            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//                return false;
//            }
//        });

    }

    public SlidingMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
