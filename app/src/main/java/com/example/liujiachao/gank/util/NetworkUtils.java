package com.example.liujiachao.gank.util;

import com.example.liujiachao.gank.entity.GankData;
import com.example.liujiachao.gank.inteface.OnLoadDataListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by liujiachao on 2016/10/12.
 */
public class NetworkUtils {
    public final static int pageSize = 20;
    public static final int GET_DURATION = 2000;
    private long lastTime;

    public void getGankNews(final int type ,final int page,final OnLoadDataListener listener) {
        lastTime = System.currentTimeMillis();
        final Callback<GankData> callback = new Callback<GankData>() {
            @Override
            public GankData parseNetworkResponse(Response response, int id) throws Exception {
                return Json.parseGankNews(response.body().string());
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                if(System.currentTimeMillis() - lastTime < GET_DURATION) {
                    requestData(type,page);
                }
            }

            @Override
            public void onResponse(GankData response, int id) {
                listener.OnLoadDataSuccess(type,response);

            }
        };

        requestData(type,page);

    }

    private void requestData(int type,int page) {

        switch (type) {
            case Constant.ANDORID_TYPE:
                OkHttpUtils.get().url(GankApi.category_url + "Android/" + String.valueOf(pageSize) + "/" + String.valueOf(page));
                break;
            case Constant.IOS_TYPE:
                OkHttpUtils.get().url(GankApi.category_url + "iOS/" + String.valueOf(pageSize) + "/" + String.valueOf(page));
                break;
            case Constant.APP_TYPE:
                OkHttpUtils.get().url(GankApi.category_url + "App/" + String.valueOf(pageSize) + "/" + String.valueOf(page));
                break;
            case Constant.EXTEND_TYPE:
                OkHttpUtils.get().url(GankApi.category_url + "拓展资源/" + String.valueOf(pageSize) + "/" + String.valueOf(page));
                break;
            case Constant.REST_TYPE:
                OkHttpUtils.get().url(GankApi.category_url + "休息视频/" + String.valueOf(pageSize) + "/" + String.valueOf(page));
                break;
            case Constant.WEB_TYPE:
                OkHttpUtils.get().url(GankApi.category_url + "前端/" + String.valueOf(pageSize) + "/" + String.valueOf(page));
                break;
            case Constant.WELFARE_TYPE:
                OkHttpUtils.get().url(GankApi.category_url + "福利/" + String.valueOf(pageSize) + "/" + String.valueOf(page));
        }
    }

}
