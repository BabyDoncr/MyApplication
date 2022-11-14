package com.lkl.sqlite;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.lkl.sqlite.adapter.UserAdapter;
import com.lkl.sqlite.dao.UserDao;
import com.lkl.sqlite.model.User;

import java.util.List;

public class UserList extends AppCompatActivity {
    List<User> list;
    ListView lv_userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list);

        lv_userList = findViewById(R.id.userlist);

        /*  查询所有 */
        UserDao userDao = new UserDao(UserList.this);

        list = userDao.findAllUser();
        lv_userList.setAdapter(new UserAdapter(UserList.this, list));

        /* 删除操作 */
        lv_userList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i1, long l) {

                /* 拿到某一行数据 */
                User user = list.get(i1);


                /* 提示 */
                AlertDialog.Builder builder = new AlertDialog.Builder(UserList.this);
                builder.setTitle("删除操作");
                builder.setMessage("确定删除吗");

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        /* 执行UserDao的删除操作 */
                        UserDao userDao = new UserDao(UserList.this);
                        userDao.delUser(user.getId());
                        /* 删除完成之后，必须做一个操作  重新查询重新绑定数据 并让UserList 页面刷新 */
                        onResume();
                    }
                });
                builder.setNegativeButton("取消",null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                /* return true的内容  可以区分  具体是长按  还是点击 */
                return true;
            }
        });

        lv_userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i1, long l) {
                /* 修改操作 */

                /* 拿到修改的数据 */
                User user = list.get(i1);


                /* 把要修改的数据塞到alertDiolog */
                AlertDialog.Builder builder = new AlertDialog.Builder(UserList.this);
                builder.setTitle("修改");

                /* 加载额外的修改的布局页面 */
                View inflate = LayoutInflater.from(UserList.this).inflate(R.layout.useer_show,null);
                builder.setView(inflate);

                EditText et_show_id = findViewById(R.id.et_show_id);
                EditText et_show_username = findViewById(R.id.et_show_username);
                EditText et_show_password = findViewById(R.id.et_show_password);
                EditText et_show_phone = findViewById(R.id.et_show_phone);


                et_show_id.setText(user.getId());
                et_show_id.setEnabled(false);
                et_show_username.setText(user.getUsername());
                et_show_password.setText(user.getPassword());
                /* 显示密码 */
//                et_show_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                /* 隐藏密码 */
                et_show_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

                et_show_phone.setText(user.getPhone());


                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        User user = new User( Integer.parseInt(et_show_id.getText().toString()),
                                et_show_username.getText().toString(),
                                et_show_password.getText().toString(),
                                et_show_phone.getText().toString());

                        UserDao userDao = new UserDao(UserList.this);
                        userDao.updateUser(user);
                        onResume();

                    }
                });
                builder.setNegativeButton("取消",null);
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
        lv_userList.setAdapter(new UserAdapter(UserList.this, list));

    }





}