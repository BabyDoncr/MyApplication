package com.lkl.sqlite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lkl.sqlite.R;
import com.lkl.sqlite.holder.UserHolder;
import com.lkl.sqlite.model.User;

import java.util.List;

public class UserAdapter extends BaseAdapter {
    //定义变量
    private LayoutInflater minflater;
    public List<User> list;

    //构造方法
    public UserAdapter(Context context, List<User> list){
        this.minflater=LayoutInflater.from(context);
        this.list=list;
    }

    public int getCount(){
        return list.size();
    }
    public Object getItem(int i){
        return null;
    }
    public long getItemId(int i){
        return 0;
    }
    public View getView(int i, View view, ViewGroup viewGroup){

        UserHolder userHolder;
        if(view==null){
            userHolder=new UserHolder();
            //加载新布局
            view=minflater.inflate(R.layout.user_list_item,null);
            userHolder.tv_id=view.findViewById(R.id.tv_id);
            userHolder.tv_username=view.findViewById(R.id.tv_username);
            userHolder.tv_password=view.findViewById(R.id.tv_password);
            userHolder.tv_phone=view.findViewById(R.id.tv_phone);
            view.setTag(userHolder);

        }else {
            userHolder=(UserHolder) view.getTag();
        }

        userHolder.tv_id.setText((String.valueOf(list.get(i).getId())));
        userHolder.tv_username.setText(list.get(i).getUsername());
        userHolder.tv_password.setText(list.get(i).getPassword());
        userHolder.tv_phone.setText(list.get(i).getPhone());
        return view;
    }



}
