package com.zyao89.view.zloading.clock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by zyao89 on 2017/3/19.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 * My Blog: http://zyao89.me
 */
public class ClockBuilder extends CircleBuilder
{
    private float mOuterRadius;
    private float mCircleSpace;
    private Paint mStrokePaint;
    private Paint mFillPaint;
    private RectF mBtnRectF;
    private RectF mBottomBtnRectF;

    @Override
    protected void initParams(Context context)
    {
        super.initParams(context);
        initOptions(context);
        createStrokePaint();
        createFillPaint();
    }

    private void initOptions(Context context)
    {
        float allSize = getAllSize();
        mCircleSpace = 4;
        mOuterRadius = allSize - mCircleSpace;
        float btnWidth = dip2px(context, 8);
        float btnHeight = dip2px(context, 3);
        float bottomBtnWidth = dip2px(context, 3);
        float bottomBtnHeight = dip2px(context, 2);
        mBtnRectF = new RectF(getViewCenterX() - btnWidth/2, getViewCenterY() - allSize - bottomBtnHeight - btnHeight, getViewCenterX() + btnWidth/2, getViewCenterY() - allSize - bottomBtnHeight);
        mBottomBtnRectF = new RectF(getViewCenterX() - bottomBtnWidth/2, getViewCenterY() - allSize - bottomBtnHeight, getViewCenterX() + bottomBtnWidth/2, getViewCenterY() - allSize);
    }

    private void createStrokePaint()
    {
        mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setStrokeWidth(mCircleSpace);
        mStrokePaint.setColor(Color.BLACK);
    }

    private void createFillPaint()
    {
        mFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFillPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mFillPaint.setColor(Color.BLACK);
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        //外圆
        canvas.drawCircle(getViewCenterX(), getViewCenterY(), mOuterRadius, mStrokePaint);

        //上方按钮
        canvas.drawRect(mBtnRectF, mFillPaint);
        canvas.drawRect(mBottomBtnRectF, mFillPaint);

        //右侧按钮
        canvas.save();
        canvas.rotate(45, getViewCenterX(), getViewCenterY());
        canvas.drawRect(mBottomBtnRectF, mFillPaint);
        canvas.restore();

        canvas.translate(0, 20);
    }

    @Override
    protected void setAlpha(int alpha)
    {
        super.setAlpha(alpha);
        mStrokePaint.setAlpha(alpha);
        mFillPaint.setAlpha(alpha);
    }

    @Override
    protected void setColorFilter(ColorFilter colorFilter)
    {
        super.setColorFilter(colorFilter);
        mStrokePaint.setColorFilter(colorFilter);
        mFillPaint.setColorFilter(colorFilter);
    }
}
