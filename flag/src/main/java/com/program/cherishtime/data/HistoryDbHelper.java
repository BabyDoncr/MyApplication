package com.program.cherishtime.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class HistoryDbHelper {
    private static final String TAG = "History";

    private static final String DATABASE_NAME = "History.db";
    private static final int DATABASE_VERSION = 2;


    // Variable to hold the database instance
    protected SQLiteDatabase mDb;
    // Context of the application using the database.
    private final Context mContext;
    // Database open/upgrade helper
    private DbHelper mDbHelper;
    
    public HistoryDbHelper(Context context) {
        mContext = context;
        mDbHelper = new DbHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    public HistoryDbHelper open() throws SQLException { 
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }
                                                     
    public void close() {
        mDb.close();
    }

    public static final String ROW_ID = "_id";

    
    // -------------- HISTORY DEFINITIONS ------------
    public static final String HISTORY_TABLE = "History";
    
    public static final String HISTORY_UID_COLUMN = "uid";
    public static final int HISTORY_UID_COLUMN_POSITION = 1;
    
    public static final String HISTORY_TID_COLUMN = "tid";
    public static final int HISTORY_TID_COLUMN_POSITION = 2;
    
    public static final String HISTORY_DATETIME_COLUMN = "dateTime";
    public static final int HISTORY_DATETIME_COLUMN_POSITION = 3;
    
    public static final String HISTORY_TITLE_COLUMN = "title";
    public static final int HISTORY_TITLE_COLUMN_POSITION = 4;
    
    public static final String HISTORY_COMPLETE_COLUMN = "complete";
    public static final int HISTORY_COMPLETE_COLUMN_POSITION = 5;
    
    public static final String HISTORY_DEL_COLUMN = "del";
    public static final int HISTORY_DEL_COLUMN_POSITION = 6;
    
    


    // -------- TABLES CREATION ----------

    
    // History CREATION 
    private static final String DATABASE_HISTORY_CREATE = "create table " + HISTORY_TABLE + " (" +
                                "_id integer primary key autoincrement, " +
                                HISTORY_UID_COLUMN + " integer, " +
                                HISTORY_TID_COLUMN + " integer, " +
                                HISTORY_DATETIME_COLUMN + " integer, " +
                                HISTORY_TITLE_COLUMN + " text, " +
                                HISTORY_COMPLETE_COLUMN + " integer, " +
                                HISTORY_DEL_COLUMN + " integer" +
                                ")";

    public long addHistory (Integer uid, Integer tid, Long dateTime, String title, Integer complete, Integer del) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(HISTORY_UID_COLUMN, uid);
        contentValues.put(HISTORY_TID_COLUMN, tid);
        contentValues.put(HISTORY_DATETIME_COLUMN, dateTime);
        contentValues.put(HISTORY_TITLE_COLUMN, title);
        contentValues.put(HISTORY_COMPLETE_COLUMN, complete);
        contentValues.put(HISTORY_DEL_COLUMN, del);
        return mDb.insert(HISTORY_TABLE, null, contentValues);
    }
    public Cursor getAllHistory(int uid){
        String where = HISTORY_UID_COLUMN + " = " + uid + " and " + HISTORY_DEL_COLUMN + " = " + 0;
        String orderBy = HISTORY_DATETIME_COLUMN + " DESC";
        return mDb.query(HISTORY_TABLE, new String[] {
                ROW_ID,
                HISTORY_UID_COLUMN,
                HISTORY_TID_COLUMN,
                HISTORY_DATETIME_COLUMN,
                HISTORY_TITLE_COLUMN,
                HISTORY_COMPLETE_COLUMN,
                HISTORY_DEL_COLUMN
        }, where, null, null, null, orderBy);
    }
    private static class DbHelper extends SQLiteOpenHelper {
        public DbHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        // Called when no database exists in disk and the helper class needs
        // to create a new one. 
        @Override
        public void onCreate(SQLiteDatabase db) {      
            db.execSQL(DATABASE_HISTORY_CREATE);
            
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
            db.execSQL("DROP TABLE IF EXISTS " + HISTORY_TABLE + ";");
            
            // Create a new one.
            onCreate(db);
        }
    }
}

