package com.lkl.liukailong.holdere;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.lkl.liukailong.R;
import com.lkl.liukailong.adapter.UserAdapter;
import com.lkl.liukailong.dao.UserDao;
import com.lkl.liukailong.model.User;

import java.util.List;

public class UserHolder {

    public TextView tv_id;
    public TextView tv_theme;
    public TextView tv_article;
    public TextView tv_writer;
    public TextView tv_time;
    public TextView user_del;
    public TextView user_update;


    public void update(int i, Context context, List<User> list, ListView listView) {
        User user = list.get(i);
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        View inflate = LayoutInflater.from(context).inflate(R.layout.user_update,null);
        builder.setView(inflate);

        EditText et_show_id = inflate.findViewById(R.id.et_show_id);
        EditText et_show_theme = inflate.findViewById(R.id.et_show_theme);
        EditText et_show_article = inflate.findViewById(R.id.et_show_article);
        EditText et_show_writer = inflate.findViewById(R.id.et_show_writer);
        EditText et_show_time = inflate.findViewById(R.id.et_show_time);


        et_show_id.setText(String.valueOf(user.getId()));
        et_show_id.setEnabled(false);

        et_show_theme.setText(String.valueOf(user.getTheme()));
        et_show_article.setText(String.valueOf(user.getArticle()));
        et_show_writer.setText(String.valueOf(user.getWriter()));
        et_show_time.setText(String.valueOf(user.getTime()));
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                User user = new User(Integer.parseInt(et_show_id.getText().toString()),
                        et_show_theme.getText().toString(),
                        et_show_article.getText().toString(),
                        et_show_writer.getText().toString(),
                        et_show_time.getText().toString());

                UserDao userDao = new UserDao(context);
                userDao.updateUser(user);
               List<User> users = userDao.findAllUser();
               listView.setAdapter(new UserAdapter(context,users,listView));

            }
        });
        builder.setNegativeButton("取消", null);
        android.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public void delete(int i, Context context, List<User> list, ListView listView) {
        User blog =  list.get(i);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("删除操作");
        builder.setMessage("确定要删除吗");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                UserDao blogDao = new UserDao(context);
                blogDao.delUser(blog.getId());
                List<User> list=blogDao.findAllUser();
                listView.setAdapter(new UserAdapter(context,list,listView));
            }
        });
        builder.setNegativeButton("取消",null);
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
}
