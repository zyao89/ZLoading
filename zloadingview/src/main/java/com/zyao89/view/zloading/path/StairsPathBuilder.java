package com.zyao89.view.zloading.path;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.animation.DecelerateInterpolator;

import com.zyao89.view.zloading.base.BaseStateBuilder;

/**
 * Created by zyao89 on 2018/3/10.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 * My Blog: https://zyao89.cn
 */
public class StairsPathBuilder extends BaseStateBuilder
{
    private final int FLOOR_NUM = 6;
    private Paint       mPaint;
    private float       mR;
    private Path        mPath;
    private Path        mPathFinal;
    private PathMeasure mPathMeasure;
    private Path        mDrawPath;

    @Override
    protected int getStateCount()
    {
        return 3;
    }

    @Override
    protected void initParams(Context context, Paint paint)
    {
        mPaint = paint;
        mR = getAllSize();
        initPathMeasure();
        initPaths();
    }

    private void initPaths()
    {
        mPath = new Path();
        float space = mR * 2 / FLOOR_NUM;
        float startXP = getViewCenterX() - mR;
        float startYP = getViewCenterY() + mR;

        mPath.moveTo(startXP, startYP);
        for (int i = 0; i < FLOOR_NUM; i++)
        {
            mPath.lineTo((i) * space + startXP, startYP - space * (i + 1));
            mPath.lineTo((i + 1) * space + startXP, startYP - space * (i + 1));
        }

        mPathFinal = new Path(mPath);
        mPathFinal.lineTo((FLOOR_NUM) * space + startXP, startYP);
        mPathFinal.lineTo(startXP, startYP);
    }

    private void initPathMeasure()
    {
        mDrawPath = new Path();
        mPathMeasure = new PathMeasure();
    }

    @Override
    protected void onComputeUpdateValue(ValueAnimator animation, float animatedValue, int state)
    {
        switch (state)
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
                mPathMeasure.setPath(mPathFinal, false);
                stop = mPathMeasure.getLength() * animatedValue;
                start = 0;
                mPathMeasure.getSegment(start, stop, mDrawPath, true);
                break;
            case 3:
                resetDrawPath();
                mPathMeasure.setPath(mPathFinal, false);
                stop = mPathMeasure.getLength() * (1 - animatedValue);
                start = 0;
                mPathMeasure.getSegment(start, stop, mDrawPath, true);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawPath(mDrawPath, mPaint);
    }

    @Override
    protected void prepareStart(ValueAnimator valueAnimator)
    {
        valueAnimator.setInterpolator(new DecelerateInterpolator());
    }

    @Override
    protected void prepareEnd()
    {

    }

    private void resetDrawPath()
    {
        mDrawPath.reset();
        mDrawPath.lineTo(0, 0);
    }
}
