package com.lkl.eventanddialogdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class EventActivity extends AppCompatActivity {
    TextView tv_show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_activity);

        tv_show = findViewById(R.id.tv_show);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /* 拿到 event 对象中的动作 */

        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                showinfo("ACTION_DOWN",event);
                break;
            case MotionEvent.ACTION_UP:
                showinfo("ACTION_UP",event);
            case MotionEvent.ACTION_MOVE:
                showinfo("ACTION_MOVE",event);

        }
        return super.onTouchEvent(event);
    }

    public void showinfo(String action_down,MotionEvent event) {

        /* 写显示的内容就可以了 */
        /* 第一显示点击的坐标 x,y */

        float x = event.getX();
        float y = event.getY();

        /* 显示触摸的压力值 */
        float pressvalue = event.getPressure();

        String str = "";

        str += "事件类型：" + action_down + "\n";
        str += "坐标(x,y)：(" + x + "," + y +")" + "\n";
        str += "压力值："+pressvalue;

        tv_show.setText(str);


    }
}