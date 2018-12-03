package com.ftw.calendar_app;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DayActivity extends AppCompatActivity {
    TextView editTitle, editDesc, showDate;
    Button addEventButton, setStartTimeButton,
            setEndTimeButton, setStartDateButton, setEndDateButton;
    DatabaseHelper myDb;
    Event event;
    long dateNum;
    Cursor res;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event);

        //Toast.makeText(DayActivity.this, "Activity loaded", Toast.LENGTH_LONG).show();
        //load database helper
        myDb = new DatabaseHelper(this);

        //load views
        editTitle = findViewById(R.id.eventTextTitle);
        editDesc = findViewById(R.id.eventTextDesc);
        setStartDateButton = findViewById(R.id.setStartDate);
        setStartTimeButton = findViewById(R.id.setStartTime);
        setEndDateButton = findViewById(R.id.setEndDate);
        setEndTimeButton = findViewById(R.id.setEndTime);

        addEventButton = findViewById(R.id.addEventButton);

        //initialize event
        event = new Event();


        //addData();

        //gets date sent through intent, default value is the current date
        dateNum = getIntent().getLongExtra("longDate", 0);

        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(dateNum);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        event.setStartDate(cal.getTimeInMillis());
        setStartDateButton.setText(event.getStartDateMMDDYY());
        setStartTimeButton.setText(event.getStartTime());

        cal.setTimeInMillis(dateNum);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        event.setEndDate(cal.getTimeInMillis());
        setEndDateButton.setText(event.getEndDateMMDDYY());
        setEndTimeButton.setText(event.getEndTime());
        if(dateNum > 0){
            //showDate.setText(date);
        }

        //call method to display events
        //build array based on result set from sql query
        Cursor res;
        res = myDb.getEventsByDate(event.getDay());
        if (res.getCount() == 0) {
            //show message
            //no events on this date
        }

        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        while (res.moveToNext()) {
            //add all contents to the array
            Map<String, String> mMap = new HashMap<>();
            mMap.put("title", res.getString(0));
            mMap.put("description", res.getString(1));
            list.add(mMap);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_2, new String[] {"title", "description"}, new int[] {android.R.id.text1, android.R.id.text2});

        ListView eventList = findViewById(R.id.eventList);
        eventList.setAdapter(simpleAdapter);

        Log.d("DATENUM", dateNum + "");

        eventList.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id)
            {
                Map value = (Map)adapter.getItemAtPosition(position);
                // assuming string and if you want to get the value on click of list item
                // do what you intend to do on click of listview row
                Intent intent = new Intent(DayActivity.this, EventDetailsActivity.class);
                intent.putExtra("event", (String)value.get("title"));
                intent.putExtra("dateLong", dateNum);
                //intent.putExtra("date", date);
                startActivity(intent);
            }
        });

    }


    public void addData(View v) {
        if(editTitle.getText().toString().isEmpty()){
            Toast.makeText(DayActivity.this, "Title can't be empty!", Toast.LENGTH_LONG).show();
        }else{
            //set title and description for event
            event.setTitle(String.valueOf(editTitle.getText()));
            event.setDescription(String.valueOf(editDesc.getText()));

            boolean isInserted = myDb.insertData(event);
            if (isInserted) {
                Toast.makeText(DayActivity.this, "Event added!", Toast.LENGTH_LONG).show();
                finish();
                startActivity(getIntent());
            }
            else {
                Toast.makeText(DayActivity.this, "Error!, Event not added!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void setStartTime(View v){
        final Calendar c = Calendar.getInstance();
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePicker = new TimePickerDialog(DayActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                event.setStartHour(hourOfDay);
                event.setStartMinute(minute);
                setStartTimeButton.setText(event.getStartTime());
            }
        }, hour, minute, false);
        timePicker.show();
    }

    public void setEndTime(View v){
        final Calendar c = Calendar.getInstance();
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePicker = new TimePickerDialog(DayActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                event.setEndHour(hourOfDay);
                event.setEndMinute(minute);
                setEndTimeButton.setText(event.getEndTime());
            }
        }, hour, minute, false);
        timePicker.show();
    }

    public void setStartDate(View v){
        final Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(DayActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                event.setStartDate(year, month, dayOfMonth);
                setStartDateButton.setText(event.getStartDateMMDDYY());
            }
        }, year, month, dayOfMonth);
        datePicker.show();
    }

    public void setEndDate(View v){
        final Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(DayActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                event.setEndDate(year, month, dayOfMonth);
                setEndDateButton.setText(event.getEndDateMMDDYY());
            }
        }, year, month, dayOfMonth);
        datePicker.show();
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

}
