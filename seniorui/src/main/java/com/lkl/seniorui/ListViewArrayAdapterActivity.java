package com.lkl.seniorui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.duanshuyu.advanceduidemo.R;

public class ListViewArrayAdapterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_array_adapter);
        //拿到ListView对象
        ListView lv_arrayAdapter = findViewById(R.id.lv_arrayAdapter);

        //初始化数据
        //String s[] ={};
        String s[] = new String[]{"aa","bb","cc","dd","ee","ff","gg","hh","ii","aa","bb"};
        //实例化ArrayAdapter
        ArrayAdapter<String> arrayAdapter =new ArrayAdapter<String>(
             ListViewArrayAdapterActivity.this,
             android.R.layout.simple_list_item_1,
             s
        );
        //绑定ArrayAdapter

        lv_arrayAdapter.setAdapter(arrayAdapter);

        //设置监听器
        lv_arrayAdapter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv =(TextView)view;
                Toast.makeText(ListViewArrayAdapterActivity.this,tv.getText(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}