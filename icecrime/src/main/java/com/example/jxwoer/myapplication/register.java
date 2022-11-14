package com.example.jxwoer.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.jxwoer.myapplication.pojo.Client;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import okhttp3.Call;

/**
 * Created by JXwoer on 2018/6/27.
 */

public class register  extends Activity {
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        final Button  regiterbutton=(Button)findViewById(R.id.registe);
      regiterbutton.setOnClickListener(new View.OnClickListener() {

          @Override
          public void onClick(View v) {
              EditText tel= (EditText)findViewById(R.id.regtel);
              EditText name= (EditText)findViewById(R.id.regusername);
              EditText pass= (EditText)findViewById(R.id.repassword);
              EditText passagain= (EditText)findViewById(R.id.againpassword);
              EditText address= (EditText)findViewById(R.id.address);
              final String  tegtel= tel.getText().toString().trim();
              final String regname= name.getText().toString().trim();
              final String regpass= pass.getText().toString().trim();
              final String againpass= passagain.getText().toString().trim();
              final  String   regaddress=address.getText().toString().trim();

              if(tegtel.equals("")){
                  Toast.makeText(register.this, "账号不能为空", Toast.LENGTH_LONG).show();
//                    new AlertDialog.Builder(MainActivity.this).setTitle("Error!").setMessage("用户名不能为空").setNegativeButton("ok",null).show();
              }
              else if (regname.equals("")){
                  Toast.makeText(register.this, "用户名不能为空", Toast.LENGTH_LONG).show();
              }
              else if (regaddress.equals("")){
                  Toast.makeText(register.this, "地址不能为空", Toast.LENGTH_LONG).show();
              }
              else if (tegtel.length()!=11){
                  Toast.makeText(register.this, "手机号码位数不够", Toast.LENGTH_LONG).show();
              }
              else if (isMobile(tegtel)==false){
                  Toast.makeText(register.this, "请输入正确的手机号码", Toast.LENGTH_LONG).show();
              }
              else if(regpass.equals("")||againpass.equals("")){
                  Toast.makeText(register.this, "密码不能为空", Toast.LENGTH_LONG).show();
              }
              else if(!(regpass).equals(againpass)) {
                  Toast.makeText(register.this, "两次密码不一致", Toast.LENGTH_LONG).show();
              }
//              }else if(isAddress(regaddress)==false) {
//                  Toast.makeText(register.this, "请输入汉字", Toast.LENGTH_LONG).show();
//              }
              else {
                  String url = "http://192.168.120.180:8080/spring4/Client/registeClient.action";
                  //OkHttpClient client =new OkHttpClient();
                  OkHttpUtils
                          .post()
                          .url(url)
                          .addParams("cname", regname)
                          .addParams("cpaw", regpass)
                          .addParams("ctel", tegtel)
                          .addParams("caddress", regaddress)
                          .build()
                          .execute(new StringCallback() {
                              @Override
                              public void onError(Call call, Exception e, int id) {
                                  Toast.makeText(register.this, "加载错误", Toast.LENGTH_LONG).show();
                              }
                              @Override
                              public void onResponse(String response, int id) {
                                  Gson gson = new Gson();
                                  Client c = gson.fromJson(response, Client.class);
                                  if (c != null) {
                                      Toast.makeText(register.this, "注册成功", Toast.LENGTH_LONG).show();
                                      Intent intent = new Intent(register.this, MainActivity.class);
                                      startActivity(intent);
                                  } else {
                                      Toast.makeText(register.this, "该用户已存在", Toast.LENGTH_LONG).show();
                                  }
                              }
                          });
              }
          }
      });

    }


//    public static boolean isAddress(String mobiles){
//        String  reg="[^a-zA-Z0-9\u4E00-\u9FA5_]";
//        if(TextUtils.isEmpty(mobiles)){
//
//            return false;
//        }
//      //  mobiles = mobiles.replaceAll(reg,"");
//        return   mobiles.matches(reg);
//    }
    public static boolean isMobile(String mobiles){
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)){
            return false;
        }
        else return mobiles.matches(telRegex);
    }

}
