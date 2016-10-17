package com.example.liujiachao.gank.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liujiachao.gank.R;
import com.example.liujiachao.gank.adapter.GankAdapter;
import com.example.liujiachao.gank.entity.GankData;
import com.example.liujiachao.gank.entity.NewsItem;
import com.example.liujiachao.gank.inteface.OnLoadDataListener;
import com.example.liujiachao.gank.util.Constant;
import com.example.liujiachao.gank.util.NetworkUtils;
import com.example.liujiachao.gank.util.SPUtil;

import java.util.List;

/**
 * Created by liujiachao on 2016/10/12.
 */
public class ListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,OnLoadDataListener {

    private final static int RECEIVER_SUCCESS = 0;
    private int page;
    private static Handler mHandler;

    private View mView;
    private RecyclerView recyclerView;
    private GankAdapter adapter;
    private LinearLayoutManager manager;
    private SwipeRefreshLayout swipeRefreshLayout;

    private int type;
//    private int firstPosition;
//    private int lastPosition;

    public static ListFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.TYPE,type);
        ListFragment fragment = new ListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
    }

    @Override
    public void onResume() {
        super.onResume();
       // page = SPUtil.getInt(type + Constant.PAGE);
//        lastPosition = SPUtil.getInt(type + Constant.POSITION);
//        recyclerView.scrollToPosition(lastPosition > 0 ? lastPosition:0);
    }

    @Override
    public void onPause() {
        //firstPosition = manager.findFirstVisibleItemPosition();
        super.onPause();
        //SPUtil.save(type + Constant.POSITION, firstPosition);
        //SPUtil.save(type + Constant.PAGE,page);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void initViews() {
        swipeRefreshLayout = (SwipeRefreshLayout)mView.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = (RecyclerView)mView.findViewById(R.id.recycle);
        adapter = new GankAdapter();
        recyclerView.setAdapter(adapter);
        manager = new LinearLayoutManager(recyclerView.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    OnListScrolled();
                }
            }
        });

        type = getArguments().getInt(Constant.TYPE);
        receiveData();
        onRefresh();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.list_fragment,container,false);
        return mView;
    }

    private void OnListScrolled() {
        //firstPosition = manager.findFirstVisibleItemPosition();
        int lastPosition = manager.findLastVisibleItemPosition();
        if (lastPosition + 1 == adapter.getItemCount() ) {
            NetworkUtils.getGankNews(type,page,false,this);
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        NetworkUtils.getGankNews(type, page, true, this);
    }

    @Override
    public void OnLoadDataSuccess(int type,boolean isRefresh, GankData gankData) {
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
