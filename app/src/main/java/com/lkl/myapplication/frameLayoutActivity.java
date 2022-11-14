package com.lkl.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

public class frameLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_layout_demo);
        View btn_1 = findViewById(R.id.btn_1);
        View t1 = findViewById(R.id.t1);
        View t2 = findViewById(R.id.t2);
        View t3 = findViewById(R.id.t3);
        View t4 = findViewById(R.id.t4);
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}