package com.example.liujiachao.gank.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by liujiachao on 2016/10/9.
 */
public class ListFragment extends BaseFragment {

    private int type;

    public static final int IOS_TYPE = 0;
    public static final int ANDDORID_TYPE = 1;
    public static final int WEB_TYPE = 2;
    public static final int APP_TYPE = 3;
    public static final int REST_TYPE = 4;
    public static final int EXTEND_TYPE = 5;

   public static ListFragment newInstance(int type) {
       ListFragment fragment = new ListFragment();
       Bundle bundle = new Bundle();
       bundle.putInt("type",type);
       fragment.setArguments(bundle);
       return fragment;
   }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
