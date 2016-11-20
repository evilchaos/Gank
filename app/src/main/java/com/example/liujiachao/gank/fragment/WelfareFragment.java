package com.example.liujiachao.gank.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.liujiachao.gank.R;
import com.example.liujiachao.gank.adapter.GankAdapter;
import com.example.liujiachao.gank.adapter.WelfareAdapter;
import com.example.liujiachao.gank.entity.GankData;
import com.example.liujiachao.gank.entity.NewsItem;
import com.example.liujiachao.gank.http.BaseCallback;
import com.example.liujiachao.gank.http.OkHttpUtils;
import com.example.liujiachao.gank.inteface.OnLoadDataListener;
import com.example.liujiachao.gank.inteface.OnLoadImageUrlSuccess;
import com.example.liujiachao.gank.util.Constant;
import com.example.liujiachao.gank.util.GankApi;
import com.example.liujiachao.gank.util.NetworkUtils;
import com.example.liujiachao.gank.widget.DividerItemDecoration;
import com.example.liujiachao.gank.widget.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by liujiachao on 2016/10/8.
 */
public class WelfareFragment extends Fragment{
    private OkHttpUtils okHttpUtils = OkHttpUtils.getInstance();

    private final static int STATE_NORMAL = 0;
    private final static int STATE_REFRESH = 1;
    private final static int STATE_MORE =2 ;
    private int state = STATE_NORMAL;

    private View mView;
    private RecyclerView mRecyclerView;
    private MaterialRefreshLayout mRefreshLayout;
    TextView tvWelfare;

    private WelfareAdapter adapter;

    private int pageSize = 20;
    private int currPage = 1;
    private List<NewsItem> newsItems = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.welfare_layout,container,false);
        tvWelfare = (TextView)mView.findViewById(R.id.welfare_tag);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Pacifico.ttf");
        tvWelfare.setTypeface(typeface);
        mRefreshLayout = (MaterialRefreshLayout)mView.findViewById(R.id.welfare_refresh);
        mRecyclerView = (RecyclerView)mView.findViewById(R.id.welfare_recycle);

        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
    }

    private void initViews() {
        initRefreshLayout();
        getData();
    }

    private void initRefreshLayout() {
        mRefreshLayout.setLoadMore(true);
        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                refreshData();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout){
                loadMoreData();
            }
        });
    }

    private void getData() {
        String url = GankApi.category_url + "福利/" + String.valueOf(pageSize) + "/" + String.valueOf(currPage);
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
                adapter = new WelfareAdapter(getContext(),newsItems);
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                mRecyclerView.setAdapter(adapter);
                mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2,10,true));
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.setHasFixedSize(true);
                break;

            case STATE_REFRESH:
                adapter.clear();
                adapter.addData(newsItems);
                mRecyclerView.scrollToPosition(0);
                mRefreshLayout.finishRefresh();
                break;

            case STATE_MORE:
                adapter.addData(adapter.getDatas().size(),newsItems);
                mRecyclerView.scrollToPosition(adapter.getDatas().size());
                mRefreshLayout.finishRefreshLoadMore();
        }
    }

    private void refreshData() {
        currPage = 1;
        state = STATE_REFRESH;
        getData();

    }

    private void loadMoreData() {
        currPage++;
        state = STATE_MORE;
        getData();
    }


}
