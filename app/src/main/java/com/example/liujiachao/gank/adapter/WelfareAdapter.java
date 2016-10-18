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
public class WelfareAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private List<NewsItem> pictures = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.grid_item,parent,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ImageViewHolder) {
            ImageViewHolder imageViewHolder = (ImageViewHolder)holder;
            try {
                 Bitmap bitmap = Glide.with(mContext).load(pictures.get(position).getUrl())
                        .asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(com.bumptech.glide.request.target.Target.SIZE_ORIGINAL, com.bumptech.glide.request.target.Target.SIZE_ORIGINAL)
                        .get();

                imageViewHolder.imageView.setOriginalSize(bitmap.getWidth(),bitmap.getHeight());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            Glide.with(mContext).load(pictures.get(position).getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade().into(imageViewHolder.imageView);

        }

    }

    @Override
    public int getItemCount() {
        return pictures.size();
    }

    public void updateData(List<NewsItem> items) {
        pictures.clear();
        addData(items);
    }

    public void addData(List<NewsItem> items) {
        pictures.addAll(items);
        notifyDataSetChanged();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public RatioImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = (RatioImageView)itemView.findViewById(R.id.image);
        }
    }

}
