package com.lkl.readandwriter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login1 extends AppCompatActivity {

    String username;
    String password;
    Boolean cb_is;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button btn_login = (Button) findViewById(R.id.btn_login);
        Button btn_zhuce = (Button) findViewById(R.id.btn_zhuce);
        EditText ed_user = (EditText) findViewById(R.id.ed_user);
        EditText ed_pwd = (EditText) findViewById(R.id.ed_pwd);
        CheckBox cb_save = (CheckBox) findViewById(R.id.cb_save);


        if (cb_save.isChecked()) cb_is = true;
        else cb_is = false;
        /* 在登录之前 必须判断这个user.xml 有没有数据 如果有 直接显示上  没有不需要显示 */
        SharedPreferences sp = getSharedPreferences("user11",0);
        username = sp.getString("username","");
        password = sp.getString("password","");
        cb_is = sp.getBoolean("is_save",false);

        /* 如果shper 有值 把这些值塞到界面上  Edittext 和 Checkbox上 */
        ed_user.setText(username);
        ed_pwd.setText(password);
        cb_save.setChecked(cb_is);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username = ed_user.getText().toString();
                password = ed_pwd.getText().toString();
                cb_is = cb_save.isChecked();

                /* 判断用户名和密码是否正确 */

                if("lixingyu".equals(username.trim())&&"1234566".equals(password.trim())){

                    if(cb_is){

                        /* 选择了对钩 */

                        /* 第一步：创建Shperfecence对象 */
                        SharedPreferences.Editor editor = getSharedPreferences("user11",0).edit();
                        editor.putString("username", username);
                        editor.putString("password", password);
                        editor.putBoolean("is_save", cb_is);
                        editor.apply();
                    }else{
                        /* 只塞 用户名 密码  cb_is 塞 false */
                        SharedPreferences.Editor editor = getSharedPreferences("user11",0).edit();
                        editor.putString("username","");
                        editor.putString("password","");
                        editor.putBoolean("is_save",cb_is);
                        editor.apply();
                    }
                    Toast.makeText(Login1.this,"登陆成功",Toast.LENGTH_SHORT).show();

                }else{


                    Toast.makeText(Login1.this,"登陆失败",Toast.LENGTH_SHORT).show();
                }



            }
        });

        btn_zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}