package com.example.liujiachao.gank.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.example.liujiachao.gank.entity.NewsItem;
import com.example.liujiachao.gank.inteface.OnLoadImageUrlSuccess;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by evilchaos on 16/11/20.
 */

public class DataService extends IntentService {

    public static Handler mHandler = new Handler(Looper.getMainLooper());
    private static Context mContxext;

    public DataService() {
        super("");
    }

    public static void startService(Context context, List<NewsItem> datas) {
        mContxext = context;
        Intent intent = new Intent(context,DataService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("newsItemList",(Serializable) datas);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent == null) {
            return;
        }

        List<NewsItem> newsItemList = (List<NewsItem>) intent.getSerializableExtra("newsItemList");
        handleNewsItem(newsItemList);


    }

    private void handleNewsItem(final List<NewsItem> newsItemList) {
        if (newsItemList.size() == 0) {
            return;
        }

        for (NewsItem item : newsItemList) {
            try {
                Bitmap bitmap  = Glide.with(getBaseContext()).load(item.getUrl()).asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL)
                        .get();
                item.setWidth(bitmap.getWidth());
                item.setHeight(bitmap.getHeight());

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        mHandler.post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
