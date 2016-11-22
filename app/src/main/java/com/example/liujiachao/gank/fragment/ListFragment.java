package com.example.liujiachao.gank.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.liujiachao.gank.R;
import com.example.liujiachao.gank.adapter.BaseAdapter;
import com.example.liujiachao.gank.adapter.GankAdapter;
import com.example.liujiachao.gank.entity.GankData;
import com.example.liujiachao.gank.entity.NewsItem;
import com.example.liujiachao.gank.http.BaseCallback;
import com.example.liujiachao.gank.http.OkHttpUtils;
import com.example.liujiachao.gank.inteface.OnLoadDataListener;
import com.example.liujiachao.gank.util.Constant;
import com.example.liujiachao.gank.util.GankApi;
import com.example.liujiachao.gank.util.NetworkUtils;
import com.example.liujiachao.gank.util.SPUtil;
import com.example.liujiachao.gank.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by liujiachao on 2016/10/12.
 */
public class ListFragment extends Fragment {

    private OkHttpUtils okHttpUtils = OkHttpUtils.getInstance();
    private  static final int STATE_NORMAL=0;
    private  static final int STATE_REFREH=1;
    private  static final int STATE_MORE=2;
    private int state = STATE_NORMAL;


    private View mView;
    private RecyclerView recyclerView;
    private MaterialRefreshLayout mRefreshLayout;
    private GankAdapter adapter;

    private int currPage = 1;
    private int pageSize = 20;
    private int type;

    private String category_url;
    private List<NewsItem> newsItems = new ArrayList<>();

    public static ListFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.TYPE,type);
        ListFragment fragment = new ListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.list_fragment,container,false);
        mRefreshLayout = (MaterialRefreshLayout)mView.findViewById(R.id.swipe_refresh);
        recyclerView = (RecyclerView)mView.findViewById(R.id.recycle);
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void initViews() {

        type = getArguments().getInt(Constant.TYPE);
        this.category_url = buildCategoryUrl(type);
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

    private void loadMoreData() {
        currPage++;
        state = STATE_MORE;
        getData();
    }

    private void refreshData() {
        currPage = 1;
        state = STATE_REFREH;
        getData();
    }

    private void getData() {
        String url = category_url + String.valueOf(currPage);
        okHttpUtils.get(url, new BaseCallback<GankData>() {
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
                adapter = new GankAdapter(getContext(),newsItems);
                adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String url = newsItems.get(position).getUrl();
                        Uri uri = Uri.parse(url);
                        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                        startActivity(intent);
                    }
                });
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));
                break;

            case STATE_REFREH:
                adapter.clear();
                adapter.addData(newsItems);
                recyclerView.scrollToPosition(0);
                mRefreshLayout.finishRefresh();
                break;

            case STATE_MORE:
                adapter.addData(adapter.getDatas().size(),newsItems);
                recyclerView.scrollToPosition(adapter.getDatas().size());
                mRefreshLayout.finishRefreshLoadMore();
        }
    }





    public  String buildCategoryUrl(int type) {
        String category_url = null;

        switch (type) {
            case Constant.ANDORID_TYPE:
                category_url = GankApi.category_url + "Android/" + String.valueOf(pageSize) + "/";
                break;
            case Constant.IOS_TYPE:
                category_url = GankApi.category_url + "iOS/" + String.valueOf(pageSize) + "/";
                break;
            case Constant.APP_TYPE:
                category_url = GankApi.category_url + "App/" + String.valueOf(pageSize) + "/";
                break;
            case Constant.EXTEND_TYPE:
                category_url = GankApi.category_url + "拓展资源/" + String.valueOf(pageSize) + "/";
                break;
            case Constant.REST_TYPE:
                category_url = GankApi.category_url + "休息视频/" + String.valueOf(pageSize) + "/";
                break;
            case Constant.WEB_TYPE:
                category_url = GankApi.category_url + "前端/" + String.valueOf(pageSize) + "/";
                break;
        }

        return category_url;
    }
}
