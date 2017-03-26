package com.zyao89.zcustomview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zyao89.view.zloading.Z_TYPE;
import com.zyao89.zcustomview.loading.ShowActivity;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout containerLinearLayout = (LinearLayout) findViewById(R.id.container);

        createButtons(containerLinearLayout);
    }

    private void createButtons(LinearLayout containerLinearLayout)
    {
        Z_TYPE[] types = Z_TYPE.values();
        for (Z_TYPE type : types)
        {
            AppCompatButton button = new AppCompatButton(this);
            int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
            button.setPadding(padding, padding, padding, padding);
            containerLinearLayout.addView(button, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            button.setText(String.format("%s LOADING", type.name()));
            setupListener(button, type);
        }
    }

    private void setupListener(View view, final Z_TYPE type)
    {
        final int index = type.ordinal();
        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                intent.putExtra(ShowActivity.LOADING_TYPE, index);
                startActivity(intent);
            }
        });
    }
}
