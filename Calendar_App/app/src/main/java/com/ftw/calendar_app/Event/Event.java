package com.ftw.calendar_app.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Event {

    //Start and end, date and time
    private Calendar startDate;
    private Calendar endDate;
    //Title and Description
    private String title;
    private String description;

    private int color;

    private int databaseID;

    public Event(){
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
        title = "";
        description = "";
    }

    public Event(Calendar start, Calendar end, String title, String description){
        startDate = start;
        endDate = end;
        this.title = title;
        this.description = description;
    }

    public Event(Calendar start, Calendar end, String title, String description, int key, int color){
        startDate = start;
        endDate = end;
        this.title = title;
        this.description = description;
        databaseID = key;
        this.color = color;
    }

    public String getStartTime(){
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        return timeFormat.format(startDate.getTime());
    }

    public String getEndTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        return timeFormat.format(endDate.getTime());
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getDatabaseID() {
        return databaseID;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDatabaseID(int databaseID) {
        this.databaseID = databaseID;
    }

    public String getDay() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy.MM.dd");
        return timeFormat.format(startDate.getTime());
    }

    public void setStartHour(int hourOfDay) {
        startDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
    }

    public void setStartMinute(int minute) {
        startDate.set(Calendar.MINUTE, minute);
    }

    public void setEndHour(int hourOfDay) {
        endDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
    }

    public void setEndMinute(int minute) {
        endDate.set(Calendar.MINUTE, minute);
    }

    public void setStartDate(int year, int month, int dayOfMonth) {
        startDate.set(Calendar.YEAR, year);
        startDate.set(Calendar.MONTH, month);
        startDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    }

    public void setEndDate(int year, int month, int dayOfMonth) {
        endDate.set(Calendar.YEAR, year);
        endDate.set(Calendar.MONTH, month);
        endDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    }

    public String getStartDateMMDDYY() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        return dateFormat.format(startDate.getTime());
    }

    public String getEndDateMMDDYY() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        return dateFormat.format(endDate.getTime());
    }

    public void setStartDate(long dateNum) {
        startDate.setTimeInMillis(dateNum);
    }

    public void setEndDate(long timeInMillis) {
        endDate.setTimeInMillis(timeInMillis);
    }
}
