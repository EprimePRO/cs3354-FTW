package com.ftw.calendar_app;

import com.ftw.calendar_app.Event.Event;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

public class EventJUnit {
    private Event tester;
    private Calendar calendar;
    private Date date;

    @Before
    public void setup(){
        tester = new Event();
        calendar = Calendar.getInstance();
        date = new GregorianCalendar(2019, Calendar.AUGUST, 1).getTime();
    }

    // JUnit test method to test getStartDate
    @Test
    public void testStart(){
        tester.setStartDate(date.getTime());
        calendar.setTime(date);
        assertEquals(calendar, tester.getStartDate());
    }

    // JUnit test method to test getEndDate
    @Test
    public void testEnd(){
        tester.setEndDate(date.getTime());
        calendar.setTime(date);
        assertEquals(calendar, tester.getEndDate());
    }

    // JUnit test method to test getTitle
    @Test
    public void testTitle(){
        tester.setTitle("My Birthday");
        assertEquals("My Birthday", tester.getTitle());
    }

    // JUnit test method to test getDescription
    @Test
    public void testDescription(){
        tester.setDescription("Today is the day I was born");
        assertEquals("Today is the day I was born", tester.getDescription());
    }
}

