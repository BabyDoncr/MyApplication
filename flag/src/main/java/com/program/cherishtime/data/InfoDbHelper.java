package com.program.cherishtime.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class InfoDbHelper {
    private static final String TAG = "Info";

    private static final String DATABASE_NAME = "Info.db";
    private static final int DATABASE_VERSION = 1;


    // Variable to hold the database instance
    protected SQLiteDatabase mDb;
    // Context of the application using the database.
    private final Context mContext;
    // Database open/upgrade helper
    private DbHelper mDbHelper;
    
    public InfoDbHelper(Context context) {
        mContext = context;
        mDbHelper = new DbHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    public InfoDbHelper open() throws SQLException { 
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }
                                                     
    public void close() {
        mDb.close();
    }

    public static final String ROW_ID = "_id";

    
    // -------------- FOLLOWS DEFINITIONS ------------
    public static final String FOLLOWS_TABLE = "Follows";
    
    public static final String FOLLOWS_ID_COLUMN = "id";
    public static final int FOLLOWS_ID_COLUMN_POSITION = 1;
    
    public static final String FOLLOWS_NAME_COLUMN = "name";
    public static final int FOLLOWS_NAME_COLUMN_POSITION = 2;
    
    public static final String FOLLOWS_IMG_COLUMN = "img";
    public static final int FOLLOWS_IMG_COLUMN_POSITION = 3;
    
    public static final String FOLLOWS_VIP_COLUMN = "vip";
    public static final int FOLLOWS_VIP_COLUMN_POSITION = 4;
    
    public static final String FOLLOWS_TYPE_COLUMN = "type";
    public static final int FOLLOWS_TYPE_COLUMN_POSITION = 5;
    
    public static final String FOLLOWS_STATE_COLUMN = "state";
    public static final int FOLLOWS_STATE_COLUMN_POSITION = 6;
    
    
    // -------------- FANS DEFINITIONS ------------
    public static final String FANS_TABLE = "Fans";
    
    public static final String FANS_ID_COLUMN = "id";
    public static final int FANS_ID_COLUMN_POSITION = 1;
    
    public static final String FANS_NAME_COLUMN = "name";
    public static final int FANS_NAME_COLUMN_POSITION = 2;
    
    public static final String FANS_IMG_COLUMN = "img";
    public static final int FANS_IMG_COLUMN_POSITION = 3;
    
    public static final String FANS_VIP_COLUMN = "vip";
    public static final int FANS_VIP_COLUMN_POSITION = 4;
    
    public static final String FANS_TYPE_COLUMN = "type";
    public static final int FANS_TYPE_COLUMN_POSITION = 5;
    
    public static final String FANS_STATE_COLUMN = "state";
    public static final int FANS_STATE_COLUMN_POSITION = 6;
    
    
    // -------------- PROP DEFINITIONS ------------
    public static final String PROP_TABLE = "Prop";
    
    public static final String PROP_ID_COLUMN = "id";
    public static final int PROP_ID_COLUMN_POSITION = 1;
    
    public static final String PROP_NAME_COLUMN = "name";
    public static final int PROP_NAME_COLUMN_POSITION = 2;
    
    public static final String PROP_EFFECT_COLUMN = "effect";
    public static final int PROP_EFFECT_COLUMN_POSITION = 3;
    
    public static final String PROP_PRICE_COLUMN = "price";
    public static final int PROP_PRICE_COLUMN_POSITION = 4;
    
    public static final String PROP_IMG_COLUMN = "img";
    public static final int PROP_IMG_COLUMN_POSITION = 5;
    
    public static final String PROP_DEL_COLUMN = "del";
    public static final int PROP_DEL_COLUMN_POSITION = 6;
    
    
    // -------------- RANK DEFINITIONS ------------
    public static final String RANK_TABLE = "Rank";
    
    public static final String RANK_ID_COLUMN = "id";
    public static final int RANK_ID_COLUMN_POSITION = 1;
    
    public static final String RANK_IMG_COLUMN = "img";
    public static final int RANK_IMG_COLUMN_POSITION = 2;
    
    public static final String RANK_NAME_COLUMN = "name";
    public static final int RANK_NAME_COLUMN_POSITION = 3;
    
    public static final String RANK_NUM_COLUMN = "num";
    public static final int RANK_NUM_COLUMN_POSITION = 4;
    
    public static final String RANK_POINT_COLUMN = "point";
    public static final int RANK_POINT_COLUMN_POSITION = 5;
    
    public static final String RANK_DAYS_COLUMN = "days";
    public static final int RANK_DAYS_COLUMN_POSITION = 6;
    
    
    // -------------- USERINFO DEFINITIONS ------------
    public static final String USERINFO_TABLE = "UserInfo";
    
    public static final String USERINFO_ID_COLUMN = "id";
    public static final int USERINFO_ID_COLUMN_POSITION = 1;
    
    public static final String USERINFO_ACCOUNT_COLUMN = "account";
    public static final int USERINFO_ACCOUNT_COLUMN_POSITION = 2;
    
    public static final String USERINFO_NICKNAME_COLUMN = "nickname";
    public static final int USERINFO_NICKNAME_COLUMN_POSITION = 3;
    
    public static final String USERINFO_EMAIL_COLUMN = "email";
    public static final int USERINFO_EMAIL_COLUMN_POSITION = 4;
    
    public static final String USERINFO_SEX_COLUMN = "sex";
    public static final int USERINFO_SEX_COLUMN_POSITION = 5;
    
    public static final String USERINFO_BIRTHDAY_COLUMN = "birthday";
    public static final int USERINFO_BIRTHDAY_COLUMN_POSITION = 6;
    
    public static final String USERINFO_PORTRAIT_COLUMN = "portrait";
    public static final int USERINFO_PORTRAIT_COLUMN_POSITION = 7;
    
    public static final String USERINFO_PHONE_COLUMN = "phone";
    public static final int USERINFO_PHONE_COLUMN_POSITION = 8;
    
    public static final String USERINFO_FAN_COLUMN = "fan";
    public static final int USERINFO_FAN_COLUMN_POSITION = 9;
    
    public static final String USERINFO_FOLLOW_COLUMN = "follow";
    public static final int USERINFO_FOLLOW_COLUMN_POSITION = 10;
    
    public static final String USERINFO_LIKES_COLUMN = "likes";
    public static final int USERINFO_LIKES_COLUMN_POSITION = 11;
    
    public static final String USERINFO_VIP_COLUMN = "vip";
    public static final int USERINFO_VIP_COLUMN_POSITION = 12;
    
    public static final String USERINFO_LEVEL_COLUMN = "level";
    public static final int USERINFO_LEVEL_COLUMN_POSITION = 13;
    
    public static final String USERINFO_EXP_COLUMN = "exp";
    public static final int USERINFO_EXP_COLUMN_POSITION = 14;
    
    public static final String USERINFO_POINT_COLUMN = "point";
    public static final int USERINFO_POINT_COLUMN_POSITION = 15;
    
    public static final String USERINFO_DAYS_COLUMN = "days";
    public static final int USERINFO_DAYS_COLUMN_POSITION = 16;
    
    
    // -------------- USERCARD DEFINITIONS ------------
    public static final String USERCARD_TABLE = "UserCard";
    
    public static final String USERCARD_ID_COLUMN = "id";
    public static final int USERCARD_ID_COLUMN_POSITION = 1;
    
    public static final String USERCARD_NICKNAME_COLUMN = "nickname";
    public static final int USERCARD_NICKNAME_COLUMN_POSITION = 2;
    
    public static final String USERCARD_SEX_COLUMN = "sex";
    public static final int USERCARD_SEX_COLUMN_POSITION = 3;
    
    public static final String USERCARD_PORTRAIT_COLUMN = "portrait";
    public static final int USERCARD_PORTRAIT_COLUMN_POSITION = 4;
    
    public static final String USERCARD_FAN_COLUMN = "fan";
    public static final int USERCARD_FAN_COLUMN_POSITION = 5;
    
    public static final String USERCARD_FOLLOW_COLUMN = "follow";
    public static final int USERCARD_FOLLOW_COLUMN_POSITION = 6;
    
    public static final String USERCARD_LIKES_COLUMN = "likes";
    public static final int USERCARD_LIKES_COLUMN_POSITION = 7;
    
    public static final String USERCARD_VIP_COLUMN = "vip";
    public static final int USERCARD_VIP_COLUMN_POSITION = 8;
    
    public static final String USERCARD_LEVEL_COLUMN = "level";
    public static final int USERCARD_LEVEL_COLUMN_POSITION = 9;
    
    public static final String USERCARD_EXP_COLUMN = "exp";
    public static final int USERCARD_EXP_COLUMN_POSITION = 10;
    
    public static final String USERCARD_TYPE_COLUMN = "type";
    public static final int USERCARD_TYPE_COLUMN_POSITION = 11;
    
    
    // -------------- TASK DEFINITIONS ------------
    public static final String TASK_TABLE = "Task";
    
    public static final String TASK_ID_COLUMN = "id";
    public static final int TASK_ID_COLUMN_POSITION = 1;
    
    public static final String TASK_STARTTIME_COLUMN = "startTime";
    public static final int TASK_STARTTIME_COLUMN_POSITION = 2;
    
    public static final String TASK_ENDTIME_COLUMN = "endTime";
    public static final int TASK_ENDTIME_COLUMN_POSITION = 3;
    
    public static final String TASK_TITLE_COLUMN = "title";
    public static final int TASK_TITLE_COLUMN_POSITION = 4;
    
    public static final String TASK_CONTENT_COLUMN = "content";
    public static final int TASK_CONTENT_COLUMN_POSITION = 5;
    
    public static final String TASK_TYPE_COLUMN = "type";
    public static final int TASK_TYPE_COLUMN_POSITION = 6;
    
    public static final String TASK_DEL_COLUMN = "del";
    public static final int TASK_DEL_COLUMN_POSITION = 7;
    
    public static final String TASK_BELONG_COLUMN = "belong";
    public static final int TASK_BELONG_COLUMN_POSITION = 8;
    
    


    // -------- TABLES CREATION ----------

    
    // Follows CREATION 
    private static final String DATABASE_FOLLOWS_CREATE = "create table " + FOLLOWS_TABLE + " (" +
                                "_id integer primary key autoincrement, " +
                                FOLLOWS_ID_COLUMN + " integer, " +
                                FOLLOWS_NAME_COLUMN + " text, " +
                                FOLLOWS_IMG_COLUMN + " text, " +
                                FOLLOWS_VIP_COLUMN + " integer, " +
                                FOLLOWS_TYPE_COLUMN + " integer, " +
                                FOLLOWS_STATE_COLUMN + " integer" +
                                ")";
    
    // Fans CREATION 
    private static final String DATABASE_FANS_CREATE = "create table " + FANS_TABLE + " (" +
                                "_id integer primary key autoincrement, " +
                                FANS_ID_COLUMN + " integer, " +
                                FANS_NAME_COLUMN + " text, " +
                                FANS_IMG_COLUMN + " text, " +
                                FANS_VIP_COLUMN + " integer, " +
                                FANS_TYPE_COLUMN + " integer, " +
                                FANS_STATE_COLUMN + " integer" +
                                ")";
    
    // Prop CREATION 
    private static final String DATABASE_PROP_CREATE = "create table " + PROP_TABLE + " (" +
                                "_id integer primary key autoincrement, " +
                                PROP_ID_COLUMN + " integer, " +
                                PROP_NAME_COLUMN + " text, " +
                                PROP_EFFECT_COLUMN + " text, " +
                                PROP_PRICE_COLUMN + " text, " +
                                PROP_IMG_COLUMN + " text, " +
                                PROP_DEL_COLUMN + " integer" +
                                ")";
    
    // Rank CREATION 
    private static final String DATABASE_RANK_CREATE = "create table " + RANK_TABLE + " (" +
                                "_id integer primary key autoincrement, " +
                                RANK_ID_COLUMN + " integer, " +
                                RANK_IMG_COLUMN + " text, " +
                                RANK_NAME_COLUMN + " text, " +
                                RANK_NUM_COLUMN + " integer, " +
                                RANK_POINT_COLUMN + " integer, " +
                                RANK_DAYS_COLUMN + " integer" +
                                ")";
    
    // UserInfo CREATION 
    private static final String DATABASE_USERINFO_CREATE = "create table " + USERINFO_TABLE + " (" +
                                "_id integer primary key autoincrement, " +
                                USERINFO_ID_COLUMN + " integer, " +
                                USERINFO_ACCOUNT_COLUMN + " text, " +
                                USERINFO_NICKNAME_COLUMN + " text, " +
                                USERINFO_EMAIL_COLUMN + " text, " +
                                USERINFO_SEX_COLUMN + " integer, " +
                                USERINFO_BIRTHDAY_COLUMN + " text, " +
                                USERINFO_PORTRAIT_COLUMN + " text, " +
                                USERINFO_PHONE_COLUMN + " text, " +
                                USERINFO_FAN_COLUMN + " integer, " +
                                USERINFO_FOLLOW_COLUMN + " integer, " +
                                USERINFO_LIKES_COLUMN + " integer, " +
                                USERINFO_VIP_COLUMN + " integer, " +
                                USERINFO_LEVEL_COLUMN + " integer, " +
                                USERINFO_EXP_COLUMN + " integer, " +
                                USERINFO_POINT_COLUMN + " integer, " +
                                USERINFO_DAYS_COLUMN + " integer" +
                                ")";
    
    // UserCard CREATION 
    private static final String DATABASE_USERCARD_CREATE = "create table " + USERCARD_TABLE + " (" +
                                "_id integer primary key autoincrement, " +
                                USERCARD_ID_COLUMN + " integer, " +
                                USERCARD_NICKNAME_COLUMN + " text, " +
                                USERCARD_SEX_COLUMN + " integer, " +
                                USERCARD_PORTRAIT_COLUMN + " text, " +
                                USERCARD_FAN_COLUMN + " integer, " +
                                USERCARD_FOLLOW_COLUMN + " integer, " +
                                USERCARD_LIKES_COLUMN + " integer, " +
                                USERCARD_VIP_COLUMN + " integer, " +
                                USERCARD_LEVEL_COLUMN + " integer, " +
                                USERCARD_EXP_COLUMN + " integer, " +
                                USERCARD_TYPE_COLUMN + " integer" +
                                ")";
    
    // Task CREATION 
    private static final String DATABASE_TASK_CREATE = "create table " + TASK_TABLE + " (" +
                                "_id integer primary key autoincrement, " +
                                TASK_ID_COLUMN + " integer, " +
                                TASK_STARTTIME_COLUMN + " text, " +
                                TASK_ENDTIME_COLUMN + " text, " +
                                TASK_TITLE_COLUMN + " text, " +
                                TASK_CONTENT_COLUMN + " text, " +
                                TASK_TYPE_COLUMN + " integer, " +
                                TASK_DEL_COLUMN + " integer, " +
                                TASK_BELONG_COLUMN + " integer" +
                                ")";
    

    
    // ----------------Follows HELPERS -------------------- 
    public long addFollows (int id, String name, String img, int vip, int type, int state) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FOLLOWS_ID_COLUMN, id);
        contentValues.put(FOLLOWS_NAME_COLUMN, name);
        contentValues.put(FOLLOWS_IMG_COLUMN, img);
        contentValues.put(FOLLOWS_VIP_COLUMN, vip);
        contentValues.put(FOLLOWS_TYPE_COLUMN, type);
        contentValues.put(FOLLOWS_STATE_COLUMN, state);
        return mDb.insert(FOLLOWS_TABLE, null, contentValues);
    }

    public long updateFollows (long rowIndex,int id, String name, String img, int vip, int type, int state) {
        String where = ROW_ID + " = " + rowIndex;
        ContentValues contentValues = new ContentValues();
        contentValues.put(FOLLOWS_ID_COLUMN, id);
        contentValues.put(FOLLOWS_NAME_COLUMN, name);
        contentValues.put(FOLLOWS_IMG_COLUMN, img);
        contentValues.put(FOLLOWS_VIP_COLUMN, vip);
        contentValues.put(FOLLOWS_TYPE_COLUMN, type);
        contentValues.put(FOLLOWS_STATE_COLUMN, state);
        return mDb.update(FOLLOWS_TABLE, contentValues, where, null);
    }

    public boolean removeFollows(long rowIndex){
        return mDb.delete(FOLLOWS_TABLE, ROW_ID + " = " + rowIndex, null) > 0;
    }

    public boolean removeAllFollows(){
        return mDb.delete(FOLLOWS_TABLE, null, null) > 0;
    }

    public Cursor getAllFollows(){
    	return mDb.query(FOLLOWS_TABLE, new String[] {
                         ROW_ID,
                         FOLLOWS_ID_COLUMN,
                         FOLLOWS_NAME_COLUMN,
                         FOLLOWS_IMG_COLUMN,
                         FOLLOWS_VIP_COLUMN,
                         FOLLOWS_TYPE_COLUMN,
                         FOLLOWS_STATE_COLUMN
                         }, null, null, null, null, null);
    }

    public Cursor getFollows(long rowIndex) {
        Cursor res = mDb.query(FOLLOWS_TABLE, new String[] {
                                ROW_ID,
                                FOLLOWS_ID_COLUMN,
                                FOLLOWS_NAME_COLUMN,
                                FOLLOWS_IMG_COLUMN,
                                FOLLOWS_VIP_COLUMN,
                                FOLLOWS_TYPE_COLUMN,
                                FOLLOWS_STATE_COLUMN
                                }, ROW_ID + " = " + rowIndex, null, null, null, null);

        if(res != null){
            res.moveToFirst();
        }
        return res;
    }
    
    // ----------------Fans HELPERS -------------------- 
    public long addFans (int id, String name, String img, int vip, int type, int state) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FANS_ID_COLUMN, id);
        contentValues.put(FANS_NAME_COLUMN, name);
        contentValues.put(FANS_IMG_COLUMN, img);
        contentValues.put(FANS_VIP_COLUMN, vip);
        contentValues.put(FANS_TYPE_COLUMN, type);
        contentValues.put(FANS_STATE_COLUMN, state);
        return mDb.insert(FANS_TABLE, null, contentValues);
    }

    public long updateFans (long rowIndex,int id, String name, String img, int vip, int type, int state) {
        String where = ROW_ID + " = " + rowIndex;
        ContentValues contentValues = new ContentValues();
        contentValues.put(FANS_ID_COLUMN, id);
        contentValues.put(FANS_NAME_COLUMN, name);
        contentValues.put(FANS_IMG_COLUMN, img);
        contentValues.put(FANS_VIP_COLUMN, vip);
        contentValues.put(FANS_TYPE_COLUMN, type);
        contentValues.put(FANS_STATE_COLUMN, state);
        return mDb.update(FANS_TABLE, contentValues, where, null);
    }

    public boolean removeFans(long rowIndex){
        return mDb.delete(FANS_TABLE, ROW_ID + " = " + rowIndex, null) > 0;
    }

    public boolean removeAllFans(){
        return mDb.delete(FANS_TABLE, null, null) > 0;
    }

    public Cursor getAllFans(){
    	return mDb.query(FANS_TABLE, new String[] {
                         ROW_ID,
                         FANS_ID_COLUMN,
                         FANS_NAME_COLUMN,
                         FANS_IMG_COLUMN,
                         FANS_VIP_COLUMN,
                         FANS_TYPE_COLUMN,
                         FANS_STATE_COLUMN
                         }, null, null, null, null, null);
    }

    public Cursor getFans(long rowIndex) {
        Cursor res = mDb.query(FANS_TABLE, new String[] {
                                ROW_ID,
                                FANS_ID_COLUMN,
                                FANS_NAME_COLUMN,
                                FANS_IMG_COLUMN,
                                FANS_VIP_COLUMN,
                                FANS_TYPE_COLUMN,
                                FANS_STATE_COLUMN
                                }, ROW_ID + " = " + rowIndex, null, null, null, null);

        if(res != null){
            res.moveToFirst();
        }
        return res;
    }
    
    // ----------------Prop HELPERS -------------------- 
    public long addProp (int id, String name, String effect, String price, String img, int del) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROP_ID_COLUMN, id);
        contentValues.put(PROP_NAME_COLUMN, name);
        contentValues.put(PROP_EFFECT_COLUMN, effect);
        contentValues.put(PROP_PRICE_COLUMN, price);
        contentValues.put(PROP_IMG_COLUMN, img);
        contentValues.put(PROP_DEL_COLUMN, del);
        return mDb.insert(PROP_TABLE, null, contentValues);
    }

    public long updateProp (long rowIndex,int id, String name, String effect, String price, String img, int del) {
        String where = ROW_ID + " = " + rowIndex;
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROP_ID_COLUMN, id);
        contentValues.put(PROP_NAME_COLUMN, name);
        contentValues.put(PROP_EFFECT_COLUMN, effect);
        contentValues.put(PROP_PRICE_COLUMN, price);
        contentValues.put(PROP_IMG_COLUMN, img);
        contentValues.put(PROP_DEL_COLUMN, del);
        return mDb.update(PROP_TABLE, contentValues, where, null);
    }

    public boolean removeProp(long rowIndex){
        return mDb.delete(PROP_TABLE, ROW_ID + " = " + rowIndex, null) > 0;
    }

    public boolean removeAllProp(){
        return mDb.delete(PROP_TABLE, null, null) > 0;
    }

    public Cursor getAllProp(){
    	return mDb.query(PROP_TABLE, new String[] {
                         ROW_ID,
                         PROP_ID_COLUMN,
                         PROP_NAME_COLUMN,
                         PROP_EFFECT_COLUMN,
                         PROP_PRICE_COLUMN,
                         PROP_IMG_COLUMN,
                         PROP_DEL_COLUMN
                         }, null, null, null, null, null);
    }

    public Cursor getProp(long rowIndex) {
        Cursor res = mDb.query(PROP_TABLE, new String[] {
                                ROW_ID,
                                PROP_ID_COLUMN,
                                PROP_NAME_COLUMN,
                                PROP_EFFECT_COLUMN,
                                PROP_PRICE_COLUMN,
                                PROP_IMG_COLUMN,
                                PROP_DEL_COLUMN
                                }, ROW_ID + " = " + rowIndex, null, null, null, null);

        if(res != null){
            res.moveToFirst();
        }
        return res;
    }
    
    // ----------------Rank HELPERS -------------------- 
    public long addRank (int id, String img, String name, int num, int point, int days) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RANK_ID_COLUMN, id);
        contentValues.put(RANK_IMG_COLUMN, img);
        contentValues.put(RANK_NAME_COLUMN, name);
        contentValues.put(RANK_NUM_COLUMN, num);
        contentValues.put(RANK_POINT_COLUMN, point);
        contentValues.put(RANK_DAYS_COLUMN, days);
        return mDb.insert(RANK_TABLE, null, contentValues);
    }

    public long updateRank (long rowIndex,int id, String img, String name, int num, int point, int days) {
        String where = ROW_ID + " = " + rowIndex;
        ContentValues contentValues = new ContentValues();
        contentValues.put(RANK_ID_COLUMN, id);
        contentValues.put(RANK_IMG_COLUMN, img);
        contentValues.put(RANK_NAME_COLUMN, name);
        contentValues.put(RANK_NUM_COLUMN, num);
        contentValues.put(RANK_POINT_COLUMN, point);
        contentValues.put(RANK_DAYS_COLUMN, days);
        return mDb.update(RANK_TABLE, contentValues, where, null);
    }

    public boolean removeRank(long rowIndex){
        return mDb.delete(RANK_TABLE, ROW_ID + " = " + rowIndex, null) > 0;
    }

    public boolean removeAllRank(){
        return mDb.delete(RANK_TABLE, null, null) > 0;
    }

    public Cursor getAllRank(){
    	return mDb.query(RANK_TABLE, new String[] {
                         ROW_ID,
                         RANK_ID_COLUMN,
                         RANK_IMG_COLUMN,
                         RANK_NAME_COLUMN,
                         RANK_NUM_COLUMN,
                         RANK_POINT_COLUMN,
                         RANK_DAYS_COLUMN
                         }, null, null, null, null, null);
    }

    public Cursor getRank(long rowIndex) {
        Cursor res = mDb.query(RANK_TABLE, new String[] {
                                ROW_ID,
                                RANK_ID_COLUMN,
                                RANK_IMG_COLUMN,
                                RANK_NAME_COLUMN,
                                RANK_NUM_COLUMN,
                                RANK_POINT_COLUMN,
                                RANK_DAYS_COLUMN
                                }, ROW_ID + " = " + rowIndex, null, null, null, null);

        if(res != null){
            res.moveToFirst();
        }
        return res;
    }
    
    // ----------------UserInfo HELPERS -------------------- 
    public long addUserInfo (int id, String account, String nickname, String email, int sex, String birthday, String portrait, String phone, int fan, int follow, int likes, int vip, int level, int exp, int point, int days) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERINFO_ID_COLUMN, id);
        contentValues.put(USERINFO_ACCOUNT_COLUMN, account);
        contentValues.put(USERINFO_NICKNAME_COLUMN, nickname);
        contentValues.put(USERINFO_EMAIL_COLUMN, email);
        contentValues.put(USERINFO_SEX_COLUMN, sex);
        contentValues.put(USERINFO_BIRTHDAY_COLUMN, birthday);
        contentValues.put(USERINFO_PORTRAIT_COLUMN, portrait);
        contentValues.put(USERINFO_PHONE_COLUMN, phone);
        contentValues.put(USERINFO_FAN_COLUMN, fan);
        contentValues.put(USERINFO_FOLLOW_COLUMN, follow);
        contentValues.put(USERINFO_LIKES_COLUMN, likes);
        contentValues.put(USERINFO_VIP_COLUMN, vip);
        contentValues.put(USERINFO_LEVEL_COLUMN, level);
        contentValues.put(USERINFO_EXP_COLUMN, exp);
        contentValues.put(USERINFO_POINT_COLUMN, point);
        contentValues.put(USERINFO_DAYS_COLUMN, days);
        return mDb.insert(USERINFO_TABLE, null, contentValues);
    }

    public long updateUserInfo (long rowIndex,int id, String account, String nickname, String email, int sex, String birthday, String portrait, String phone, int fan, int follow, int likes, int vip, int level, int exp, int point, int days) {
        String where = ROW_ID + " = " + rowIndex;
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERINFO_ID_COLUMN, id);
        contentValues.put(USERINFO_ACCOUNT_COLUMN, account);
        contentValues.put(USERINFO_NICKNAME_COLUMN, nickname);
        contentValues.put(USERINFO_EMAIL_COLUMN, email);
        contentValues.put(USERINFO_SEX_COLUMN, sex);
        contentValues.put(USERINFO_BIRTHDAY_COLUMN, birthday);
        contentValues.put(USERINFO_PORTRAIT_COLUMN, portrait);
        contentValues.put(USERINFO_PHONE_COLUMN, phone);
        contentValues.put(USERINFO_FAN_COLUMN, fan);
        contentValues.put(USERINFO_FOLLOW_COLUMN, follow);
        contentValues.put(USERINFO_LIKES_COLUMN, likes);
        contentValues.put(USERINFO_VIP_COLUMN, vip);
        contentValues.put(USERINFO_LEVEL_COLUMN, level);
        contentValues.put(USERINFO_EXP_COLUMN, exp);
        contentValues.put(USERINFO_POINT_COLUMN, point);
        contentValues.put(USERINFO_DAYS_COLUMN, days);
        return mDb.update(USERINFO_TABLE, contentValues, where, null);
    }

    public boolean removeUserInfo(long rowIndex){
        return mDb.delete(USERINFO_TABLE, ROW_ID + " = " + rowIndex, null) > 0;
    }

    public boolean removeAllUserInfo(){
        return mDb.delete(USERINFO_TABLE, null, null) > 0;
    }

    public Cursor getAllUserInfo(){
    	return mDb.query(USERINFO_TABLE, new String[] {
                         ROW_ID,
                         USERINFO_ID_COLUMN,
                         USERINFO_ACCOUNT_COLUMN,
                         USERINFO_NICKNAME_COLUMN,
                         USERINFO_EMAIL_COLUMN,
                         USERINFO_SEX_COLUMN,
                         USERINFO_BIRTHDAY_COLUMN,
                         USERINFO_PORTRAIT_COLUMN,
                         USERINFO_PHONE_COLUMN,
                         USERINFO_FAN_COLUMN,
                         USERINFO_FOLLOW_COLUMN,
                         USERINFO_LIKES_COLUMN,
                         USERINFO_VIP_COLUMN,
                         USERINFO_LEVEL_COLUMN,
                         USERINFO_EXP_COLUMN,
                         USERINFO_POINT_COLUMN,
                         USERINFO_DAYS_COLUMN
                         }, null, null, null, null, null);
    }

    public Cursor getUserInfo(long rowIndex) {
        Cursor res = mDb.query(USERINFO_TABLE, new String[] {
                                ROW_ID,
                                USERINFO_ID_COLUMN,
                                USERINFO_ACCOUNT_COLUMN,
                                USERINFO_NICKNAME_COLUMN,
                                USERINFO_EMAIL_COLUMN,
                                USERINFO_SEX_COLUMN,
                                USERINFO_BIRTHDAY_COLUMN,
                                USERINFO_PORTRAIT_COLUMN,
                                USERINFO_PHONE_COLUMN,
                                USERINFO_FAN_COLUMN,
                                USERINFO_FOLLOW_COLUMN,
                                USERINFO_LIKES_COLUMN,
                                USERINFO_VIP_COLUMN,
                                USERINFO_LEVEL_COLUMN,
                                USERINFO_EXP_COLUMN,
                                USERINFO_POINT_COLUMN,
                                USERINFO_DAYS_COLUMN
                                }, ROW_ID + " = " + rowIndex, null, null, null, null);

        if(res != null){
            res.moveToFirst();
        }
        return res;
    }
    
    // ----------------UserCard HELPERS -------------------- 
    public long addUserCard (int id, String nickname, int sex, String portrait, int fan, int follow, int likes, int vip, int level, int exp, int type) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERCARD_ID_COLUMN, id);
        contentValues.put(USERCARD_NICKNAME_COLUMN, nickname);
        contentValues.put(USERCARD_SEX_COLUMN, sex);
        contentValues.put(USERCARD_PORTRAIT_COLUMN, portrait);
        contentValues.put(USERCARD_FAN_COLUMN, fan);
        contentValues.put(USERCARD_FOLLOW_COLUMN, follow);
        contentValues.put(USERCARD_LIKES_COLUMN, likes);
        contentValues.put(USERCARD_VIP_COLUMN, vip);
        contentValues.put(USERCARD_LEVEL_COLUMN, level);
        contentValues.put(USERCARD_EXP_COLUMN, exp);
        contentValues.put(USERCARD_TYPE_COLUMN, type);
        return mDb.insert(USERCARD_TABLE, null, contentValues);
    }

    public long updateUserCard (long rowIndex,int id, String nickname, int sex, String portrait, int fan, int follow, int likes, int vip, int level, int exp, int type) {
        String where = ROW_ID + " = " + rowIndex;
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERCARD_ID_COLUMN, id);
        contentValues.put(USERCARD_NICKNAME_COLUMN, nickname);
        contentValues.put(USERCARD_SEX_COLUMN, sex);
        contentValues.put(USERCARD_PORTRAIT_COLUMN, portrait);
        contentValues.put(USERCARD_FAN_COLUMN, fan);
        contentValues.put(USERCARD_FOLLOW_COLUMN, follow);
        contentValues.put(USERCARD_LIKES_COLUMN, likes);
        contentValues.put(USERCARD_VIP_COLUMN, vip);
        contentValues.put(USERCARD_LEVEL_COLUMN, level);
        contentValues.put(USERCARD_EXP_COLUMN, exp);
        contentValues.put(USERCARD_TYPE_COLUMN, type);
        return mDb.update(USERCARD_TABLE, contentValues, where, null);
    }

    public boolean removeUserCard(long rowIndex){
        return mDb.delete(USERCARD_TABLE, ROW_ID + " = " + rowIndex, null) > 0;
    }

    public boolean removeAllUserCard(){
        return mDb.delete(USERCARD_TABLE, null, null) > 0;
    }

    public boolean removeUserCardByUserId(long id) {
        String where = USERCARD_ID_COLUMN + " = " + id;
        return mDb.delete(USERCARD_TABLE, where, null) > 0;
    }

    public Cursor getAllUserCard(){
    	return mDb.query(USERCARD_TABLE, new String[] {
                         ROW_ID,
                         USERCARD_ID_COLUMN,
                         USERCARD_NICKNAME_COLUMN,
                         USERCARD_SEX_COLUMN,
                         USERCARD_PORTRAIT_COLUMN,
                         USERCARD_FAN_COLUMN,
                         USERCARD_FOLLOW_COLUMN,
                         USERCARD_LIKES_COLUMN,
                         USERCARD_VIP_COLUMN,
                         USERCARD_LEVEL_COLUMN,
                         USERCARD_EXP_COLUMN,
                         USERCARD_TYPE_COLUMN
                         }, null, null, null, null, null);
    }

    public Cursor getUserCard(long rowIndex) {
        Cursor res = mDb.query(USERCARD_TABLE, new String[] {
                                ROW_ID,
                                USERCARD_ID_COLUMN,
                                USERCARD_NICKNAME_COLUMN,
                                USERCARD_SEX_COLUMN,
                                USERCARD_PORTRAIT_COLUMN,
                                USERCARD_FAN_COLUMN,
                                USERCARD_FOLLOW_COLUMN,
                                USERCARD_LIKES_COLUMN,
                                USERCARD_VIP_COLUMN,
                                USERCARD_LEVEL_COLUMN,
                                USERCARD_EXP_COLUMN,
                                USERCARD_TYPE_COLUMN
                                }, ROW_ID + " = " + rowIndex, null, null, null, null);

        if(res != null){
            res.moveToFirst();
        }
        return res;
    }

    public Cursor getUserCardByUserId(long uid) {
        String where = USERCARD_ID_COLUMN + " = " + uid;
        Cursor res = mDb.query(USERCARD_TABLE, new String[]{
                ROW_ID,
                USERCARD_ID_COLUMN,
                USERCARD_NICKNAME_COLUMN,
                USERCARD_SEX_COLUMN,
                USERCARD_PORTRAIT_COLUMN,
                USERCARD_FAN_COLUMN,
                USERCARD_FOLLOW_COLUMN,
                USERCARD_LIKES_COLUMN,
                USERCARD_VIP_COLUMN,
                USERCARD_LEVEL_COLUMN,
                USERCARD_EXP_COLUMN,
                USERCARD_TYPE_COLUMN
        }, where, null, null, null, null);
        return res;
    }
    
    // ----------------Task HELPERS -------------------- 
    public long addTask (int id, String startTime, String endTime, String title, String content, int type, int del, int belong) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK_ID_COLUMN, id);
        contentValues.put(TASK_STARTTIME_COLUMN, startTime);
        contentValues.put(TASK_ENDTIME_COLUMN, endTime);
        contentValues.put(TASK_TITLE_COLUMN, title);
        contentValues.put(TASK_CONTENT_COLUMN, content);
        contentValues.put(TASK_TYPE_COLUMN, type);
        contentValues.put(TASK_DEL_COLUMN, del);
        contentValues.put(TASK_BELONG_COLUMN, belong);
        return mDb.insert(TASK_TABLE, null, contentValues);
    }

    public long updateTask (long rowIndex,int id, String startTime, String endTime, String title, String content, int type, int del, int belong) {
        String where = ROW_ID + " = " + rowIndex;
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK_ID_COLUMN, id);
        contentValues.put(TASK_STARTTIME_COLUMN, startTime);
        contentValues.put(TASK_ENDTIME_COLUMN, endTime);
        contentValues.put(TASK_TITLE_COLUMN, title);
        contentValues.put(TASK_CONTENT_COLUMN, content);
        contentValues.put(TASK_TYPE_COLUMN, type);
        contentValues.put(TASK_DEL_COLUMN, del);
        contentValues.put(TASK_BELONG_COLUMN, belong);
        return mDb.update(TASK_TABLE, contentValues, where, null);
    }

    public boolean removeTask(long rowIndex){
        return mDb.delete(TASK_TABLE, ROW_ID + " = " + rowIndex, null) > 0;
    }

    public boolean removeTaskByTidAndBelong(long tid, int belong) {
        String where = TASK_ID_COLUMN + " = " + tid + " and " + TASK_BELONG_COLUMN + " = " + belong;
        return mDb.delete(TASK_TABLE, where, null) > 0;
    }

    public boolean removeAllTask(){
        return mDb.delete(TASK_TABLE, null, null) > 0;
    }

    public boolean removeTasksByBelong(int belong) {
        String where = TASK_BELONG_COLUMN + " = " + belong;
        return mDb.delete(TASK_TABLE, where, null) > 0;
    }

    public Cursor getAllTask(){
    	return mDb.query(TASK_TABLE, new String[] {
                         ROW_ID,
                         TASK_ID_COLUMN,
                         TASK_STARTTIME_COLUMN,
                         TASK_ENDTIME_COLUMN,
                         TASK_TITLE_COLUMN,
                         TASK_CONTENT_COLUMN,
                         TASK_TYPE_COLUMN,
                         TASK_DEL_COLUMN,
                         TASK_BELONG_COLUMN
                         }, null, null, null, null, null);
    }

    public Cursor getTasksByBelong(int belong) {
        String where = TASK_BELONG_COLUMN + " = " + belong;
        return mDb.query(TASK_TABLE, new String[]{
                ROW_ID,
                TASK_ID_COLUMN,
                TASK_STARTTIME_COLUMN,
                TASK_ENDTIME_COLUMN,
                TASK_TITLE_COLUMN,
                TASK_CONTENT_COLUMN,
                TASK_TYPE_COLUMN,
                TASK_DEL_COLUMN,
                TASK_BELONG_COLUMN
        }, where, null, null, null, null);
    }

    public Cursor getTask(long rowIndex) {
        Cursor res = mDb.query(TASK_TABLE, new String[] {
                                ROW_ID,
                                TASK_ID_COLUMN,
                                TASK_STARTTIME_COLUMN,
                                TASK_ENDTIME_COLUMN,
                                TASK_TITLE_COLUMN,
                                TASK_CONTENT_COLUMN,
                                TASK_TYPE_COLUMN,
                                TASK_DEL_COLUMN,
                                TASK_BELONG_COLUMN
                                }, ROW_ID + " = " + rowIndex, null, null, null, null);

        if(res != null){
            res.moveToFirst();
        }
        return res;
    }

    public Cursor getTaskByTidAndBelong(long tid, int belong) {
        String where = TASK_ID_COLUMN + " = " + tid + " and " + TASK_BELONG_COLUMN + " = " + belong;
        Cursor res = mDb.query(TASK_TABLE, new String[]{
                ROW_ID,
                TASK_ID_COLUMN,
                TASK_STARTTIME_COLUMN,
                TASK_ENDTIME_COLUMN,
                TASK_TITLE_COLUMN,
                TASK_CONTENT_COLUMN,
                TASK_TYPE_COLUMN,
                TASK_DEL_COLUMN,
                TASK_BELONG_COLUMN
        }, where, null, null, null, null);
        return res;
    }
    

    private static class DbHelper extends SQLiteOpenHelper {
        public DbHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        // Called when no database exists in disk and the helper class needs
        // to create a new one. 
        @Override
        public void onCreate(SQLiteDatabase db) {      
            db.execSQL(DATABASE_FOLLOWS_CREATE);
            db.execSQL(DATABASE_FANS_CREATE);
            db.execSQL(DATABASE_PROP_CREATE);
            db.execSQL(DATABASE_RANK_CREATE);
            db.execSQL(DATABASE_USERINFO_CREATE);
            db.execSQL(DATABASE_USERCARD_CREATE);
            db.execSQL(DATABASE_TASK_CREATE);
            
        }

        // Called when there is a database version mismatch meaning that the version
        // of the database on disk needs to be upgraded to the current version.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Log the version upgrade.
            Log.w(TAG, "Upgrading from version " + 
                        oldVersion + " to " +
                        newVersion + ", which will destroy all old data");
            
            // Upgrade the existing database to conform to the new version. Multiple 
            // previous versions can be handled by comparing _oldVersion and _newVersion
            // values.

            // The simplest case is to drop the old table and create a new one.
            db.execSQL("DROP TABLE IF EXISTS " + FOLLOWS_TABLE + ";");
            db.execSQL("DROP TABLE IF EXISTS " + FANS_TABLE + ";");
            db.execSQL("DROP TABLE IF EXISTS " + PROP_TABLE + ";");
            db.execSQL("DROP TABLE IF EXISTS " + RANK_TABLE + ";");
            db.execSQL("DROP TABLE IF EXISTS " + USERINFO_TABLE + ";");
            db.execSQL("DROP TABLE IF EXISTS " + USERCARD_TABLE + ";");
            db.execSQL("DROP TABLE IF EXISTS " + TASK_TABLE + ";");
            
            // Create a new one.
            onCreate(db);
        }
    }
}

