package com.zyao89.zcustomview.loading;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zyao89 on 2017/3/16.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 * My Blog: http://zyao89.me
 */
public class ClockLoading extends View implements Animator.AnimatorListener, ValueAnimator.AnimatorUpdateListener
{
    private Paint         mPaint;
    private int           mViewWidth;
    private int           mViewHeight;
    private int           mViewCenterX;
    private int           mViewCenterY;
    private int           mBigRadius;
    private int           mInnerRadius;
    private RectF         mInnerCircleRectF;
    private float         mStartAngle;
    private float         mEndAngle;
    private ValueAnimator mStartAnimator;
    private boolean mIsFirstState = true;

    public ClockLoading(Context context)
    {
        this(context, null);
    }

    public ClockLoading(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public ClockLoading(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs)
    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);

        mStartAnimator = ValueAnimator.ofFloat(0, 359);
        mStartAnimator.setStartDelay(500);

        initValues();
    }

    private void initValues()
    {
        mBigRadius = 50;
        mInnerRadius = mBigRadius - 10;
        mInnerCircleRectF = new RectF();

        mStartAngle = 0;
        mEndAngle = 0;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        mViewWidth = w;
        mViewHeight = h;
        mViewCenterX = w / 2;
        mViewCenterY = h / 2;

        mInnerCircleRectF.set(mViewCenterX - mInnerRadius, mViewCenterY - mInnerRadius, mViewCenterX + mInnerRadius, mViewCenterY + mInnerRadius);
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        if (mStartAnimator != null)
        {
            mStartAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mStartAnimator.setDuration(1000);
            mStartAnimator.addListener(this);
            mStartAnimator.addUpdateListener(this);
            mStartAnimator.start();
        }
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        if (mStartAnimator != null)
        {
            mStartAnimator.setRepeatCount(0);
            mStartAnimator.setDuration(0);
            mStartAnimator.cancel();
            mStartAnimator.removeAllUpdateListeners();
            mStartAnimator.removeAllListeners();
        }
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
//        canvas.drawCircle(mViewCenterX, mViewCenterY ,50, mPaint);

        canvas.drawArc(mInnerCircleRectF, mStartAngle, mEndAngle - mStartAngle, true, mPaint);
    }


    @Override
    public void onAnimationStart(Animator animation)
    {
        mStartAngle = 0;
        mEndAngle = 0;
    }

    @Override
    public void onAnimationEnd(Animator animation)
    {
        mStartAngle = 0;
        mEndAngle = 0;
    }

    @Override
    public void onAnimationCancel(Animator animation)
    {
        mStartAngle = 0;
        mEndAngle = 0;
    }

    @Override
    public void onAnimationRepeat(Animator animation)
    {
        mIsFirstState = !mIsFirstState;
        if (mIsFirstState)
        {
            mStartAngle = 0;
            mEndAngle = 0;
        }
        else
        {
            mStartAngle = 0;
            mEndAngle = 360;
        }

    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation)
    {
        float value = (float) animation.getAnimatedValue();

        if (mIsFirstState)
        {
            mEndAngle = value;
        }
        else
        {
            mStartAngle = value;
        }

        postInvalidate();
    }
}
