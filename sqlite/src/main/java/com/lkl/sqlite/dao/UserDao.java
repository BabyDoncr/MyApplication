package com.lkl.sqlite.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lkl.sqlite.helper.MyHelper;
import com.lkl.sqlite.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private MyHelper myHelper;

    public UserDao(Context context) {
        this.myHelper = new MyHelper(context);
    }

    //    增加
    public void addUser(User user) {
        SQLiteDatabase sqldb = myHelper.getWritableDatabase();
        sqldb.execSQL("insert into info(username,password,phone) values (?,?,?)", new Object[]{user.getUsername(), user.getPassword(), user.getPhone()});

        sqldb.close();
    }

    //    删除
    public void delUser(int id) {
        SQLiteDatabase sqldb = myHelper.getWritableDatabase();
        sqldb.execSQL("delete from info where id=?", new String[]{String.valueOf(id)});

        sqldb.close();

    }

    //    修改
    public void updateUser(User user) {
        SQLiteDatabase sqldb = myHelper.getWritableDatabase();
        sqldb.execSQL("update info set username=?,password=?,phone=? where id=?", new Object[]{user.getUsername(), user.getPassword(), user.getPhone(), user.getId()});
        sqldb.close();
    }

    //    查询所有
    public List<User> findAllUser() {
        List<User> list = new ArrayList<User>();
        SQLiteDatabase sqldb = myHelper.getWritableDatabase();

        Cursor cursor = sqldb.rawQuery("select * from info", null);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                User user = new User();
                user.setId(cursor.getInt(0));
                user.setUsername(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                user.setPhone(cursor.getString(3));

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

        Cursor cursor = sqldb.rawQuery("select * from info where id=?", new String[]{String.valueOf(id)});

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                user = new User();

                user.setId(cursor.getInt(0));
                user.setUsername(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                user.setPhone(cursor.getString(3));

            }
        }
        cursor.close();
        return user;
    }

    //    登录
    public User login(String username, String password) {
        User user = null;
        SQLiteDatabase sqldb = myHelper.getWritableDatabase();

        Cursor cursor = sqldb.rawQuery("select * from info where username=? and password=?", new String[]{username, password});

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                user = new User();
                user.setId(cursor.getInt(0));
                user.setUsername(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                user.setPhone(cursor.getString(3));

            }
        }
        cursor.close();
        return user;
    }

    //    根据名字查询对象
    public boolean findByUsername(String username) {
        SQLiteDatabase sqldb = myHelper.getWritableDatabase();

        Cursor cursor = sqldb.rawQuery("select * from info where username=?", new String[]{username});

        if (cursor != null && cursor.getCount() > 0) {
            return true;

        } else {
            cursor.close();
            return false;
        }

    }
}
