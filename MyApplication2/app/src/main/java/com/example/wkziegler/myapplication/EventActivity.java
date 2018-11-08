package com.example.wkziegler.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class EventActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event);

        TextView textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);

        final String date = getIntent().getStringExtra("date");

        //gets date sent through intent, default value is the current date
        final long dateNum = getIntent().getLongExtra("longDate", Calendar.getInstance().getTimeInMillis());
        if(dateNum > 0){
            textView.setText(date);
        }

        Log.d("DATENUM", dateNum + "");

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(EventActivity.this, MainActivity.class);
                intent.putExtra("date", date);
                intent.putExtra("longDate", dateNum);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}
