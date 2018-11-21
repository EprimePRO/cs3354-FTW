package com.example.wkziegler.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Calendar.db";
    public static final String TABLE_NAME = "events_table";
    public static final String COL_1 = "start_epoch_time";
    //public static final String COL_2 = "end_epoch_time";
    public static final String COL_3 = "title";
    public static final String COL_4 = "description";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + COL_1 + " LONG, " + COL_3 + " TEXT, " + COL_4 + " TEXT) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String start_time, String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, start_time);
        contentValues.put(COL_3, title);
        contentValues.put(COL_4, description);

        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }

    }

    public Cursor getEventsByDate(String dateNum) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT " + COL_3 + ", " + COL_4 + " FROM " + TABLE_NAME + " WHERE " + COL_1 + " = " + dateNum, null);

        return res;
    }
}
