package com.lkl.readandwriter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.lkl.readandwriter.adapter.UserAdapter;
import com.lkl.readandwriter.mode.User;

import java.util.List;


public class UserList1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userlist);

        ListView lv_users = (ListView) findViewById(R.id.lv_users);

        /* 拿到intent的数据 */
        Intent intent = getIntent();

        List<User> list = (List<User>) intent.getParcelableExtra("list");

        /* 给对象设置适配器 */
        lv_users.setAdapter(new UserAdapter(UserList1.this,list));


    }
}