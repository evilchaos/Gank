package com.example.liujiachao.gank.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andbase.tractor.utils.LogUtils;
import com.example.liujiachao.gank.R;

import com.example.liujiachao.gank.activity.DailyContentActivity;
import com.example.liujiachao.gank.adapter.CardAdapter;
import com.example.liujiachao.gank.entity.GankData;
import com.example.liujiachao.gank.entity.NewsItem;
import com.example.liujiachao.gank.http.BaseCallback;
import com.example.liujiachao.gank.http.OkHttpUtils;
import com.example.liujiachao.gank.util.Constant;
import com.example.liujiachao.gank.util.GankApi;
import com.huxq17.swipecardsview.SwipeCardsView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

public class DailyGankFragment extends Fragment  {

    private OkHttpUtils okHttpUtils = OkHttpUtils.getInstance();

    private View mView;
    private SwipeCardsView swipeCardsView;
    private CardAdapter cardAdapter;
    private TextView tvDaily;
    private ImageView iv_retry;
    private ImageView imageView;

    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFRESH = 1;
    private static final int STATE_MORE = 2;
    private int state = STATE_NORMAL;

    private int currPage = 1;
    private int curIndex;

    private List<NewsItem> newsItems = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.daily_layout,container,false);
        return mView;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
    }

    private void initViews() {

        tvDaily = (TextView)mView.findViewById(R.id.tv_daily);
        iv_retry = (ImageView) mView.findViewById(R.id.iv_retry);

        iv_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRetry();
            }
        });

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Pacifico.ttf");
        tvDaily.setTypeface(typeface);

        imageView = (ImageView)mView.findViewById(R.id.iv_refresh);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshData();
            }
        });

        swipeCardsView = (SwipeCardsView) mView.findViewById(R.id.swipCardsView);
        swipeCardsView.enableSwipe(true);
        swipeCardsView.setCardsSlideListener(new SwipeCardsView.CardsSlideListener() {
            @Override
            public void onShow(int index) {
                curIndex = index;
                LogUtils.i("test showing index = " + index);
            }

            //卡片滑动时回调
            @Override
            public void onCardVanish(int index, SwipeCardsView.SlideType type) {
                if (curIndex == newsItems.size() - 1) {
                    loadMoreData();
                }
            }

            @Override
            public void onItemClick(View cardImageView, int index) {
                NewsItem newsItem = newsItems.get(index);
                final String para_date = parseRawDate(newsItem.getPublishedAt());
                final String pic_url = newsItem.getUrl();

                Intent intent = new Intent(getActivity(), DailyContentActivity.class);
                intent.putExtra("daily_date",para_date);
                intent.putExtra("picture_url",pic_url);
                startActivity(intent);
            }
        });
        getData();
    }

    public void doRetry() {
        refreshData();
    }

    //刷新数据，点击右上角按钮会被调用
    private void refreshData(){
        currPage = 1;
        state = STATE_REFRESH;
        getData();
    }

    private void loadMoreData() {
        state = STATE_MORE;
        currPage++;
        getData();
    }

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
                List<NewsItem> mDatas  = gankData.getResults();
                newsItems.addAll(mDatas);
                show();
            }

            @Override
            public void OnError(Response response, int code, Exception e) {

            }
        });
    }

    private void show() {
        switch (state) {
            case STATE_NORMAL:
                cardAdapter = new CardAdapter(newsItems,getContext());
                swipeCardsView.setAdapter(cardAdapter);
                break;

            case STATE_REFRESH:
                cardAdapter.clear();
                cardAdapter.setData(newsItems);
                swipeCardsView.notifyDatasetChanged(0);
                break;

            case STATE_MORE:
                cardAdapter.setData(newsItems);
                swipeCardsView.notifyDatasetChanged(curIndex + 1);
                break;
        }
    }

    private String parseRawDate(String rawDate) {
        String[] newStr = rawDate.split("T");
        return newStr[0].replace('-','/');
    }
}
