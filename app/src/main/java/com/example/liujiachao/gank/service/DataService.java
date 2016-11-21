package com.example.liujiachao.gank.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.example.liujiachao.gank.entity.NewsItem;
import com.example.liujiachao.gank.fragment.WelfareFragment;
import com.example.liujiachao.gank.inteface.OnLoadImageUrlSuccess;
import com.example.liujiachao.gank.util.Constant;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by evilchaos on 16/11/20.
 */

public class DataService extends IntentService {


    public DataService() {
        super("");
    }

    public static void startService(Context context, List<NewsItem> datas) {
        Intent intent = new Intent(context,DataService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("newsItemList",(Serializable) datas);
        intent.putExtras(bundle);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent == null) {
            return;
        }

        List<NewsItem> newsItems = (List<NewsItem>)intent.getExtras().getSerializable("newsItemList");
        handleNewsItem(newsItems);
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

        Message msg = Message.obtain();
        msg.what = Constant.RECEIVER_SUCCESS;
        Bundle bundle = new Bundle();
        bundle.putSerializable("image_data",(Serializable) newsItemList);
        msg.setData(bundle);
        WelfareFragment.mHandler.sendMessage(msg);
    }
}
