package com.zyao89.view.zloading.circle;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.FloatRange;

import com.zyao89.view.zloading.ZLoadingBuilder;

/**
 * Created by zyao89 on 2017/3/23.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 * My Blog: http://zyao89.me
 */
public class DoubleCircleBuilder extends ZLoadingBuilder
{
    private static final int OUTER_CIRCLE_ANGLE = 270;
    private static final int INTER_CIRCLE_ANGLE = 90;
    private Paint mStrokePaint;
    private RectF mOuterCircleRectF;
    private RectF mInnerCircleRectF;
    //旋转角度
    private int mRotateAngle;

    @Override
    protected void initParams(Context context)
    {
        //最大尺寸
        float outR = getAllSize();
        //小圆尺寸
        float inR = outR * 0.6f;
        //初始化画笔
        initPaint(inR * 0.4f);
        //旋转角度
        mRotateAngle = 0;
        //圆范围
        mOuterCircleRectF = new RectF();
        mOuterCircleRectF.set(getViewCenterX() - outR, getViewCenterY() - outR, getViewCenterX() + outR, getViewCenterY() + outR);
        mInnerCircleRectF = new RectF();
        mInnerCircleRectF.set(getViewCenterX() - inR, getViewCenterY() - inR, getViewCenterX() + inR, getViewCenterY() + inR);

    }

    /**
     * 初始化画笔
     */
    private void initPaint(float lineWidth)
    {
        mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setStrokeWidth(lineWidth);
        mStrokePaint.setColor(Color.WHITE);
        mStrokePaint.setDither(true);
        mStrokePaint.setFilterBitmap(true);
        mStrokePaint.setStrokeCap(Paint.Cap.ROUND);
        mStrokePaint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.save();
        //外圆
        canvas.drawArc(mOuterCircleRectF, mRotateAngle % 360, OUTER_CIRCLE_ANGLE, false, mStrokePaint);
        //内圆
        canvas.drawArc(mInnerCircleRectF, 270 - mRotateAngle % 360, INTER_CIRCLE_ANGLE, false, mStrokePaint);
        canvas.restore();
    }

    @Override
    protected void setAlpha(int alpha)
    {
        mStrokePaint.setAlpha(alpha);
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
        mRotateAngle = (int) (360 * animatedValue);
    }

    @Override
    protected void setColorFilter(ColorFilter colorFilter)
    {
        mStrokePaint.setColorFilter(colorFilter);
    }
}
