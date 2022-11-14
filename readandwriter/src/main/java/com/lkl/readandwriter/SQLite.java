package com.lkl.readandwriter;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lkl.readandwriter.mode.User;
import com.lkl.readandwriter.openhelper.MyHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class SQLite extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqlite);

        Button btn_addUser = findViewById(R.id.btn_addUser);
        Button btn_delUser = findViewById(R.id.btn_delUser);
        Button btn_updateUser = findViewById(R.id.btn_updateUser);
        Button btn_findAll = findViewById(R.id.btn_findAll);


        //增加用户
        btn_addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //1.MyHelper实例化

                MyHelper myHelper = new MyHelper(getApplicationContext());

                //2.桶MyHelper类实例化出SQLiteDatabase对象

                SQLiteDatabase sqldb = myHelper.getWritableDatabase();

                //3.执行SQL语句  insert info info (username,password,phone) values(?,?,?)

                User user = new User("白龙马","123456","10086");

                sqldb.execSQL("insert into info (username,password,phone) values(?,?,?)",
                        new Object[]{user.getUsername(),user.getPassword(),user.getPhone()});

                //4.关闭SQLiteDatabase对象

                sqldb.close();

            }
        });

        //删除用户
        btn_delUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //1.MyHelper实例化

                MyHelper myHelper = new MyHelper(getApplicationContext());

                //2.桶MyHelper类实例化出SQLiteDatabase对象

                SQLiteDatabase sqldb = myHelper.getWritableDatabase();

                //3.执行SQL语句  insert info info (username,password,phone) values(?,?,?)

                User user = new User(1,"白龙马","123456","10086");

                sqldb.execSQL("delete from info where id = ?",
                        new Object[]{2});

                //4.关闭SQLiteDatabase对象

                sqldb.close();

            }
        });

        //修改用户
        btn_updateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //1.MyHelper实例化

                MyHelper myHelper = new MyHelper(getApplicationContext());

                //2.桶MyHelper类实例化出SQLiteDatabase对象

                SQLiteDatabase sqldb = myHelper.getWritableDatabase();

                //3.执行SQL语句  insert info info (username,password,phone) values(?,?,?)

                User user = new User(1,"白龙马","123456","10010");

                sqldb.execSQL("update info set username = ? , password = ? , phone = ? where id = ?",
                        new Object[]{user.getUsername(),user.getPassword(),user.getPhone(),user.getId()});

                //4.关闭SQLiteDatabase对象

                sqldb.close();

            }
        });

        //查询用户
        btn_findAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //1.MyHelper实例化

                List<User> list = new ArrayList<User>();
                MyHelper myHelper = new MyHelper(getApplicationContext());

                //2.桶MyHelper类实例化出SQLiteDatabase对象

                SQLiteDatabase sqldb = myHelper.getWritableDatabase();

                //3.执行SQL语句  insert info info (username,password,phone) values(?,?,?)


                Cursor cursor = sqldb.rawQuery("select * from info ", null);

                if (cursor!=null&&cursor.getCount()>0){

                    while (cursor.moveToNext()) {

                        User user = new User();
                        user.setId(cursor.getInt(0));
                        user.setUsername(cursor.getString(1));
                        user.setPassword(cursor.getString(2));
                        user.setPhone(cursor.getString(3));

                        list.add(user);

                    }

                }else{

                    Toast.makeText(SQLite.this, "sqlite没有数据", Toast.LENGTH_SHORT).show();

                }

                //把List集合发送到ListView界面上

                Intent intent = new Intent(SQLite.this, UserList1.class);

                intent.putExtra("list", (Serializable)list);

                startActivity(intent);

                //4.关闭SQLiteDatabase对象

                sqldb.close();

            }
        });


    }

}