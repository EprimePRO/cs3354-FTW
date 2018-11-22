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

    TextView eventTitle, eventDescription;
    DatabaseHelper db;
    Long dateNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        db = new DatabaseHelper(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dateNum = intent.getLongExtra("dateLong", Calendar.getInstance().getTimeInMillis());
        String eventName = intent.getStringExtra("event");
        Cursor cursor = db.getEvent(eventName, String.valueOf(dateNum));

        eventTitle = (TextView) findViewById(R.id.eventTitle);
        eventDescription = (TextView) findViewById(R.id.eventDescription);
        cursor.moveToNext();
        eventTitle.setText(cursor.getString(0));
        eventDescription.setText(cursor.getString(1));


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
    }

}
