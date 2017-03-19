package com.zyao89.zcustomview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.zyao89.zcustomview.loading.ShowActivity;

public class MainActivity extends AppCompatActivity
{

    private AppCompatButton mClockLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mClockLoading = (AppCompatButton) findViewById(R.id.clockLoading);

        initListeners();
    }

    private void initListeners()
    {
        mClockLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                startActivity(intent);
            }
        });
    }
}
