package com.example.liujiachao.gank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.liujiachao.gank.R;
import com.example.liujiachao.gank.entity.GankData;
import com.example.liujiachao.gank.entity.NewsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by evilchaos on 16/10/18.
 */
public class DailyAdapter extends SimpleAdapter<NewsItem> {


    public DailyAdapter(Context context, List<NewsItem> datas) {
        super(context, R.layout.daily_pic_item,datas);
    }

    @Override
    public void bindData(BaseViewHolder viewHolder, NewsItem newsItem) {
        Glide.with(mContext).load(newsItem.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade().into(viewHolder.getImageView(R.id.daily_pic));
    }
}
