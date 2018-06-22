package com.sjw.androidcustomview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sjw.androidcustomview.banner.BannerAdapter;
import com.sjw.androidcustomview.banner.BannerView;
import com.sjw.androidcustomview.banner.BannerViewPager;

import java.util.ArrayList;
import java.util.List;

public class BannerActivity extends AppCompatActivity {

    private BannerView bannerView;
    //图片网址
    private List<String> imgList;
    //广告位文字
    private List<String> bannerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        bannerView = (BannerView) findViewById(R.id.bannerView);
        imgList = new ArrayList<>();
        imgList.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
        imgList.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg");
        imgList.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg");
        imgList.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2e7vsaj30ci08cglz.jpg");
        bannerList = new ArrayList<>();
        bannerList.add("横推九天十地");
        bannerList.add("纵横天上地下");
        bannerList.add("威压诸天万界");
        bannerList.add("睥睨宇宙八荒");

        bannerView.setAdapter(new BannerAdapter() {
            @Override
            public View getView(int position, View convertView) {
                ImageView imageView;

                if (convertView == null) {

                    imageView = new ImageView(BannerActivity.this);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                } else {

                    imageView = (ImageView) convertView;
                    Log.e("jmfy", "界面复用" + convertView);
                }

                //利用第三方将图片设置上去
                Glide.with(BannerActivity.this)
                        .load(imgList.get(position))
                        .placeholder(R.mipmap.ic_launcher)
                        .into(imageView);


                return imageView;
            }

            @Override
            public int getCount() {
                return imgList.size();
            }

            @Override
            public String getBannerDesc(int position) {
                return bannerList.get(position);
            }
        });
        //开始自动轮播
        bannerView.startRoll();
        //设置速率
        bannerView.setmScrollerDuration(2000);

        bannerView.setOnBannerViewListener(new BannerViewPager.OnBannerViewListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(BannerActivity.this, position + "被点击了", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
