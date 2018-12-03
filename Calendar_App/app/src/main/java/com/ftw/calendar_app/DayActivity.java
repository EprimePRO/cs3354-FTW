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
import android.widget.EditText;
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
    EditText editTitle, editDesc, showDate;
    Button addEventButton, setStartTimeButton,
            setEndTimeButton, setStartDateButton, setEndDateButton;
    DatabaseHelper myDb;
    Event event;
    long dateNum;
    int id;
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

        id = getIntent().getIntExtra("event", -1);

        if(id>=0){

            addEventButton.setText(getString(R.string.button_update));
            event = myDb.getEvent(id);

            editTitle.setText(event.getTitle());
            editDesc.setText(event.getDescription());
            setStartDateButton.setText(event.getStartDateMMDDYY());
            setStartTimeButton.setText(event.getStartTime());
            setEndDateButton.setText(event.getEndDateMMDDYY());
            setEndTimeButton.setText(event.getEndTime());

        }else {
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
        }

    }


    public void addData(View v) {
        if(id>=0) {
            if (editTitle.getText().toString().isEmpty()) {
                Toast.makeText(DayActivity.this, "Title can't be empty!", Toast.LENGTH_LONG).show();
            } else {
                //set title and description for event
                event.setTitle(String.valueOf(editTitle.getText()));
                event.setDescription(String.valueOf(editDesc.getText()));

                int numModified = myDb.editData(event);
                if (numModified == 1) {
                    Toast.makeText(DayActivity.this, "Event updated!", Toast.LENGTH_LONG).show();
                    Intent returnIntent = new Intent();
                    setResult(RESULT_OK, returnIntent);
                    finish();
                } else {
                    Toast.makeText(DayActivity.this, "Error!, Event not modified!", Toast.LENGTH_LONG).show();
                }
            }
        }else{
            if (editTitle.getText().toString().isEmpty()) {
                Toast.makeText(DayActivity.this, "Title can't be empty!", Toast.LENGTH_LONG).show();
            } else {
                //set title and description for event
                event.setTitle(String.valueOf(editTitle.getText()));
                event.setDescription(String.valueOf(editDesc.getText()));

                boolean isInserted = myDb.insertData(event);
                if (isInserted) {
                    Toast.makeText(DayActivity.this, "Event added!", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(DayActivity.this, "Error!, Event not added!", Toast.LENGTH_LONG).show();
                }
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
