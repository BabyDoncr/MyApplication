package com.lkl.TimePicker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.lkl.myapplication.R;

public class picker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picker);

        Button bt_date = (Button) findViewById(R.id.bt_date);
        Button bt_time = (Button) findViewById(R.id.bt_time);
        Button bt_view = (Button) findViewById(R.id.bt_view);
        Button bt_clock = (Button) findViewById(R.id.bt_clock);
        Button bt_Chronometer = (Button) findViewById(R.id.bt_Chronometer);


        bt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(picker.this, datePickerActivity.class);
                startActivity(intent);
            }
        });
          bt_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(picker.this, timePickerActivity.class);
                startActivity(intent);
            }
        });
          bt_clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(picker.this, analogClockActivity.class);
                startActivity(intent);
            }
        });
          bt_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(picker.this, calendarViewActivity.class);
                startActivity(intent);
            }
        });
          bt_Chronometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(picker.this, chronometerActivity.class);
                startActivity(intent);
            }
        });

    }
}