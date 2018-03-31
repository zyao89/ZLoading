package com.zyao89.view.zloading.ball;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.FloatRange;
import android.view.animation.AccelerateInterpolator;

/**
 * Created by zyao89 on 2017/3/29.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 * My Blog: http://zyao89.me
 */
public class IntertwineBuilder extends BaseBallBuilder
{
    //最终阶段
    private static final int FINAL_STATE = 1;
    private float mBallR;
    private Path  mPath;
    //当前动画阶段
    private int mCurrAnimatorState = 0;

    @Override
    protected void initParams(Context context)
    {
        mBallR = getAllSize();
        mPath = new Path();
        initPaint(5);
        initPoints(mBallR);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        drawBall(canvas);
    }

    /**
     * 绘制小球
     *
     * @param canvas
     */
    private void drawBall(Canvas canvas)
    {
        canvas.save();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        super.drawBall(canvas, mPath, mPaint);
        canvas.restore();
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
        switch (mCurrAnimatorState)
        {
            case 1:
                animation.setInterpolator(new AccelerateInterpolator());
                for (int i = 0; i < mBallPoints.size(); i++)
                {
                    CirclePoint circlePoint = mBallPoints.get(i);
                    if (2 <= i && i <= 7)
                    {
                        circlePoint.setOffsetX(-mBallR * (1 - animatedValue));
                        circlePoint.setOffsetY(-mBallR * (1 - animatedValue));
                    }
                    else
                    {
                        circlePoint.setOffsetX(mBallR * (1 - animatedValue));
                        circlePoint.setOffsetY(mBallR * (1 - animatedValue));
                    }
                }
                break;
            case 0:
                animation.setInterpolator(new AccelerateInterpolator());
                for (int i = 0; i < mBallPoints.size(); i++)
                {
                    CirclePoint circlePoint = mBallPoints.get(i);
                    if (2 <= i && i <= 7)
                    {
                        circlePoint.setOffsetX(-mBallR * (animatedValue));
                        circlePoint.setOffsetY(-mBallR * (animatedValue));
                    }
                    else
                    {
                        circlePoint.setOffsetX(mBallR * (animatedValue));
                        circlePoint.setOffsetY(mBallR * (animatedValue));
                    }
                }
                break;
        }
    }

    @Override
    public void onAnimationRepeat(Animator animation)
    {
        if (++mCurrAnimatorState > FINAL_STATE)
        {//还原到第一阶段
            mCurrAnimatorState = 0;
            for (CirclePoint point : mBallPoints)
            {
                point.setOffsetY(0);
                point.setOffsetX(0);
            }
        }
    }

}
