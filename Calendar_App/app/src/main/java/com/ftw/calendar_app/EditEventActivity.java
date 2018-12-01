package com.ftw.calendar_app;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class EditEventActivity extends AppCompatActivity {

    TextView eventTitle, eventDescription;
    DatabaseHelper db;
    Spinner startTime, endTime, startPeriod, endPeriod;
    Long dateNum;
    String eventName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        //Spinner Stuff
        String[] times = new String[] {"12:00","12:15","12:30","12:45","1:00","1:15","1:30","1:45","2:00","2:15",
                "2:30","2:45","3:00","3:15","3:30","3:45","4:00","4:15","4:30","4:45","5:00","5:15","5:30","5:45",
                "6:00","6:15","6:30","6:45","7:00","7:15","7:30","7:45","8:00","8:15","8:30","8:45",
                "9:00","9:15","9:30","9:45","10:00","10:15","10:30","10:45","11:00","11:15","11:30","11:45"};
        String[] periods = new String[] {"AM", "PM"};

        //get intent from activity and create database object
        Intent intent = getIntent();
        db = new DatabaseHelper(this);

        //get data from intent
        dateNum = intent.getLongExtra("dateLong", Calendar.getInstance().getTimeInMillis());
        eventName = intent.getStringExtra("event");

        //get event details from database
        Cursor cursor = db.getEvent(eventName, String.valueOf(dateNum));

        //get views by id
        eventTitle = (EditText) findViewById(R.id.eventTextTitle);
        eventDescription = (EditText) findViewById(R.id.eventTextDesc);
        startTime = findViewById(R.id.spinnerStartTime);
        endTime = findViewById(R.id.spinnerEndTime);
        startPeriod = findViewById(R.id.spinnerStartPeriod);
        endPeriod = findViewById(R.id.spinnerEndPeriod);

        //set spinner adapter
        ArrayAdapter<String> adapterTimes = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,times);
        ArrayAdapter<String> adapterPeriods = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,periods);

        startTime.setAdapter(adapterTimes);
        endTime.setAdapter(adapterTimes);
        startPeriod.setAdapter(adapterPeriods);
        endPeriod.setAdapter(adapterPeriods);

        //set the views to the event's current values
        cursor.moveToNext();
        eventTitle.setText(cursor.getString(0));
        eventDescription.setText(cursor.getString(1));
        int position = 0;
        for(int i=0; i<times.length; i++){
            if(cursor.getString(2).equals(times[i])){
                position = i;
                break;
            }
        }
        startTime.setSelection(position);
        for(int i=0; i<times.length; i++){
            if(cursor.getString(3).equals(times[i])){
                position = i;
                break;
            }
        }
        endTime.setSelection(position);
        for(int i=0; i<periods.length; i++){
            if(cursor.getString(4).equals(periods[i])){
                position = i;
                break;
            }
        }
        startPeriod.setSelection(position);
        for(int i=0; i<periods.length; i++){
            if(cursor.getString(5).equals(periods[i])){
                position = i;
                break;
            }
        }
        endPeriod.setSelection(position);
    }

    public void updateEvent(View v){
        db.editData(String.valueOf(dateNum), String.valueOf(eventTitle.getText()),
                String.valueOf(eventDescription.getText()), String.valueOf(startTime.getSelectedItem()), String.valueOf(endTime.getSelectedItem()),
                String.valueOf(startPeriod.getSelectedItem()), String.valueOf(endPeriod.getSelectedItem()));
        Toast.makeText(EditEventActivity.this, "Updated!", Toast.LENGTH_LONG).show();
    }
}
