package com.zyao89.view.zloading.star;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.FloatRange;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;

import com.zyao89.view.zloading.ZLoadingBuilder;

/**
 * Created by zyao89 on 2017/3/20.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 * My Blog: http://zyao89.me
 */
public class StarBuilder extends ZLoadingBuilder
{
    private Paint mFullPaint;
    private float mStarOutR;
    private float mStarInR;
    private float mStarOutMidR;
    private float mStarInMidR;
    //开始偏移角度
    private int   mStartAngle;
    private Path  mStarPath;
    private float mOffsetTranslateY;
    private RectF         mOvalRectF;
    private float         mShadowWidth;
    private ValueAnimator mShadowAnimator;

    private final ValueAnimator.AnimatorUpdateListener mAnimatorUpdateListener = new ValueAnimator.AnimatorUpdateListener()
    {
        @Override
        public void onAnimationUpdate(ValueAnimator animation)
        {
            float value = (float) animation.getAnimatedValue();
            mOffsetTranslateY = getViewCenterY() * 0.4f * value;
            mShadowWidth = (mOffsetTranslateY + 10) * 0.9f;
        }
    };

    @Override
    protected void initParams(Context context)
    {
        mFullPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFullPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mFullPaint.setStrokeWidth(2);
        mFullPaint.setColor(Color.BLACK);
        mFullPaint.setDither(true);
        mFullPaint.setFilterBitmap(true);

        initValue(context);
        initAnimator();
    }

    private void initAnimator()
    {
        mShadowAnimator = ValueAnimator.ofFloat(0.0f, 1.0f, 0.0f);
        mShadowAnimator.setRepeatCount(Animation.INFINITE);
        mShadowAnimator.setDuration(ANIMATION_DURATION);
        mShadowAnimator.setStartDelay(ANIMATION_START_DELAY);
        mShadowAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
    }

    private void initValue(Context context)
    {
        float allSize = getAllSize();
        mStarOutR = allSize - dip2px(context, 5);
        mStarOutMidR = mStarOutR * 0.9f;
        mStarInR = mStarOutMidR * 0.6f;
        mStarInMidR = mStarInR * 0.9f;
        mStartAngle = 0;
        mOffsetTranslateY = 0;

        //星路径
        mStarPath = createStarPath(5, -18);

        //阴影宽度
        mShadowWidth = mStarOutR;
        mOvalRectF = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.save();
        canvas.translate(0, mOffsetTranslateY);
        canvas.rotate(mStartAngle, getViewCenterX(), getViewCenterY());
        canvas.drawPath(mStarPath, mFullPaint);
        canvas.restore();

        mOvalRectF.set(getViewCenterX() - mShadowWidth, getIntrinsicHeight() - 20, getViewCenterX() + mShadowWidth, getIntrinsicHeight() - 10);
        canvas.drawOval(mOvalRectF, mFullPaint);
    }

    /**
     * 绘制五角星
     *
     * @param num        角数量
     * @param startAngle 初始角度
     * @return
     */
    private Path createStarPath(int num, int startAngle)
    {
        final Path path = new Path();
        int angle = 360 / num;
        int roundSize = 5;//圆角弧度
        int offsetAngle = angle / 2;
        path.moveTo(getViewCenterX() + mStarOutMidR * cos(startAngle - roundSize), getViewCenterY() + mStarOutMidR * sin(startAngle - roundSize));
        for (int i = 0; i < num; i++)
        {
            int value = angle * i + startAngle;
            path.lineTo(getViewCenterX() + mStarOutMidR * cos(value - roundSize), getViewCenterY() + mStarOutMidR * sin(value - roundSize));
            //圆角
            path.quadTo(getViewCenterX() + mStarOutR * cos(value), getViewCenterY() + mStarOutR * sin(value), getViewCenterX() + mStarOutMidR * cos(value + roundSize), getViewCenterY() + mStarOutMidR * sin(value + roundSize));
            path.lineTo(getViewCenterX() + mStarInR * cos(value + offsetAngle - roundSize), getViewCenterY() + mStarInR * sin(value + offsetAngle - roundSize));
            //内圆角
            path.quadTo(getViewCenterX() + mStarInMidR * cos(value + offsetAngle), getViewCenterY() + mStarInMidR * sin(value + offsetAngle), getViewCenterX() + mStarInR * cos(value + offsetAngle + roundSize), getViewCenterY() + mStarInR * sin(value + offsetAngle + roundSize));
        }
        path.close();
        return path;
    }

    @Override
    protected void setAlpha(int alpha)
    {
        mFullPaint.setAlpha(alpha);
    }

    @Override
    protected void prepareStart(ValueAnimator floatValueAnimator)
    {
        floatValueAnimator.setInterpolator(new DecelerateInterpolator());

        mShadowAnimator.setRepeatCount(Animation.INFINITE);
        mShadowAnimator.setDuration(ANIMATION_DURATION);
        mShadowAnimator.setStartDelay(ANIMATION_START_DELAY);
        mShadowAnimator.addUpdateListener(mAnimatorUpdateListener);
        mShadowAnimator.start();
    }

    @Override
    protected void prepareEnd()
    {
        mShadowAnimator.removeAllUpdateListeners();
        mShadowAnimator.removeAllListeners();
        mShadowAnimator.setRepeatCount(0);
        mShadowAnimator.setDuration(0);
        mShadowAnimator.end();
    }

    @Override
    protected void computeUpdateValue(@FloatRange(from = 0.0, to = 1.0) float animatedValue)
    {
        mStartAngle = (int) (360 * animatedValue);
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
