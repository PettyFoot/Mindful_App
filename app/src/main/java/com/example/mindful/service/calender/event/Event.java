package com.example.mindful.service.calender.event;

import java.time.LocalDate;

public class Event {

    //Start and end time of the event
    private EventTime eventTime;

    //Right now there is no need for eventEndDate, just wanted to keep system flexible if multiple day
    //event was needed in the future
    private LocalDate eventStartDate, eventEndDate;

    private String eventTitle, eventDescription;


    //Constructor with end date specified
    public Event(EventTime eventTime, LocalDate eventStartDate, LocalDate eventEndDate, String eventTitle, String eventDescription) {
        this.eventTime = eventTime;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
    }

    //Constructor w/out end date specified
    //Defaults to same as start date
    public Event(EventTime eventTime, LocalDate eventStartDate, String eventTitle, String eventDescription) {
        this.eventTime = eventTime;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventStartDate;
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
    }
}
