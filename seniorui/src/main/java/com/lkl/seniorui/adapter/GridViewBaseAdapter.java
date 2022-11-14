package com.lkl.seniorui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.duanshuyu.advanceduidemo.R;
import com.lkl.seniorui.holder.GrideViewHolder;
import com.lkl.seniorui.model.AppModel;

import java.util.ArrayList;

public class GridViewBaseAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<AppModel> list;

    public GridViewBaseAdapter(Context context, ArrayList<AppModel> list){
        this.mInflater = LayoutInflater.from(context);
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
        GrideViewHolder grideViewHolder;
        if (view ==null){
            grideViewHolder =new GrideViewHolder();
            //加载新布局 加载items布局
            view = mInflater.inflate(R.layout.gv_items,null);
            //布局id
            grideViewHolder.iv_app = view.findViewById(R.id.iv_app);
            grideViewHolder.tv_app = view.findViewById(R.id.tv_app);
            //塞进
            view.setTag(grideViewHolder);

        }else{
            grideViewHolder = (GrideViewHolder) view.getTag();
        }
        //给具体的gridViewHolder赋值
        grideViewHolder.iv_app.setImageResource(list.get(i).getImage_id());
        grideViewHolder.tv_app.setText(list.get(i).getTv_id());
        return view;

    }
}
