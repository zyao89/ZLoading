package com.zyao89.view.zloading.ball;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.FloatRange;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.zyao89.view.zloading.ZLoadingBuilder;

import java.util.LinkedList;

/**
 * Created by zyao89 on 2017/3/26.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 * My Blog: http://zyao89.me
 */
public class ElasticBallBuilder extends ZLoadingBuilder
{
    //动画间隔时间
    private static final long DURATION_TIME = 333;
    //最终阶段
    private static final int                     FINAL_STATE   = 2;
    //小球共5个位置
    private static final int                     SUM_POINT_POS = 5;
    //贝塞尔曲线常量
    private static final float                   PROP_VALUE    = 0.551915024494f;
    //小球点集合
    private final        LinkedList<CirclePoint> mBallPoints   = new LinkedList<>();
    //背景圆集合
    private final        LinkedList<CirclePoint> mBGCircles    = new LinkedList<>();
    private Paint mPaint;
    private float mBallR;
    private Path  mPath;
    //当前动画阶段
    private int mCurrAnimatorState = 0;
    //每个小球的偏移量
    private float mCanvasTranslateOffset;
    //当前状态是否翻转
    private boolean mIsReverse    = false;
    //当前小球的位置
    private int     mCurrPointPos = 0;

    @Override
    protected void initParams(Context context)
    {
        mBallR = getAllSize() / SUM_POINT_POS;
        mCanvasTranslateOffset = getIntrinsicWidth() / SUM_POINT_POS;
        mPath = new Path();
        initPaint(5);
        initPoints();
        initBGPoints();
    }

    /**
     * 背景圆点初始化
     */
    private void initBGPoints()
    {
        float centerX = getViewCenterX();
        float centerY = getViewCenterY();
        CirclePoint p_0 = new CirclePoint(centerX - mCanvasTranslateOffset * 2, centerY);
        CirclePoint p_1 = new CirclePoint(centerX - mCanvasTranslateOffset, centerY);
        CirclePoint p_2 = new CirclePoint(centerX, centerY);
        CirclePoint p_3 = new CirclePoint(centerX + mCanvasTranslateOffset, centerY);
        CirclePoint p_4 = new CirclePoint(centerX + mCanvasTranslateOffset * 2, centerY);

        p_0.setEnabled(false);//默认第一个圆不显示
        mBGCircles.add(p_0);
        mBGCircles.add(p_1);
        mBGCircles.add(p_2);
        mBGCircles.add(p_3);
        mBGCircles.add(p_4);
    }

    /**
     * p10    p9      p8
     * ------  ------
     * p11                     p7
     * |                       |
     * |                       |
     * p0 |      (0,0)         | p6
     * |                       |
     * |                       |
     * p1                      p5
     * ------  ------
     * p2      p3      p4
     */
    private void initPoints()
    {
        float centerX = getViewCenterX();
        float centerY = getViewCenterY();
        CirclePoint p_0 = new CirclePoint(centerX - mBallR, centerY);
        mBallPoints.add(p_0);
        CirclePoint p_1 = new CirclePoint(centerX - mBallR, centerY + mBallR * PROP_VALUE);
        mBallPoints.add(p_1);
        CirclePoint p_2 = new CirclePoint(centerX - mBallR * PROP_VALUE, centerY + mBallR);
        mBallPoints.add(p_2);
        CirclePoint p_3 = new CirclePoint(centerX, centerY + mBallR);
        mBallPoints.add(p_3);
        CirclePoint p_4 = new CirclePoint(centerX + mBallR * PROP_VALUE, centerY + mBallR);
        mBallPoints.add(p_4);
        CirclePoint p_5 = new CirclePoint(centerX + mBallR, centerY + mBallR * PROP_VALUE);
        mBallPoints.add(p_5);
        CirclePoint p_6 = new CirclePoint(centerX + mBallR, centerY);
        mBallPoints.add(p_6);
        CirclePoint p_7 = new CirclePoint(centerX + mBallR, centerY - mBallR * PROP_VALUE);
        mBallPoints.add(p_7);
        CirclePoint p_8 = new CirclePoint(centerX + mBallR * PROP_VALUE, centerY - mBallR);
        mBallPoints.add(p_8);
        CirclePoint p_9 = new CirclePoint(centerX, centerY - mBallR);
        mBallPoints.add(p_9);
        CirclePoint p_10 = new CirclePoint(centerX - mBallR * PROP_VALUE, centerY - mBallR);
        mBallPoints.add(p_10);
        CirclePoint p_11 = new CirclePoint(centerX - mBallR, centerY - mBallR * PROP_VALUE);
        mBallPoints.add(p_11);
    }

