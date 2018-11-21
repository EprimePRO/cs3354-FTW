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
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

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

        String[] events_array = new String[res.getCount()*2];
        int counter = 0;

        while (res.moveToNext()) {
            //add all contents to the array
            events_array[counter] = res.getString(0);
            events_array[counter+1] = res.getString(1);
            counter+=2;
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, events_array);
        //ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, events_array);

        //must create a List of mappings for this
        //SimpleAdapter simpleAdapter = new SimpleAdapter(this, events_array, android.R.layout.simple_list_item_2, new String[] {"title", "data"}, new int[] {android.R.id.text1, android.R.id.text2});



        ListView eventList = findViewById(R.id.eventList);
        eventList.setAdapter(adapter);
        //eventList.setAdapter(simpleAdapter);
        eventList.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id)
            {
                String value = (String)adapter.getItemAtPosition(position);
                // assuming string and if you want to get the value on click of list item
                // do what you intend to do on click of listview row
                Intent intent = new Intent(DayActivity.this, EventDetailsActivity.class);
                intent.putExtra("event", value);
                startActivity(intent);
            }
        });

        Log.d("DATENUM", dateNum + "");


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
