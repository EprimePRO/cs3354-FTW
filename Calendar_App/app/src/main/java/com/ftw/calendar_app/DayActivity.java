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
    Button eventButton, backButton;
    Spinner startTime, endTime;
    DatabaseHelper myDb;
    long dateNum;
    Cursor res;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event);

        //Toast.makeText(DayActivity.this, "Activity loaded", Toast.LENGTH_LONG).show();

        // Probably a better way to do this but I am too lazy
        String[] times = new String[] {"12:00AM","12:15AM","12:30AM","12:45AM","1:00AM","1:15AM","1:30AM","1:45AM","2:00AM","2:15AM",
                "2:30AM","2:45AM","3:00AM","3:15AM","3:30AM","3:45AM","4:00AM","4:15AM","4:30AM","4:45AM","5:00AM","5:15AM","5:30AM","5:45AM",
                "6:00AM","6:15AM","6:30AM","6:45AM","7:00AM","7:15AM","7:30AM","7:45AM","8:00AM","8:15AM","8:30AM","8:45AM",
                "9:00AM","9:15AM","9:30AM","9:45AM","10:00AM","10:15AM","10:30AM","10:45AM","11:00AM","11:15AM","11:30AM","11:45AM",
                "12:00PM","12:15PM","12:30PM","12:45PM","1:00PM","1:15PM","1:30PM","1:45PM","2:00PM","2:15PM",
                "2:30PM","2:45PM","3:00PM","3:15PM","3:30PM","3:45PM","4:00PM","4:15PM","4:30PM","4:45PM","5:00PM","5:15PM","5:30PM","5:45PM",
                "6:00PM","6:15PM","6:30PM","6:45PM","7:00PM","7:15PM","7:30PM","7:45PM","8:00PM","8:15PM","8:30PM","8:45PM",
                "9:00PM","9:15PM","9:30PM","9:45PM","10:00PM","10:15PM","10:30PM","10:45PM","11:00PM","11:15PM","11:30PM","11:45PM"};

        startTime = findViewById(R.id.spinnerStart);
        endTime = findViewById(R.id.spinnerEnd);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,times);
        startTime.setAdapter(adapter);
        endTime.setAdapter(adapter);

        myDb = new DatabaseHelper(this);

        editTitle = findViewById(R.id.eventTextTitle);
        editDesc = findViewById(R.id.eventTextDesc);

        TextView showDate = findViewById(R.id.showDate);
        eventButton = findViewById(R.id.addEvent);


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
                        String.valueOf(editDesc.getText()), String.valueOf(startTime.getSelectedItem()), String.valueOf(endTime.getSelectedItem()));
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
