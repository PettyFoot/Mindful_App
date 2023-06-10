package com.example.mindful.service.appointment.data;

import android.util.Log;
import android.view.View;

import com.example.mindful.service.calender.Date;

import java.util.ArrayList;

public class PersonalInfo {

    private Date dob;

    private String firstName, lastName, addressOne, stateOne, cityOne, zipOne;

    private String TAG = "PersonalInfo";



    public PersonalInfo(Date dob, String firstName, String lastName, String addressOne, String stateOne, String cityOne, String zipOne) {
        this.dob = dob;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressOne = addressOne;
        this.stateOne = stateOne;
        this.cityOne = cityOne;
        this.zipOne = zipOne;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddressOne() {
        return addressOne;
    }

    public void setAddressOne(String addressOne) {
        this.addressOne = addressOne;
    }

    public String getStateOne() {
        return stateOne;
    }

    public void setStateOne(String stateOne) {
        this.stateOne = stateOne;
    }

    public String getCityOne() {
        return cityOne;
    }

    public void setCityOne(String cityOne) {
        this.cityOne = cityOne;
    }

    public String getZipOne() {
        return zipOne;
    }

    public void setZipOne(String zipOne) {
        this.zipOne = zipOne;
    }

    public PersonalInfo() {
        dob = new Date();
    }

    public Date getDob() {
        return dob;
    }

    public void printInfo(){
        Log.d(TAG, "dob " + dob.printDateString());
        Log.d(TAG, "firstName " + firstName);
        Log.d(TAG, "lastName " + lastName);
        Log.d(TAG, "addressOne " + addressOne);
        Log.d(TAG, "stateOne " + stateOne);
        Log.d(TAG, "cityOne " + cityOne);
        Log.d(TAG, "zipOne " + zipOne);
    }
}
