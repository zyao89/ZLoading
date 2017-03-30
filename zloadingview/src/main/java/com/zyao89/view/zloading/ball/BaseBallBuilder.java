package com.zyao89.view.zloading.ball;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;

import com.zyao89.view.zloading.ZLoadingBuilder;

import java.util.LinkedList;

/**
 * Created by zyao89 on 2017/3/26.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 * My Blog: http://zyao89.me
 */
abstract class BaseBallBuilder extends ZLoadingBuilder
{
    //贝塞尔曲线常量
    private static final float                   PROP_VALUE  = 0.551915024494f;
    //小球点集合
    protected final      LinkedList<CirclePoint> mBallPoints = new LinkedList<>();
    //画笔
    protected Paint mPaint;

    /**
     * 初始化画笔
     */
    protected void initPaint(float lineWidth)
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
    protected final void initPoints(float ballR)
    {
        float centerX = getViewCenterX();
        float centerY = getViewCenterY();
        CirclePoint p_0 = new CirclePoint(centerX - ballR, centerY);
        mBallPoints.add(p_0);
        CirclePoint p_1 = new CirclePoint(centerX - ballR, centerY + ballR * PROP_VALUE);
        mBallPoints.add(p_1);
        CirclePoint p_2 = new CirclePoint(centerX - ballR * PROP_VALUE, centerY + ballR);
        mBallPoints.add(p_2);
        CirclePoint p_3 = new CirclePoint(centerX, centerY + ballR);
        mBallPoints.add(p_3);
        CirclePoint p_4 = new CirclePoint(centerX + ballR * PROP_VALUE, centerY + ballR);
        mBallPoints.add(p_4);
        CirclePoint p_5 = new CirclePoint(centerX + ballR, centerY + ballR * PROP_VALUE);
        mBallPoints.add(p_5);
        CirclePoint p_6 = new CirclePoint(centerX + ballR, centerY);
        mBallPoints.add(p_6);
        CirclePoint p_7 = new CirclePoint(centerX + ballR, centerY - ballR * PROP_VALUE);
        mBallPoints.add(p_7);
        CirclePoint p_8 = new CirclePoint(centerX + ballR * PROP_VALUE, centerY - ballR);
        mBallPoints.add(p_8);
        CirclePoint p_9 = new CirclePoint(centerX, centerY - ballR);
        mBallPoints.add(p_9);
        CirclePoint p_10 = new CirclePoint(centerX - ballR * PROP_VALUE, centerY - ballR);
        mBallPoints.add(p_10);
        CirclePoint p_11 = new CirclePoint(centerX - ballR, centerY - ballR * PROP_VALUE);
        mBallPoints.add(p_11);
    }

    protected final void drawBall(Canvas canvas, Path path, Paint paint)
    {
        path.reset();
        path.moveTo(mBallPoints.get(0).getX(), mBallPoints.get(0).getY());
        path.cubicTo(mBallPoints.get(1).getX(), mBallPoints.get(1).getY(), mBallPoints.get(2).getX(), mBallPoints.get(2).getY(), mBallPoints.get(3).getX(), mBallPoints.get(3).getY());
        path.cubicTo(mBallPoints.get(4).getX(), mBallPoints.get(4).getY(), mBallPoints.get(5).getX(), mBallPoints.get(5).getY(), mBallPoints.get(6).getX(), mBallPoints.get(6).getY());
        path.cubicTo(mBallPoints.get(7).getX(), mBallPoints.get(7).getY(), mBallPoints.get(8).getX(), mBallPoints.get(8).getY(), mBallPoints.get(9).getX(), mBallPoints.get(9).getY());
        path.cubicTo(mBallPoints.get(10).getX(), mBallPoints.get(10).getY(), mBallPoints.get(11).getX(), mBallPoints.get(11).getY(), mBallPoints.get(0).getX(), mBallPoints.get(0).getY());
        canvas.drawPath(path, paint);
    }

    @Override
    protected void setAlpha(int alpha)
    {
        mPaint.setAlpha(alpha);
    }

    @Override
    protected void setColorFilter(ColorFilter colorFilter)
    {
        mPaint.setColorFilter(colorFilter);
    }

    /**
     * 圆点内部类
     */
    static class CirclePoint
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