    /**
     * 初始化画笔
     */
    private void initPaint(float lineWidth)
    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(lineWidth);
        mPaint.setColor(Color.BLACK);
        mPaint.setDither(true);
        mPaint.setFilterBitmap(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        drawBG(canvas);
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
        float offsetX = mBGCircles.size() / 2 * mCanvasTranslateOffset;
        canvas.translate(-offsetX + mCanvasTranslateOffset * mCurrPointPos, 0);
        mPath.reset();
        mPath.moveTo(mBallPoints.get(0).getX(), mBallPoints.get(0).getY());
        mPath.cubicTo(mBallPoints.get(1).getX(), mBallPoints.get(1).getY(), mBallPoints.get(2).getX(), mBallPoints.get(2).getY(), mBallPoints.get(3).getX(), mBallPoints.get(3).getY());
        mPath.cubicTo(mBallPoints.get(4).getX(), mBallPoints.get(4).getY(), mBallPoints.get(5).getX(), mBallPoints.get(5).getY(), mBallPoints.get(6).getX(), mBallPoints.get(6).getY());
        mPath.cubicTo(mBallPoints.get(7).getX(), mBallPoints.get(7).getY(), mBallPoints.get(8).getX(), mBallPoints.get(8).getY(), mBallPoints.get(9).getX(), mBallPoints.get(9).getY());
        mPath.cubicTo(mBallPoints.get(10).getX(), mBallPoints.get(10).getY(), mBallPoints.get(11).getX(), mBallPoints.get(11).getY(), mBallPoints.get(0).getX(), mBallPoints.get(0).getY());
        canvas.drawPath(mPath, mPaint);
        canvas.restore();
    }

    /**
     * 绘制背景圆
     *
     * @param canvas
     */
    private void drawBG(Canvas canvas)
    {
        canvas.save();
        mPaint.setStyle(Paint.Style.STROKE);
        for (CirclePoint point : mBGCircles)
        {
            point.draw(canvas, mBallR, mPaint);
        }
        canvas.restore();
    }

    @Override
    protected void setAlpha(int alpha)
    {
        mPaint.setAlpha(alpha);
    }

    @Override
    protected void prepareStart(ValueAnimator floatValueAnimator)
    {
        floatValueAnimator.setDuration(DURATION_TIME);
    }

    @Override
    protected void prepareEnd()
    {

    }

    @Override
    protected void computeUpdateValue(ValueAnimator animation, @FloatRange(from = 0.0, to = 1.0) float animatedValue)
    {
        float offset = mCanvasTranslateOffset;
        int currState = mIsReverse ? mCurrAnimatorState + 3 : mCurrAnimatorState;
        switch (currState)
        {
            case 0:
                animation.setDuration(DURATION_TIME);
                animation.setInterpolator(new AccelerateInterpolator());
                mBallPoints.get(5).setOffsetX(animatedValue * offset);
                mBallPoints.get(6).setOffsetX(animatedValue * offset);
                mBallPoints.get(7).setOffsetX(animatedValue * offset);
                break;
            case 1:
                animation.setDuration(DURATION_TIME + 111);
                animation.setInterpolator(new DecelerateInterpolator());
                mBallPoints.get(2).setOffsetX(animatedValue * offset);
                mBallPoints.get(3).setOffsetX(animatedValue * offset);
                mBallPoints.get(4).setOffsetX(animatedValue * offset);
                mBallPoints.get(8).setOffsetX(animatedValue * offset);
                mBallPoints.get(9).setOffsetX(animatedValue * offset);
                mBallPoints.get(10).setOffsetX(animatedValue * offset);
                break;
            case 2:
                animation.setDuration(DURATION_TIME + 333);
                animation.setInterpolator(new BounceInterpolator());
                mBallPoints.get(0).setOffsetX(animatedValue * offset);
                mBallPoints.get(1).setOffsetX(animatedValue * offset);
                mBallPoints.get(11).setOffsetX(animatedValue * offset);
                break;
            case 3:
                animation.setDuration(DURATION_TIME);
                animation.setInterpolator(new AccelerateInterpolator());
                mBallPoints.get(0).setOffsetX((1 - animatedValue) * offset);
                mBallPoints.get(1).setOffsetX((1 - animatedValue) * offset);
                mBallPoints.get(11).setOffsetX((1 - animatedValue) * offset);
                break;
            case 4:
                animation.setDuration(DURATION_TIME + 111);
                animation.setInterpolator(new DecelerateInterpolator());
                mBallPoints.get(2).setOffsetX((1 - animatedValue) * offset);
                mBallPoints.get(3).setOffsetX((1 - animatedValue) * offset);
                mBallPoints.get(4).setOffsetX((1 - animatedValue) * offset);
                mBallPoints.get(8).setOffsetX((1 - animatedValue) * offset);
                mBallPoints.get(9).setOffsetX((1 - animatedValue) * offset);
                mBallPoints.get(10).setOffsetX((1 - animatedValue) * offset);
                break;
            case 5:
                animation.setDuration(DURATION_TIME + 333);
                animation.setInterpolator(new BounceInterpolator());
                mBallPoints.get(5).setOffsetX((1 - animatedValue) * offset);
                mBallPoints.get(6).setOffsetX((1 - animatedValue) * offset);
                mBallPoints.get(7).setOffsetX((1 - animatedValue) * offset);
                break;
        }
    }

