package com.zyao89.zcustomview.loading;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zyao89.view.zloading.Z_TYPE;
import com.zyao89.view.zloading.ZLoadingView;
import com.zyao89.zcustomview.R;

public class ShowActivity extends AppCompatActivity
{
    public static final String LOADING_TYPE = "loading_type";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        Intent intent = getIntent();
        int type = intent.getIntExtra(LOADING_TYPE, 0);
        init(type);
    }

    private void init(int type)
    {
        ZLoadingView zLoadingView_1 = (ZLoadingView) findViewById(R.id.loadingView_1);
        ZLoadingView zLoadingView_2 = (ZLoadingView) findViewById(R.id.loadingView_2);
        ZLoadingView zLoadingView_3 = (ZLoadingView) findViewById(R.id.loadingView_3);
        ZLoadingView zLoadingView_4 = (ZLoadingView) findViewById(R.id.loadingView_4);
        ZLoadingView zLoadingView_5 = (ZLoadingView) findViewById(R.id.loadingView_5);
//        clockLoading.setColorFilter(Color.WHITE);

        initBuilder(zLoadingView_1, type);
        initBuilder(zLoadingView_2, type);
        initBuilder(zLoadingView_3, type);
        initBuilder(zLoadingView_4, type);
        initBuilder(zLoadingView_5, type);
    }

    private void initBuilder(ZLoadingView zLoadingView, int type)
    {
        zLoadingView.setLoadingBuilder(Z_TYPE.values()[type]);

        zLoadingView.setLoadingBuilder(Z_TYPE.CIRCLE);
        zLoadingView.setColorFilter(Color.WHITE);
    }
}
