package com.ftw.calendar_app;

import android.content.Intent;
import android.database.Cursor;
import android.nfc.FormatException;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    
    //log Tag
    private static final String TAG = "MainActivity";

    CalendarView calendarView;

    TextView myDate;
    String date;
    Long selectedDate;

    DatabaseHelper myDb;

    private RecyclerView mRecyclerView;
    private RecyclerViewEventAdapter mRecyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ArrayList<Event> mEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setupToolbar
        Toolbar myToolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(myToolbar);

        //load Database
        myDb = new DatabaseHelper(this);

        calendarView = findViewById(R.id.calendarView);
        final Calendar calendar = Calendar.getInstance();


        int m = calendar.get(Calendar.MONTH);
        int d = calendar.get(Calendar.DAY_OF_MONTH);
        int y = calendar.get(Calendar.YEAR);

        date = (m+1) + "/" + d + "/" + y;

        //Set first day of the week to Sunday
        calendarView.setFirstDayOfWeek(1);
        selectedDate = calendarView.getDate();

        date = new SimpleDateFormat("yyyy.MM.dd").format(new Date(selectedDate));
        mEvents = getmEventsOnDate(date);
        initRecyclerView();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {

                selectedDate = calendarView.getDate();
                date = (month+1) + "/" + dayOfMonth + "/" + year;
                try {
                    selectedDate = new SimpleDateFormat("MM/dd/yyyy").parse(date).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                date =  new SimpleDateFormat("yyyy.MM.dd").format(new Date(selectedDate));

                mEvents = getmEventsOnDate(date);

                mRecyclerAdapter.swap(mEvents);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_event) {
            Intent intent = new Intent(MainActivity.this, DayActivity.class);
            intent.putExtra("longDate", selectedDate);
            Log.d(TAG, selectedDate + "");
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    ArrayList<Event> getmEventsOnDate(String date){
        Cursor res = myDb.getEventsByDate(date);
        ArrayList<Event> events = new ArrayList<>();
        while(res.moveToNext()){
            Calendar startDate = Calendar.getInstance();
            Calendar endDate = Calendar.getInstance();

            startDate.setTimeInMillis(res.getLong(2));
            endDate.setTimeInMillis(res.getLong(3));

            Event tempEvent = new Event(startDate, endDate, res.getString(0),
                    res.getString( 1), res.getInt(4));

            events.add(tempEvent);

        }
        return events;
    }

    void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerView");
        mRecyclerView = findViewById(R.id.eventList);
        mRecyclerAdapter = new RecyclerViewEventAdapter(mEvents, this);

        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }


}
