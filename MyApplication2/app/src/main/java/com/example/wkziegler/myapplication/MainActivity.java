package com.example.wkziegler.myapplication;

import android.app.usage.UsageEvents;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    CalendarView calendarView;
    TextView myDate;
    ListView events;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarView = findViewById(R.id.calendarView);
        Calendar calendar = Calendar.getInstance();
        myDate = findViewById(R.id.myDate);

        //calendarView.setDate(Calendar.getInstance().getTimeInMillis(),false,true);

        //events = findViewById(R.id.ctx);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(null);



        int m = calendar.get(calendar.MONTH);
        int d = calendar.get(calendar.DAY_OF_MONTH);
        int y = calendar.get(calendar.YEAR);

        date = (m+1) + "/" + d + "/" + y;

        myDate.setText(date);
        calendarView.setFirstDayOfWeek(1);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                Intent intent = new Intent(MainActivity.this, EventActivity.class);


                String date = (month+1) + "/" + dayOfMonth + "/" + year;
                myDate.setText(date);

                long dateNum = calendarView.getDate();

                intent.putExtra("date", date);
                intent.putExtra("longDate", dateNum);
                Log.d("LONGDATE1", dateNum + "");
                startActivity(intent);
            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        calendarView = findViewById(R.id.calendarView);

        String passedDate = getIntent().getStringExtra("date");
        long dateNumber = getIntent().getLongExtra("longDate", Calendar.getInstance().getTimeInMillis());

        Log.d("RESUME", dateNumber + "");

        if(passedDate!=null && dateNumber > 0){
            myDate.setText(passedDate);
            calendarView.setDate(dateNumber);
        }
        else if (dateNumber == 0) {
            calendarView.setDate(Calendar.getInstance().getTimeInMillis());
        }

    }
}
