package com.example.liujiachao.gank.adapter;

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
public class GankAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<NewsItem> newsItemList = new ArrayList<>();
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.news_item,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final NewsItem newsItem = newsItemList.get(position);
        if (holder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder)holder;
            String time = Dater.parseTime(newsItem.getPublishedAt());
            itemViewHolder.tvTime.setText(time);
            itemViewHolder.tvAuthor.setText(newsItem.getWho());
            itemViewHolder.tvCategory.setText(newsItem.getType());
            itemViewHolder.tvDesc.setText(newsItem.getDesc());
        }

    }

    public void updateData(List<NewsItem> items) {
        newsItemList.clear();
        newsItemList.addAll(items);
        notifyDataSetChanged();
    }

    public void addData(List<NewsItem> items) {
        newsItemList.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return newsItemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTime;
        public TextView tvAuthor;
        public TextView tvCategory;
        public TextView tvDesc;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvTime = (TextView)itemView.findViewById(R.id.tv_time);
            tvAuthor = (TextView)itemView.findViewById(R.id.tv_author);
            tvCategory = (TextView)itemView.findViewById(R.id.tv_category);
            tvDesc = (TextView)itemView.findViewById(R.id.title);
        }
    }
}
