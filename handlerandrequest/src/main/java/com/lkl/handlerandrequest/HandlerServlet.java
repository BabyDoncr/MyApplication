package com.lkl.handlerandrequest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HandlerServlet extends AppCompatActivity {
    TextView tv_hello;

    private Handler handler = new Handler() {
        public void handleMessage(@Nullable Message message) {
            handleMessage(message);
            switch (message.what) {
                case 1:
                    String value = (String) message.obj;
                    tv_hello.setText(value);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.handler);

        /* 这个操作是主线程  更新主线程的UI */
        tv_hello = findViewById(R.id.tv_hello);


        /* 子线程更新主线程的UI */
        /* new 一个线程出现   在这个子线程中更新主线程的UI看看可否 */

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /* 创建Message对象 */

                Message message = new Message();

                /* 给message设置参数  what (message 的标注)  obj(message的信息风格) */
                message.what = 1;
                message.obj = "不好！";
                /* 发送message */
                handler.sendMessage(message);

            }
        }).start();
    }

}

