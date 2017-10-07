package com.zyao89.view.zloading.circle;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.support.annotation.FloatRange;

import com.zyao89.view.zloading.ZLoadingBuilder;

/**
 * Created by zyao89 on 2017/4/24.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 * My Blog: https://zyao89.cn
 */
public class RotateCircleBuilder extends ZLoadingBuilder
{
    private final static int CIRCLE_NUM = 10;
    private Paint mFullPaint;
    private float mOuterRadius;
    private float mDefaultAngle;
    private float mInterRadius;

    @Override
    protected void initParams(Context context)
    {
        mOuterRadius = getAllSize();
        initPaint();
        mInterRadius = dip2px(context, 2);
    }

    private void initPaint()
    {
        mFullPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFullPaint.setStyle(Paint.Style.FILL);
        mFullPaint.setColor(Color.BLACK);
        mFullPaint.setDither(true);
        mFullPaint.setFilterBitmap(true);
        mFullPaint.setStrokeCap(Paint.Cap.ROUND);
        mFullPaint.setStrokeJoin(Paint.Join.ROUND);
    }

    private void drawCircles(Canvas canvas)
    {
        int all = CIRCLE_NUM;
        for (int i = 0; i < all; i++)
        {
            int angle = (int) (360 / all * i + mDefaultAngle);
            float centerX = getViewCenterX() + (mOuterRadius * cos(angle));
            float centerY = getViewCenterY() + (mOuterRadius * sin(angle));
            mFullPaint.setAlpha(255 / all * i);
            canvas.drawCircle(centerX, centerY, i + mInterRadius, mFullPaint);
        }
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        drawCircles(canvas);
    }

    @Override
    protected void setAlpha(int alpha)
    {
        mFullPaint.setAlpha(alpha);
    }

    @Override
    protected void prepareStart(ValueAnimator floatValueAnimator)
    {

    }

    @Override
    protected void prepareEnd()
    {

    }

    @Override
    protected void computeUpdateValue(ValueAnimator animation, @FloatRange(from = 0.0, to = 1.0) float animatedValue)
    {
        mDefaultAngle = 360 * animatedValue;
    }

    @Override
    protected void setColorFilter(ColorFilter colorFilter)
    {
        mFullPaint.setColorFilter(colorFilter);
    }

    protected final float cos(int num)
    {
        return (float) Math.cos(num * Math.PI / 180);
    }

    protected final float sin(int num)
    {
        return (float) Math.sin(num * Math.PI / 180);
    }
}
