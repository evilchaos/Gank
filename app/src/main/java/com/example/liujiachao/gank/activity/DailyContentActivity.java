package com.example.liujiachao.gank.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.liujiachao.gank.R;
import com.example.liujiachao.gank.entity.DailyData;
import com.example.liujiachao.gank.entity.GankData;
import com.example.liujiachao.gank.entity.NewsItem;
import com.example.liujiachao.gank.inteface.OnLoadDailyContentListener;
import com.example.liujiachao.gank.inteface.OnLoadDataListener;
import com.example.liujiachao.gank.util.Constant;
import com.example.liujiachao.gank.util.NetworkUtils;

import java.util.List;

/**
 * Created by evilchaos on 16/10/18.
 */
public class DailyContentActivity extends AppCompatActivity implements OnLoadDailyContentListener{

    private static Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String date = intent.getStringExtra("daily_date");

        setContentView(R.layout.daily_content);
        receiveData();
        NetworkUtils.getDailyNews(date,this);


    }




    private void receiveData() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case Constant.RECEIVER_SUCCESS:
                        DailyData dailyData = (DailyData)msg.getData().getSerializable("news");
                        //绘制视图

                }
                super.handleMessage(msg);
            }
        };
    }

    @Override
    public void OnLoadDailyContentSuccess(DailyData dailyData) {
        Message msg = Message.obtain();
        msg.what = Constant.RECEIVER_SUCCESS;
        Bundle bundle = new Bundle();
        bundle.putSerializable("news", dailyData);
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }
}
