package com.example.liujiachao.gank.activity;

import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.liujiachao.gank.R;
import com.example.liujiachao.gank.entity.Tab;
import com.example.liujiachao.gank.fragment.CategoryFragment;
import com.example.liujiachao.gank.fragment.DailyGankFragment;
import com.example.liujiachao.gank.fragment.WelfareFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private FragmentTabHost mTabHost;
    private LayoutInflater mInflater;

    private List<Tab> mTabs = new ArrayList<>(3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        initTab();
    }

    private void initTab() {
        Tab tab_dailygank = new Tab("每日一干",DailyGankFragment.class);
        Tab tab_category = new Tab("干货分类",CategoryFragment.class);
        Tab tab_welfare = new Tab("每日福利",WelfareFragment.class);

        mTabs.add(tab_dailygank);
        mTabs.add(tab_category);
        mTabs.add(tab_welfare);

        mInflater = LayoutInflater.from(this);
        mTabHost = (FragmentTabHost)this.findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.content_layout);

        for (Tab tab : mTabs) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(tab.getTitle());
            tabSpec.setIndicator(buildIndicator(tab));
            mTabHost.addTab(tabSpec,tab.getFragment(),null);
        }

        mTabHost.setCurrentTab(0);
    }

    private View buildIndicator(Tab tab) {
        View view = mInflater.inflate(R.layout.tab_indicator,null);
        TextView textIndicator = (TextView)view.findViewById(R.id.txt_indicator);
        textIndicator.setText(tab.getTitle());

        return view;
    }

}
