package com.lkl.readandwriter;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FileReadAndWriter extends AppCompatActivity {
    EditText et_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_read_writer);

        et_show = findViewById(R.id.et_show);
        Button btn_readRaw = findViewById(R.id.btn_readRaw);
        Button btn_dataWriter = findViewById(R.id.btn_dataWriter);
        Button btn_dataRead = findViewById(R.id.btn_dataRead);
        Button btn_sdkWriter = findViewById(R.id.btn_sdkWriter);
        Button btn_sdkRead = findViewById(R.id.btn_sdkRead);

        btn_readRaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fileReadRaw();

            }
        });

        btn_dataRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fileDataRead();

            }
        });

        btn_dataWriter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fileDataWriter();

            }
        });

        btn_sdkWriter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fileSdRead();
            }
        });

        btn_sdkRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fileSdWriter();

            }
        });

    }

    private void fileSdRead() {

        String filename = "sd_20221101.txt";

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            filename = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + filename;

            try {
                FileInputStream fis = new FileInputStream(filename);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                et_show.setText(new String(buffer, "utf-8"));
                fis.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(this, "sd卡不存在", Toast.LENGTH_SHORT).show();
        }


    }

    private void fileSdWriter() {

        /* 拿到数据 */
        String s = et_show.getText().toString();
        String filename = "sd_20221101.txt";

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            filename = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + filename;

            try {
                FileOutputStream fos = new FileOutputStream(filename);
                byte[] buffer = s.getBytes(StandardCharsets.UTF_8);
                fos.write(buffer, 0, buffer.length);
                fos.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(this, "sd卡不存在", Toast.LENGTH_SHORT).show();
        }

    }

    private void fileDataWriter() {

        /* 首先拿到写进文件的内容 */
        String s = et_show.getText().toString();

        /* 通过字节流来写 */
        try {
            FileOutputStream fos = openFileOutput("data", MODE_APPEND);
            fos.write(s.getBytes());
            fos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void fileDataRead() {

        try {
            FileInputStream fis = openFileInput("data");

            try {
                byte[] bytes = new byte[fis.available()];
                fis.read(bytes);
                et_show.setText(new String(bytes));
                fis.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void fileReadRaw() {

        InputStream inputStream = getResources().openRawResource(R.raw.hello);

        try {

            int length = inputStream.available();
            byte[] bytes = new byte[length];
            inputStream.read(bytes);
            inputStream.close();

            /* get 请求  数据有中文的时候会出现乱码  utf-8 gdk  */

            et_show.setText(new String(bytes, "utf-8"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void checkNeedPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            /* 多个权限一起申请 */
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                    }, 1);


        }
    }
}