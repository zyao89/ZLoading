package com.zyao89.view.zloading.path;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.FloatRange;
import android.view.animation.DecelerateInterpolator;

import com.zyao89.view.zloading.ZLoadingBuilder;

/**
 * Created by zyao89 on 2017/4/11.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 * My Blog: http://zyao89.me
 */
public class SearchPathBuilder extends ZLoadingBuilder
{
    //最终阶段
    private static final int FINAL_STATE        = 3;
    //当前动画阶段
    private              int mCurrAnimatorState = 0;
    private float       mR;
    private Paint       mPaint;
    private Path        mPath;
    private Path        mPathZoom;
    private PathMeasure mPathMeasure;
    private Path        mDrawPath;

    @Override
    protected void initParams(Context context)
    {
        mR = getAllSize();
        initPaint();
        initPathMeasure();
        initPaths();
    }

    private void initPaint()
    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(15);
        mPaint.setColor(Color.BLACK);
        mPaint.setDither(true);
        mPaint.setFilterBitmap(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
    }

    private void initPaths()
    {
        float r = mR * 0.4f;

        mPath = new Path();
        mPath.addArc(new RectF(getViewCenterX() - mR, getViewCenterY() - mR, getViewCenterX() + mR, getViewCenterY() + mR), 45, 359.9f);
        mPathMeasure.setPath(mPath, false);
        float[] pos = new float[2];
        mPathMeasure.getPosTan(0, pos, null);

        mPathZoom = new Path();
        mPathZoom.addArc(new RectF(getViewCenterX() - r, getViewCenterY() - r, getViewCenterX() + r, getViewCenterY() + r), 45, 359.9f);
        mPathZoom.lineTo(pos[0], pos[1]);
    }

    private void initPathMeasure()
    {
        mDrawPath = new Path();
        mPathMeasure = new PathMeasure();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawPath(mDrawPath, mPaint);
    }

    @Override
    protected void setAlpha(int alpha)
    {
        mPaint.setAlpha(alpha);
    }

    @Override
    protected void prepareStart(ValueAnimator floatValueAnimator)
    {
        floatValueAnimator.setInterpolator(new DecelerateInterpolator());
    }

    @Override
    protected void prepareEnd()
    {

    }

    @Override
    protected void computeUpdateValue(ValueAnimator animation, @FloatRange(from = 0.0, to = 1.0) float animatedValue)
    {
        switch (mCurrAnimatorState)
        {
            case 0:
            case 1:
                resetDrawPath();
                mPathMeasure.setPath(mPath, false);
                float stop = mPathMeasure.getLength() * animatedValue;
                float start = (float) (stop - ((0.5 - Math.abs(animatedValue - 0.5)) * 200f));
                mPathMeasure.getSegment(start, stop, mDrawPath, true);
                break;
            case 2:
                resetDrawPath();
                mPathMeasure.setPath(mPath, false);
                stop = mPathMeasure.getLength() * animatedValue;
                start = 0;
                mPathMeasure.getSegment(start, stop, mDrawPath, true);
                break;
            case 3:
                mPathMeasure.setPath(mPathZoom, false);
                stop = mPathMeasure.getLength();
                start = stop * (1 - animatedValue);
                mPathMeasure.getSegment(start, stop, mDrawPath, true);
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
        mPaint.setColorFilter(colorFilter);
    }

    private void resetDrawPath()
    {
        mDrawPath.reset();
        mDrawPath.lineTo(0, 0);//不知道什么坑
    }
}
