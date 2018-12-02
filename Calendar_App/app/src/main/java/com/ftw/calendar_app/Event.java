package com.ftw.calendar_app;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Event {

    //Start and end, date and time
    private Date startDate;
    private Date endDate;
    //Title and Description
    private String title;
    private String description;

    private Long databaseKey;

    public Event(Date start, Date end, String title, String description){
        startDate = start;
        endDate = end;
        this.title = title;
        this.description = description;
    }

    public Event(Date start, Date end, String title, String description, Long key){
        startDate = start;
        endDate = end;
        this.title = title;
        this.description = description;
        databaseKey = key;
    }

    public String getTime(){
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:m a");
        return timeFormat.format(startDate);
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Long getDatabaseKey() {
        return databaseKey;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDatabaseKey(Long databaseKey) {
        this.databaseKey = databaseKey;
    }
}
