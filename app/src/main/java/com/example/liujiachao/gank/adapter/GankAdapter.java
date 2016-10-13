package com.example.liujiachao.gank.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.liujiachao.gank.entity.NewsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by evilchaos on 16/10/13.
 */
public class GankAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<NewsItem> newsItemList = new ArrayList<>();


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return newsItemList.size();
    }


}
