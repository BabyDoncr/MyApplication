package com.lkl.readandwriter.openhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyHelper extends SQLiteOpenHelper {

    public MyHelper(@Nullable Context context){

        super(context,"test.db",null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqldb) {

        sqldb.execSQL("create table info (id integer primary key autoincrement , username varchar(20) , password varchar(20) , phone varchar(20))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
