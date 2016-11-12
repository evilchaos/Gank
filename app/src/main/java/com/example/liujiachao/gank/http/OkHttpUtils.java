package com.example.liujiachao.gank.http;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by liujiachao on 2016/11/9.
 */
public class OkHttpUtils {

    private static OkHttpUtils mInstance;
    private static final int TIME_OUT = 30;
    private static OkHttpClient okHttpClient;
    private Handler mHandler;
    private Gson mGson;

    private static class SingletonHolder {
        private static final OkHttpUtils mInstance = new OkHttpUtils();
    }

    private OkHttpUtils() {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpBuilder.followRedirects(true);
        okHttpClient = okHttpBuilder.build();

        mGson = new Gson();

        mHandler = new Handler(Looper.getMainLooper());
    }
    public static OkHttpUtils getInstance() {
        return SingletonHolder.mInstance;
    }

    public void get(String url,BaseCallback callback) {
        Request request = buildRequest(url,null,HttpMethodType.GET);
        doRequest(request,callback);
    }

    public void post(String url, Map<String,String> params,BaseCallback callback) {
        Request request = buildRequest(url,params,HttpMethodType.POST);
        doRequest(request, callback);
    }

    //发送请求
    private void doRequest(final Request request, final BaseCallback callback) {

        callback.onBeforeRequest(request);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                wrapFailureCallback(request,callback,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                wrapResponseCallback(callback,response);

                if (response.isSuccessful()) {
                    String result = response.body().string();
                    try {
                        Object obj = mGson.fromJson(result,callback.mType);
                        wrapSuccessCallback(callback,response,obj);
                    } catch (JsonParseException e){
                        callback.OnError(response,response.code(),e );
                    }
                } else {
                    wrapErrorCallback(callback,response,null);
                }

            }
        });
    }

    //构造请求
    private Request buildRequest(String url,Map<String,String> params,HttpMethodType methodType){
        Request.Builder builder = new Request.Builder();
        builder.url(url);

        if(methodType == HttpMethodType.GET) {
            builder.get();
        } else if (methodType == HttpMethodType.POST){
            FormBody body = buildRequestBody(params);
            builder.post(body);
        }
        return builder.build();
    }

    private FormBody buildRequestBody(Map<String,String> params) {
        FormBody.Builder body = new FormBody.Builder();
        for (Map.Entry<String,String> entry :params.entrySet()) {
            body.add(entry.getKey(),entry.getValue());
        }

        return body.build();
    }

    private void  wrapFailureCallback(final Request request, final BaseCallback callback, final Exception e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onFailure(request,e);
            }
        });
    }

    private void wrapResponseCallback(final BaseCallback callback, final Response response) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(response);
            }
        });
    }

    private void wrapSuccessCallback(final BaseCallback callback,final Response response, final Object obj) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(response, obj);
            }
        });
    }

    private void wrapErrorCallback(final BaseCallback callback,final Response response, final Exception e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.OnError(response,response.code(),e);
            }
        });
    }

    enum HttpMethodType{
        GET,
        POST
    }
}
