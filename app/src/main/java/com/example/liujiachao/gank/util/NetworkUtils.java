package com.example.liujiachao.gank.util;

import com.example.liujiachao.gank.entity.DailyData;
import com.example.liujiachao.gank.entity.GankData;
import com.example.liujiachao.gank.inteface.OnLoadDailyContentListener;
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
    public static long lastTime;



    public static void getDailyNews(final String date, final OnLoadDailyContentListener listener) {
        lastTime = System.currentTimeMillis();
        final Callback<DailyData> callback = new Callback<DailyData>() {
            @Override
            public DailyData parseNetworkResponse(Response response, int id) throws Exception {
                return Json.parseDailyNews(response.body().string());
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                if(System.currentTimeMillis() - lastTime < GET_DURATION) {
                    OkHttpUtils.get().url(GankApi.daily_url + date);
                }
            }

            @Override
            public void onResponse(DailyData response, int id) {
                listener.OnLoadDailyContentSuccess(response);
            }
        };
        OkHttpUtils.get().url(GankApi.daily_url + date).build().execute(callback);
    }

    public static void requestData(int type,int page,Callback<GankData> callback) {
        String get_url = null;

        switch (type) {
            case Constant.ANDORID_TYPE:
                get_url = GankApi.category_url + "Android/" + String.valueOf(pageSize) + "/" + String.valueOf(page);
                break;
            case Constant.IOS_TYPE:
                get_url = GankApi.category_url + "iOS/" + String.valueOf(pageSize) + "/" + String.valueOf(page);
                break;
            case Constant.APP_TYPE:
                get_url = GankApi.category_url + "App/" + String.valueOf(pageSize) + "/" + String.valueOf(page);
                break;
            case Constant.EXTEND_TYPE:
                get_url = GankApi.category_url + "拓展资源/" + String.valueOf(pageSize) + "/" + String.valueOf(page);
                break;
            case Constant.REST_TYPE:
                get_url = GankApi.category_url + "休息视频/" + String.valueOf(pageSize) + "/" + String.valueOf(page);
                break;
            case Constant.WEB_TYPE:
                get_url = GankApi.category_url + "前端/" + String.valueOf(pageSize) + "/" + String.valueOf(page);
                break;
            case Constant.WELFARE_TYPE:
                get_url = GankApi.category_url + "福利/" + String.valueOf(pageSize) + "/" + String.valueOf(page);
                break;
        }
        OkHttpUtils.get().url(get_url).build().execute(callback);
    }

}
