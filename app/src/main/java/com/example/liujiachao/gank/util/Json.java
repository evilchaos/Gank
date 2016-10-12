package com.example.liujiachao.gank.util;

import android.util.JsonReader;
import android.util.JsonWriter;

import com.example.liujiachao.gank.entity.GankData;
import com.example.liujiachao.gank.entity.RealmString;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by liujiachao on 2016/10/12.
 */
public class Json {

    public Json() {
    }

    public static Type token = new TypeToken<RealmList<RealmString>>() {
    }.getType();

    public static Gson mGson = new GsonBuilder().
            setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getDeclaringClass().equals(RealmObject.class);
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            })
            .registerTypeAdapter(token, new TypeAdapter<RealmList<RealmString>>() {

                @Override
                public void write(com.google.gson.stream.JsonWriter out, RealmList<RealmString> value) throws IOException {

                }

                @Override
                public RealmList<RealmString> read(com.google.gson.stream.JsonReader in) throws IOException {
                    RealmList<RealmString> list = new RealmList<>();
                    in.beginArray();
                    while (in.hasNext()) {
                        list.add(new RealmString(in.nextString()));
                    }
                    in.endArray();
                    return list;
                }
            }).create();

    public static GankData parseGankNews(String gankNews) {
        return mGson.fromJson(gankNews, GankData.class);
    }
}
