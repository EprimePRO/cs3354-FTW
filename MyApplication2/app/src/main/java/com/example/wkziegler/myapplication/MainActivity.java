package com.example.wkziegler.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    CalendarView calendarView;
    TextView myDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarView = findViewById(R.id.calendarView);
        Calendar calendar = Calendar.getInstance();
        myDate = findViewById(R.id.myDate);


        //set first day to sunday
        int m = calendar.get(calendar.MONTH);
        int d = calendar.get(calendar.DAY_OF_MONTH);
        int y = calendar.get(calendar.YEAR);

        myDate.setText((m+1) + "/" + d + "/" + y);
        calendarView.setFirstDayOfWeek(1);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                String date = (month+1) + "/" + dayOfMonth + "/" + year;
                myDate.setText(date);
            }
        });
    }

}
