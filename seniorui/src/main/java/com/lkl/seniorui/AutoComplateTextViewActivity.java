package com.lkl.seniorui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.duanshuyu.advanceduidemo.R;

public class AutoComplateTextViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auto_complate_textview);

        AutoCompleteTextView actv_names = findViewById(R.id.actv_names);
        //创建数据源
        String names[] = new String[]{"lixin","duanshuyu","xuetianyu","zhangtongman","hanlu","huyang"};
        //创建ArrayAdapter——数据绑定器

        ArrayAdapter<String> adapter =new ArrayAdapter<String>(
                AutoComplateTextViewActivity.this,
                android.R.layout.simple_dropdown_item_1line,
                names
        );
        //给对象绑定Adapter
        actv_names.setAdapter(adapter);
    }
}