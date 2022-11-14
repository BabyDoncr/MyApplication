package com.example.jxwoer.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2018/6/14 0014.
 */

public class UserFragment extends BaseFragment {
    private View mView;
    @Override
    protected View initView(LayoutInflater inflater) {
        TextView textView = new TextView(mContext);
//        textView.setText("个人中心");
//        textView.setTextSize(30);
//        textView.setTextColor(Color.parseColor("#ff0000"));
//        textView.setGravity(Gravity.CENTER);
        return textView;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.user, null);
        TextView textname1=(TextView) mView.findViewById(R.id.user_name);
        TextView address=(TextView)mView.findViewById(R.id.useraddress) ;

//        TextView textpass=(TextView) mView.findViewById(R.id.user_password);
        SharedPreferences sp = this.getActivity().getSharedPreferences("config",Context.MODE_PRIVATE);
        textname1.setText(sp.getString("username",""));
        address.setText(sp.getString("address",""));
//        textpass.setText(sp.getString("caddress",""));
        return mView;
    }

}
