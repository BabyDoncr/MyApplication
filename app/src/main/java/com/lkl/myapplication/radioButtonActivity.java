package com.lkl.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class radioButtonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.radio_button_demo);
        RadioGroup radioGroup = findViewById(R.id.radio_button);
        Button btn4 = findViewById(R.id.btn4);
        Button rbt2 = findViewById(R.id.rbt2);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = findViewById(i);
                Toast.makeText(getApplicationContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton radioButton1 = findViewById(radioGroup.getCheckedRadioButtonId());
                if(radioButton1.getId() == rbt2.getId()){
                    Toast.makeText(getApplicationContext(), "回答正确", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(getApplicationContext(), "回答错误", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}