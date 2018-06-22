package com.sjw.androidcustomview.banner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by pc on 2018/6/22.
 */

public class DotIndicatorView extends View {
    private Drawable drawable;

    public DotIndicatorView(Context context) {
        this(context, null);
    }

    public DotIndicatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DotIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (drawable != null) {

//            drawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
//            drawable.draw(canvas);

            //7.把指示器变成圆形
            //画圆
            Bitmap bitmap = drawableToBitmap(drawable);
            //把 bitmap变为圆
            Bitmap circleBitmap = getCircleBitmap(bitmap);
            //把圆形的bitmap画在画布上
            canvas.drawBitmap(circleBitmap, 0, 0, null);

        }
    }

    /**
     * 获取圆形bitmap
     */
    private Bitmap getCircleBitmap(Bitmap bitmap) {
        //创建一个bitmap
        Bitmap circleBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        //画布
        Canvas canvas = new Canvas(circleBitmap);

        Paint paint = new Paint();
        //设置抗锯齿
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        //设置防抖动
        paint.setDither(true);
        //在画布上画圆
        canvas.drawCircle(getMeasuredWidth() / 2
                , getMeasuredHeight() / 2
                , getMeasuredWidth() / 2
                , paint);


        //取bitmap和圆的交集 PorterDuff.Mode.SRC_IN)
        //学习网址 https://www.cnblogs.com/tianzhijiexian/p/4297172.html
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));


        //再把原来的bitmap绘制到新的圆上
        canvas.drawBitmap(bitmap, 0, 0, paint);

        bitmap.recycle();
        bitmap=null;
        return circleBitmap;
    }

    /**
     * 从drawable得到bitmap
     */
    private Bitmap drawableToBitmap(Drawable drawable) {
        //如果是BitmapDrawable类型
        if (drawable instanceof BitmapDrawable) {

            return ((BitmapDrawable) drawable).getBitmap();
        }

        //其他类型  ColorDrawable
        //创建一个什么都没有的bitmap
        Bitmap outBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        //创建一个画布
        Canvas canvas = new Canvas(outBitmap);
        //把drawable画到画布上面
        drawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
        drawable.draw(canvas);


        return outBitmap;
    }


    /**
     * 5设置drawable
     *
     * @param drawable
     */
    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
        invalidate();
    }
}
