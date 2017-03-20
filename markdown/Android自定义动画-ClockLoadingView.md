# Android自定义动画-ClockLoadingView

有一段时间没写文章了，原因肯定只有两种咯，第一、懒；第二、工作忙。嘿嘿，我的原因肯定是 *后者* 呗（脸皮厚）。废话不多说，今天我们分享一个自定义的加载动画简单实现。

**实现效果在最后，GIF有点大，手机流量慎重。**

### 介绍
首先做这个动画的初衷是为了学习和分享，所以从这里起，我准备做一个系列的加载动画（截止时间：我放弃的时候）。

### 正文
这次分享的是一个简单的秒表样式的加载动画。初始化如下，参数随意写的：

```java
    private void init(Context context, AttributeSet attrs)
    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);//颜色

        mStartAnimator = ValueAnimator.ofFloat(0, 359);
        mStartAnimator.setStartDelay(500);//起始延迟

        initValues();
    }

    private void initValues()
    {
        mBigRadius = 50;//外框
        mInnerRadius = mBigRadius - 10;//内框
        mInnerCircleRectF = new RectF();

        mStartAngle = 0;//初角度
        mEndAngle = 0;//终角度
    }

```

当View大小改变时，重新计算。
```java

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        mViewWidth = w;
        mViewHeight = h;
        mViewCenterX = w / 2;
        mViewCenterY = h / 2;

        mInnerCircleRectF.set(mViewCenterX - mInnerRadius, mViewCenterY - mInnerRadius, mViewCenterX + mInnerRadius, mViewCenterY + mInnerRadius);
    }

```

加载：
```java

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        if (mStartAnimator != null)
        {
            mStartAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mStartAnimator.setDuration(1000);
            mStartAnimator.addListener(this);
            mStartAnimator.addUpdateListener(this);
            mStartAnimator.start();
        }
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        if (mStartAnimator != null)
        {
            mStartAnimator.setRepeatCount(0);
            mStartAnimator.setDuration(0);
            mStartAnimator.cancel();
            mStartAnimator.removeAllUpdateListeners();
            mStartAnimator.removeAllListeners();
        }
    }

```

绘制方法，绘制扇形。
额。。。这里就一句话，我都觉得不好意思了，尴尬...
```java

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawArc(mInnerCircleRectF, mStartAngle, mEndAngle - mStartAngle, true, mPaint);
    }

```

动画Animation监听回调
```java
    @Override
    public void onAnimationStart(Animator animation)
    {
        mStartAngle = 0;
        mEndAngle = 0;
    }

    @Override
    public void onAnimationEnd(Animator animation)
    {
        mStartAngle = 0;
        mEndAngle = 0;
    }

    @Override
    public void onAnimationCancel(Animator animation)
    {
        mStartAngle = 0;
        mEndAngle = 0;
    }

    @Override
    public void onAnimationRepeat(Animator animation)
    {
        mIsFirstState = !mIsFirstState;
        if (mIsFirstState)//动画分为两个阶段，这里简单判断是否是第一阶段。
        {
            mStartAngle = 0;
            mEndAngle = 0;
        }
        else
        {
            mStartAngle = 0;
            mEndAngle = 360;
        }

    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation)
    {
        float value = (float) animation.getAnimatedValue();

        if (mIsFirstState)
        {
            mEndAngle = value;
        }
        else
        {
            mStartAngle = value;
        }

        postInvalidate();
    }
```

### 总结
文章里举例的代码，是最简单的实现原理，Github上的代码是进一步的优化。这里先到这里，希望大家能给个Star，后面我还会持续分享的。谢谢。

### 演示

![结果演示图](../capture/circle_loading.gif)

Github：[zyao89/ZCustomView](https://github.com/zyao89/ZCustomView)