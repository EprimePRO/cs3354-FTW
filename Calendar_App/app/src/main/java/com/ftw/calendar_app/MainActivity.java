package com.ftw.calendar_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    
    //log Tag
    private static final String TAG = "MainActivity";

    CalendarView calendarView;

    TextView myDate;
    String date;
    Calendar androidCalendarDate;

    DatabaseHelper myDb;

    private RecyclerView mRecyclerView;
    private RecyclerViewEventAdapter mRecyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ArrayList<Event> mEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //load Database
        myDb = new DatabaseHelper(this);

        calendarView = findViewById(R.id.calendarView);
        final Calendar calendar = Calendar.getInstance();
        myDate = findViewById(R.id.myDate);


        int m = calendar.get(Calendar.MONTH);
        int d = calendar.get(Calendar.DAY_OF_MONTH);
        int y = calendar.get(Calendar.YEAR);

        date = (m+1) + "/" + d + "/" + y;

        myDate.setText(date);

        //Set first day of the week to Sunday
        calendarView.setFirstDayOfWeek(1);

        mEvents = getmEventsOnDate();
        initRecyclerView();

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

    ArrayList<Event> getmEventsOnDate(){
        Calendar cal = Calendar.getInstance();
        ArrayList<Event> events = new ArrayList<>();
        events.add(new Event(cal.getTime(), cal.getTime(), "Title", ""));
        return events;
    }

    void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerView");
        mRecyclerView = findViewById(R.id.eventList);
        mRecyclerAdapter = new RecyclerViewEventAdapter(mEvents, this);

        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
