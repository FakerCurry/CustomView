package com.sjw.androidcustomview.viewgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.sjw.androidcustomview.R;

/**
 * Created by pc on 2018/6/20.
 */

public class MyTagLayout extends ViewGroup {
    /**
     * save the max select count.</p>
     * it is also select mode.
     */
    private int mMaxSelectCount = 1;

    /**
     * Is the single line.
     */
    private boolean mIsSingleLine = false;

    /**
     * is the item equal width
     */
    private boolean mIsItemsEqualWidth = false;

    /**
     * the max items in one line
     */
    private int mMaxItemsInOneLine = -1;

    /**
     */
    private int mMaxWidth;

    /**
     */
    private int mMaxHeight;

    public MyTagLayout(Context context) {
        this(context, null);
    }

    public MyTagLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.MyTagLayout, defStyleAttr, 0);
        init(array);

    }

    private void init(TypedArray array) {

        if (array != null) {
            mMaxSelectCount = array.getInteger(
                    R.styleable.MyTagLayout_maxSelectCount, 1);
            mIsSingleLine = array.getBoolean(R.styleable.MyTagLayout_singleLine, false);
            mMaxItemsInOneLine = array.getInteger(
                    R.styleable.MyTagLayout_maxItemsInOneLine, -1);
            mIsItemsEqualWidth = array.getBoolean(
                    R.styleable.MyTagLayout_isItemsEqualWidth, false);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        //去测量子控件
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        int childWidth = 0;
        int childHeight = 0;
        int lines = 1;
        int lineWidth = 0;
        mMaxWidth = 0;
        mMaxHeight = 0;
        int eachLineHeight = 0;



    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }


}
