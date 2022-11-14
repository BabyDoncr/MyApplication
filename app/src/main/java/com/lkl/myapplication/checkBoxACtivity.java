package com.lkl.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class checkBoxACtivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_box);

        CheckBox cbBanana = findViewById(R.id.cb_banana);
        CheckBox cbApple = findViewById(R.id.cb_apple);
        CheckBox cbSnowPear = findViewById(R.id.cb_snowPear);

        Button btnSubmit = findViewById(R.id.btn_Submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = "";
                boolean isTrue = false;
                if (!cbBanana.isChecked() && !cbApple.isChecked() && !cbSnowPear.isChecked()) {
                    Toast.makeText(checkBoxACtivity.this, "你未进行选择", Toast.LENGTH_SHORT).show();
                } else {
                    if (cbBanana.isChecked()) {
                        text += cbBanana.getText().toString()+"、";
                    }
                    if (cbApple.isChecked()) {
                        text += cbApple.getText().toString()+"、";
                    }
                    if (cbSnowPear.isChecked()) {
                        text += cbSnowPear.getText().toString()+"、";

                    }
                    Toast.makeText(checkBoxACtivity.this, "你选择了" + text.substring(0,text.length()-1), Toast.LENGTH_SHORT).show();


                }
                if(cbBanana.isChecked()&&cbApple.isChecked()&&!cbSnowPear.isChecked()) {
                    Toast.makeText(checkBoxACtivity.this,"回答正确", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(checkBoxACtivity.this,"回答错误", Toast.LENGTH_SHORT).show();
                }
            }


        });
    }
}