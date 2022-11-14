package com.lkl.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.Toast;

import com.lkl.myapplication.R;

import java.util.Calendar;

public class analogClockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analog_clock_activity);

        Button btn_clock = (Button) findViewById(R.id.btn_clock);
        AnalogClock clock = (AnalogClock) findViewById(R.id.analog_clock);
        Calendar calendar = Calendar.getInstance();


        btn_clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour =  calendar.get(Calendar.HOUR_OF_DAY); //小时（24制）
                int minute = calendar.get(Calendar.MINUTE);  	//分
                int second = calendar.get(Calendar.SECOND);  	//秒
                Toast.makeText(analogClockActivity.this,hour+"时"+minute+"分"+second+"秒",Toast.LENGTH_SHORT).show();
            }
        });


    }
}