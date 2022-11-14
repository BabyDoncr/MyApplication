package com.program.cherishtime.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.program.cherishtime.R;
import com.program.cherishtime.bean.Msg;
import com.program.cherishtime.network.NetworkWrapper;
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

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.register_nickname)
    EditText registerNickname;
    @BindView(R.id.register_account)
    EditText registerAccount;
    @BindView(R.id.register_pwd)
    EditText registerPwd;
    @BindView(R.id.register_repeat_pwd)
    EditText registerRepeatPwd;
    @BindView(R.id.register_email)
    EditText registerEmail;
    @BindView(R.id.register_ok)
    Button registerOk;

    private List<Disposable> mDisposables = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        registerOk.setOnClickListener(view -> {
           if (checkInput()) {
               Disposable subscribe = NetworkWrapper.getInstance().getDesertApiClient()
                       .isRepeated(registerAccount.getText().toString())
                       .subscribeOn(Schedulers.io())
                       .observeOn(AndroidSchedulers.mainThread())
                       .subscribe(this::checkAccount, this::onError);
               mDisposables.add(subscribe);
           }
        });
    }

    private void checkAccount(Msg msg) {
        if (msg.getCode() == 1) {
            Map<String, String> params = new HashMap<>();
            params.put("appKey", MyApplication.getContext().getString(R.string.registerAppKey));
            params.put("account", registerAccount.getText().toString());
            params.put("nickname", registerNickname.getText().toString());
            params.put("password", registerPwd.getText().toString());
            params.put("email", registerEmail.getText().toString());

            Disposable subscribe = NetworkWrapper.getInstance().getDesertApiClient()
                    .register(params)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::registerSuccess, this::onError);
            mDisposables.add(subscribe);
        } else {
            Toast.makeText(this, "账号重复！请重新输入", Toast.LENGTH_SHORT).show();
        }
    }

    private void registerSuccess(Msg msg) {
        if (msg.getCode() == 1) {
            Toast.makeText(this, "注册成功！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "注册失败！请稍后重试", Toast.LENGTH_SHORT).show();
        }
    }

    private void onError(Throwable throwable) {
        LogUtil.d("RegisterActivity", throwable.getMessage());
    }

    private boolean checkInput() {
        if (registerNickname.getText().toString().equals("")) {
            Toast.makeText(this, "昵称不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (registerAccount.getText().toString().equals("")) {
            Toast.makeText(this, "账号不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (registerPwd.getText().toString().equals("")) {
            Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (registerRepeatPwd.getText().toString().equals("")) {
            Toast.makeText(this, "请再次确认密码！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!registerPwd.getText().toString().equals(registerRepeatPwd.getText().toString())) {
            Toast.makeText(this, "两次密码不一致！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (registerEmail.getText().toString().equals("")) {
            Toast.makeText(this, "请输入邮箱！", Toast.LENGTH_SHORT).show();
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
