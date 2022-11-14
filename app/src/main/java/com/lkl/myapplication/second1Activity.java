package com.lkl.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class second1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second1_activity);

        Button btn_2 =findViewById(R.id.btn_2);

        Intent intent=getIntent();
        String data= intent.getStringExtra("data");
        Toast.makeText(second1Activity.this,data,Toast.LENGTH_SHORT).show();

        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent.putExtra("data1","hi");
                setResult(123,intent);
                finish();

            }
        });
    }
}