package com.ftw.calendar_app;

import com.ftw.calendar_app.Event.Event;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EventJUnitEmanuel {

    Event event;

    @Before
    public void setUp() {
        event = new Event();
    }

    @Test

    public void testGetStartDateMMddyy() {
        long date = 1545717600000L; //in central time
        String expected = "12/25/18";
        event.setStartDate(date);

        assertEquals("Checking start MMddyy", expected, event.getStartDateMMDDYY());
    }

    @Test

    public void testGetEndDateMMddyy() {
        long date = 1544162400000L; //in central time
        String expected = "12/07/18";
        event.setStartDate(date);

        assertEquals("Checking end MMddyy", expected, event.getStartDateMMDDYY());
    }
}
