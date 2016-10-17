package com.example.liujiachao.gank.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liujiachao.gank.R;
import com.example.liujiachao.gank.adapter.WelfareAdapter;
import com.example.liujiachao.gank.entity.GankData;
import com.example.liujiachao.gank.entity.NewsItem;
import com.example.liujiachao.gank.inteface.OnLoadDataListener;
import com.example.liujiachao.gank.util.Constant;
import com.example.liujiachao.gank.util.NetworkUtils;

import java.util.List;

/**
 * Created by liujiachao on 2016/10/8.
 */
public class WelfareFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,OnLoadDataListener{

    private final static int RECEIVER_SUCCESS = 0;
    private static Handler mHandler;
    private WelfareAdapter adapter;
    private View mView;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private StaggeredGridLayoutManager layoutManager;
    private int lastPostion;
    private int page;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.welfare_layout,container);
        return mView;
    }

    private void OnListScrolled() {
        int[] spans = new int[layoutManager.getSpanCount()];
        lastPostion = layoutManager.findLastVisibleItemPositions(spans)[1];
        if (lastPostion + 2 >= layoutManager.getItemCount() ) {
            NetworkUtils.getGankNews(Constant.WELFARE_TYPE,page,false,this);
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
    }

    private void initViews() {
        refreshLayout = (SwipeRefreshLayout)mView.findViewById(R.id.welfare_refresh);

        recyclerView = (RecyclerView)mView.findViewById(R.id.welfare_recycle);
        layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new WelfareAdapter();
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    OnListScrolled();
                }
            }
        });

        receiveData();
        onRefresh();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onRefresh() {
        page = 1;
        NetworkUtils.getGankNews(Constant.WELFARE_TYPE, page, true, this);

    }

    @Override
    public void OnLoadDataSuccess(int type, boolean isRefresh, GankData gankData) {
        Message msg = Message.obtain();
        msg.what = RECEIVER_SUCCESS;
        Bundle bundle = new Bundle();
        bundle.putSerializable("news", gankData);
        bundle.putBoolean("isRefresh", isRefresh);
        msg.setData(bundle);
        mHandler.sendMessage(msg);

    }

    private void receiveData() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case RECEIVER_SUCCESS:
                        boolean isRefresh = msg.getData().getBoolean("isRefresh");
                        GankData gankData = (GankData)msg.getData().getSerializable("news");
                        List<NewsItem> newsItems = gankData.getResults();
                        //加载最新数据还是添加老数据
                        if (isRefresh) {
                            //更新数据源
                            adapter.updateData(newsItems);
                        }  else {
                            //向数据源添加数据
                            adapter.addData(newsItems);
                        }
                        page++;
                }
                super.handleMessage(msg);
            }
        };
    }
}
