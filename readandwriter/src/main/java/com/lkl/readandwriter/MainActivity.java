package com.lkl.readandwriter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_rAndw = (Button) findViewById(R.id.btn_rAndw);
        Button btn_sqlite = (Button) findViewById(R.id.btn_sqlite);

        btn_rAndw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Login1.class);

                startActivity(intent);
            }
        });

        btn_sqlite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SQLite.class);

                startActivity(intent);
            }
        });

    }
}