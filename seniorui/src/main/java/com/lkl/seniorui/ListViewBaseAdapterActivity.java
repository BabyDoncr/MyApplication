package com.lkl.seniorui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.duanshuyu.advanceduidemo.R;
import com.lkl.seniorui.adapter.MyAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class ListViewBaseAdapterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_base_adapter);
        //1第一步 拿到listView对象
        ListView lv_baseAdapter = findViewById(R.id.lv_baseAdapter);

        //2数据源

        //2.1创建基础数据源
        String arr_desc[] = new String[]{"衬衫1","衬衫2","衬衫3","衬衫4","衬衫5","衬衫6"};
        int []images = new int[]{R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e,R.drawable.f};
        //2.2把基础的数据源进行封装 封装到ArrayList中
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<HashMap<String, Object>>();
        for (int i=0;i<arr_desc.length;i++){
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("map_imgId",images[i]);
            map.put("map_tvId",arr_desc[i]);
            arrayList.add(map);
        }
        //3创建自定义的Adapter
        MyAdapter myAdapter = new MyAdapter(ListViewBaseAdapterActivity.this,arrayList);
        //4把自定义的Adapter绑定到ListView对象上
        lv_baseAdapter.setAdapter(myAdapter);
    }
}