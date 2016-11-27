package com.example.liujiachao.gank.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.liujiachao.gank.R;

/**
 * Created by evilchaos on 16/11/27.
 */

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvCancel;
    RelativeLayout rlWeibo;
    RelativeLayout rlGithub;
    RelativeLayout rlBlog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        initViews();
    }

    private void initViews() {
        tvCancel = (TextView)findViewById(R.id.cancel);
        rlWeibo = (RelativeLayout)findViewById(R.id.my_weibo);
        rlGithub = (RelativeLayout)findViewById(R.id.my_github);
        rlBlog = (RelativeLayout)findViewById(R.id.my_blog);

        tvCancel.setOnClickListener(this);
        rlWeibo.setOnClickListener(this);
        rlGithub.setOnClickListener(this);
        rlBlog.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                finish();
                break;
            case R.id.my_weibo:
                visitUrl("http://weibo.com/1703097395/");
                break;
            case R.id.my_github:
                visitUrl("https://github.com/evilchaos");
                break;
            case R.id.my_blog:
                visitUrl("https://evilchaos.github.io");
                break;
        }
    }

    private void visitUrl(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }
}
