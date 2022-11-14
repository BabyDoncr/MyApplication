package com.lkl.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class linerLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // 拿到button对象
        View btn_1 = findViewById(R.id.btn1);
        // 设立点击事件
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(linerLayoutActivity.this, relativeLayoutActivity.class);
                //开启intent
                startActivity(intent);
            }
        });


        // 拿到button对象
        View btn_2 = findViewById(R.id.btn2);

        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.liner_layout_demo);
            }
        });

     /*   View bt1 = findViewById(R.id.bt1);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.liner_layout_login);
            }
        });*/
        View btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 如何打开firstContainer Intent 两个参数 第一个参数
                Intent intent = new Intent(linerLayoutActivity.this,firstContainer.class);
                //开启intent
                startActivity(intent);
            }
        });



    }
}