package com.example.jxwoer.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jxwoer.myapplication.R;
import com.example.jxwoer.myapplication.pojo.Icream;

import java.util.List;
/**
 * Created by JXwoer on 2018/6/28.
 */
public class IcreamAdapter extends ArrayAdapter<Icream> {

    private int resourceId;
    public IcreamAdapter(Context context, int textViewResourceId, List<Icream> objects)
    { super(context, textViewResourceId, objects);
        resourceId = textViewResourceId; }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        Icream icream = (Icream)getItem(position); // 获取当前项的Fruit实例
//         View view= LayoutInflater.from(getContext()).inflate(resourceId,null);
//         ImageView IcreamImage = (ImageView) view.findViewById(R.id.icream_image);
//         TextView icreamName=(TextView)view.findViewById(R.id.icream_name);
//        TextView icreamPrice=(TextView)view.findViewById(R.id.icream_price);
//        IcreamImage.setImageResource(icream.getImageId());
//        icreamName.setText(icream.getName());
//        icreamPrice.setText(String.valueOf(icream.getPrice()));
//        return view;
        Icream icream = (Icream)getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null)
        { view=LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder = new ViewHolder();
//            viewHolder.IcreamImage= (ImageView) view.findViewById (R.id.icream_image);
            viewHolder.icreamName = (TextView) view.findViewById (R.id.icream_name);
            viewHolder.icreamPrice= (TextView) view.findViewById (R.id.icream_price);
            view.setTag(viewHolder);
            } else { view = convertView;
            viewHolder = (ViewHolder) view.getTag();
            // 重新获取ViewHolder
            }
        viewHolder.IcreamImage.setImageResource(icream.getImageId());
        viewHolder.icreamName.setText(icream.getName());
       viewHolder.icreamPrice.setText(String.valueOf(icream.getPrice()));
        return view;
        }
    class ViewHolder {
        ImageView IcreamImage;
        TextView icreamName;
        TextView icreamPrice;
    }
    }
