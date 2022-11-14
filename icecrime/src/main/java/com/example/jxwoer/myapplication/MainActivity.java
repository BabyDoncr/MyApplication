package com.example.jxwoer.myapplication;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.jxwoer.myapplication.pojo.Client;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button login =(Button)findViewById(R.id.login);
//        final String user="admin";
//       final String  pass="123";
        login.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view){
              final  String tel;
                EditText  editText1= (EditText)findViewById(R.id.username);
                tel= editText1.getText().toString().trim();
               final String password;
                EditText  editText2= (EditText)findViewById(R.id.pasword);
                password=editText2.getText().toString().trim();
                  if(tel.equals("")){
                    Toast.makeText(MainActivity.this, "账号不能为空", Toast.LENGTH_LONG).show();
//                    new AlertDialog.Builder(MainActivity.this).setTitle("Error!").setMessage("用户名不能为空").setNegativeButton("ok",null).show();
                }
                  else if (isMobile(tel)==false){
                      Toast.makeText(MainActivity.this, "请输入正确的手机号码", Toast.LENGTH_LONG).show();
                  }
                  else if (editText1.length()!=11){
                      Toast.makeText(MainActivity.this, "手机号码位数不够", Toast.LENGTH_LONG).show();
                  }
               else if(password.equals("")){
                    Toast.makeText(MainActivity.this, "密码不能为空", Toast.LENGTH_LONG).show();
                }
                else{
                    String url="http://192.168.120.180:8080/spring4/Client/loginClient.action";
                    //OkHttpClient client =new OkHttpClient();
                    OkHttpUtils
                            .post()
                            .url(url)
                            .addParams("ctel",tel)
                            .addParams("cpaw",password)
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    Toast.makeText(MainActivity.this, "加载错误", Toast.LENGTH_LONG).show();
//                        dialog.dismiss();
                                    Log.d("abc", e.getMessage());
                                }
                                @Override
                                public void onResponse(String response, int id) {
//                        dialog.dismiss();
                                    Gson gson = new Gson();
                                    Client c = gson.fromJson(response, Client.class);
                                    if (c != null) {
                                        Log.e("Tag1", "1111111111111111111111");
//                                        Log.v("addree","scasdwdwdwdwd");
                                        Log.e("addree",c.getCtel());
                                        Log.e("addreess",c.getCaddress());
                                        Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                                        SharedPreferences sp =getSharedPreferences("config", MainActivity.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sp.edit();
                            editor.putString ("caddress", c.getCaddress());
                             editor.putInt("userid",c.getCid());
                                        editor.putString("address",c.getCaddress());
//                            editor.putString("tel", tel);
//                            editor.putString("password", password);
                             editor.putString("username", c.getCname());
                                        editor.commit();
                                        Intent intent = new Intent(MainActivity.this, main.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(MainActivity.this, "您的账号或者密码错误,请重新输入", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }); }


//                if(tel.equals(user)&password.equals(pass)){
//                Intent intent =new Intent(MainActivity.this,main.class);
//                startActivity(intent);
//            }
//             else{
//                new AlertDialog.Builder(MainActivity.this).setTitle("Error!").setMessage("账号或密码错误").setNegativeButton("ok",null).show();
//            }

            }

        });



        final TextView  zhuce=(TextView)findViewById(R.id.zhuce);
        zhuce.setOnClickListener( new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, register.class);
                startActivity(intent);
            }
        });


}


//    reg="[^a-zA-Z0-9\u4E00-\u9FA5_]";
//    str = str.replaceAll(reg,"");
public static boolean isMobile(String mobiles){
    String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
    if (TextUtils.isEmpty(mobiles)){
        return false;
    }
    else return mobiles.matches(telRegex);
}
}









