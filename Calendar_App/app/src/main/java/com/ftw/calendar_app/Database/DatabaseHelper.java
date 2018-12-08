package com.ftw.calendar_app.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ftw.calendar_app.Event.Event;

import java.util.Calendar;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Calendar.db";
    private static final String TABLE_NAME = "events_table";
    private static final String ID = "id";
    private static final String DAY = "day";
    private static final String COLOR = "color";
    private static final String COL_1 = "start_epoch_time";
    private static final String COL_2 = "end_epoch_time";
    private static final String COL_3 = "title";
    private static final String COL_4 = "description";


    //version without color
    public static int version1 = 1;
    //new version with color
    public static int version2 = 2;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, version2);
    }

    private static final String TAG = "DatabaseHelper";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLOR + " INTEGER, "
                + DAY + " TEXT, "
                + COL_1 + " LONG, "
                + COL_2 + " LONG, "
                + COL_3 + " TEXT, "
                + COL_4 + " TEXT)");
        Log.d(TAG, "create table called");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == version1) {
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD " + COLOR + " INTEGER DEFAULT " + android.R.color.white);
        }
    }

    /**
     * Adds an event to the database
     *
     * @param event event that is being added
     * @return returns true if the entry was added successfully, false if not
     */
    public boolean insertData(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DAY, event.getDay());
        contentValues.put(COLOR, event.getColor());
        contentValues.put(COL_1, event.getStartDate().getTimeInMillis());
        contentValues.put(COL_2, event.getEndDate().getTimeInMillis());
        contentValues.put(COL_3, event.getTitle());
        contentValues.put(COL_4, event.getDescription());

        long result = db.insert(TABLE_NAME, null, contentValues);
        Cursor res = db.query(true, TABLE_NAME, new String[]{ID},
                DAY + " = '" + event.getDay() + "' AND "
                        + COL_3 + " = '" + event.getTitle() + "'",
                null, null, null, null, null);
        res.moveToNext();
        event.setDatabaseID(res.getInt(0));
        db.close();
        return result != -1;

    }

    /**
     * Edits an existing event in the database
     *
     * @param event event that is being updated
     * @return returns 1 if the entry updated successfully, 0 if not
     */
    public int editData(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DAY, event.getDay());
        contentValues.put(COLOR, event.getColor());
        contentValues.put(COL_1, event.getStartDate().getTimeInMillis());
        contentValues.put(COL_2, event.getEndDate().getTimeInMillis());
        contentValues.put(COL_3, event.getTitle());
        contentValues.put(COL_4, event.getDescription());


        return db.update(TABLE_NAME, contentValues, ID + " = " + event.getDatabaseID(), null);
    }

    /**
     * Get events on specified date.
     *
     * @param date incoming date should be formatted as: yyyy.MM.dd
     * @return returns as a cursor with columns: {title, description, start, end, id, color}
     */
    public Cursor getEventsByDate(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.query(TABLE_NAME, new String[]{COL_3, COL_4, COL_1, COL_2, ID, COLOR}, DAY + " = '" + date + "'", null, null, null, COL_1 + " ASC");
        return res;
    }


    /**
     * Get a certain event given the id.
     *
     * @param id an event id to look up in the database
     * @return an event object
     */
    public Event getEvent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.query(TABLE_NAME,
                new String[]{COL_1, COL_2, COL_3, COL_4, COLOR},
                ID + " = '" + id + "'",
                null, null, null, null);
        if (res.moveToNext()) {
            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();
            start.setTimeInMillis(res.getLong(0));
            end.setTimeInMillis(res.getLong(1));
            return new Event(start, end, res.getString(2), res.getString(3), id, res.getInt(4));
        } else {
            return null;
        }
    }

    /**
     * Delete the event with the given id.
     *
     * @param id an event id to look up in the database
     * @return true if deleted, false if not deleted.
     */
    public boolean deleteEvent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int numRowsDeleted = db.delete(TABLE_NAME,
                ID + " = " + id,
                null);
        return numRowsDeleted == 1;
    }
}
