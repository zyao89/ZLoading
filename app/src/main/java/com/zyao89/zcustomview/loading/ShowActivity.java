package com.zyao89.zcustomview.loading;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zyao89.view.zloading.Z_TYPE;
import com.zyao89.view.zloading.ZLoadingView;
import com.zyao89.zcustomview.R;

public class ShowActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        init();
    }

    private void init()
    {
        ZLoadingView clockLoading = (ZLoadingView) findViewById(R.id.loadingView);
        clockLoading.setColorFilter(Color.BLUE);
        clockLoading.setLoadingBuilder(Z_TYPE.CIRCLE_CLOCK);
    }
}
