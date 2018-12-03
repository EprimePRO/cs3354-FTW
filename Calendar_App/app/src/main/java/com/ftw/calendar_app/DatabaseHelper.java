package com.ftw.calendar_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Calendar.db";
    public static final String TABLE_NAME = "events_table";
    public static final String ID = "id";
    public static final String DAY = "day";
    public static final String COL_1 = "start_epoch_time";
    public static final String COL_2 = "end_epoch_time";
    public static final String COL_3 = "title";
    public static final String COL_4 = "description";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    private static final String TAG = "DatabaseHelper";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DAY + " TEXT, "
                + COL_1 + " LONG, "
                + COL_2 + " LONG, "
                + COL_3 + " TEXT, "
                + COL_4 + " TEXT)");
        Log.d(TAG, "create table called");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DAY, event.getDay());
        contentValues.put(COL_1, event.getStartDate().getTimeInMillis());
        contentValues.put(COL_2, event.getEndDate().getTimeInMillis());
        contentValues.put(COL_3, event.getTitle());
        contentValues.put(COL_4, event.getDescription());

        long result = db.insert(TABLE_NAME, null, contentValues);
        Cursor res = db.query(true, TABLE_NAME, new String[] {ID},
                DAY + " = '" + event.getDay() + "' AND "
                        + COL_3 + " = '" + event.getTitle() + "'",
                null, null, null, null, null);
        res.moveToNext();
        event.setDatabaseID(res.getInt(0));
        db.close();
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }

    }

    public int editData(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DAY, event.getDay());
        contentValues.put(COL_1, event.getStartDate().getTimeInMillis());
        contentValues.put(COL_2, event.getEndDate().getTimeInMillis());
        contentValues.put(COL_3, event.getTitle());
        contentValues.put(COL_4, event.getDescription());


        return db.update(TABLE_NAME, contentValues,ID + " = " + event.getDatabaseID(),null);
    }

    /**
     * col 0 = title col 1 = desc col 2 = startDate col 3 = end Date col 4 = id
    */
    public Cursor getEventsByDate(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.query(TABLE_NAME, new String[] {COL_3, COL_4, COL_1, COL_2, ID}, DAY+" = '"+date+"'", null, null, null, null);
        return res;
    }


    public Event getEvent(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.query(TABLE_NAME,
                new String[] {COL_1, COL_2, COL_3, COL_4},
                ID+" = '"+id+"'",
                null, null, null, null);
        if(res.moveToNext()){
            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();
            start.setTimeInMillis(res.getLong(0));
            end.setTimeInMillis(res.getLong(1));
            return new Event(start, end, res.getString(2), res.getString(3), id);
        }else{
            return null;
        }
    }

    public boolean deleteEvent(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        int numRowsDeleted = db.delete(TABLE_NAME,
                ID + " = " + id,
                null);
        return numRowsDeleted == 1;
    }
}
