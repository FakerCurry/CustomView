package com.sjw.androidcustomview.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sjw.androidcustomview.ColorChangeView;
import com.sjw.androidcustomview.R;

/**
 * Created by pc on 2018/6/21.
 */

public class BannerView extends RelativeLayout {
    //4.自定义BannerView -轮播的ViewPager
    private BannerViewPager mBannerVp;
    //4.自定义BannerView -轮播的描述
    private TextView mBannerDescTv;
    //4.自定义BannerView -轮播的点的容器
    private LinearLayout mDotContaner;
    //4.自定义BannerView -自定义的bannerAdapter
    private BannerAdapter mBannerAdapter;

    private Context mContext;

    //5.初始化点的指示器 --点选中的drawable
    private Drawable mIndicatorFocusDrawable;

    //5.初始化点的指示器 --点默认的drawable
    private Drawable mIndicatorNormalDrawable;

    //6.当前位置
    private int mCurrentPosition = 0;


    //8自定义属性  点的显示位置 默认左边
    private int mDotGravity = -1;

    //8自定义属性  点的大小  默认8个dp
    private int mDotSize = 8;
    //8自定义属性  点的距离  默认8个dp
    private int mDotDistance = 8;
    //8自定义属性   底部颜色
    private RelativeLayout mBannerBv;
    //8自定义属性   底部容器默认颜色
    private int mBottomColor = Color.TRANSPARENT;

    //8自定义属性  宽高比例
    private float mWidthProportion = 2;
    private float mHeightProportion = 1;


    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        //把布局加到这个view里面来
        inflate(context, R.layout.ui_banner_layout, this);

        initAttribution(attrs);
        initView();

    }


    /**
     * 初始化自定义属性
     */
    private void initAttribution(AttributeSet attrs) {
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.BannerView);

        //获取自定义属性
        mDotGravity = array.getInt(R.styleable.BannerView_dotGravity, mDotGravity);

        //用getDrawable的原因   format="color|reference" 兼容两个
        mIndicatorFocusDrawable = array.getDrawable(R.styleable.BannerView_dotIndicatorFocus);

        if (mIndicatorFocusDrawable == null) {

            //如果在布局文件中没有配置
            mIndicatorFocusDrawable = new ColorDrawable(Color.RED);

        }


        mIndicatorNormalDrawable = array.getDrawable(R.styleable.BannerView_dotIndicatorNormal);

        if (mIndicatorNormalDrawable == null) {

            //如果在布局文件中没有配置
            mIndicatorNormalDrawable = new ColorDrawable(Color.WHITE);

        }

        mDotSize = (int) array.getDimension(R.styleable.BannerView_dotSize, dip2Px(mDotSize));
        mDotDistance = (int) array.getDimension(R.styleable.BannerView_dotDistance, dip2Px(mDotDistance));
        mBottomColor = (int) array.getColor(R.styleable.BannerView_bottomColor, mBottomColor);

        mWidthProportion = array.getFloat(R.styleable.BannerView_widthProportion, mWidthProportion);
        mHeightProportion = array.getFloat(R.styleable.BannerView_heightProportion, mHeightProportion);

        array.recycle();
    }


    private void initView() {
        mBannerVp = (BannerViewPager) findViewById(R.id.banner_vp);
        mBannerDescTv = (TextView) findViewById(R.id.banner_desc_tv);
        mDotContaner = (LinearLayout) findViewById(R.id.dot_contaner);
        mBannerBv = (RelativeLayout) findViewById(R.id.banner_bottom_view);
        mBannerBv.setBackgroundColor(mBottomColor);
    }

    /**
     * 4.设置适配器
     */
    public void setAdapter(BannerAdapter adapter) {
        mBannerAdapter = adapter;
        mBannerVp.setAdapter(adapter);
        //5初始化点的指示器
        initDotIndicator();
        //6Bug修复
        mBannerVp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
//监听当前选中位置
                pageSelect(position);

            }
        });
        //初始化第一条广告描述
        String firstDesc = mBannerAdapter.getBannerDesc(0);
        mBannerDescTv.setText(firstDesc);


        //这个可以测量view的宽高，而且建议使用
        this.post(new Runnable() {

            @Override
            public void run() {
                //动态指定宽高 计算高度

                if (mHeightProportion == 0 || mWidthProportion == 0) {

                    return;
                }
                int width = getWidth();


                int height = (int) (width * mHeightProportion / mWidthProportion);

                getLayoutParams().height = height;
            }
        });


    }

    /**
     * 页面切换的回调
     */
    private void pageSelect(int position) {
        //6.1把原先亮的点 改成默认
        DotIndicatorView oldDotIndicatorView =
                (DotIndicatorView) mDotContaner.getChildAt(mCurrentPosition);

        oldDotIndicatorView.setDrawable(mIndicatorNormalDrawable);
        //6.2把当前位置的点 点亮
        mCurrentPosition = position % mBannerAdapter.getCount();

        DotIndicatorView currentDotIndicatorView =
                (DotIndicatorView) mDotContaner.getChildAt(mCurrentPosition);

        currentDotIndicatorView.setDrawable(mIndicatorFocusDrawable);
        //6.3设置广告位描述
        String bannerString = mBannerAdapter.getBannerDesc(mCurrentPosition);
        mBannerDescTv.setText(bannerString);

    }

    private void initDotIndicator() {
        //获取广告的量
        int count = mBannerAdapter.getCount();
        //设置点在右边
        mDotContaner.setGravity(getDotGravity());

        for (int i = 0; i < count; i++) {
            //不断的往指示器中添加远点
            DotIndicatorView mDotIndicatorView = new DotIndicatorView(mContext);

            //设置大小
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mDotSize, mDotSize);

            layoutParams.leftMargin = mDotDistance;
            mDotIndicatorView.setLayoutParams(layoutParams);

            if (i == 0) {
                //选中的位置
                mDotIndicatorView.setDrawable(mIndicatorFocusDrawable);

            } else {
                //选中的位置
                mDotIndicatorView.setDrawable(mIndicatorNormalDrawable);

            }

            //添加
            mDotContaner.addView(mDotIndicatorView);

        }

    }

    /**
     * 把dip转px
     */

    public int dip2Px(int dip) {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());

    }


    /**
     * 4.开始轮播
     */
    public void startRoll() {
        mBannerVp.startRoll();
    }

    /**
     * 4.设置滚动的时候切换动画的速率
     */
    public void setmScrollerDuration(int duration) {
        mBannerVp.setmScrollerDuration(duration);
    }

    /**
     * 获取点的位置
     */
    public int getDotGravity() {
        switch (mDotGravity) {
            case 0:
                return Gravity.CENTER;
            case -1:
                return Gravity.LEFT;
            case 1:
                return Gravity.RIGHT;


        }

        return Gravity.RIGHT;
    }

    /**
     * 使用BannerViewPager的回调接口
     */
    public void setOnBannerViewListener(BannerViewPager.OnBannerViewListener listener) {
        mBannerVp.setOnBannerViewListener(listener);

    }
}
