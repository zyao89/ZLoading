package com.zyao89.view.zloading.text;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.support.annotation.FloatRange;
import android.text.TextUtils;
import android.view.animation.AccelerateInterpolator;

import com.zyao89.view.zloading.ZLoadingBuilder;

/**
 * Created by zyao89 on 2017/4/9.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 * My Blog: http://zyao89.me
 */
public class TextBuilder extends ZLoadingBuilder
{
    private static final long DURATION_TIME = 333;
    private static final int BASE_ALPHA = 100;
    private static final String DEFAULT_TEXT = "Zyao89";
    private Paint  mTextPaint;
    private char[] mTextChars;
    private int mDrawTextCount = 0;

    @Override
    protected void initParams(Context context)
    {
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setDither(true);
        mTextPaint.setFilterBitmap(true);
        mTextPaint.setTextSize(DEFAULT_SIZE);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        //默认值
        mTextChars = DEFAULT_TEXT.toCharArray();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        if (isNotEmpty())
        {
            float measureText = mTextPaint.measureText(mTextChars, 0, mTextChars.length);
            Paint paint = new Paint(mTextPaint);
            paint.setAlpha(BASE_ALPHA);
            canvas.drawText(mTextChars, 0, mTextChars.length, getViewCenterX() - measureText / 2, getViewCenterY(), paint);
            canvas.drawText(mTextChars, 0, mDrawTextCount, getViewCenterX() - measureText / 2, getViewCenterY(), mTextPaint);
        }
    }

    @Override
    protected void setAlpha(int alpha)
    {
        mTextPaint.setAlpha(alpha);
    }

    @Override
    protected void prepareStart(ValueAnimator floatValueAnimator)
    {
        floatValueAnimator.setDuration(DURATION_TIME);
        floatValueAnimator.setInterpolator(new AccelerateInterpolator());
    }

    @Override
    protected void prepareEnd()
    {

    }

    @Override
    protected void computeUpdateValue(ValueAnimator animation, @FloatRange(from = 0.0, to = 1.0) float animatedValue)
    {
        mTextPaint.setAlpha((int) (animatedValue * 155) + BASE_ALPHA);
    }

    @Override
    public void onAnimationRepeat(Animator animation)
    {
        if (isNotEmpty())
        {
            if (++mDrawTextCount > mTextChars.length)
            {//还原到第一阶段
                mDrawTextCount = 0;
            }
        }
    }

    @Override
    protected void setColorFilter(ColorFilter colorFilter)
    {
        mTextPaint.setColorFilter(colorFilter);
    }

    public void setText(String text)
    {
        if (TextUtils.isEmpty(text))
        {
            return;
        }
        float measureText = mTextPaint.measureText(text);
        if (measureText >= getIntrinsicWidth())
        {
            float v = measureText / DEFAULT_SIZE;
            mTextPaint.setTextSize(getIntrinsicWidth() / v);
        }
        mTextChars = text.toCharArray();
    }

    private boolean isNotEmpty()
    {
        return mTextChars != null && mTextChars.length > 0;
    }
}
