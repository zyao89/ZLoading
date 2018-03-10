package com.zyao89.zcustomview.loading;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.zyao89.view.zloading.ZLoadingView;
import com.zyao89.view.zloading.Z_TYPE;
import com.zyao89.zcustomview.R;

import java.util.Random;

/**
 * Created by zyao89 on 2018/3/11.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 * My Blog: https://zyao89.cn
 */
public class ShowTimeAllActivity extends AppCompatActivity
{
    private RecyclerView mRecyclerView;
    private GridLayoutManager mManagerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_time);

        init();
    }

    private void init()
    {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        mManagerLayout = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(mManagerLayout);
        mRecyclerView.setAdapter(new MyRecycleViewAdapter());
    }

    private static class MyRecycleViewAdapter extends RecyclerView.Adapter<MyViewHolder>
    {

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            ZLoadingView zLoadingView = new ZLoadingView(parent.getContext());
            return new MyViewHolder(zLoadingView, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
        {
            if (holder.itemView instanceof ZLoadingView) {
                ((ZLoadingView) holder.itemView).setLoadingBuilder(Z_TYPE.values()[position]);
                ((ZLoadingView) holder.itemView).setColorFilter(Color.WHITE);
            }
        }

        @Override
        public int getItemCount()
        {
            return Z_TYPE.values().length;
        }
    }

    private static class MyViewHolder extends RecyclerView.ViewHolder {

        private MyViewHolder(View itemView, ViewGroup parent)
        {
            super(itemView);
            int measuredWidth = parent.getMeasuredWidth();
            itemView.setMinimumHeight(measuredWidth / 3);
            itemView.setPadding(measuredWidth / 9, measuredWidth / 9, measuredWidth / 9, measuredWidth / 9);
            int rgb = Color.rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));
            itemView.setBackgroundColor(rgb);
        }
    }
}
