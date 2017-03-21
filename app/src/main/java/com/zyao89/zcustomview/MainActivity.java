package com.zyao89.zcustomview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.zyao89.zcustomview.loading.ShowActivity;

public class MainActivity extends AppCompatActivity
{

    private AppCompatButton mCircleLoading;
    private AppCompatButton mClockLoading;
    private AppCompatButton mStarLoading;
    private AppCompatButton mLeafLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCircleLoading = (AppCompatButton) findViewById(R.id.circleLoading);
        mClockLoading = (AppCompatButton) findViewById(R.id.clockLoading);
        mStarLoading = (AppCompatButton) findViewById(R.id.starLoading);
        mLeafLoading = (AppCompatButton) findViewById(R.id.leafLoading);

        initListeners();
    }

    private void initListeners()
    {
        setupListener(mCircleLoading, 0);
        setupListener(mClockLoading, 1);
        setupListener(mStarLoading, 2);
        setupListener(mLeafLoading, 3);
    }

    private void setupListener(View view, final int type)
    {
        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                intent.putExtra(ShowActivity.LOADING_TYPE, type);
                startActivity(intent);
            }
        });
    }
}
