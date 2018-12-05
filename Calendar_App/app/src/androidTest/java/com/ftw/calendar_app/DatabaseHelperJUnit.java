package com.ftw.calendar_app;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import com.ftw.calendar_app.Database.DatabaseHelper;
import com.ftw.calendar_app.Event.Event;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DatabaseHelperJUnit {
    private DatabaseHelper tester;
    private Context context = InstrumentationRegistry.getTargetContext();
    Event editEvent, editEvent2, addEvent, deleteEvent;

    @Before
    public void setup(){
        tester = new DatabaseHelper(context);
    }

    // JUnit test method to test insertData
    @Test
    public void testInsert(){
        addEvent = new Event();
        addEvent.setTitle("Adding Title");
        assertTrue("Adding Event", tester.insertData(addEvent));
        assertEquals("Adding Title", addEvent.getTitle());
    }

    // JUnit test method to test editData
    @Test
    public void testEdit1(){
        editEvent = new Event();
        editEvent.setTitle("Default Dance");
        tester.insertData(editEvent);
        assertEquals("Default Dance", editEvent.getTitle());

        editEvent.setTitle("Orange Justice");
        assertEquals("Edit Event",1, tester.editData(editEvent));
        assertEquals("Orange Justice", editEvent.getTitle());
    }

    // JUnit test method to test editData
    @Test
    public void testEdit2(){
        editEvent2 = new Event();
        assertEquals("Edit Event",0, tester.editData(editEvent2));
    }

    // JUnit test method to test deleteEvent
    @Test
    public void testDeleted(){
        deleteEvent = new Event();
        deleteEvent.setTitle("You should not see this");
        tester.insertData(deleteEvent);

        assertTrue("Deleting Event", tester.deleteEvent(deleteEvent.getDatabaseID()));
    }
}
