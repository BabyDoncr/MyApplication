package com.example.jxwoer.myapplication;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.annotation.IdRes;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import java.util.ArrayList;
/**
 * Created by JXwoer on 2018/6/26.
 */

public class main extends AppCompatActivity{
    private RadioGroup mainRg;

    private ArrayList<BaseFragment> fragments;


    private int position;
    //    private FrameLayout mContainer;
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    mainRg = (RadioGroup) findViewById(R.id.main_rg);
    initFragments();
    // 绑定监听器 - 单选框选中的监听
        mainRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            switch (checkedId) {
                case R.id.main_rb_home:
                    // 说明用户选中了首页
                    position = 0;
                    break;
//                case R.id.main_rb_type:
//                    position = 1;
//                    break;
//                case R.id.main_rb_community:
//                    position = 2;
//                    break;
                case R.id.main_rb_cart:
                    position = 1;
                    break;
                case R.id.main_rb_user:
                    position = 2;
                    break;
            }

            BaseFragment baseFragment = getFragment(position);

            // 切换 Fragment ，这样就能实现我们的基本导航功能了
            Log.e("Tag", "1111111111111111111111");
            switchFragment(tempFragment, baseFragment);
        }
    });

    // 默认选中第一个
        mainRg.check(R.id.main_rb_home);

    //mContainer = (FrameLayout) findViewById(R.id.container);

//        // 1. 获取FragmentManager对象
//        FragmentManager fm = getSupportFragmentManager();
//
//        // 2. 开启一个事务
//        FragmentTransaction transaction = fm.beginTransaction();
//
//        // 3. 通过transaction来动态添加碎片
//        LeftFragment leftFragment = new LeftFragment();
//        transaction.replace(R.id.container, leftFragment);
//
//        // 4. 提交事务
//        transaction.commit();
}
    private BaseFragment tempFragment;
    private void switchFragment(BaseFragment fromFragment, BaseFragment nextFragment) {
        if (fromFragment != nextFragment) {
            if (nextFragment != null) {
                // 开启事务
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                // 先隐藏前面的fragment
                if(fromFragment != null) {
                    transaction.hide(fromFragment);
                }

                // 判断 baseFragment是否已经添加
                if(!nextFragment.isAdded()) {
                    // 添加
                    transaction.add(R.id.main_fragment_container, nextFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                } else {
                    // 直接显示
                    transaction.show(nextFragment).commit();

                }

                tempFragment = nextFragment;



            }
        }
    }

    /**
     * 初始化fragment的方法，即将所有的framment放到一个集合里面
     */
    private void initFragments() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
//        fragments.add(new TypeFragment());
//        fragments.add(new CommunityFragment());
        fragments.add(new CartFragment());
        fragments.add(new UserFragment());
    }

    /**
     * 根据索引返回对应的 fragment
     */
    private BaseFragment getFragment(int position) {
        if (fragments != null && fragments.size() > 0) {
            return fragments.get(position);

        }

        return null;
    }
}
