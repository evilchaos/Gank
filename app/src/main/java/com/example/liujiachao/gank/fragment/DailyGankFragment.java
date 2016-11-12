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
import com.example.liujiachao.gank.adapter.BaseAdapter;
import com.example.liujiachao.gank.adapter.DailyAdapter;
import com.example.liujiachao.gank.entity.GankData;
import com.example.liujiachao.gank.entity.NewsItem;
import com.example.liujiachao.gank.http.BaseCallback;
import com.example.liujiachao.gank.http.OkHttpUtils;
import com.example.liujiachao.gank.util.Constant;
import com.example.liujiachao.gank.util.GankApi;

import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

public class DailyGankFragment extends Fragment  {

    private View mView;
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private DailyAdapter adapter;

    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFRESH = 1;
    private static final int STATE_MORE = 2;

    private int state = STATE_NORMAL;

    private int currPage = 1;
    private int lastPostion;
    private OkHttpUtils okHttpUtils = OkHttpUtils.getInstance();;

    private List<NewsItem> newsItems;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.daily_layout,container,false);
        return mView;
    }


    //表示activity执行OnCreate方法完成了的时候调用此方法
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
    }

    private void initViews() {
        recyclerView = (RecyclerView)mView.findViewById(R.id.daily_recycle);
        manager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    OnListScrolled();
                }
            }
        });

        getData();
    }

    private void OnListScrolled() {

        lastPostion = manager.findLastVisibleItemPosition();
        if (lastPostion + 3 == manager.getItemCount() ) {
            loadMoreData();

        }
    }

    //刷新数据，点击右上角按钮会被调用
    private void refreshData(){
        currPage = 1;
        state = STATE_REFRESH;
        getData();
    }

    //加载更多
    private void loadMoreData() {
        state = STATE_MORE;
        currPage++;
        getData();
    }

    //只是发起网络请求
    private void getData() {
        String url = GankApi.category_url + "福利/" + String.valueOf(Constant.PAGE_SIZE) + "/" + String.valueOf(currPage);
        okHttpUtils.get(url,new BaseCallback<GankData>() {
            @Override
            public void onBeforeRequest(Request request) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onSuccess(Response response, GankData gankData) {
                newsItems = gankData.getResults();
                showData();
            }

            @Override
            public void OnError(Response response, int code, Exception e) {

            }
        });
    }

    private void showData() {
        switch (state) {
            case STATE_NORMAL:
                adapter = new DailyAdapter(getContext(),newsItems);
                adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener(){
                    @Override
                    public void onItemClick(View view, int position) {
                        NewsItem newsItem = adapter.getItem(position);
                        final String para_date = parseRawDate(newsItem.getPublishedAt());
                        final String pic_url = newsItem.getUrl();

                        Intent intent = new Intent(getActivity(), DailyContentActivity.class);
                        intent.putExtra("daily_date",para_date);
                        intent.putExtra("picture_url",pic_url);
                        startActivity(intent);

                    }
                });
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(manager);
                break;

            case STATE_REFRESH:
                adapter.clear();
                adapter.addData(newsItems);
                recyclerView.scrollToPosition(0);
                break;

            case STATE_MORE:
                adapter.addData(adapter.getDatas().size(),newsItems);
                recyclerView.scrollToPosition(adapter.getDatas().size());
                break;
        }

    }

    private String parseRawDate(String rawDate) {
        String[] newStr = rawDate.split("T");
        return newStr[0].replace('-','/');
    }

}
