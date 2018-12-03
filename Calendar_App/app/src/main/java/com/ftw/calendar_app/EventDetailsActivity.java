package com.ftw.calendar_app;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class EventDetailsActivity extends AppCompatActivity {

    TextView eventTitle, eventDescription, startTime, endTime;
    DatabaseHelper db;
    int eventID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        db = new DatabaseHelper(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        eventTitle = (TextView) findViewById(R.id.eventTitle);
        eventDescription = (TextView) findViewById(R.id.eventDescription);
        startTime = (TextView) findViewById(R.id.startTime);
        endTime = (TextView) findViewById(R.id.endTime);


        eventID = intent.getIntExtra("event", 0);
        //Cursor cursor = db.getEvent(eventName, String.valueOf(dateNum));

        Event event = db.getEvent(eventID);

        if(event!= null){
            eventTitle.setText(event.getTitle());
            eventDescription.setText(event.getDescription());
            String start = event.getStartDateMMDDYY()+ " at "+event.getStartTime();
            String end = event.getEndDateMMDDYY()+ " at "+event.getEndTime();
            startTime.setText(start);
            endTime.setText(end);
        }

        /*cursor.moveToNext();
        eventTitle.setText(cursor.getString(0));
        eventDescription.setText(cursor.getString(1));
        startTime.setText(cursor.getString(2) + " " + cursor.getString(4));
        endTime.setText(cursor.getString(3) + " " + cursor.getString(5));*/

    }

    /*public void editEvent(View v){
        //db.editData(id, title, description, startTime, endTime, startPeriod, endPeriod);
        Intent intent = new Intent(EventDetailsActivity.this, EditEventActivity.class);
        intent.putExtra("dateLong", dateNum);
        intent.putExtra("event", eventName);
        startActivity(intent);
    }

    public void deleteEvent(View v){
        Boolean deleted = db.deleteEvent(eventTitle.getText().toString(), String.valueOf(dateNum));
        if(deleted){
            Toast.makeText(EventDetailsActivity.this, "Event deleted!", Toast.LENGTH_LONG).show();
            finish();

            Intent returnToDayView = new Intent(EventDetailsActivity.this, DayActivity.class);
            returnToDayView.putExtra("longDate", dateNum);
            returnToDayView.putExtra("date", getIntent().getStringExtra("date"));
            startActivity(returnToDayView);
        }else{
            Toast.makeText(EventDetailsActivity.this, "Deletion Failed!", Toast.LENGTH_LONG).show();
        }
    }*/

}
