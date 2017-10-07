package com.zyao89.view.zloading.circle;

import android.animation.Animator;
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
 * Created by zyao89 on 2017/10/2.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 * My Blog: https://zyao89.cn
 */
public class SingleCircleBuilder extends ZLoadingBuilder
{
    private static final int OUTER_CIRCLE_ANGLE = 320;
    //最终阶段
    private static final int FINAL_STATE        = 2;
    //当前动画阶段
    private              int mCurrAnimatorState = 0;
    private Paint mStrokePaint;
    private RectF mOuterCircleRectF;
    //旋转开始角度
    private int   mStartRotateAngle;
    //旋转角度
    private int   mRotateAngle;

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
        mStartRotateAngle = 0;
        //圆范围
        mOuterCircleRectF = new RectF();
        mOuterCircleRectF.set(getViewCenterX() - outR, getViewCenterY() - outR, getViewCenterX() + outR, getViewCenterY() + outR);
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
        canvas.drawArc(mOuterCircleRectF, mStartRotateAngle % 360, mRotateAngle % 360, false, mStrokePaint);
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
        mStartRotateAngle = (int) (360 * animatedValue);
        switch (mCurrAnimatorState)
        {
            case 0:
                mRotateAngle = (int) (OUTER_CIRCLE_ANGLE * animatedValue);
                break;
            case 1:
                mRotateAngle = OUTER_CIRCLE_ANGLE - (int) (OUTER_CIRCLE_ANGLE * animatedValue);
                break;
            default:
                break;
        }
    }

    @Override
    public void onAnimationRepeat(Animator animation)
    {
        if (++mCurrAnimatorState > FINAL_STATE)
        {//还原到第一阶段
            mCurrAnimatorState = 0;
        }
    }

    @Override
    protected void setColorFilter(ColorFilter colorFilter)
    {
        mStrokePaint.setColorFilter(colorFilter);
    }
}
