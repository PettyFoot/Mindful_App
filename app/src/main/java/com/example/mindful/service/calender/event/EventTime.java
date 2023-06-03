package com.example.mindful.service.calender.event;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class EventTime {

    private LocalTime startTime, endTime;
    private boolean startTimeIsPM, endTimeIsPM;

    public EventTime(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;

        if(checkTimePM(this.startTime)){
            this.startTime.withHour(startTime.getHour()-12);
            setStartTimeIsPM(true);
        }else{
            setStartTimeIsPM(false);
        }

        if(checkTimePM(this.endTime)){
            this.endTime.withHour(startTime.getHour()-12);
            setEndTimeIsPM(true);
        }else{
            setEndTimeIsPM(false);
        }
    }


    public String formatTime(String format, boolean formatStartTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        String formattedTime;
        if(formatStartTime){
            formattedTime = startTime.format(formatter);
        }else{
            formattedTime = endTime.format(formatter);
        }

        return formattedTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public boolean isStartTimeIsPM() {
        return startTimeIsPM;
    }

    public void setStartTimeIsPM(boolean startTimeIsPM) {
        this.startTimeIsPM = startTimeIsPM;
    }

    public boolean isEndTimeIsPM() {
        return endTimeIsPM;
    }

    public void setEndTimeIsPM(boolean endTimeIsPM) {
        this.endTimeIsPM = endTimeIsPM;
    }

    private boolean checkTimePM(LocalTime time){
        if(time.getHour()>12){
            return true;
        }
        return false;
    }
}
