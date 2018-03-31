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
 * Created by zyao89 on 2017/3/23.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 * My Blog: http://zyao89.me
 */
public class PacManBuilder extends ZLoadingBuilder
{
    //最终阶段
    private static final int  FINAL_STATE     = 9;
    private static final int  MAX_MOUTH_ANGLE = 45;
    private              long mDurationTime   = 333;
    private Paint mFullPaint;
    private RectF mOuterCircleRectF;
    private int   mMouthAngle;
    private float mMoveDistance;
    //当前动画阶段
    private int mCurrAnimatorState = 0;
    private int   HorizontalAngle;
    private float mMaxMoveRange;
    private float mLastMoveDistance;
    private float mDefaultStartMoveX;

    @Override
    protected void initParams(Context context)
    {
        float outR = getAllSize();
        float inR = outR * 0.7f;
        mMaxMoveRange = getIntrinsicWidth() + 2 * inR; //移动距离范围
        initPaint();//圆范围
        mMouthAngle = MAX_MOUTH_ANGLE;//嘴度数
        HorizontalAngle = 0;//水平翻转度数
        mDefaultStartMoveX = -mMaxMoveRange * 0.5f;//默认偏移量
        mMoveDistance = 0;//移动距离
        mOuterCircleRectF = new RectF(getViewCenterX() - inR, getViewCenterY() - inR, getViewCenterX() + inR, getViewCenterY() + inR);
    }

    private void initPaint()
    {
        mFullPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFullPaint.setStyle(Paint.Style.FILL);
        mFullPaint.setColor(Color.WHITE);
        mFullPaint.setDither(true);
        mFullPaint.setFilterBitmap(true);
        mFullPaint.setStrokeCap(Paint.Cap.ROUND);
        mFullPaint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.save();
        canvas.translate(mDefaultStartMoveX + mMoveDistance, 0);
        canvas.rotate(HorizontalAngle, getViewCenterX(), getViewCenterY());
        canvas.drawArc(mOuterCircleRectF, mMouthAngle, 360 - mMouthAngle * 2, true, mFullPaint);
        canvas.restore();
    }

    @Override
    protected void setAlpha(int alpha)
    {
        mFullPaint.setAlpha(alpha);
    }

    @Override
    protected void prepareStart(ValueAnimator floatValueAnimator)
    {
        mDurationTime = ceil(getAnimationDuration() * 0.3);

        floatValueAnimator.setDuration(mDurationTime);
    }

    @Override
    protected void prepareEnd()
    {

    }

    @Override
    protected void computeUpdateValue(ValueAnimator animation, @FloatRange(from = 0.0, to = 1.0) float animatedValue)
    {
        int half = FINAL_STATE / 2 + 1;
        float step = mMaxMoveRange / half;
        if (mCurrAnimatorState < half)//以下分为两个阶段
        {//向右
            HorizontalAngle = 0;
            mMoveDistance = mLastMoveDistance + step * animatedValue;
        }
        else
        {//向左
            HorizontalAngle = 180;
            mMoveDistance = mLastMoveDistance - step * animatedValue;
        }
        //嘴张开度数
        if (mCurrAnimatorState % 2 == 0)
        {
            mMouthAngle = (int) (MAX_MOUTH_ANGLE * (animatedValue)) + 5;
        }
        else
        {
            mMouthAngle = (int) (MAX_MOUTH_ANGLE * (1 - animatedValue)) + 5;
        }
    }

    @Override
    public void onAnimationRepeat(Animator animation)
    {
        if (++mCurrAnimatorState > FINAL_STATE)
        {//还原到第一阶段
            mCurrAnimatorState = 0;
        }
        //矫正
        int half = FINAL_STATE / 2 + 1;
        float stepRange = mMaxMoveRange / half;
        if (mCurrAnimatorState < half)
        {
            mLastMoveDistance = stepRange * mCurrAnimatorState;
        }
        else
        {
            mLastMoveDistance = stepRange * (half - mCurrAnimatorState % half);
        }
    }

    @Override
    protected void setColorFilter(ColorFilter colorFilter)
    {
        mFullPaint.setColorFilter(colorFilter);
    }
}
