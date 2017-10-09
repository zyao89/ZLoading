package com.zyao89.zcustomview;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;
import com.zyao89.zcustomview.loading.ShowActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
{
    private              int    mSelectedItemIndex = 1;
    private static final String INDEX_01           = "切换为新页面";
    private static final String INDEX_02           = "切换为Dialog";
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout containerLinearLayout = (LinearLayout) findViewById(R.id.container);
        createButtons(containerLinearLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.add(Menu.NONE, 0, Menu.NONE, INDEX_01);
        menu.add(Menu.NONE, 1, Menu.NONE, INDEX_02);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (mToast == null)
        {
            mToast = Toast.makeText(MainActivity.this, "Error, 出错了", Toast.LENGTH_LONG);
        }
        switch (item.getItemId())
        {
            case 0:
                mToast.setText(INDEX_01);
                break;
            case 1:
                mToast.setText(INDEX_02);
                break;
        }
        mToast.show();
        mSelectedItemIndex = item.getItemId();
        return super.onOptionsItemSelected(item);
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
            button.setText(String.format(Locale.getDefault(), "【%d】%s LOADING", type.ordinal(), type.name()));
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
                switch (mSelectedItemIndex)
                {
                    case 0:
                        Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                        intent.putExtra(ShowActivity.LOADING_TYPE, index);
                        startActivity(intent);
                        break;
                    case 1:
                    default:
                        ZLoadingDialog dialog = new ZLoadingDialog(MainActivity.this);
                        dialog.setLoadingBuilder(type)
                                .setLoadingColor(Color.BLACK)
                                .setHintText("Loading...")
//                                .setHintTextSize(16) // 设置字体大小
                                .setHintTextColor(Color.GRAY)  // 设置字体颜色
                                .show();
                        break;
                }
            }
        });
    }
}
