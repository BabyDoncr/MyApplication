package com.lkl.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class secondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_ctivity);

        Button btn_1=findViewById(R.id.btn_1);

        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(secondActivity.this,second1Activity.class);
                intent.putExtra("data","hello");
                startActivityForResult(intent,111);


            }
        });


    }
    protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent intent){
        super.onActivityResult(requestCode,resultCode,intent);

        if(requestCode==111){
            if (resultCode==123){
                String data1= intent.getStringExtra("data1");
                Toast.makeText(secondActivity.this,data1,Toast.LENGTH_SHORT).show();
            }
        }
    }
}