package com.example.liujiachao.gank.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liujiachao.gank.R;
import com.example.liujiachao.gank.entity.GankData;
import com.example.liujiachao.gank.inteface.OnLoadDataListener;
import com.example.liujiachao.gank.util.Constant;
import com.example.liujiachao.gank.util.NetworkUtils;

/**
 * Created by liujiachao on 2016/10/12.
 */
public class AndroidFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,OnLoadDataListener {

    private final static int ANDROID_NEWS = 0;
    private int page;
    private View mView;
    private boolean isRefresh;
    private static Handler mHandler;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        receiveData();
        onRefresh();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.list_fragment,container,false);
        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onRefresh() {
        page = 1;
        isRefresh = true;
        NetworkUtils.getGankNews(Constant.ANDORID_TYPE,page,this);
    }

    @Override
    public void OnLoadDataSuccess(int type, GankData gankData) {
        Message msg = Message.obtain();
        msg.what = ANDROID_NEWS;
        Bundle bundle = new Bundle();
        bundle.putSerializable("android_news",gankData);
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }

    private void receiveData() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case ANDROID_NEWS:
                        GankData gankData = (GankData)msg.getData().getSerializable("android_news");
                        //加载最新数据还是添加老数据
                        if (isRefresh) {
                          //更新数据源
                        }  else {
                            //向数据源添加数据
                        }

                }
                super.handleMessage(msg);
            }
        };
    }
}
