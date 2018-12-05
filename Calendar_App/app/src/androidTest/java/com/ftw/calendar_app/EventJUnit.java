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

    @Before
    public void setup(){
        tester = new Event();
        calendar = Calendar.getInstance();
    }

    // JUnit test method to test getStartDate
    @Test
    public void testStart(){
        tester.setStartDate(2019,8,1);
        Date date = new GregorianCalendar(2019, Calendar.AUGUST, 1).getTime();
        calendar.setTime(date);
        assertEquals(calendar, tester.getStartDate());
    }

    // JUnit test method to test getEndDate
    /*@Test
    public void testEnd(){
        editEvent = new Event();
        editEvent.setTitle("Default Dance");
        tester.insertData(editEvent);
        assertEquals("Default Dance", editEvent.getTitle());

        editEvent.setTitle("Orange Justice");
        assertEquals("Edit Event",1, tester.editData(editEvent));
        assertEquals("Orange Justice", editEvent.getTitle());
    }

    // JUnit test method to test getTitle
    @Test
    public void testTitle(){
        editEvent2 = new Event();
        assertEquals("Edit Event",0, tester.editData(editEvent2));
    }

    // JUnit test method to test getDescription
    @Test
    public void testDescription(){
        deleteEvent = new Event();
        deleteEvent.setTitle("You should not see this");
        tester.insertData(deleteEvent);

        assertTrue("Deleting Event", tester.deleteEvent(deleteEvent.getDatabaseID()));
    }*/
}

