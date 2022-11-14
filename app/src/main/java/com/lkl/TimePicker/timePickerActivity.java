package com.lkl.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.lkl.myapplication.R;

import java.sql.Time;

public class timePickerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timepicker_activity);

        Button btn_time = findViewById(R.id.btn_time);
        TimePicker timePicker = findViewById(R.id.time_picker);
        timePicker.setIs24HourView(true);


        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(timePickerActivity.this,timePicker.getCurrentHour()+"时"+timePicker.getCurrentMinute()+"分", Toast.LENGTH_SHORT).show();
            }
        });
    }
}