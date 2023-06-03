package com.example.mindful.service.appointment.data;

import com.example.mindful.service.calender.Date;

public class PersonalInfo {

    private Date dob;

    public PersonalInfo() {
        dob = new Date();
    }

    public Date getDob() {
        return dob;
    }
}
