package com.ftw.calendar_app;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class EventDetailsActivity extends AppCompatActivity {

    TextView eventTitle, eventDescription;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        db = new DatabaseHelper(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String temp = intent.getStringExtra("event");
        Cursor cursor = db.getEventByTitle(temp);

        eventTitle = (TextView) findViewById(R.id.eventTitle);
        eventDescription = (TextView) findViewById(R.id.eventDescription);
        cursor.moveToNext();
        eventTitle.setText(cursor.getString(0));
        eventDescription.setText(cursor.getString(1));


    }

}
