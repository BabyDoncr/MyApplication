package com.lkl.seniorui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.duanshuyu.advanceduidemo.R;
import com.lkl.seniorui.holder.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;

public class MyAdapter extends BaseAdapter {

    //通过构造方法来把ListViewBaseAdapter中的ArrayList  实例化
    private LayoutInflater mInflater;
    private ArrayList<HashMap<String, Object>> list;
    public MyAdapter(Context context, ArrayList<HashMap<String,Object>> list){
        this.mInflater = LayoutInflater.from(context);
        this.list = list;

    }


    @Override
    public int getCount() {
        //统计 你的ArrayList中有多少个  怎么统计？？

        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        //第一次的时候  我们需要new出ViewHolder  然后把对象和items的对象进行匹配
        ViewHolder viewHolder;
        if (view==null){
            viewHolder = new ViewHolder();
            //加载新布局 加载items布局
            view = mInflater.inflate(R.layout.items,null);
            viewHolder.imageView = view.findViewById(R.id.imgv_pic);
            viewHolder.textView = view.findViewById(R.id.tv_desc);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();

        }
        //第二次和以后的次数 就直接拿viewholder就可以了
        viewHolder.imageView.setImageResource(Integer.parseInt(list.get(i).get("map_imgId").toString()));
        viewHolder.textView.setText(list.get(i).get("map_tvId").toString());
        return view;
    }
}