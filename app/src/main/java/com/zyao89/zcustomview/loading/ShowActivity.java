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
        ZLoadingView clockLoading = (ZLoadingView) findViewById(R.id.loadingView);
        clockLoading.setColorFilter(Color.WHITE);
        clockLoading.setLoadingBuilder(Z_TYPE.values()[type]);
    }
}
