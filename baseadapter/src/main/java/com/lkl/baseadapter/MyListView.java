package com.lkl.baseadapter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.lkl.baseadapter.adapter.MyAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MyListView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        ListView listview = findViewById(R.id.lv_list);

        int images[] = new int[] {R.drawable.ysdq,R.drawable.yy,R.drawable.cgyy,R.drawable.egdd,R.drawable.wksz,R.drawable.ajj,R.drawable.cls,R.drawable.mdjsb,R.drawable.mfbk,R.drawable.ysdqcjb};
        String textView[] = new String[] {"影视大全"," 阅友免费小说","橙光阅读器", "儿歌点点","悟空识字","爱剪辑"," 财联社","米读极速版", "必看免费小说","影视大全极速版"};
        String textView1[] = new String[] {"影视大全，就是片全"," 正版免费小说一百年","全新沉浸式互动阅读颠覆想象力",
                "中英文儿歌故事，宝宝动画大全","有趣的汉字","爱剪辑专业视频剪辑编辑制作",
                " A股24小时电报","看小说就能领奖励", "热门网络小说电子书大全","影视大全，免费高清广告"};

        String textView2[]= new String[] {"↓ 3.4亿 | 62M","↓ 4842万 | 40M","↓ 2326万 | 91M", "↓ 1.4亿 | 46M","↓ 1500万 | 174M","↓ 5467万 | 91M",
                "↓ 1843万 | 97M","↓ 1.1亿 | 36M", "↓ 4533万 | 41M","↓ 5535万 | 91M"};
        String button = "安装";
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        for (int i = 0; i < images.length*3; i++) {
            HashMap<String, Object> map = new HashMap<>();

            map.put("image", images[i% images.length]);

            map.put("textView", textView[i% images.length]);
            map.put("textView1", textView1[i% images.length]);
            map.put("textView2", textView2[i% images.length]);

            list.add(map);
        }

        MyAdapter myAdapter = new MyAdapter(MyListView.this, list);

        listview.setAdapter(myAdapter);

    }
}