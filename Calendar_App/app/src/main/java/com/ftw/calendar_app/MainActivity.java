package com.ftw.calendar_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    CalendarView calendarView;
    TextView myDate;
    ListView events;
    String date;

    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarView = findViewById(R.id.calendarView);
        final Calendar calendar = Calendar.getInstance();
        myDate = findViewById(R.id.myDate);


        int m = calendar.get(calendar.MONTH);
        int d = calendar.get(calendar.DAY_OF_MONTH);
        int y = calendar.get(calendar.YEAR);

        date = (m+1) + "/" + d + "/" + y;

        myDate.setText(date);

        //Set first day of the week to Sunday
        calendarView.setFirstDayOfWeek(1);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                Intent intent = new Intent(MainActivity.this, DayActivity.class);


                String date = (month+1) + "/" + dayOfMonth + "/" + year;
                myDate.setText(date);
                long dateNum = calendarView.getDate();

                try {
                    dateNum = new SimpleDateFormat("MM/dd/yyyy").parse(date).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

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
        //long dateNumber = getIntent().getLongExtra("longDate", Calendar.getInstance().getTimeInMillis());
        long dateNumber = getIntent().getLongExtra("longDate", 0);

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
