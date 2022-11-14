package com.lkl.seniorui;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.duanshuyu.advanceduidemo.R;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;

import java.util.ArrayList;

public class BannerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.banner_activity);

        Banner banner_image = findViewById(R.id.banner_image);

        ArrayList<Integer> mBannerList = new ArrayList<Integer>();

        mBannerList.add(R.drawable.yg1);
        mBannerList.add(R.drawable.yg2);
        mBannerList.add(R.drawable.yg3);

        banner_image.setAdapter(new BannerImageAdapter<Integer>(mBannerList) {
            @Override
            public void onBindView(BannerImageHolder holder, Integer data, int position, int size) {
                holder.imageView.setImageResource(data);
            }
        });
    }
}