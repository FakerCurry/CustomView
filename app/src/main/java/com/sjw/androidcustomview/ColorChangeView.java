package com.sjw.androidcustomview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by pc on 2018/6/16.
 */

public class ColorChangeView extends TextView {

    //原来的颜色
    private int mOriginColor;
    //改变的颜色
    private int mChangeColor;
    //画笔
    private Paint mOriginPaint, mChangePaint;
    //改变
    private float mCurrentProgress = 0f;

    //实现不同朝向
    private Direction mDirection = Direction.LEFT_TO_RIGHT;

    public enum Direction {
        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT

    }

    public void setmDirection(Direction mDirection) {
        this.mDirection = mDirection;
    }

    public void setmCurrentProgress(float mCurrentProgress) {
        this.mCurrentProgress = mCurrentProgress;
        invalidate();
    }

    public ColorChangeView(Context context) {
        this(context, null);
    }

    public ColorChangeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorChangeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //先获取到整个的array
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ColorChangeView);
        //再获取到需要的属性复制变量
        mOriginColor = array.getColor(R.styleable.ColorChangeView_originColor, getTextColors().getDefaultColor());

        mChangeColor = array.getColor(R.styleable.ColorChangeView_changeColor, getTextColors().getDefaultColor());

        //根据颜色初始化画笔
        mChangePaint = getPaintByColor(mChangeColor);
        //根据颜色初始化画笔
        mOriginPaint = getPaintByColor(mOriginColor);

        array.recycle();

    }

    private Paint getPaintByColor(int mChangeColor) {
        Paint paint = new Paint();
        paint.setColor(mChangeColor);
        paint.setAntiAlias(true);
//防止抖动
        paint.setDither(true);
        paint.setTextSize(getTextSize());
        return paint;


    }

    //一个文字两种颜色
    //利用clipRect的API 进行裁剪   左边用一个画笔去画 右边用另一个画笔去画  不断的改变中间值
    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);  super不要调用，因为系统的textview只用一种颜色

//        canvas.save();
//        //画不变色的
//        //根据进度把中间值算出来
        int middle = (int) (mCurrentProgress * getWidth());

        if (mDirection == Direction.LEFT_TO_RIGHT) {
            drawText(canvas, mChangePaint, 0, middle);
            //画变色的
            drawText(canvas, mOriginPaint, middle, getWidth());
        } else {
            drawText(canvas, mChangePaint, getWidth() - middle, getWidth());
            //画变色的
            drawText(canvas, mOriginPaint, 0, getWidth() - middle);

        }

    }


    /**
     * 绘制drawText
     *
     * @param canvas
     * @param paint
     * @param start
     * @param end
     */
    private void drawText(Canvas canvas, Paint paint, int start, int end) {

        canvas.save();
        //画不变色的
        Rect cRect = new Rect(start, 0, end, getHeight());
        canvas.clipRect(cRect);
        //我们自己画
        String text = getText().toString();
        Rect rect = new Rect();
        //获取字体的宽度
        mOriginPaint.getTextBounds(text, 0, text.length(), rect);
//        drawText第二个参数
        int x = getWidth() / 2 - rect.width() / 2;
        //        drawText第三个参数  基线baseLine
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(text, x, baseLine, paint);//这么画还是一种颜色
        canvas.restore();


    }
}
