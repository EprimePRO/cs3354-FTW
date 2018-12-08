package com.ftw.calendar_app;

import com.ftw.calendar_app.Event.Event;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EventJUnitSteven {

    Event event;

    @Before
    public void setUp() {
        event = new Event();
    }

    @Test

    public void testGetStartTime() {
        int hourOfDay = 17;
        int minute = 55;
        String expected = "05:55 PM";
        event.setStartHour(hourOfDay);
        event.setStartMinute(minute);

        assertEquals("Checking 'hh:mm a':", expected, event.getStartTime());
    }

    @Test

    public void testGetEndTime() {
        int hourOfDay = 2;
        int minute = 12;
        String expected = "02:12 AM";
        event.setStartHour(hourOfDay);
        event.setStartMinute(minute);

        assertEquals("Checking 'hh:mm a':", expected, event.getStartTime());
    }

}
