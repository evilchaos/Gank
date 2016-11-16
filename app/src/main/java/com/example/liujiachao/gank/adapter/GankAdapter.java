package com.example.liujiachao.gank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.liujiachao.gank.R;
import com.example.liujiachao.gank.entity.NewsItem;
import com.example.liujiachao.gank.util.Dater;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by evilchaos on 16/10/13.
 */
public class GankAdapter extends SimpleAdapter<NewsItem> {

    public GankAdapter(Context context, List<NewsItem> datas) {
        super(context, R.layout.news_item,datas);
    }

    @Override
    public void bindData(BaseViewHolder viewHolder, NewsItem newsItem) {

        String time = Dater.parseTime(newsItem.getPublishedAt());
        viewHolder.getTextView(R.id.title).setText(newsItem.getDesc());
        viewHolder.getTextView(R.id.tv_category).setText(newsItem.getType());
        viewHolder.getTextView(R.id.tv_author).setText(newsItem.getWho());
        viewHolder.getTextView(R.id.tv_time).setText(time);
    }
}
