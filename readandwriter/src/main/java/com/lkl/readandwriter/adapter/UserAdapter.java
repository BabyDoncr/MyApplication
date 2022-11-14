package com.lkl.readandwriter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lkl.readandwriter.R;
import com.lkl.readandwriter.holder.UserHolder;
import com.lkl.readandwriter.mode.User;

import java.util.List;

public class UserAdapter  extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<User> list;

    public UserAdapter(Context context, List<User> list) {
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
            UserHolder userHolder;

            if(view ==null){

                view = mInflater.inflate(R.layout.userlist_item,null);
                userHolder =new UserHolder();

                userHolder.tv_id = view.findViewById(R.id.tv_id);
                userHolder.tv_username = view.findViewById(R.id.tv_username);
                userHolder.tv_password = view.findViewById(R.id.tv_password);
                userHolder.tv_phone = view.findViewById(R.id.tv_phone);

                view.setTag(userHolder);



            }else {

                userHolder = (UserHolder) view.getTag();

            }

                userHolder.tv_id.setText(list.get(i).getId());
                userHolder.tv_username.setText(list.get(i).getUsername());
                userHolder.tv_password.setText(list.get(i).getPassword());
                userHolder.tv_phone.setText(list.get(i).getPhone());

            return view;
    }
}
