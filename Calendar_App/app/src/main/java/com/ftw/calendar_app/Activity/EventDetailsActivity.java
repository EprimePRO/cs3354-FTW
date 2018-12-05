package com.ftw.calendar_app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ftw.calendar_app.Database.DatabaseHelper;
import com.ftw.calendar_app.Event.Event;
import com.ftw.calendar_app.R;

public class EventDetailsActivity extends AppCompatActivity {

    TextView eventTitle, eventDescription, startTime, endTime;
    View colorView;
    DatabaseHelper db;
    int eventID;

    final int EVENT_MODIFIED = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        db = new DatabaseHelper(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        eventTitle = findViewById(R.id.eventTitle);
        eventDescription = findViewById(R.id.eventDescription);
        startTime = findViewById(R.id.startTime);
        endTime = findViewById(R.id.endTime);
        colorView = findViewById(R.id.colorView);


        eventID = intent.getIntExtra("event", 0);

        setEventInfo(eventID);

    }

    public void setEventInfo(int eventID){
        Event event = db.getEvent(eventID);

        if(event!= null){
            eventTitle.setText(event.getTitle());
            eventDescription.setText(event.getDescription());
            String start = event.getStartDateMMDDYY()+ " at "+event.getStartTime();
            String end = event.getEndDateMMDDYY()+ " at "+event.getEndTime();
            startTime.setText(start);
            endTime.setText(end);
            colorView.setBackgroundResource(event.getColor());
        }
    }

    public void editEvent(View v){
        //db.editData(id, title, description, startTime, endTime, startPeriod, endPeriod);
        Intent intent = new Intent(EventDetailsActivity.this, AddModifyActivity.class);
        intent.putExtra("event", eventID);
        startActivityForResult(intent, EVENT_MODIFIED);
    }

    public void deleteEvent(View v){
        Boolean deleted = db.deleteEvent(eventID);
        if(deleted){
            Toast.makeText(EventDetailsActivity.this, "Event deleted!", Toast.LENGTH_LONG).show();

            Intent returnToDayMain = new Intent(EventDetailsActivity.this, MainActivity.class);
            startActivity(returnToDayMain);

            this.finish();

        }else{
            Toast.makeText(EventDetailsActivity.this, "Deletion Failed!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == EVENT_MODIFIED) {
            if(resultCode==RESULT_OK){
                setEventInfo(eventID);
            }
        }
    }

}