    @Override
    public void onAnimationRepeat(Animator animation)
    {
        if (++mCurrAnimatorState > FINAL_STATE)
        {//还原到第一阶段
            mCurrAnimatorState = 0;

            /* 小球位置改变 */
            if (mIsReverse)
            {//倒序
                mCurrPointPos--;
            }
            else
            {//顺序
                mCurrPointPos++;
            }

            /* 重置并翻转动画过程 */
            if (mCurrPointPos >= SUM_POINT_POS - 1)
            {//倒序
                mIsReverse = true;
                mCurrPointPos = SUM_POINT_POS - 2;//I Don't Know
                for (int i = 0; i < mBGCircles.size(); i++)
                {
                    CirclePoint point = mBGCircles.get(i);
                    if (i == mBGCircles.size() - 1)
                    {
                        point.setEnabled(true);
                    }
                    else
                    {
                        point.setEnabled(false);
                    }

                }
            }
            else if (mCurrPointPos < 0)
            {//顺序
                mIsReverse = false;
                mCurrPointPos = 0;
                for (int i = 0; i < mBGCircles.size(); i++)
                {
                    CirclePoint point = mBGCircles.get(i);
                    if (i == 0)
                    {
                        point.setEnabled(false);
                    }
                    else
                    {
                        point.setEnabled(true);
                    }

                }
            }

            //每个阶段恢复状态，以及对背景圆的控制
            if (mIsReverse)
            {//倒序
                //恢复状态
                for (CirclePoint point : mBallPoints)
                {
                    point.setOffsetX(mCanvasTranslateOffset);
                }
                mBGCircles.get(mCurrPointPos + 1).setEnabled(true);
            }
            else
            {//顺序
                //恢复状态
                for (CirclePoint point : mBallPoints)
                {
                    point.setOffsetX(0);
                }
                mBGCircles.get(mCurrPointPos).setEnabled(false);
            }
        }
    }

    @Override
    protected void setColorFilter(ColorFilter colorFilter)
    {
        mPaint.setColorFilter(colorFilter);
    }

    /**
     * 圆点内部类
     */
    private class CirclePoint
    {
        private final float mX;
        private final float mY;
        private float   mOffsetX = 0;
        private float   mOffsetY = 0;
        private boolean mEnabled = true;

        CirclePoint(float x, float y)
        {
            mX = x;
            mY = y;
        }

        float getX()
        {
            return mX + mOffsetX;
        }

        float getY()
        {
            return mY + mOffsetY;
        }

        void setOffsetX(float offsetX)
        {
            mOffsetX = offsetX;
        }

        void setOffsetY(float offsetY)
        {
            mOffsetY = offsetY;
        }

        public void setEnabled(boolean enabled)
        {
            mEnabled = enabled;
        }

        void draw(Canvas canvas, float r, Paint paint)
        {
            if (this.mEnabled)
            {
                canvas.drawCircle(this.getX(), this.getY(), r, paint);
            }
        }
    }
}
