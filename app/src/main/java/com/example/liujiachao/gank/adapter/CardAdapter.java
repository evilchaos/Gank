package com.example.liujiachao.gank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.liujiachao.gank.R;
import com.example.liujiachao.gank.entity.NewsItem;
import com.huxq17.swipecardsview.BaseCardAdapter;

import java.util.List;

/**
 * Created by evilchaos on 16/11/14.
 */

public class CardAdapter extends BaseCardAdapter {

    private List<NewsItem> datas;
    private Context mContext;

    public CardAdapter(List<NewsItem> datas,Context context) {
        this.datas = datas;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public int getCardLayoutId() {
        return R.layout.card_item;
    }

    public void setData(List<NewsItem> datas) {
        this.datas = datas;
    }

    public void clear(){
        datas.clear();
    }

    @Override
    public void onBindData(int position, View cardview) {
        if (datas == null && datas.size() == 0) {
            return;
        }

        ImageView imageView = (ImageView)cardview.findViewById(R.id.wel_pic);
        NewsItem newsItem = datas.get(position);
        String url = newsItem.getUrl();
        Glide.with(mContext).load(newsItem.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade().into(imageView);

    }
}
