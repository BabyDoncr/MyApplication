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


public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        Button btn_reg = findViewById(R.id.btn_reg);

        EditText et_reg_username = findViewById(R.id.et_reg_username);
        EditText et_reg_password = findViewById(R.id.et_reg_password);
        EditText et_reg_phone = findViewById(R.id.et_reg_phone);

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User(et_reg_username.getText().toString(), et_reg_password.getText().toString(), et_reg_phone.getText().toString());
                UserDao userDao = new UserDao(Register.this);

                if (userDao.findByUsername(et_reg_username.getText().toString())) {
                    Toast.makeText(Register.this, "已经存在用户名了", Toast.LENGTH_SHORT).show();

                } else {
                    userDao.addUser(user);
                    Intent intent = new Intent(Register.this, Login.class);
                    startActivity(intent);
                }
            }
        });

    }
}