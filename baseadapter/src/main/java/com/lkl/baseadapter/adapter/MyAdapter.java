package com.lkl.baseadapter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lkl.baseadapter.R;
import com.lkl.baseadapter.holder.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;

public class MyAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;
    private ArrayList<HashMap<String,Object>> list;

    public MyAdapter(Context context, ArrayList<HashMap<String, Object>> list) {
        this.mLayoutInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() {
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

        ViewHolder holder ;
        if(view == null){
            holder = new ViewHolder();
            view  = mLayoutInflater.inflate(R.layout.items,null);
            holder.imageView = view.findViewById(R.id.image);
            holder.textView = view.findViewById(R.id.textView);
            holder.textView1 = view.findViewById(R.id.textView1);
            holder.textView2 = view.findViewById(R.id.textView2);

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.imageView.setImageResource((int)(list.get(i).get("image")));
        holder.textView.setText(list.get(i).get("textView").toString());
        holder.textView1.setText(list.get(i).get("textView1").toString());
        holder.textView2.setText(list.get(i).get("textView2").toString());


        return view;
    }
}
