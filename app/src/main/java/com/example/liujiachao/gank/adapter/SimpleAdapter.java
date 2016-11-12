package com.example.liujiachao.gank.adapter;

import android.content.Context;

import java.util.List;

/**
 * Created by liujiachao on 2016/11/10.
 */
public abstract class SimpleAdapter<T> extends BaseAdapter<T,BaseViewHolder> {
    public SimpleAdapter(Context context, int mLayoutResId) {
        super(context, mLayoutResId);
    }

    public SimpleAdapter(Context context, int mLayoutResId, List<T> datas) {
        super(context, mLayoutResId, datas);
    }
}
