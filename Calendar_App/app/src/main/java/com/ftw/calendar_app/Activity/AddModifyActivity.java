package com.ftw.calendar_app.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ftw.calendar_app.Database.DatabaseHelper;
import com.ftw.calendar_app.Event.Event;
import com.ftw.calendar_app.R;

import java.util.Calendar;

public class AddModifyActivity extends AppCompatActivity {
    EditText editTitle, editDesc;
    TextView addOrModifyText;
    final String modifyEvent = "Modify Event";
    Button addEventButton, setStartTimeButton,
            setEndTimeButton, setStartDateButton, setEndDateButton;
    DatabaseHelper myDb;
    Event event;
    long dateNum;
    int id;
    Cursor res;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_modify);

        //Toast.makeText(AddModifyActivity.this, "Activity loaded", Toast.LENGTH_LONG).show();
        //load database helper
        myDb = new DatabaseHelper(this);

        //load views
        addOrModifyText = findViewById(R.id.addOrModifyEventText);
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
            addOrModifyText.setText(modifyEvent);
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
                Toast.makeText(AddModifyActivity.this, "Title can't be empty!", Toast.LENGTH_LONG).show();
            } else {
                //set title and description for event
                event.setTitle(String.valueOf(editTitle.getText()));
                event.setDescription(String.valueOf(editDesc.getText()));

                int numModified = myDb.editData(event);
                if (numModified == 1) {
                    Toast.makeText(AddModifyActivity.this, "Event updated!", Toast.LENGTH_LONG).show();
                    Intent returnIntent = new Intent();
                    setResult(RESULT_OK, returnIntent);
                    finish();
                } else {
                    Toast.makeText(AddModifyActivity.this, "Error!, Event not modified!", Toast.LENGTH_LONG).show();
                }
            }
        }else{
            if (editTitle.getText().toString().isEmpty()) {
                Toast.makeText(AddModifyActivity.this, "Title can't be empty!", Toast.LENGTH_LONG).show();
            } else {
                //set title and description for event
                event.setTitle(String.valueOf(editTitle.getText()));
                event.setDescription(String.valueOf(editDesc.getText()));

                boolean isInserted = myDb.insertData(event);
                if (isInserted) {
                    Toast.makeText(AddModifyActivity.this, "Event added!", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(AddModifyActivity.this, "Error!, Event not added!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void setStartTime(View v){
        final Calendar c = Calendar.getInstance();
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePicker = new TimePickerDialog(AddModifyActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
        TimePickerDialog timePicker = new TimePickerDialog(AddModifyActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
        DatePickerDialog datePicker = new DatePickerDialog(AddModifyActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        DatePickerDialog datePicker = new DatePickerDialog(AddModifyActivity.this, new DatePickerDialog.OnDateSetListener() {
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