package com.ftw.calendar_app;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DayActivity extends AppCompatActivity {
    TextView editTitle, editDesc, showDate;
    Button eventButton;
    Spinner startTime, endTime, startPeriod, endPeriod;
    DatabaseHelper myDb;
    long dateNum;
    Cursor res;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event);

        //Toast.makeText(DayActivity.this, "Activity loaded", Toast.LENGTH_LONG).show();

        // Probably a better way to do this but I am too lazy
        String[] times = new String[] {"12:00","12:15","12:30","12:45","1:00","1:15","1:30","1:45","2:00","2:15",
                "2:30","2:45","3:00","3:15","3:30","3:45","4:00","4:15","4:30","4:45","5:00","5:15","5:30","5:45",
                "6:00","6:15","6:30","6:45","7:00","7:15","7:30","7:45","8:00","8:15","8:30","8:45",
                "9:00","9:15","9:30","9:45","10:00","10:15","10:30","10:45","11:00","11:15","11:30","11:45"};
        String[] periods = new String[] {"AM", "PM"};

        startTime = findViewById(R.id.spinnerStartTime);
        endTime = findViewById(R.id.spinnerEndTime);
        startPeriod = findViewById(R.id.spinnerStartPeriod);
        endPeriod = findViewById(R.id.spinnerEndPeriod);

        ArrayAdapter<String> adapterTimes = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,times);
        ArrayAdapter<String> adapterPeriods = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,periods);

        startTime.setAdapter(adapterTimes);
        endTime.setAdapter(adapterTimes);
        startPeriod.setAdapter(adapterPeriods);
        endPeriod.setAdapter(adapterPeriods);

        myDb = new DatabaseHelper(this);

        editTitle = findViewById(R.id.eventTextTitle);
        editDesc = findViewById(R.id.eventTextDesc);

        TextView showDate = findViewById(R.id.showDate);
        eventButton = findViewById(R.id.updateButton);


        addData();

        final String date = getIntent().getStringExtra("date");

        //gets date sent through intent, default value is the current date
        dateNum = getIntent().getLongExtra("longDate", Calendar.getInstance().getTimeInMillis());
        if(dateNum > 0){
            showDate.setText(date);
        }

        //call method to display events
        //build array based on result set from sql query
        Cursor res;
        res = myDb.getEventsByDate(String.valueOf(dateNum));
        if (res.getCount() == 0) {
            //show message
            //no events on this date
        }

        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        while (res.moveToNext()) {
            //add all contents to the array
            Map mMap = new HashMap();
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
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });

    }

    public void addData() {
        eventButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                boolean isInserted = myDb.insertData(String.valueOf(dateNum), String.valueOf(editTitle.getText()),
                        String.valueOf(editDesc.getText()), String.valueOf(startTime.getSelectedItem()), String.valueOf(endTime.getSelectedItem()),
                        String.valueOf(startPeriod.getSelectedItem()), String.valueOf(endPeriod.getSelectedItem()));
                if (isInserted) {
                    Toast.makeText(DayActivity.this, "Event added!", Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(getIntent());
                }
                else {
                    Toast.makeText(DayActivity.this, "Error!, Event not added!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();


    }


}
