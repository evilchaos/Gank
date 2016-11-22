package com.example.liujiachao.gank.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.liujiachao.gank.R;
import com.example.liujiachao.gank.entity.NewsItem;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by liujiachao on 2016/11/18.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private List<NewsItem> newsItems = new ArrayList<>();
    private View mView;

    public ViewPagerAdapter(Context context, List<NewsItem> datas) {
        this.context = context;
        this.newsItems = datas;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        mView = LayoutInflater.from(context).inflate(R.layout.item_image,container,false);
        final PhotoView photoView = (PhotoView) mView.findViewById(R.id.galary_image);


        Glide.with(context).load(newsItems.get(position).getUrl())
                .into(photoView);

        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                Activity activity = (Activity)context;
                activity.finish();
            }
        });

        container.addView(mView);
        return mView;
    }

    @Override
    public int getCount() {
        return newsItems.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View)object;
        container.removeView(view);
    }
}
