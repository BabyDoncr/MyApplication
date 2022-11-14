package com.lkl.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class relativeLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relative_layout_demo);

//        RelativeLayout relativeLayout = new RelativeLayout(this);
//        RelativeLayout.LayoutParams params =  new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.WRAP_CONTENT,
//                RelativeLayout.LayoutParams.WRAP_CONTENT);
////addRule参数对应RelativeLayout XML布局的属性
//        params.addRule(RelativeLayout.CENTER_IN_PARENT);  //设置居中显示
//        TextView textView = new TextView(this);  //创建TextView控件
//        textView.setText("Java 代码实现界面布局");  //设置TextView的文字内容
//        textView.setTextColor(Color.RED);         //设置TextView的文字颜色
//        textView.setTextSize(18);                   //设置TextView的文字大小
////添加TextView对象和TextView的布局属性
//        relativeLayout.addView(textView, params);
//        setContentView(relativeLayout); //设置在Activity中显示RelativeLayout
        View button5 = findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(relativeLayoutActivity.this, linerLayoutActivity.class);

                startActivity(intent);
            }
        });
    }

}