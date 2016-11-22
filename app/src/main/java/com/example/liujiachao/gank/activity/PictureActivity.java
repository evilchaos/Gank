package com.example.liujiachao.gank.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.liujiachao.gank.R;
import com.example.liujiachao.gank.adapter.ViewPagerAdapter;
import com.example.liujiachao.gank.entity.NewsItem;
import com.example.liujiachao.gank.widget.ViewPagerFixed;

import java.util.List;

/**
 * Created by liujiachao on 2016/11/22.
 */

public class PictureActivity extends AppCompatActivity implements  View.OnClickListener{

    private ViewPagerFixed mViewPagerFixed;
    private TextView mTextView;

    private List<NewsItem> newsItems;
    private int position;

    private ViewPagerAdapter mPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        newsItems = (List<NewsItem>) bundle.getSerializable("pic_urls");
        position = bundle.getInt("position");

        setContentView(R.layout.girl_show);

        initViews();
    }

    private void initViews() {
        mViewPagerFixed = (ViewPagerFixed)findViewById(R.id.girl_viewpager);
        mTextView = (TextView)findViewById(R.id.save);
        mTextView.setOnClickListener(this);
        
        setImageBrowse();
    }

    private void setImageBrowse() {
        if (mPagerAdapter == null && newsItems != null && newsItems.size() != 0) {
            mPagerAdapter = new ViewPagerAdapter(this,newsItems);
            mViewPagerFixed.setAdapter(mPagerAdapter);
            mViewPagerFixed.setCurrentItem(position);
        }
    }

    @Override
    public void onClick(View v) {
        
    }

}
