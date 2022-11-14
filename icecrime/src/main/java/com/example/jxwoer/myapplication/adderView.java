package com.example.jxwoer.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by JXwoer on 2018/7/2.
 */

public class adderView  extends LinearLayout implements View.OnClickListener  {

    private Context mContext;
    private ImageView iv_sub;
    private ImageView iv_add;
    private TextView tv_number;
    private int currentValue = 1; // 默认为1
    private int minValue = 1;

    private int maxValue = 10; // 实际情况为最大库存
    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }
    public adderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        // 把布局实例化并且把当前的view加入到当前视图中
        // 把布局文件实例化，并且加载到AddSubView类中
        View.inflate(context,R.layout.add_sub_count, this);
        iv_sub = (ImageView) findViewById(R.id.btn_reduce);
        iv_add = (ImageView) findViewById(R.id.btn_add);
        tv_number = (TextView) findViewById(R.id.tv_count);

        // 注意此处不能写成tv_number.setText(getCurrentValue()),但可以这样写tv_number.setText(getCurrentValue() + "");
        setCurrentValue(getCurrentValue());

        iv_sub.setOnClickListener(this);
        iv_add.setOnClickListener(this);
    }

    public int getCurrentValue() {

        String strValue = tv_number.getText().toString();
        if (!TextUtils.isEmpty(strValue)) {
            currentValue = Integer.parseInt(strValue);
        }
        return currentValue;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
        tv_number.setText(currentValue + "");
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reduce:
                subNumber();
                break;
            case R.id.btn_add:
                addNumber();
                break;
        }
//        Toast.makeText(mContext, "当前的值为:" + currentValue, Toast.LENGTH_SHORT).show();
    }

    private void subNumber() {
        if (currentValue > minValue) {
            currentValue--;
        }
        setCurrentValue(currentValue);
        if (onAddSubClickListener != null) {
            onAddSubClickListener.onNumberChange(currentValue);
        }
    }

    private void addNumber() {
        if (currentValue < getMaxValue()) {
            currentValue++;
        }
        setCurrentValue(currentValue);


        if (onAddSubClickListener != null) {
            onAddSubClickListener.onNumberChange(currentValue);
        }
    }


    private OnAddSubClickListener onAddSubClickListener;

    public void setOnAddSubClickListener(OnAddSubClickListener onAddSubClickListener) {
        this.onAddSubClickListener = onAddSubClickListener;
    }

    public interface OnAddSubClickListener {
        void onNumberChange(int value);
    }


}