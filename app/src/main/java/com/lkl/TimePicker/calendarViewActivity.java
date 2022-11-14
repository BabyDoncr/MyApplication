package com.lkl.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.lkl.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class calendarViewActivity extends AppCompatActivity {

    int year,month,day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_view_activity);

        CalendarView calendar = findViewById(R.id.calendar_view);
        Button btn_calendar = (Button) findViewById(R.id.btn_calendar);
        Calendar c = Calendar.getInstance();

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int i, int i1,
                                            int i2) {
              year = i;
              month = i1+1;
              day = i2;
            }
        });

        btn_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(calendarViewActivity.this,""+year+"年"+month+"月"+day+"日",Toast.LENGTH_SHORT).show();
            }
        });
    }
}