package com.example.liujiachao.gank.activity;


import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.example.liujiachao.gank.R;
import com.example.liujiachao.gank.fragment.CategoryFragment;
import com.example.liujiachao.gank.fragment.DailyGankFragment;
import com.example.liujiachao.gank.fragment.WelfareFragment;

public class HomeActivity extends AppCompatActivity {

    private static final int DAILY_FRAGMENT = 0X001;
    private static final int CATEGORY_FRAGMENT = 0X002;
    private static final int WELFARETAB_FRAGMENT = 0X003;

    private Context mContext;
    private int mCurrentIndex;

    private RelativeLayout mDailyGankTab;
    private RelativeLayout mCategoryTab;
    private RelativeLayout mWelfareTab;

    private DailyGankFragment mDailyGankFragment;
    private CategoryFragment mCateGoryFragment;
    private WelfareFragment mWelfareFragment;

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        mContext = this;
        mDailyGankTab = (RelativeLayout)findViewById(R.id.gank_daily);
        mCategoryTab = (RelativeLayout)findViewById(R.id.gank_category);
        mWelfareTab = (RelativeLayout)findViewById(R.id.gank_welfare);
        mFragmentManager = getSupportFragmentManager();

        setTabFragment(DAILY_FRAGMENT);

    }

    private void setTabFragment(int tab_index) {
        if (mCurrentIndex != tab_index) {
            FragmentTransaction fr = mFragmentManager.beginTransaction();
            hideAllFragments(fr);
            switch (tab_index) {
                case DAILY_FRAGMENT:
                    mDailyGankTab.setSelected(true);
                    if (mDailyGankFragment == null) {
                        mDailyGankFragment = new DailyGankFragment();
                        fr.add(R.id.content_layout,mDailyGankFragment);
                    } else {
                        fr.show(mDailyGankFragment);
                    }
                    mCurrentIndex = DAILY_FRAGMENT;
                    break;

                case CATEGORY_FRAGMENT:
                    mCategoryTab.setSelected(true);
                    if (mCateGoryFragment == null) {
                        mCateGoryFragment = new CategoryFragment();
                        fr.add(R.id.content_layout,mCateGoryFragment);
                    } else {
                        fr.show(mCateGoryFragment);
                    }
                    mCurrentIndex = CATEGORY_FRAGMENT;
                    break;

                case WELFARETAB_FRAGMENT:
                    mWelfareTab.setSelected(true);
                    if (mWelfareFragment == null) {
                        mWelfareFragment = new WelfareFragment();
                        fr.add(R.id.content_layout,mWelfareFragment);
                    } else {
                        fr.show(mWelfareFragment);
                    }
                    mCurrentIndex = WELFARETAB_FRAGMENT;
                    break;
            }
            fr.commit();
        } else if ( mCurrentIndex == tab_index && mDailyGankFragment != null) {

        }

    }

    private void hideAllFragments(FragmentTransaction fr) {
        if (mDailyGankTab != null) {
            fr.hide(mDailyGankFragment);
        }

        if (mCategoryTab != null) {
            fr.hide(mCateGoryFragment);
        }

        if (mWelfareTab != null) {
            fr.hide(mWelfareFragment);
        }

        mCategoryTab.setSelected(false);
        mWelfareTab.setSelected(false);
        mDailyGankTab.setSelected(false);
    }
}
