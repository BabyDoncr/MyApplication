package com.lkl.seniorui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.duanshuyu.advanceduidemo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_autoComplateTv = findViewById(R.id.btn_autoComplateTv);
        Button btn_spinner = findViewById(R.id.btn_spinner);
        Button btn_lv_arryAdapter = findViewById(R.id.btn_lv_arryAdapter);
        Button btn_lv_simpleAdapter = findViewById(R.id.btn_lv_simpleAdapter);
        Button btn_lv_baseAdapter = findViewById(R.id.btn_lv_baseAdapter);
        Button btn_gv_baseAdapter = findViewById(R.id.btn_gv_baseAdapter);
        Button btn_glide = findViewById(R.id.btn_glide);
        Button btn_banner = findViewById(R.id.btn_banner);
        btn_autoComplateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AutoComplateTextViewActivity.class);
                startActivity(intent);
            }
        });
        btn_spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SpinnerActivity.class);
                startActivity(intent);
            }
        });
        btn_lv_arryAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListViewArrayAdapterActivity.class);
                startActivity(intent);
            }
        });
        btn_lv_simpleAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListViewSimpleAdapterActivity.class);
                startActivity(intent);
            }
        });
        btn_lv_baseAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListViewBaseAdapterActivity.class);
                startActivity(intent);
            }
        });
        btn_gv_baseAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GrideViewBaseAdapterActivity.class);
                startActivity(intent);
            }
        });
        btn_glide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GlideActivity.class);
                startActivity(intent);
            }
        });
        btn_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BannerActivity.class);
                startActivity(intent);
            }
        });
    }
}