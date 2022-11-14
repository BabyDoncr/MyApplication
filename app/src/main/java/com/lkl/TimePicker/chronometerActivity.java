package com.lkl.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import android.widget.Toast;


import com.lkl.myapplication.R;

public class chronometerActivity extends AppCompatActivity {
    private int startTime = 0;
    private Chronometer recordChronometer;
    private long recordingTime = 0;// 记录下来的总时间
    private Context context;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chronometer_activity);
        context = this;
        Chronometer chronometer = findViewById(R.id.chronometer);
        Button btnStart = (Button) findViewById(R.id.btnStart);
        Button btnWait = (Button) findViewById(R.id.btnWait);
        Button btnStop = (Button) findViewById(R.id.btnStop);
        Button btnRest = (Button) findViewById(R.id.btnReset);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(chronometerActivity.this, "开始记时", Toast.LENGTH_SHORT).show();
                //将时间设置为暂停时的时间
                chronometer.setBase(convertStrTimeToLong(chronometer.getText().toString()));
                chronometer.start();//开始计时
            }
        });


        btnWait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(chronometerActivity.this, "暂停记时", Toast.LENGTH_SHORT).show();
                chronometer.stop();
                // 保存这次记录了的时间
                //SystemClock.elapsedRealtime()是系统启动到现在的毫秒数
                recordingTime = SystemClock.elapsedRealtime() - chronometer.getBase();//getBase():返回时间
            }
        });


        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 停止
                Toast.makeText(chronometerActivity.this, "停止记时", Toast.LENGTH_LONG).show();
                recordingTime = 0;
                chronometer.stop();
            }
        });
        // 重置
        btnRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(chronometerActivity.this, "重置计时", Toast.LENGTH_LONG).show();
                chronometer.stop();
                recordingTime = 0;
                chronometer.setBase(SystemClock.elapsedRealtime());// setBase(long base):设置计时器的起始时间
            }
        });
    }
    protected long convertStrTimeToLong(String strTime) {
        // TODO Auto-generated method stub
        String[] timeArry = strTime.split(":");
        long longTime = 0;
        if (timeArry.length == 2) {//如果时间是MM:SS格式
            longTime = Integer.parseInt(timeArry[0]) * 1000 * 60 + Integer.parseInt(timeArry[1]) * 1000;
        } else if (timeArry.length == 3) {//如果时间是HH:MM:SS格式
            longTime = Integer.parseInt(timeArry[0]) * 1000 * 60 * 60 + Integer.parseInt(timeArry[1])
                    * 1000 * 60 + Integer.parseInt(timeArry[0]) * 1000;
        }
        return SystemClock.elapsedRealtime() - longTime;
    }

}
