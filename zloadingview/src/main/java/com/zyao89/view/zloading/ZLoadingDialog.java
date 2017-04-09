package com.zyao89.view.zloading;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

/**
 * Created by zyao89 on 2017/4/9.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 * My Blog: http://zyao89.me
 */
public class ZLoadingDialog
{
    private final Context mContext;
    private final int     mThemeResId;
    private       Z_TYPE  mLoadingBuilderType;
    private       int     mLoadingBuilderColor;
    private       String  mHintText;
    private boolean mCancelable             = true;
    private boolean mCanceledOnTouchOutside = true;

    public ZLoadingDialog(@NonNull Context context)
    {
        this(context, R.style.alert_dialog);
    }

    public ZLoadingDialog(@NonNull Context context, int themeResId)
    {
        this.mContext = context;
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

    private View createContentView()
    {
        return View.inflate(this.mContext, R.layout.z_loading_dialog, null);
    }

    public void setCancelable(boolean cancelable)
    {
        mCancelable = cancelable;
    }

    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside)
    {
        mCanceledOnTouchOutside = canceledOnTouchOutside;
    }

    public Dialog create()
    {
        final Dialog zLoadingDialog = new Dialog(this.mContext, this.mThemeResId);
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
        zLoadingDialog.setContentView(contentView);
        zLoadingDialog.setCancelable(mCancelable);
        zLoadingDialog.setCanceledOnTouchOutside(mCanceledOnTouchOutside);
        return zLoadingDialog;
    }

    public Dialog show()
    {
        final Dialog zLoadingDialog = create();
        zLoadingDialog.show();
        return zLoadingDialog;
    }
}
