package com.sjw.androidcustomview.banner;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by pc on 2018/6/21.
 */

public class BannerViewPager extends ViewPager {

//自定义BannerViewPager
    BannerAdapter mAdapter;
    //实现自动轮播 --发送消息的msg
    private final int SCROLL_MSG = 0x0011;
    //页面切换间隔时间
    private int mCutDownTime = 3500;
    //改变切换的速率 -自定义的
    BannerScroller mScroller;

    //内存优化  复用界面
    private List<View> mConvertViews;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //每隔多少秒后切换到下一页

            setCurrentItem(getCurrentItem() + 1);
            Log.e("BannerViewPager", "startRoll");
            //不断的循环
            startRoll();


        }
    };

    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);


        try {
            //3.改变viewpager的速率
            //3.1 duration 持续时间   局部变量
            // 3.2改变mScroller 但是是private的   通过反射设置
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            //设置参数 第一个object当前属性在哪个类 第二个参数代表要设置的值
            mScroller = new BannerScroller(context);
            //设置强制改变速率为true
            mField.setAccessible(true);
            mField.set(this, mScroller);

        } catch (Exception e) {
            e.printStackTrace();
        }


        mConvertViews = new ArrayList<>();


    }

    /**
     * 设置适配器
     */
    public void setAdapter(BannerAdapter adapter) {
        this.mAdapter = adapter;
        setAdapter(new BannerPagerAdapter());

        //注册管理生命周期
        ((Activity) getContext()).getApplication().registerActivityLifecycleCallbacks(activityLifecycleCallbacks);

    }


    /**
     * 设置页面切换动画的速率
     */
    public void setmScrollerDuration(int mScrollerDuration) {
        mScroller.setmScrollerDuration(mScrollerDuration);
    }


    /**
     * 实现自动轮播
     */
    public void startRoll() {
        mHandler.removeMessages(SCROLL_MSG);
        mHandler.sendEmptyMessageDelayed(SCROLL_MSG, mCutDownTime);


    }

    /**
     * 销毁handler，防止内存泄露
     */
    @Override
    protected void onDetachedFromWindow() {
        //销毁handler的生命周期
        mHandler.removeMessages(SCROLL_MSG);
        mHandler = null;
        ((Activity) getContext()).getApplication().unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks);
        super.onDetachedFromWindow();
    }

    /**
     * 获取复用界面
     *
     * @return
     */
    public View getConvertView() {
        for (int i = 0; i < mConvertViews.size(); i++) {
            if (mConvertViews.get(i).getParent() == null) {
                return mConvertViews.get(i);
            }
        }
        return null;
    }


    //
    private class BannerPagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            //为了实现无线循环
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            //官方推荐这么写，看了源码就知道了
            return view == object;
        }


        /**
         * 创建条目的方法
         */
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            //Adapter为了让用户完全自定义
            View bannerItemView = mAdapter.getView(position % mAdapter.getCount(), getConvertView());
            //添加viewPager里面
            container.addView(bannerItemView);
            bannerItemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(position % mAdapter.getCount());
                }
            });

            return bannerItemView;
        }

        /**
         * 删除条目的方法
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
//            object = null;
//            mConvertView = (View) object;
            mConvertViews.add((View) object);
        }


    }

    /**
     * 管理activity的生命周期
     */

    Application.ActivityLifecycleCallbacks activityLifecycleCallbacks = new DefaultActivityLifecycleCallbacks() {

        @Override
        public void onActivityResumed(Activity activity) {
            //判断是否监听的是当前的activity
            if (activity == getContext()) {
                //开启轮播
                mHandler.sendEmptyMessageDelayed(mCutDownTime, SCROLL_MSG);

            }

        }

        @Override
        public void onActivityPaused(Activity activity) {
            //判断是否监听的是当前的activity
            if (activity == getContext()) {
                //停止轮播
                mHandler.removeMessages(SCROLL_MSG);
            }

        }
    };

    private OnBannerViewListener mListener;

    /**
     * 使用回调接口
     */
    public void setOnBannerViewListener(OnBannerViewListener listener) {
        mListener = listener;

    }


    /**
     * 回调接口，实现轮播图的点击
     */

    public interface OnBannerViewListener {

        public void onClick(int position);

    }

}
