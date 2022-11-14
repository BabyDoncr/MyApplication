package com.lkl.liukailong;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.lkl.liukailong.adapter.UserAdapter;
import com.lkl.liukailong.dao.UserDao;
import com.lkl.liukailong.model.User;

import java.util.List;

public class UserList extends AppCompatActivity {
    List<User> list;
    ListView lv_userList;
    TextView user_del;
    TextView user_add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list);

        lv_userList = findViewById(R.id.userlist);

        user_add = findViewById(R.id.user_add);


        user_del = findViewById(R.id.user_del);

        /*  查询所有 */
        UserDao userDao = new UserDao(UserList.this);

        list = userDao.findAllUser();
        lv_userList.setAdapter(new UserAdapter(UserList.this, list,lv_userList));



        /* 添加操作 */
        user_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* 把要修改的数据塞到alertDiolog */
                AlertDialog.Builder builder = new AlertDialog.Builder(UserList.this);

                /* 加载额外的修改的布局页面 */
                View inflate = LayoutInflater.from(UserList.this).inflate(R.layout.user_add, null);
                builder.setView(inflate);

                EditText et_add_theme = inflate.findViewById(R.id.et_add_theme);
                EditText et_add_article = inflate.findViewById(R.id.et_add_article);
                EditText et_add_writer = inflate.findViewById(R.id.et_add_writer);
                EditText et_add_time = inflate.findViewById(R.id.et_add_time);


                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        User user = new User(
                                et_add_theme.getText().toString(),
                                et_add_article.getText().toString(),
                                et_add_writer.getText().toString(),
                                et_add_time.getText().toString());

                        UserDao userDao = new UserDao(UserList.this);
                        userDao.addUser(user);
                        onResume();

                    }
                });
                builder.setNegativeButton("取消", null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        /* 重新执行查询所有，并绑定 */
        UserDao userDao = new UserDao(UserList.this);
        list = userDao.findAllUser();
        lv_userList.setAdapter(new UserAdapter(UserList.this, list,lv_userList));

    }


}