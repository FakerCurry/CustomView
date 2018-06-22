package com.sjw.androidcustomview.banner;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by pc on 2018/6/21.
 */

public class BannerScroller extends Scroller{

    //动画持续的时间
    private int mScrollerDuration=850;

       /**
     * 设置页面切换动画的速率
     */
    public void setmScrollerDuration(int mScrollerDuration) {
        this.mScrollerDuration = mScrollerDuration;
    }

    public BannerScroller(Context context) {
        super(context);
    }

    public BannerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public BannerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy,int duration) {
        super.startScroll(startX, startY, dx, dy,mScrollerDuration);
    }
}
