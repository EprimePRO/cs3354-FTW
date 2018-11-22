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
    DatabaseHelper myDb;
    long dateNum;
    Cursor res;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event);

        //Toast.makeText(DayActivity.this, "Activity loaded", Toast.LENGTH_LONG).show();

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
                boolean isInserted = myDb.insertData(String.valueOf(dateNum), String.valueOf(editTitle.getText()), String.valueOf(editDesc.getText()));
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
