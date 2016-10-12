package com.example.liujiachao.gank.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.Nullable;

/**
 * Created by liujiachao on 2016/10/9.
 */
public class ListFragment extends BaseFragment {

    private int type;

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
        type = getArguments().getInt("type");


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
