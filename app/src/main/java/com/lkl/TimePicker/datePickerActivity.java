package com.lkl.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.lkl.myapplication.R;

import java.util.Calendar;

public class datePickerActivity extends AppCompatActivity {

    int year,month,day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datepicker_activity);

        Button btn_date = findViewById(R.id.btn_date);
        DatePicker date_dp = findViewById(R.id.date_dp);
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.YEAR);
        day = cal.get(Calendar.DAY_OF_MONTH);

        date_dp.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                year = i;
                month = i1+1;
                day = i2;
            }
        });

       btn_date.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Toast.makeText(datePickerActivity.this,year+"年"+month+"月"+day+"日",Toast.LENGTH_SHORT).show();
           }
       });



    }
}