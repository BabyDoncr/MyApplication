package com.lkl.seniorui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.duanshuyu.advanceduidemo.R;

public class GlideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gride_activity);

        ImageView iv_glide = (ImageView) findViewById(R.id.iv_glide);
        String url = "https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF";

        Glide.with(GlideActivity.this).load(url).into(iv_glide);
    }
}