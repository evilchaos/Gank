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
import com.example.liujiachao.gank.entity.NewsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by evilchaos on 16/10/18.
 */
public class DailyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    private List<NewsItem> newsItemList = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.daily_pic_item,parent);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof  ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder)holder;
            NewsItem newsItem = newsItemList.get(position);
            Glide.with(mContext).load(newsItem.getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade().into(itemViewHolder.imageView);

            String rawDate = newsItem.getPublishedAt();
            final String para_date = parseRawdate(rawDate);

            if (mOnItemClickListener != null) {
                itemViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(itemViewHolder.itemView,para_date,position);
                    }
                });
            }
        }
    }

    private String parseRawdate(String rawDate) {
        String[] newStr = rawDate.split("T");
        return newStr[0].replace('-','/');
    }

    @Override
    public int getItemCount() {
        return newsItemList.size();
    }

    public void updateData(List<NewsItem> items) {
        newsItemList.clear();
        addData(items);
    }

    public void addData(List<NewsItem> items) {
        newsItemList.addAll(items);
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.daily_pic);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view,String date,int position);
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
