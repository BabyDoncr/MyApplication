package com.program.cherishtime.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import java.util.Date;

public class TaskDbHelper {
    private static final String TAG = "Task";

    private static final String DATABASE_NAME = "Task.db";
    private static final int DATABASE_VERSION = 1;


    // Variable to hold the database instance
    protected SQLiteDatabase mDb;
    // Context of the application using the database.
    private final Context mContext;
    // Database open/upgrade helper
    private DbHelper mDbHelper;
    
    public TaskDbHelper(Context context) {
        mContext = context;
        mDbHelper = new DbHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    public TaskDbHelper open() throws SQLException { 
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }
                                                     
    public void close() {
        mDb.close();
    }

    public static final String ROW_ID = "_id";

    
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
    
    public static final String TASK_TASKDATE_COLUMN = "taskDate";
    public static final int TASK_TASKDATE_COLUMN_POSITION = 7;
    
    public static final String TASK_DEL_COLUMN = "del";
    public static final int TASK_DEL_COLUMN_POSITION = 8;
    
    public static final String TASK_BELONG_COLUMN = "belong";
    public static final int TASK_BELONG_COLUMN_POSITION = 9;
    
    


    // -------- TABLES CREATION ----------

    
    // Task CREATION 
    private static final String DATABASE_TASK_CREATE = "create table " + TASK_TABLE + " (" +
                                "_id integer primary key autoincrement, " +
                                TASK_ID_COLUMN + " integer, " +
                                TASK_STARTTIME_COLUMN + " text, " +
                                TASK_ENDTIME_COLUMN + " text, " +
                                TASK_TITLE_COLUMN + " text, " +
                                TASK_CONTENT_COLUMN + " text, " +
                                TASK_TYPE_COLUMN + " integer, " +
                                TASK_TASKDATE_COLUMN + " integer, " +
                                TASK_DEL_COLUMN + " integer, " +
                                TASK_BELONG_COLUMN + " integer" +
                                ")";
    

    
    // ----------------Task HELPERS -------------------- 
    public long addTask (int id, String startTime, String endTime, String title, String content, int type, int taskDate, int del, int belong) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK_ID_COLUMN, id);
        contentValues.put(TASK_STARTTIME_COLUMN, startTime);
        contentValues.put(TASK_ENDTIME_COLUMN, endTime);
        contentValues.put(TASK_TITLE_COLUMN, title);
        contentValues.put(TASK_CONTENT_COLUMN, content);
        contentValues.put(TASK_TYPE_COLUMN, type);
        contentValues.put(TASK_TASKDATE_COLUMN, taskDate);
        contentValues.put(TASK_DEL_COLUMN, del);
        contentValues.put(TASK_BELONG_COLUMN, belong);
        return mDb.insert(TASK_TABLE, null, contentValues);
    }

    public long updateTask (long rowIndex,int id, String startTime, String endTime, String title, String content, int type, int taskDate, int del, int belong) {
        String where = ROW_ID + " = " + rowIndex;
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK_ID_COLUMN, id);
        contentValues.put(TASK_STARTTIME_COLUMN, startTime);
        contentValues.put(TASK_ENDTIME_COLUMN, endTime);
        contentValues.put(TASK_TITLE_COLUMN, title);
        contentValues.put(TASK_CONTENT_COLUMN, content);
        contentValues.put(TASK_TYPE_COLUMN, type);
        contentValues.put(TASK_TASKDATE_COLUMN, taskDate);
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
                         TASK_TASKDATE_COLUMN,
                         TASK_DEL_COLUMN,
                         TASK_BELONG_COLUMN
                         }, null, null, null, null, null);
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
                                TASK_TASKDATE_COLUMN,
                                TASK_DEL_COLUMN,
                                TASK_BELONG_COLUMN
                                }, ROW_ID + " = " + rowIndex, null, null, null, null);

        if(res != null){
            res.moveToFirst();
        }
        return res;
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
                TASK_TASKDATE_COLUMN,
                TASK_DEL_COLUMN,
                TASK_BELONG_COLUMN
        }, where, null, null, null, null);
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
                TASK_TASKDATE_COLUMN,
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
            db.execSQL("DROP TABLE IF EXISTS " + TASK_TABLE + ";");
            
            // Create a new one.
            onCreate(db);
        }
    }
}

