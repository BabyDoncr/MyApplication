package com.program.cherishtime.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.program.cherishtime.utils.ActivityController;
import com.program.cherishtime.utils.LogUtil;
import com.program.cherishtime.utils.StatusBarUtil;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d("BaseActivity", getClass().getSimpleName());

        ActivityController.addActivity(this);
        setStatusBar();
    }

    protected void setStatusBar() {
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        //true=黑色字体  false=白色
        StatusBarUtil.setStatusBarDarkTheme(this, true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }
}
