package com.example.jxwoer.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jxwoer.myapplication.R;
import com.example.jxwoer.myapplication.pojo.Good;

import java.util.List;

/**
 * Created by JXwoer on 2018/7/8.
 */

public class GoodAdapter  extends BaseAdapter {

    private List<Good> list;
    private Context context;

    private int[] image={
            R.drawable.timg1,
            R.drawable.timg,
            R.drawable.timg2,
            R.drawable.timg1,
            R.drawable.timg,
            R.drawable.timg2,
            R.drawable.timg1,
            R.drawable.timg,
            R.drawable.timg2,
            R.drawable.timg1,
            R.drawable.timg,
            R.drawable.timg2,
            R.drawable.timg1,
            R.drawable.timg,
            R.drawable.timg2,




    };

    public GoodAdapter(List<Good> list, Context context){
        this.list = list;
        this.context = context;
    }

    /**
     * 获取item（条目|子项）的总个数
     */
    @Override
    public int getCount() {
        return list.size();
    }
    /**
     * 根据索引返回对应的item数据
     */
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    /**
     * 返回一个item的视图，在视图返回之前，需要我们去绑定动态的数据
     *
     * @param position 当前绘制的item的索引
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO 1. 创建出来一个子项的视图
        View view = LayoutInflater.from(context).inflate(R.layout.icream_list, null);
        // TODO 2. 获取设置动态数据的那些控件
        ImageView img = (ImageView) view.findViewById(R.id.icream_image);
        TextView tvName = (TextView) view.findViewById(R.id.icream_name);
        TextView tvPrice = (TextView) view.findViewById(R.id.icream_price);
        TextView tvStore = (TextView) view.findViewById(R.id.icream_store);
        TextView tvMunber=(TextView)view.findViewById(R.id.icream_number);
//        TextView tvPrice = (TextView) view.findViewById(R.id.goods_price);
        // TODO 3. 给对应的控件设置对应的数据
        Good good = list.get(position);
        // TODO 4. 设置
//        img.setImageResource(R.drawable.timg);

        img.setImageResource(image[position]);
        img.setTag(image[position]);
        // 通过资源ID设置它的图片内容
        tvName.setText(good.getGtype());
//      tvNum.setText("月销量" +good.getNum());
        tvStore.setText(good.getRtype());
        tvPrice.setText(String.valueOf(good.getGprice()));
        tvMunber.setText(String.valueOf(good.getGnumber()));
        return view;
    }
}

