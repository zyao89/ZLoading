package com.zyao89.view.zloading.star;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.FloatRange;
import android.view.animation.DecelerateInterpolator;

import com.zyao89.view.zloading.ZLoadingBuilder;

/**
 * Created by zyao89 on 2017/3/20.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 * My Blog: http://zyao89.me
 */
public class LeafBuilder extends ZLoadingBuilder
{
    //最终阶段
    private static final int FINAL_STATE = 2;
    private Paint mFullPaint;
    //属性
    private float mStarOutR;
    private float mStarInR;
    private float mStarOutMidR;
    private float mStarInMidR;
    private float mCenterCircleR;
    private int   mRotateAngle;
    //当前动画阶段
    private int   mCurrAnimatorState = 0;

    @Override
    protected void initParams(Context context)
    {
        initPaint();

        mStarOutR = getAllSize();
        mStarOutMidR = mStarOutR * 0.9f;
        mStarInR = mStarOutR * 0.7f;
        mStarInMidR = mStarOutR * 0.3f;

        //中心圆半径
        mCenterCircleR = dip2px(context, 3);
        //旋转角度
        mRotateAngle = 0;
    }

    private void initPaint()
    {
        mFullPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFullPaint.setStyle(Paint.Style.FILL);
        mFullPaint.setStrokeWidth(2);
        mFullPaint.setColor(Color.WHITE);
        mFullPaint.setDither(true);
        mFullPaint.setFilterBitmap(true);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.save();
        canvas.rotate(mRotateAngle, getViewCenterX(), getViewCenterY());
        //路径
        Path starPath = createStarPath(5, -18);
        starPath.addCircle(getViewCenterX(), getViewCenterY(), mCenterCircleR, Path.Direction.CW);
        starPath.setFillType(Path.FillType.EVEN_ODD);
        canvas.drawPath(starPath, mFullPaint);
        canvas.restore();
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
    }

    @Override
    protected void prepareEnd()
    {

    }

    @Override
    protected void computeUpdateValue(@FloatRange(from = 0.0, to = 1.0) float animatedValue)
    {
        switch (mCurrAnimatorState)
        {
            case 0:
                mStarOutMidR = getAllSize() * animatedValue;
                mRotateAngle = (int) (360 * animatedValue);
                break;
            case 1:
                mRotateAngle = (int) (360 * (1 - animatedValue));
                break;
            case 2:
                mStarOutMidR = getAllSize() * (1 - animatedValue);
                break;
        }
    }

    @Override
    public void onAnimationRepeat(Animator animation)
    {
        if (++mCurrAnimatorState > FINAL_STATE)
        {
            mCurrAnimatorState = 0;
        }
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
