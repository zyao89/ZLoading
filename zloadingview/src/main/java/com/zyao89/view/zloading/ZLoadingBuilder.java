package com.zyao89.view.zloading;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.FloatRange;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

/**
 * Created by zyao89 on 2017/3/19.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 * My Blog: http://zyao89.me
 */
public abstract class ZLoadingBuilder implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener
{
    protected static final float DEFAULT_SIZE          = 56.0f;
    protected static final long  ANIMATION_START_DELAY = 333;
    protected static final long  ANIMATION_DURATION    = 1333;

    private float mAllSize;
    private float mViewWidth;
    private float mViewHeight;

    private Drawable.Callback mCallback;
    private ValueAnimator     mFloatValueAnimator;

    private double mDurationTimePercent = 1.0;

    void init(Context context)
    {
        mAllSize = dip2px(context, DEFAULT_SIZE * 0.5f - 10);
        mViewWidth = dip2px(context, DEFAULT_SIZE);
        mViewHeight = dip2px(context, DEFAULT_SIZE);

        initAnimators();
    }

    private void initAnimators()
    {
        mFloatValueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        mFloatValueAnimator.setRepeatCount(Animation.INFINITE);
        mFloatValueAnimator.setDuration(getAnimationDuration());
        mFloatValueAnimator.setStartDelay(getAnimationStartDelay());
        mFloatValueAnimator.setInterpolator(new LinearInterpolator());
    }

    protected abstract void initParams(Context context);

    protected abstract void onDraw(Canvas canvas);

    protected abstract void setAlpha(int alpha);

    protected abstract void prepareStart(ValueAnimator animation);

    protected abstract void prepareEnd();

    protected abstract void computeUpdateValue(ValueAnimator animation, @FloatRange(from = 0.0, to = 1.0) float animatedValue);

    protected abstract void setColorFilter(ColorFilter colorFilter);

    void setCallback(Drawable.Callback callback)
    {
        this.mCallback = callback;
    }

    void draw(Canvas canvas)
    {
        onDraw(canvas);
    }

    void start()
    {
        if (mFloatValueAnimator.isStarted())
        {
            return;
        }
        mFloatValueAnimator.addUpdateListener(this);
        mFloatValueAnimator.addListener(this);
        mFloatValueAnimator.setRepeatCount(Animation.INFINITE);
        mFloatValueAnimator.setDuration(getAnimationDuration());
        prepareStart(mFloatValueAnimator);
        mFloatValueAnimator.start();
    }

    void stop()
    {
        mFloatValueAnimator.removeAllUpdateListeners();
        mFloatValueAnimator.removeAllListeners();
        mFloatValueAnimator.setRepeatCount(0);
        mFloatValueAnimator.setDuration(0);
        prepareEnd();
        mFloatValueAnimator.end();
    }

    boolean isRunning()
    {
        return mFloatValueAnimator.isRunning();
    }

    @Override
    public final void onAnimationUpdate(ValueAnimator animation)
    {
        computeUpdateValue(animation, (float) animation.getAnimatedValue());
        invalidateSelf();
    }

    private void invalidateSelf()
    {
        if (mCallback != null)
        {
            mCallback.invalidateDrawable(null);
        }
    }

    @Override
    public void onAnimationStart(Animator animation)
    {

    }

    @Override
    public void onAnimationEnd(Animator animation)
    {

    }

    @Override
    public void onAnimationCancel(Animator animation)
    {

    }

    @Override
    public void onAnimationRepeat(Animator animation)
    {

    }

    public void setDurationTimePercent(double durationTimePercent)
    {
        if (durationTimePercent <= 0)
        {
            mDurationTimePercent = 1.0f;
        }
        else
        {
            mDurationTimePercent = durationTimePercent;
        }
    }

    protected long getAnimationStartDelay()
    {
        return ANIMATION_START_DELAY;
    }

    protected long getAnimationDuration()
    {
        return ceil(ANIMATION_DURATION * mDurationTimePercent);
    }

    protected float getIntrinsicHeight()
    {
        return mViewHeight;
    }

    protected float getIntrinsicWidth()
    {
        return mViewWidth;
    }

    protected final float getViewCenterX()
    {
        return getIntrinsicWidth() * 0.5f;
    }

    protected final float getViewCenterY()
    {
        return getIntrinsicHeight() * 0.5f;
    }

    protected final float getAllSize()
    {
        return mAllSize;
    }

    protected static float dip2px(Context context, float dpValue)
    {
        float scale = context.getResources().getDisplayMetrics().density;
        return dpValue * scale;
    }

    protected static long ceil(double value)
    {
        return (long) Math.ceil(value);
    }
}
