package com.program.cherishtime.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.program.cherishtime.R;
import com.program.cherishtime.bean.Code;
import com.program.cherishtime.bean.Msg;
import com.program.cherishtime.network.NetworkWrapper;
import com.program.cherishtime.utils.DataUtil;
import com.program.cherishtime.utils.LogUtil;
import com.program.cherishtime.utils.MyApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ForgetActivity extends BaseActivity {

    @BindView(R.id.forget_account)
    EditText forgetAccount;
    @BindView(R.id.forget_email)
    EditText forgetEmail;
    @BindView(R.id.forget_pwd)
    EditText forgetPwd;
    @BindView(R.id.forget_code)
    EditText forgetCode;
    @BindView(R.id.send_code)
    Button sendCode;
    @BindView(R.id.find_password)
    Button findPassword;

    private List<Disposable> mDisposables = new ArrayList<>();

    private long mTime;
    private String mToken;

    public static void actionStart(Context context, String account) {
        Intent intent = new Intent(context, ForgetActivity.class);
        intent.putExtra(DataUtil.USER_ACCOUNT, account);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String account = getIntent().getStringExtra(DataUtil.USER_ACCOUNT);

        forgetAccount.setText(account);

        sendCode.setOnClickListener(view -> {
            if (sendCodeCheckInput()) {
                Disposable subscribe = NetworkWrapper.getInstance().getDesertApiClient()
                        .getCode(forgetEmail.getText().toString(), forgetAccount.getText().toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::getCodeSuccess, this::onError);

                mDisposables.add(subscribe);
            }
        });

        findPassword.setOnClickListener(view -> {
            if (checkAllInput()) {
                Map<String, Object> params = new HashMap<>();
                params.put("appKey", MyApplication.getContext().getString(R.string.forgetPasswordAppKey));
                params.put("account", forgetAccount.getText().toString());
                params.put("password", forgetPwd.getText().toString());
                params.put("email", forgetEmail.getText().toString());
                params.put("code", forgetCode.getText().toString());
                params.put("time", mTime);
                params.put("token", mToken);

                Disposable subscribe = NetworkWrapper.getInstance().getDesertApiClient()
                        .changePassword(params)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::changePwdSuccess, this::onError);
                mDisposables.add(subscribe);
            }
        });

    }

    private void changePwdSuccess(Msg msg) {
        if (msg.getCode() == 1) {
            Toast.makeText(this, "修改成功！", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, msg.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    private void getCodeSuccess(Code code) {
        if (code.getCode() == 1) {
            mTime = code.getTime();
            mToken = code.getToken();
            Toast.makeText(this, "验证码发送成功，请在3分钟内完成操作！", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "输入信息有误，请重试！", Toast.LENGTH_SHORT).show();
        }
    }

    private void onError(Throwable throwable) {
        LogUtil.d("ForgetActivity", throwable.getMessage());
    }

    private boolean sendCodeCheckInput() {
        if (forgetAccount.getText().toString().equals("")) {
            Toast.makeText(this, "请输入账号！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (forgetEmail.getText().toString().equals("")) {
            Toast.makeText(this, "请输入邮箱！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean checkAllInput() {
        if (!sendCodeCheckInput())
            return false;
        if (forgetPwd.getText().toString().equals("")) {
            Toast.makeText(this, "请输入新密码！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (forgetCode.getText().toString().equals("")) {
            Toast.makeText(this, "请输入验证码！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        for (Disposable disposable : mDisposables) {
            if (disposable != null)
                disposable.dispose();
        }
        super.onDestroy();
    }
}
