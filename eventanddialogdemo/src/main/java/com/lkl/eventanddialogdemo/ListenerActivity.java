package com.lkl.eventanddialogdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ListenerActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listener_activity);

        /* 第一种：匿名内部类的方式进行 */
        Button btn_innerclass = findViewById(R.id.btn_innerclass);
        btn_innerclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ListenerActivity.this,"匿名内部类的方式进行",Toast.LENGTH_SHORT).show();
            }
        });

        /* 第二种：Button */
        Button btn_impl_interface = findViewById(R.id.btn_impl_interface);
        btn_impl_interface.setOnClickListener(this);
        
        /* 第四种：Button */
        Button btn_my_listener = findViewById(R.id.btn_my_listener);
        btn_my_listener.setOnClickListener(new MyOnClickListener());

    }

    /* 第二种：实现接口的方式监听点击事件 */
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_impl_interface:
                Toast.makeText(this,"接口实现的方式进行的事件监听",Toast.LENGTH_LONG).show();
                break;

        }
    }


    /*  第三种：属性值的方式进行的点击事件进行接听 */
    public void aa(View view){

        switch (view.getId()) {
            case R.id.btn_onclick_shuxing:
                Toast.makeText(this,"属性值的方式的点击事件进行接听",Toast.LENGTH_LONG).show();
                break;

        }
    }



    /* 第四种：自定义的时间响应机制 */
    public class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_my_listener:
                    Toast.makeText(ListenerActivity.this, "自定义的时间响应机制", Toast.LENGTH_SHORT).show();

            }
        }
    }
}