package com.zyao89.view.zloading.ball;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.FloatRange;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by zyao89 on 2017/3/29.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 * My Blog: http://zyao89.me
 */
public class InfectionBallBuilder extends BaseBallBuilder
{
    //动画间隔时间
    private              long mDurationTime   = 888;
    private              long mDurationTime_1 = 222;
    private              long mDurationTime_2 = 333;
    private              long mDurationTime_3 = 1333;
    private              long mDurationTime_4 = 1333;
    //最终阶段
    private static final int  FINAL_STATE     = 4;
    private static final int  SUM_POINT_POS   = 3;
    private float mBallR;
    private Path  mPath;
    //当前动画阶段
    private int mCurrAnimatorState = 0;
    //每个小球的偏移量
    private float mCanvasTranslateOffset;

    @Override
    protected void initParams(Context context)
    {
        mBallR = getAllSize() / SUM_POINT_POS;
        mCanvasTranslateOffset = getIntrinsicWidth() / SUM_POINT_POS;
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
        canvas.translate(0, -mCanvasTranslateOffset);
        super.drawBall(canvas, mPath, mPaint);
        canvas.restore();
    }

    @Override
    protected void prepareStart(ValueAnimator floatValueAnimator)
    {
        mDurationTime = ceil(getAnimationDuration() * 0.7);
        mDurationTime_1 = ceil(getAnimationDuration() * 0.2);
        mDurationTime_2 = ceil(getAnimationDuration() * 0.3);
        mDurationTime_3 = getAnimationDuration();
        mDurationTime_4 = getAnimationDuration();
    }

    @Override
    protected void prepareEnd()
    {

    }

    @Override
    protected void computeUpdateValue(ValueAnimator animation, @FloatRange(from = 0.0, to = 1.0) float animatedValue)
    {
        float offset = mCanvasTranslateOffset;
        switch (mCurrAnimatorState)
        {
            case 0:
                animation.setDuration(mDurationTime);
                animation.setInterpolator(new AccelerateInterpolator());
                mBallPoints.get(2).setOffsetY(animatedValue * offset);
                mBallPoints.get(3).setOffsetY(animatedValue * offset);
                mBallPoints.get(4).setOffsetY(animatedValue * offset);
                break;
            case 1:
                animation.setDuration(mDurationTime_1);
                animation.setInterpolator(new LinearInterpolator());
                mBallPoints.get(5).setOffsetY(animatedValue * offset);
                mBallPoints.get(6).setOffsetY(animatedValue * offset);
                mBallPoints.get(7).setOffsetY(animatedValue * offset);
                mBallPoints.get(1).setOffsetY(animatedValue * offset);
                mBallPoints.get(0).setOffsetY(animatedValue * offset);
                mBallPoints.get(11).setOffsetY(animatedValue * offset);
                break;
            case 2:
                animation.setDuration(mDurationTime_2);
                animation.setInterpolator(new AccelerateInterpolator());
                for (int i = 0; i < mBallPoints.size(); i++)
                {
                    if (i > 10 || i < 8)
                    {
                        mBallPoints.get(i).setOffsetY(animatedValue * offset + offset);
                    }
                    else
                    {
                        mBallPoints.get(i).setOffsetY(animatedValue * offset);
                    }
                }
                break;
            case 3:
                animation.setDuration(mDurationTime_3);
                animation.setInterpolator(new DecelerateInterpolator());

                mBallPoints.get(8).setOffsetY(animatedValue * offset + offset);
                mBallPoints.get(9).setOffsetY(animatedValue * offset + offset);
                mBallPoints.get(10).setOffsetY(animatedValue * offset + offset);


                mBallPoints.get(5).setOffsetX(animatedValue * offset);
                mBallPoints.get(6).setOffsetX(animatedValue * offset);
                mBallPoints.get(7).setOffsetX(animatedValue * offset);
                mBallPoints.get(1).setOffsetX(-animatedValue * offset);
                mBallPoints.get(0).setOffsetX(-animatedValue * offset);
                mBallPoints.get(11).setOffsetX(-animatedValue * offset);
                break;
            case 4:
                animation.setDuration(mDurationTime_4);
                mPaint.setAlpha((int) ((1 - animatedValue) * 255));
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
            for (CirclePoint point : mBallPoints)
            {
                point.setOffsetY(0);
                point.setOffsetX(0);
            }
            mPaint.setAlpha(255);
        }
    }

}
