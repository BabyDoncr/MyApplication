package com.program.cherishtime.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import com.program.cherishtime.R;
import com.program.cherishtime.bean.Msg;
import com.program.cherishtime.bean.UserInfo;
import com.program.cherishtime.data.ObservableUserInfoDb;
import com.program.cherishtime.network.NetworkWrapper;
import com.program.cherishtime.utils.ActivityController;
import com.program.cherishtime.utils.DataUtil;
import com.program.cherishtime.utils.LogUtil;
import com.program.cherishtime.utils.MyApplication;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@RequiresApi(api = Build.VERSION_CODES.M)
public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_account)
    EditText loginAccount;
    @BindView(R.id.login_pwd)
    EditText loginPwd;
    @BindView(R.id.login_sign_in)
    Button loginSignIn;
    @BindView(R.id.login_forget_pwd)
    Button loginForgetPwd;

    private List<Disposable> mDisposables = new ArrayList<>();

    private ObservableUserInfoDb mUserInfoDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mUserInfoDb = new ObservableUserInfoDb(this);

        init();
    }

    private void init() {
        loginSignIn.setOnClickListener(view -> {
            String account = loginAccount.getText().toString();
            String pwd = loginPwd.getText().toString();

            Disposable disposable = NetworkWrapper.getInstance()
                    .getDesertApiClient()
                    .login(MyApplication.getContext().getString(R.string.loginAppKey), account, pwd)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::loginSuccess, this::onError);
            mDisposables.add(disposable);
        });

        loginForgetPwd.setOnClickListener(view -> ForgetActivity.actionStart(this, loginAccount.getText().toString()));
    }

    private void loginSuccess(Msg msg) {
        switch (msg.getCode()) {
            case 1:
                fetchUpdates();
                gotoMainActivity();
                break;
            case 2:
                Toast.makeText(this, "用户名错误或不存在！", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(this, "密码错误！", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
    }

    private void gotoMainActivity() {
        Disposable disposable = mUserInfoDb.getObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::saveInfo, this::onError);

        mDisposables.add(disposable);

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        ActivityController.finishAll();
    }

    private void saveInfo(UserInfo userInfo) {
        SharedPreferences sp = getSharedPreferences(DataUtil.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(DataUtil.LOGIN_SIGN, MyApplication.getContext().getString(R.string.loginSign));

        editor.putInt(DataUtil.USER_ID, userInfo.getId());
        editor.putString(DataUtil.USER_ACCOUNT, userInfo.getAccount());
        editor.putString(DataUtil.USER_NICKNAME, userInfo.getNickname());
        editor.putString(DataUtil.USER_HEAD_PORTRAIT, userInfo.getPortrait());

        editor.apply();

        LogUtil.d("LoginActivity", userInfo.toString());
//        Toast.makeText(this, "登录成功, " + userInfo.getNickname(), Toast.LENGTH_SHORT).show();
    }

    private void fetchUpdates() {
        String appKey = MyApplication.getContext().getString(R.string.userInfoDetailsAppKey);
        Disposable disposable = NetworkWrapper.getInstance().getDesertApiClient()
                .queryUserInfoDetails(appKey, loginAccount.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mUserInfoDb::insertUserInfo, this::onError);
        mDisposables.add(disposable);
    }

    private void onError(Throwable throwable) {
        LogUtil.d("LoginActivity", throwable.toString());
        Toast.makeText(this, "无法登录，请检查您的网络是否通畅！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
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
