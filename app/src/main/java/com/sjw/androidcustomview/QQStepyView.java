package com.sjw.androidcustomview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by pc on 2018/6/16.
 */

public class QQStepyView extends View {
    //      <attr name="outerColor" format="color" />
//        <attr name="innerColor" format="color" />
//        <attr name="borderWidth" format="dimension" />
//        <attr name="stepyTextSize" format="dimension" />
//        <attr name="stepyTextColor" format="color" />
    private int mOuterColor;
    private int mInnerColor;
    private int mBorderWidth;
    private int mStepyTextSize;
    private int mStepyTextColor;
    private Paint mOutPaint, mInnerPaint, mTextPaint;

    //总共  当前 步数
    private int mMaxStep = 0;
    private int mCurrentStep = 0;


    public QQStepyView(Context context) {
//        super(context);
        this(context, null);
    }

    public QQStepyView(Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
        this(context, attrs, 0);
    }

    public QQStepyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        1.分析效果
//        2.确定自定义属性，编写attrs。xml
//        3.在布局中使用
//        4.在自定义view中获取自定义属性
        //首先获取整个attrs中对应的
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.QQStepyView);
        mOuterColor = array.getColor(R.styleable.QQStepyView_outerColor, Color.BLACK);
        mInnerColor = array.getColor(R.styleable.QQStepyView_innerColor, Color.BLACK);

        //普通的尺寸getDimension
        mBorderWidth = (int) array.getDimension(R.styleable.QQStepyView_borderWidth, 12);
        //字体的大小getDimensionPixelSize
        mStepyTextSize = array.getDimensionPixelSize(R.styleable.QQStepyView_stepyTextSize, 12);
        mStepyTextColor = array.getColor(R.styleable.QQStepyView_stepyTextColor, Color.BLACK);


        array.recycle();

        mOutPaint = new Paint();
        mOutPaint.setAntiAlias(true);
        mOutPaint.setStrokeWidth(mBorderWidth);
        mOutPaint.setColor(mOuterColor);
        // 设置为 ROUND 圆弧两端为圆形凸起
        mOutPaint.setStrokeCap(Paint.Cap.ROUND);
        mOutPaint.setStrokeJoin(Paint.Join.ROUND);
        mOutPaint.setStyle(Paint.Style.STROKE);//画笔实心


        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setStrokeWidth(mBorderWidth);
        mInnerPaint.setColor(mInnerColor);
        // 设置为 ROUND 圆弧两端为圆形凸起
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);
        mInnerPaint.setStrokeJoin(Paint.Join.ROUND);
        mInnerPaint.setStyle(Paint.Style.STROKE);//画笔实心

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mStepyTextColor);
        mTextPaint.setTextSize(mStepyTextSize);
//        5.onMeasure重新
//        6.画外圆弧，内圆弧，文字
//        7.其他


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //调用者在不居中使用 wrapcontent  宽度高度不一致
        //获取AT_MOST模式

        //宽度高度不一致取最小值  保证是个正方形
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width > height ? height : width, width > height ? height : width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //1.画外圆弧
        //出现有部分边不显示问题
//        int center = getWidth() / 2;
//        int radius = center - mBorderWidth;
        //left:mBorderWidth 画布起始点右移  top： mBorderWidth 画布起始点下移
        RectF rectF = new RectF(mBorderWidth / 2, mBorderWidth / 2, getWidth() - mBorderWidth / 2, getHeight() - mBorderWidth / 2);
        canvas.drawArc(rectF, 135, 270, false, mOutPaint);

        //2.画内圆弧  不能写死 是百分比的  使用者外面传
        if (mMaxStep == 0) {
            return;
        }
        float sweepAngle = (float) mCurrentStep / mMaxStep;
        canvas.drawArc(rectF, 135, sweepAngle * 270, false, mInnerPaint);

        //画文字
        String stepText = mCurrentStep + "";
        Rect textBound = new Rect();
        mTextPaint.getTextBounds(stepText, 0, stepText.length(), textBound);
        //计算文字的最左边的坐标
        int dx = getWidth() / 2 - textBound.width() / 2;
        //基线 baseLine
        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(stepText, dx, baseLine, mTextPaint);


    }


    //        写几个方法让他动起来
    public int getmMaxStep() {
        return mMaxStep;
    }

    public void setmMaxStep(int mMaxStep) {
        this.mMaxStep = mMaxStep;
    }

    public int getmCurrentStep() {
        return mCurrentStep;
    }

    public void setmCurrentStep(int mCurrentStep) {
        this.mCurrentStep = mCurrentStep;
        //不断绘制  不断调用onDraw的方法
        invalidate();
    }

}
