package com.lkl.sqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lkl.sqlite.dao.UserDao;
import com.lkl.sqlite.model.User;


public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button btn_login=findViewById(R.id.btn_login);
        Button btn_register=findViewById(R.id.btn_register);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et_login_username=findViewById(R.id.et_login_username);
                EditText et_login_password=findViewById(R.id.et_login_password);

                UserDao userDao=new UserDao(Login.this);
                User user=userDao.login(et_login_username.getText().toString(),et_login_password.getText().toString());
                if(user!=null){

                    Intent intent=new Intent(Login.this,MainActivity.class);
                    startActivity(intent);

                }else {
                    Toast.makeText(Login.this,"用户名或密码不对",Toast.LENGTH_SHORT).show();

                }

            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });
    }
}