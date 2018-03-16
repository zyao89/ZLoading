package com.zyao89.view.zloading.rect;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.zyao89.view.zloading.base.BaseStateBuilder;

/**
 * Created by zyao89 on 2018/3/17.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 * My Blog: https://zyao89.cn
 */
public class ChartRectBuilder extends BaseStateBuilder
{
    /**
     * 总数
     */
    private final int      SUM_NUM               = 5;
    /**
     * 动画间隔时长
     */
    private final int      ANIMATE_DURATION_TIME = 500;
    /**
     * 当前
     */
    private volatile int   mCurrStateNum         = 0;
    private volatile float mCurrAnimatedValue    = 0;
    private Paint mPaint;
    private float mR;
    private RectF mStairRectF;

    @Override
    protected int getStateCount()
    {
        return SUM_NUM + 1;
    }

    @Override
    protected void initParams(Context context, Paint paint)
    {
        mPaint = paint;
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mR = getAllSize();
        mStairRectF = new RectF();
    }

    @Override
    protected void onComputeUpdateValue(ValueAnimator animation, float animatedValue, int state)
    {
        mCurrStateNum = state;
        mCurrAnimatedValue = animatedValue;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        // 高度
        float floorHeight = mR * 2 / SUM_NUM;
        float space = floorHeight * 0.5f;
        // 起点
        float startXP = getViewCenterX() - mR;
        float startYP = getViewCenterY() + mR;

        // 清
        mStairRectF.setEmpty();
        for (int i = 0; i < SUM_NUM; i++)
        {
            if (i > mCurrStateNum)
            {// 限制层
                break;
            }
            float offsetHV = (0.5f - Math.abs(mCurrAnimatedValue - 0.5f)) * floorHeight;
            int j = i % 3;
            if (i == mCurrStateNum)
            {// 当前
                mStairRectF.set(startXP + i * floorHeight, startYP - (j + 1) * floorHeight * mCurrAnimatedValue, startXP + (i + 1) * floorHeight - space, startYP);
            }
            else
            {
                mStairRectF.set(startXP + i * floorHeight, startYP - (j + 1) * floorHeight - offsetHV, startXP + (i + 1) * floorHeight - space, startYP);
            }
            canvas.drawRect(mStairRectF, mPaint);
        }
    }

    @Override
    protected void prepareStart(ValueAnimator animation)
    {
        // 动画间隔
        animation.setDuration(ANIMATE_DURATION_TIME);
    }

    @Override
    protected void prepareEnd()
    {
        mCurrStateNum = 0;
        mCurrAnimatedValue = 0;
    }
}
