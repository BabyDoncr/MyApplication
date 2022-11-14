package com.lkl.seniorui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.duanshuyu.advanceduidemo.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ListViewSimpleAdapterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_simple_adapter);

        //1拿到listview的对象
        ListView lv_simpleAdapter =findViewById(R.id.lv_simpleAdapter);
        //2封装数据
        //2.1基础数据
        String arr_desc[] =new String[]{"衬衫1","衬衫2","衬衫3","衬衫4","衬衫5","衬衫6"};
        int []images =new int[]{R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e,R.drawable.f};
        //2.2封装成ArrayList 泛型里面是一个HashMap
        ArrayList<HashMap<String,Object>> arrayList =new ArrayList<HashMap<String,Object>>();

        for (int i=0;i<arr_desc.length;i++){
            HashMap<String,Object> map =new HashMap<String,Object>();
            map.put("map_imgId",images[i]);
            map.put("map_tvId",arr_desc[i]);
            arrayList.add(map);

        }
        //创建simpleAdapter
        SimpleAdapter simpleAdapter =new SimpleAdapter(
                ListViewSimpleAdapterActivity.this,
                arrayList,
                R.layout.items,
                new String[]{"map_imgId","map_tvId"},
                new int[]{R.id.imgv_pic,R.id.tv_desc}
        );

        //把simpleAdapter绑定到listview对象上
        lv_simpleAdapter.setAdapter(simpleAdapter);
    }
}