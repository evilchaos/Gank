package com.example.liujiachao.gank.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.liujiachao.gank.R;
import com.example.liujiachao.gank.entity.DailyData;
import com.example.liujiachao.gank.entity.GankData;
import com.example.liujiachao.gank.entity.NewsItem;
import com.example.liujiachao.gank.inteface.OnLoadDailyContentListener;
import com.example.liujiachao.gank.inteface.OnLoadDataListener;
import com.example.liujiachao.gank.util.Constant;
import com.example.liujiachao.gank.util.Dater;
import com.example.liujiachao.gank.util.NetworkUtils;


import java.util.List;
import java.util.Map;

/**
 * Created by evilchaos on 16/10/18.
 */
public class DailyContentActivity extends AppCompatActivity implements OnLoadDailyContentListener{

    private static Handler mHandler;
    private ImageView imageView;
    private LinearLayout llDailyContent;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void initViews() {
        Intent intent = getIntent();
        String date = intent.getStringExtra("daily_date");
        String pic_url = intent.getStringExtra("picture_url");

        setContentView(R.layout.daily_content);
        imageView = (ImageView)findViewById(R.id.iv_content_pic);
        llDailyContent = (LinearLayout)findViewById(R.id.daily_content);

        Glide.with(this).load(pic_url).diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade().into(imageView);

        receiveData();
        NetworkUtils.getDailyNews(date,this);
    }


    private void receiveData() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case Constant.RECEIVER_SUCCESS:
                        DailyData dailyData = (DailyData)msg.getData().getSerializable("news");
                        //绘制视图
                        drawContentViews(dailyData);
                }
                super.handleMessage(msg);
            }
        };
    }

    private void drawContentViews(DailyData dailyData) {
        List<String> categories = dailyData.getCategory();
        Map<String,List<NewsItem> > results = dailyData.getResults();
        for (String category : categories ) {
            View tagView = LayoutInflater.from(this).inflate(R.layout.category_tag,null);
            TextView tvTag =  (TextView)tagView.findViewById(R.id.tv_tag);
            tvTag.setText(category);
            llDailyContent.addView(tvTag);

            List<NewsItem> itemList = results.get(category);
            for (NewsItem item : itemList) {
                addItemView(item);
            }
        }
    }

    private void addItemView(final NewsItem item) {
        View itemView = LayoutInflater.from(this).inflate(R.layout.news_item, null);
        TextView title = (TextView)itemView.findViewById(R.id.title);
        TextView time = (TextView)itemView.findViewById(R.id.tv_time);
        TextView author = (TextView)itemView.findViewById(R.id.tv_author);
        TextView tvCategory = (TextView)itemView.findViewById(R.id.tv_category);
        title.setText(item.getDesc());
        time.setText(Dater.parseTime(item.getPublishedAt()));
        author.setText(item.getWho());
        tvCategory.setText(item.getType());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = item.getUrl();
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });

        llDailyContent.addView(itemView);
    }

    @Override
    public void OnLoadDailyContentSuccess(DailyData dailyData) {
        Message msg = Message.obtain();
        msg.what = Constant.RECEIVER_SUCCESS;
        Bundle bundle = new Bundle();
        bundle.putSerializable("news", dailyData);
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
