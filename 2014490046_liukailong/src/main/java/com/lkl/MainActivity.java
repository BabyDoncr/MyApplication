package com.lkl;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = findViewById(R.id.btn1);
        Button bt2 = findViewById(R.id.btn2);


        // 获取姓名  密码 验证码
        EditText ed1 = findViewById(R.id.ed1);
        EditText ed2 = findViewById(R.id.ed3);
        EditText ed3 = findViewById(R.id.ed3);



        // 获取 随机验证码
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                int i = random.nextInt(8999);
                Toast.makeText(MainActivity.this,"验证码："+(i+1000),Toast.LENGTH_LONG).show();
            }
        });

        // 获取性别

/*
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = findViewById(i);
                String sex = radioButton.getText().toString();
            }
        });*/


        // 获取爱好
        CheckBox cb_chang = findViewById(R.id.cb_chang);
        CheckBox cb_tiao = findViewById(R.id.cb_tiao);
        CheckBox cb_you = findViewById(R.id.cb_you);
        CheckBox cb_du = findViewById(R.id.cb_du);



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = "";
                boolean isTrue = false;
                if (!cb_chang.isChecked() && !cb_tiao.isChecked() && !cb_you.isChecked()&& !cb_du.isChecked()) {
                    Toast.makeText(MainActivity.this, "你未进行选择爱好", Toast.LENGTH_SHORT).show();
                } else {
                    if (cb_chang.isChecked()) {
                        text += cb_chang.getText().toString()+"、";
                    }
                    if (cb_tiao.isChecked()) {
                        text += cb_tiao.getText().toString()+"、";
                    }
                    if (cb_you.isChecked()) {
                        text += cb_you.getText().toString()+"、";

                    }
                    if (cb_du.isChecked()) {
                        text += cb_du.getText().toString()+"、";

                    }
              }

                String username = ed1.getText().toString();
                String password = ed2.getText().toString();
                String yanzhengma = ed3.getText().toString();

                RadioGroup radioGroup = findViewById(R.id.rd);
                RadioButton rdbt1 = findViewById(R.id.rdbt1);
                RadioButton rdbt2 = findViewById(R.id.rdbt2);
                CharSequence sex = "";
                if(rdbt1.isChecked())  sex = rdbt1.getText().toString();
                else sex = rdbt2.getText().toString();


                Toast.makeText(MainActivity.this, String.format("注册成功   你的姓名是：%s         你的密码是：%s  验证码是：%s      性别是：%s 爱好是%s", username, password, yanzhengma, sex, text.substring(0, text.length() - 1)), Toast.LENGTH_SHORT).show();
            }
        });
    }

}