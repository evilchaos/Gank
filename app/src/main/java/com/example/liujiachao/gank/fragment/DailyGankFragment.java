package com.example.liujiachao.gank.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liujiachao.gank.R;
import com.example.liujiachao.gank.activity.DailyContentActivity;
import com.example.liujiachao.gank.adapter.DailyAdapter;
import com.example.liujiachao.gank.entity.GankData;
import com.example.liujiachao.gank.entity.NewsItem;
import com.example.liujiachao.gank.inteface.OnLoadDataListener;
import com.example.liujiachao.gank.util.Constant;
import com.example.liujiachao.gank.util.NetworkUtils;

import java.util.List;

/**
 * Created by liujiachao on 2016/10/8.
 * 再添加一个按钮进行刷新，其实就是在按钮响应事件中调用getGankNews()
 *
 */
public class DailyGankFragment extends Fragment implements OnLoadDataListener {

    private View mView;
    private RecyclerView recyclerView;
    private DailyAdapter adapter;
    private LinearLayoutManager manager;
    private Handler mHandler;
    private int page;
    private int lastPostion;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.daily_layout,container);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
    }

    private void initViews() {
        recyclerView = (RecyclerView)mView.findViewById(R.id.daily_recycle);
        adapter = new DailyAdapter();

        adapter.setmOnItemClickListener(new DailyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String date,String pic_url,int position) {
                Intent intent = new Intent(getActivity(), DailyContentActivity.class);
                intent.putExtra("daily_date",date);
                intent.putExtra("picture_url",pic_url);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        manager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
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

        receiveData();
        initData();

    }

    private void OnListScrolled() {

        lastPostion = manager.findLastVisibleItemPosition();
        if (lastPostion + 3 == manager.getItemCount() ) {
            NetworkUtils.getGankNews(Constant.WELFARE_TYPE,page,false,this);
        }
    }

    private void initData() {
        page = 1;
        NetworkUtils.getGankNews(Constant.WELFARE_TYPE,page,true,this);
    }

    private void receiveData() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case Constant.RECEIVER_SUCCESS:
                        boolean isRefresh = msg.getData().getBoolean("isRefresh");//这个刷新是点击刷新图标进行刷新
                        GankData gankData = (GankData)msg.getData().getSerializable("news");
                        List<NewsItem> newsItems = gankData.getResults();
                        if (isRefresh) {
                            adapter.updateData(newsItems);
                        }  else {
                            adapter.addData(newsItems);
                        }
                        page++;
                }
                super.handleMessage(msg);
            }
        };
    }

    @Override
    public void OnLoadDataSuccess(int type, boolean isRefresh, GankData gankData) {
        Message msg = Message.obtain();
        msg.what = Constant.RECEIVER_SUCCESS;
        Bundle bundle = new Bundle();
        bundle.putSerializable("news", gankData);
        bundle.putBoolean("isRefresh", isRefresh);
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }
}
