package com.example.jxwoer.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.FloatProperty;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Response;

import static android.app.PendingIntent.getActivity;

/**
 * Created by JXwoer on 2018/6/29.
 */

public class Icream_detail  extends AppCompatActivity {
    private ImageView image_icream;
    private TextView  text_name;
    private TextView  text_price;
    private  adderView adderview;
    private Button cart_button;
    private  TextView text_store;
    private  TextView text_number;
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.icream_detail);
        //监听
         text_store=(TextView)findViewById(R.id.icream_store_detail);
       image_icream=(ImageView)findViewById(R.id.icream_image_detail);
        text_name=(TextView)findViewById(R.id.icream_name_detail);
        text_price=(TextView)findViewById(R.id.icream_price_detail);
        text_number=(TextView)findViewById(R.id.icream_number_detail);
         final Bundle b  =getIntent().getExtras();
         int icream_image= b.getInt("icream_image");
         image_icream.setImageResource(icream_image);
         b.getString("icream_name");
         String icream_name= b.getString("icream_name");
         text_name.setText(icream_name);
         String  icream_price =b.getString("icream_price");
         text_price.setText(icream_price);
        String  icream_store=b.getString("icream_store");
        text_store.setText(icream_store);
     final  int icream_number=b.getInt("icream_number");
        text_number.setText(String.valueOf(icream_number));
//        icream_number.setTag();
       // Log.v("price",icream_price);
//        String abc=String.valueOf(adderview.getCurrentValue());EXT
//        Log.v("abc",abc);
       // String total=String.valueOf(icream_price* adderview.getCurrentValue());

      //  Log.v("abc",total);

        adderview=(adderView)findViewById(R.id.addview);
        adderview.setMaxValue(icream_number);
        adderview.setOnAddSubClickListener(new adderView.OnAddSubClickListener() {
            @Override
            public void onNumberChange(int value) {
//                Toast.makeText(Icream_detail.this, "添加=>" +value+"个", Toast.LENGTH_SHORT).show();
                adderview.setMaxValue(icream_number);
            }
        });
       //    Log.v("icream_name",String.valueOf(icream_image));
        cart_button=(Button)findViewById(R.id.goodcar);
        cart_button.setOnClickListener(  new View.OnClickListener(){
            public void onClick(View view)  {

                String value="";
                TextView textView=(TextView)findViewById(R.id.tv_count);
                value =textView.getText().toString().trim();
                TextView tvname = (TextView) findViewById(R.id.icream_name_detail);
                String tname = tvname.getText().toString().trim();
                Log.e("name",tname);
                TextView tvprice = (TextView) findViewById(R.id.icream_price_detail);
                String tprice = tvprice.getText().toString().trim();
//                 Float abc= Float.valueOf(tprice);
                TextView tvstore = (TextView)findViewById(R.id.icream_store_detail);
                String tstore = tvstore.getText().toString().trim();
                ImageView iv = (ImageView) findViewById(R.id.icream_image_detail);
//                int imgid=(int)iv.getTag();
//                int imgid = (int)iv.getTag();
                Float toprice=Float.valueOf(tprice)*Integer.valueOf(value);
                SharedPreferences sp = getSharedPreferences("config",Context.MODE_PRIVATE);
                int cid=sp.getInt("userid",0);
//                BroadCastManager.getInstance().sendBroadCast(Icream_detail.this, intent_cart);
                String url="http://192.168.120.180:8080/spring4/Buy/saveBuy.action";
                //OkHttpClient client =new OkHttpClient();
                OkHttpUtils
                        .post()
                        .url(url)
//                      .addParams("bid",String.valueOf(imgid))
                        .addParams("cid",String.valueOf(cid))
                        .addParams("number",value)
                        .addParams("type",tname)
                        .addParams("rtype",tstore)
                        .addParams("price",tprice)
                        .addParams("sumprice",String.valueOf(toprice))
//                      .addParams("cpaw",password)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(Icream_detail.this, "添加失败", Toast.LENGTH_LONG).show();
                            }
                            @Override
                            public void onResponse(String response, int id) {
                                Toast.makeText(Icream_detail.this, "添加成功", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }


}
