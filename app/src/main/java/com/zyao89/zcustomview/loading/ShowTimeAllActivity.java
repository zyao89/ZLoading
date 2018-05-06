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
import android.widget.FrameLayout;

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
            FrameLayout rootLayout = new FrameLayout(parent.getContext());
            ZLoadingView zLoadingView = new ZLoadingView(parent.getContext());
            rootLayout.addView(zLoadingView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return new MyViewHolder(rootLayout, zLoadingView, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
        {
            if (holder.mZLoadingView instanceof ZLoadingView) {
//                ((ZLoadingView) holder.itemView).setLoadingBuilder(Z_TYPE.values()[position], 0.2f);
                ((ZLoadingView) holder.mZLoadingView).setLoadingBuilder(Z_TYPE.values()[position]);
                ((ZLoadingView) holder.mZLoadingView).setColorFilter(Color.WHITE);
            }
        }

        @Override
        public int getItemCount()
        {
            return Z_TYPE.values().length;
        }
    }

    private static class MyViewHolder extends RecyclerView.ViewHolder {

        private final View mZLoadingView;

        private MyViewHolder(ViewGroup itemView, View zLoadingView, ViewGroup parent)
        {
            super(itemView);
            this.mZLoadingView = zLoadingView;

            initSize(parent);

            initListener();
        }

        private void initListener()
        {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if (mZLoadingView.isShown()) {
                        mZLoadingView.setVisibility(View.GONE);
                    } else {
                        mZLoadingView.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

        private void initSize(ViewGroup parent)
        {
            int measuredWidth = parent.getMeasuredWidth();
            itemView.setMinimumHeight(measuredWidth / 3);
            itemView.setPadding(measuredWidth / 9, measuredWidth / 9, measuredWidth / 9, measuredWidth / 9);
            int rgb = Color.rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));
            itemView.setBackgroundColor(rgb);
        }
    }
}
