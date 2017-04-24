package com.zyao89.view.zloading;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

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
    private boolean mCancelable             = true;
    private boolean mCanceledOnTouchOutside = true;
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

    public ZLoadingDialog setLoadingColor(int color)
    {
        this.mLoadingBuilderColor = color;
        return this;
    }

    public ZLoadingDialog setHintText(String text)
    {
        this.mHintText = text;
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

    private @NonNull View createContentView()
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
        ZLoadingView zLoadingView = (ZLoadingView) contentView.findViewById(R.id.z_loading_view);
        ZLoadingTextView zTextView = (ZLoadingTextView) contentView.findViewById(R.id.z_text_view);
        if (!TextUtils.isEmpty(mHintText))
        {
            zTextView.setVisibility(View.VISIBLE);
            zTextView.setText(mHintText);
        }
        zLoadingView.setLoadingBuilder(this.mLoadingBuilderType);
        zLoadingView.setColorFilter(this.mLoadingBuilderColor);
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
        cancel();
    }

    private boolean isContextNotExist()
    {
        Context context = this.mContext.get();
        return context == null;
    }
}
