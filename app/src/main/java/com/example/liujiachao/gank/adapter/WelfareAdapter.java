package com.example.liujiachao.gank.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.liujiachao.gank.R;
import com.example.liujiachao.gank.entity.NewsItem;
import com.example.liujiachao.gank.widget.RatioImageView;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by liujiachao on 2016/10/16.
 */
public class WelfareAdapter extends SimpleAdapter<NewsItem>{

    public WelfareAdapter(Context context, List<NewsItem> datas) {
        super(context,R.layout.grid_item,datas);
    }

    @Override
    public void bindData(BaseViewHolder viewHolder, NewsItem newsItem) {
        RatioImageView ratioImageView = (RatioImageView)viewHolder.getView(R.id.iv_item);
        ratioImageView.setOriginalSize(newsItem.getWidth(),newsItem.getHeight());
        Glide.with(mContext).load(newsItem.getUrl()).diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade().into(ratioImageView);


    }
}
