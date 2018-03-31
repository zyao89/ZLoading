package com.zyao89.view.zloading.circle;

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

import com.zyao89.view.zloading.ZLoadingBuilder;

/**
 * Created by zyao89 on 2017/10/9.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 * My Blog: https://zyao89.cn
 */
public class SnakeCircleBuilder extends ZLoadingBuilder
{
    //最终阶段
    private static final int FINAL_STATE        = 1;
    //当前动画阶段
    private              int mCurrAnimatorState = 0;
    private Paint mStrokePaint;
    private float mOuterRF;
    private float mInterRF;
    private RectF mOuterCircleRectF;
    private RectF mInterCircleRectF;
    private int mAlpha = 255;
    //旋转角度
    private float       mRotateAngle;
    private float       mAntiRotateAngle;
    //路径
    private Path        mPath;
    private PathMeasure mPathMeasure;
    private Path        mDrawPath;

    @Override
    protected void initParams(Context context)
    {
        //最大尺寸
        mOuterRF = getAllSize() * 1.0f;
        //小圆尺寸
        mInterRF = mOuterRF * 0.7f;
        //初始化画笔
        initPaint(mInterRF * 0.4f);
        //旋转角度
        mRotateAngle = 0;
        //圆范围
        mOuterCircleRectF = new RectF();
        mOuterCircleRectF.set(getViewCenterX() - mOuterRF, getViewCenterY() - mOuterRF, getViewCenterX() + mOuterRF, getViewCenterY() + mOuterRF);
        mInterCircleRectF = new RectF();
        mInterCircleRectF.set(getViewCenterX() - mInterRF, getViewCenterY() - mInterRF, getViewCenterX() + mInterRF, getViewCenterY() + mInterRF);

        initPathMeasure();
        initPaths();
    }

    /**
     * 初始化画笔
     */
    private void initPaint(float lineWidth)
    {
        mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setStrokeWidth(lineWidth);
        mStrokePaint.setColor(Color.WHITE);
        mStrokePaint.setDither(true);
        mStrokePaint.setFilterBitmap(true);
        mStrokePaint.setStrokeCap(Paint.Cap.ROUND);
        mStrokePaint.setStrokeJoin(Paint.Join.ROUND);
    }

    private void initPathMeasure()
    {
        mDrawPath = new Path();
        mPathMeasure = new PathMeasure();
    }

    private void initPaths()
    {
        //中心折线
        mPath = new Path();
        float pointOffset = mOuterRF * 0.3f;
        float pointOffsetHalf = mOuterRF * 0.3f * 0.5f;
        mPath.moveTo(getViewCenterX() - mOuterRF * 0.8f, getViewCenterY());
        mPath.lineTo(getViewCenterX() - pointOffset, getViewCenterY());
        mPath.lineTo(getViewCenterX() - pointOffsetHalf, getViewCenterY() + pointOffsetHalf);
        mPath.lineTo(getViewCenterX() + pointOffsetHalf, getViewCenterY() - pointOffsetHalf);
        mPath.lineTo(getViewCenterX() + pointOffset, getViewCenterY());
        mPath.lineTo(getViewCenterX() + mOuterRF * 0.8f, getViewCenterY());
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.save();
        //外圆
        mStrokePaint.setStrokeWidth(mOuterRF * 0.05f);
        mStrokePaint.setAlpha((int) (mAlpha * 0.6f));
        canvas.drawCircle(getViewCenterX(), getViewCenterY(), mOuterRF, mStrokePaint);
        canvas.drawCircle(getViewCenterX(), getViewCenterY(), mInterRF, mStrokePaint);
        canvas.restore();

        canvas.save();
        mStrokePaint.setStrokeWidth(mOuterRF * 0.1f);
        mStrokePaint.setAlpha(mAlpha);
        canvas.rotate(mRotateAngle, getViewCenterX(), getViewCenterY());
        canvas.drawArc(mOuterCircleRectF, 0, 120, false, mStrokePaint);
        canvas.drawArc(mOuterCircleRectF, 180, 120, false, mStrokePaint);
        canvas.restore();

        // 蛇
        canvas.save();
        mStrokePaint.setAlpha((int) (mAlpha * 0.6f));
        canvas.drawPath(mDrawPath, mStrokePaint);
        canvas.restore();

        canvas.save();
        mStrokePaint.setStrokeWidth(mOuterRF * 0.1f);
        mStrokePaint.setAlpha(mAlpha);
        canvas.rotate(mAntiRotateAngle, getViewCenterX(), getViewCenterY());
        canvas.drawArc(mInterCircleRectF, 60, 60, false, mStrokePaint);
        canvas.drawArc(mInterCircleRectF, 180, 180, false, mStrokePaint);
        canvas.restore();
    }

    @Override
    protected void setAlpha(int alpha)
    {
        mAlpha = alpha;
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
        mRotateAngle = 360 * animatedValue;
        mAntiRotateAngle = 360 * (1 - animatedValue);

        switch (mCurrAnimatorState)
        {
            case 0:
                resetDrawPath();
                mPathMeasure.setPath(mPath, false);
                float stop = mPathMeasure.getLength() * animatedValue;
                float start = 0;
                mPathMeasure.getSegment(start, stop, mDrawPath, true);
                break;
            case 1:
                resetDrawPath();
                mPathMeasure.setPath(mPath, false);
                stop = mPathMeasure.getLength();
                start = mPathMeasure.getLength() * animatedValue;
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
        mStrokePaint.setColorFilter(colorFilter);
    }

    private void resetDrawPath()
    {
        mDrawPath.reset();
        mDrawPath.lineTo(0, 0);
    }
}
