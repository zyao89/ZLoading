package com.zyao89.view.zloading;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Created by zyao89 on 2017/4/9.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 * My Blog: http://zyao89.me
 */
public class ZLoadingDialog
{
    private final WeakReference<Context> mContext;
    private final int                    mThemeResId;
    private       Z_TYPE                 mLoadingBuilderType;
    private       int                    mLoadingBuilderColor;
    private       String                 mHintText;
    private float   mHintTextSize           = -1;
    private int     mHintTextColor          = -1;
    private boolean mCancelable             = true;
    private boolean mCanceledOnTouchOutside = true;
    private double  mDurationTimePercent    = 1.0f;
    private int     mDialogBackgroundColor  = -1;
    private Dialog mZLoadingDialog;

    public ZLoadingDialog(@NonNull Context context)
    {
        this(context, R.style.alert_dialog);
    }

    public ZLoadingDialog(@NonNull Context context, int themeResId)
    {
        this.mContext = new WeakReference<>(context);
        this.mThemeResId = themeResId;
    }

    public ZLoadingDialog setLoadingBuilder(@NonNull Z_TYPE type)
    {
        this.mLoadingBuilderType = type;
        return this;
    }

    public ZLoadingDialog setLoadingColor(@ColorInt int color)
    {
        this.mLoadingBuilderColor = color;
        return this;
    }

    public ZLoadingDialog setHintText(String text)
    {
        this.mHintText = text;
        return this;
    }

    /**
     * 设置了大小后，字就不会有动画了。
     *
     * @param size 大小
     * @return
     */
    public ZLoadingDialog setHintTextSize(float size)
    {
        this.mHintTextSize = size;
        return this;
    }

    public ZLoadingDialog setHintTextColor(@ColorInt int color)
    {
        this.mHintTextColor = color;
        return this;
    }

    public ZLoadingDialog setCancelable(boolean cancelable)
    {
        mCancelable = cancelable;
        return this;
    }

    public ZLoadingDialog setCanceledOnTouchOutside(boolean canceledOnTouchOutside)
    {
        mCanceledOnTouchOutside = canceledOnTouchOutside;
        return this;
    }

    public ZLoadingDialog setDurationTime(double percent)
    {
        this.mDurationTimePercent = percent;
        return this;
    }

    public ZLoadingDialog setDialogBackgroundColor(@ColorInt int color)
    {
        this.mDialogBackgroundColor = color;
        return this;
    }

    private
    @NonNull
    View createContentView()
    {
        if (isContextNotExist())
        {
            throw new RuntimeException("Context is null...");
        }
        return View.inflate(this.mContext.get(), R.layout.z_loading_dialog, null);
    }

    public Dialog create()
    {
        if (isContextNotExist())
        {
            throw new RuntimeException("Context is null...");
        }
        if (mZLoadingDialog != null)
        {
            cancel();
        }
        mZLoadingDialog = new Dialog(this.mContext.get(), this.mThemeResId);
        View contentView = createContentView();
        LinearLayout zLoadingRootView = (LinearLayout) contentView.findViewById(R.id.z_loading);

        // init color
        if (this.mDialogBackgroundColor != -1)
        {
            final Drawable drawable = zLoadingRootView.getBackground();
            if (drawable != null)
            {
                drawable.setAlpha(Color.alpha(this.mDialogBackgroundColor));
                drawable.setColorFilter(this.mDialogBackgroundColor, PorterDuff.Mode.SRC_ATOP);
            }
        }

        ZLoadingView zLoadingView = (ZLoadingView) contentView.findViewById(R.id.z_loading_view);
        ZLoadingTextView zTextView = (ZLoadingTextView) contentView.findViewById(R.id.z_text_view);
        TextView zCustomTextView = (TextView) contentView.findViewById(R.id.z_custom_text_view);
        if (this.mHintTextSize > 0 && !TextUtils.isEmpty(mHintText))
        {
            zCustomTextView.setVisibility(View.VISIBLE);
            zCustomTextView.setText(mHintText);
            zCustomTextView.setTextSize(this.mHintTextSize);
            zCustomTextView.setTextColor(this.mHintTextColor == -1 ? this.mLoadingBuilderColor : this.mHintTextColor);
        }
        else if (!TextUtils.isEmpty(mHintText))
        {
            zTextView.setVisibility(View.VISIBLE);
            zTextView.setText(mHintText);
            zTextView.setColorFilter(this.mHintTextColor == -1 ? this.mLoadingBuilderColor : this.mHintTextColor, PorterDuff.Mode.SRC_ATOP);
        }
        zLoadingView.setLoadingBuilder(this.mLoadingBuilderType);
        // 设置间隔百分比
        if (zLoadingView.mZLoadingBuilder != null)
        {
            zLoadingView.mZLoadingBuilder.setDurationTimePercent(this.mDurationTimePercent);
        }
        zLoadingView.setColorFilter(this.mLoadingBuilderColor, PorterDuff.Mode.SRC_ATOP);
        mZLoadingDialog.setContentView(contentView);
        mZLoadingDialog.setCancelable(this.mCancelable);
        mZLoadingDialog.setCanceledOnTouchOutside(this.mCanceledOnTouchOutside);
        return mZLoadingDialog;
    }

    public void show()
    {
        if (mZLoadingDialog != null)
        {
            mZLoadingDialog.show();
        }
        else
        {
            final Dialog zLoadingDialog = create();
            zLoadingDialog.show();
        }
    }

    public void cancel()
    {
        if (mZLoadingDialog != null)
        {
            mZLoadingDialog.cancel();
        }
        mZLoadingDialog = null;
    }

    public void dismiss()
    {
        if (mZLoadingDialog != null)
        {
            mZLoadingDialog.dismiss();
        }
        mZLoadingDialog = null;
    }

    private boolean isContextNotExist()
    {
        Context context = this.mContext.get();
        return context == null;
    }
}
