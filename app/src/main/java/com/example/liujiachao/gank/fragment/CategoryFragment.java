package com.example.liujiachao.gank.fragment;


import android.graphics.Typeface;
import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.liujiachao.gank.R;
import com.example.liujiachao.gank.adapter.MyFragmentAdapter;
import com.example.liujiachao.gank.util.Constant;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujiachao on 2016/10/8.
 */
public class CategoryFragment extends Fragment {
    private ViewPager viewPager;
    private TabLayout mTabLayout;
    private View mView;
    private TextView titleCategory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.category_layout,container,false);
        viewPager = (ViewPager)mView.findViewById(R.id.viewpager);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        titleCategory = (TextView)mView.findViewById(R.id.title_category);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Pacifico.ttf");
        titleCategory.setTypeface(typeface);
        setupViewPager();
    }

    private void setupViewPager() {
        mTabLayout = (TabLayout)mView.findViewById(R.id.tabs);
        List<String> titles = new ArrayList<>();
        titles.add("iOS");
        titles.add("Android");
        titles.add("前端");
        titles.add("App");
        titles.add("休息视频");
        titles.add("拓展资源");

        List<Fragment> fragments = new ArrayList<>();

        fragments.add(ListFragment.newInstance(Constant.IOS_TYPE));
        fragments.add(ListFragment.newInstance(Constant.ANDORID_TYPE));
        fragments.add(ListFragment.newInstance(Constant.WEB_TYPE));
        fragments.add(ListFragment.newInstance(Constant.APP_TYPE));
        fragments.add(ListFragment.newInstance(Constant.REST_TYPE));
        fragments.add(ListFragment.newInstance(Constant.EXTEND_TYPE));

        MyFragmentAdapter adapter = new MyFragmentAdapter(
                getChildFragmentManager(),fragments,titles);
        viewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(viewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);


    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
