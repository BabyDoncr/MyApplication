package com.lkl.liukailong.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lkl.liukailong.helper.MyHelper;
import com.lkl.liukailong.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private MyHelper myHelper;

    public UserDao(Context context) {
        this.myHelper = new MyHelper(context);
    }

    public UserDao() {

    }

    //    增加
    public void addUser(User user) {
        SQLiteDatabase sqldb = myHelper.getWritableDatabase();
        sqldb.execSQL("insert into user(theme,article,writer,time) values (?,?,?,?)", new Object[]{user.getTheme(), user.getArticle(), user.getWriter(), user.getTime()});

        sqldb.close();
    }

    //    删除
    public void delUser(int id) {
        SQLiteDatabase sqldb = myHelper.getWritableDatabase();
        sqldb.execSQL("delete from user where id=?", new String[]{String.valueOf(id)});

        sqldb.close();

    }

    //    修改
    public void updateUser(User user) {
        SQLiteDatabase sqldb = myHelper.getWritableDatabase();
        sqldb.execSQL("update user set theme=?,article=?,writer=?,time=? where id=?", new Object[]{user.getTheme(), user.getArticle(), user.getWriter(), user.getTime(),user.getId()});
        sqldb.close();
    }

    //    查询所有
    public List<User> findAllUser() {
        List<User> list = new ArrayList<User>();
        SQLiteDatabase sqldb = myHelper.getWritableDatabase();

        Cursor cursor = sqldb.rawQuery("select * from user", null);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                User user = new User();
                user.setId(cursor.getInt(0));
                user.setTheme(cursor.getString(1));
                user.setArticle(cursor.getString(2));
                user.setWriter(cursor.getString(3));
                user.setTime(cursor.getString(4));

                list.add(user);
            }
        }
        sqldb.close();
        return list;
    }

//    根据id查询

    public User findUserById(int id) {
        User user = null;
        SQLiteDatabase sqldb = myHelper.getWritableDatabase();

        Cursor cursor = sqldb.rawQuery("select * from userlist where id=?", new String[]{String.valueOf(id)});

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                user = new User();
                user.setId(cursor.getInt(0));
                user.setTheme(cursor.getString(1));
                user.setArticle(cursor.getString(2));
                user.setWriter(cursor.getString(3));
                user.setTime(cursor.getString(4));
            }
        }
        cursor.close();
        return user;
    }


}
