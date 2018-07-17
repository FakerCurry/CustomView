package com.sjw.androidcustomview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.sjw.androidcustomview.R;

/**
 * Created by pc on 2018/6/17.
 */

public class LetterSideBarView extends View {
    //定义画笔
    private Paint mPaint, mCurrentPaint;
    //获取到自定义属性
    private int mLetterColor;
    private int mLetterCurrentColor;
    private int mLetterSize;
    //定义26个字母
    private String[] mLetterArr = {"A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z", "#"
    };
    private String mTouchLetter;

    public LetterSideBarView(Context context) {
        this(context, null);
    }

    public LetterSideBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterSideBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        private int mLetterColor;
//        private int mLetterSize;
        //先获取资源文件attrs中数组
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LetterSideBarView);
        mLetterColor = array.getColor(R.styleable.LetterSideBarView_letterColor, Color.BLACK);
        mLetterCurrentColor = array.getColor(R.styleable.LetterSideBarView_letterCurrentColor, Color.BLACK);
        mLetterSize = array.getDimensionPixelSize(R.styleable.LetterSideBarView_letterSize, 15);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mLetterSize);
        mPaint.setColor(mLetterColor);

        mCurrentPaint = new Paint();
        mCurrentPaint.setAntiAlias(true);
        mCurrentPaint.setTextSize(mLetterSize);
        mCurrentPaint.setColor(mLetterCurrentColor);

        array.recycle();

    }

    private float sp2px(int mLetterSize) {

        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mLetterSize, getResources().getDisplayMetrics());


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int textWidth = (int) mPaint.measureText("A");
        //计算指定的宽度=左右的padding+字母的宽度(取决你的画笔)
        int width = getPaddingLeft() + getPaddingRight() + textWidth;
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width, height);


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        //计算每个字母的高度
        int itemHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / mLetterArr.length;
        for (int i = 0; i < mLetterArr.length; i++) {
            //画26个字母 绘制在中间  宽度/2-文字宽度/2
            int textWidth = (int) mPaint.measureText(mLetterArr[i]);
            int x = getWidth() / 2 - textWidth / 2;
            //计算每个字母的中心高度
            int letterCenterY = itemHeight * i + itemHeight / 2 + getPaddingTop();
            //计算基准线
            Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
            int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
            int baseLine = letterCenterY + dy;
            if (!mLetterArr[i].equals(mTouchLetter)) {
                canvas.drawText(mLetterArr[i], x, baseLine, mPaint);

            } else {

                canvas.drawText(mLetterArr[i], x, baseLine, mCurrentPaint);
            }


        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                //计算出当前的字母  获取当前的位置
                float currentMoveY = event.getY();
                //计算出当前的处于哪个字母
//                currentMoveY/字母高度   通过这个得到相应字母
                //计算每个字母的高度
                int itemHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / mLetterArr.length;
                int currentPosition = (int) (currentMoveY / itemHeight);
                if (currentPosition < 0) {
                    currentPosition = 0;
                }

                if (currentPosition > mLetterArr.length - 1) {
                    currentPosition = mLetterArr.length - 1;
                }
                mTouchLetter = mLetterArr[currentPosition];
                if (letterTouchListener != null) {

                    letterTouchListener.touch(mTouchLetter,true);
                }

//                重新绘制
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (letterTouchListener != null) {

                    letterTouchListener.touch(mTouchLetter,false);
                }
                break;

        }

//        return super.onTouchEvent(event);
        return true;//这样才调用MOVE
    }

    private LetterTouchListener letterTouchListener;

    public void setOnLetterTouchListener(LetterTouchListener letterTouchListener) {

        this.letterTouchListener = letterTouchListener;

    }

    public interface LetterTouchListener {

        void touch(CharSequence letter,boolean isTouch);
    }
}
