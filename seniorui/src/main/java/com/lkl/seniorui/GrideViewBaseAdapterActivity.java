package com.lkl.seniorui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.duanshuyu.advanceduidemo.R;
import com.lkl.seniorui.adapter.GridViewBaseAdapter;
import com.lkl.seniorui.model.AppModel;

import java.util.ArrayList;

public class GrideViewBaseAdapterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gride_view_base_adapter);

        //拿到grideView对象
        GridView gv_app =findViewById(R.id.gv_app);

        //基础数据 基础封装——两个数组变成ArrayList<AppModel>
        int[] images ={R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e,R.drawable.f};
        String [] names = {"美团","微信","学习强国","微信读书","爱奇艺","腾讯"};
        //基础数据进行封装
        ArrayList<AppModel> list = new ArrayList<AppModel>();
        for (int i=0;i<images.length;i++){
            AppModel appModel =new AppModel();
            appModel.setImage_id(images[i]);
            appModel.setTv_id(names[i]);

            list.add(appModel);
        }
        //创建GrideViewBaseAdapter

        GridViewBaseAdapter gridViewBaseAdapter =new GridViewBaseAdapter(GrideViewBaseAdapterActivity.this,list);

        //给grideView对象赋值
        gv_app.setAdapter(gridViewBaseAdapter);
    }
}