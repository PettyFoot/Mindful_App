package com.example.mindful.service.calender;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;

import java.time.LocalDate;
import java.util.Calendar;

public class Date {

    private int month, day, year, dayOfWeek;;
    private String monthS, dayS, yearS, dayOfWeekS;

    private String TAG = "Date";

    public Date(){

    }

    public Date(int month, int day, int year, int dayOfWeek) {

        Log.d(TAG, "month: " + month);
        // Log.d(TAG, "day: " + day);
        //Log.d(TAG, "day of week: " + dayOfWeek);

        setDate(month, day, year, dayOfWeek);

    }

    //String form of parameterized constructor
    //for now ensure to only pass in the numeric string of these values (i.e "1" for january, "3" for wednesday
    //@param String - month string (1-12)
    //@param String - day string (1-31)
    //@param String - year string (all A.D, no B.C)
    //@param String - day of week (1-7, monday = 1, sunday = 7)
    public Date(String monthS, String dayS, String yearS, String dayOfWeekS) {

        setDate(monthS, dayS, yearS, dayOfWeekS);
    }

    /**
     * sets date based off string values of a date. Will store string version and convert and store as int as well.
     *
     * @param monthS        value between 1-12 (enter as "1" not "jan", or "january" etc.)
     * @param dayS          value between 1-31
     * @param yearS         no B.C just A.D
     * @param dayOfWeekS    value between 1-7, monday = 1, sunday = 7 (enter as "1" not "mon", or "monday" etc.)
     *
     * TODO: make overload that doesn't take dayOfWeek and uses month, day, year to determine day of week inside method
     */
    public void setDate(String monthS, String dayS, String yearS, String dayOfWeekS){

        this.monthS = monthS;
        this.dayS = dayS;
        this.yearS = yearS;
        this.dayOfWeekS = dayOfWeekS;

        convertDateData(false);

    }
    //Setting date via integer values (month = [1, 12], date = [1-31] etc.)
    //Converts integer param values to string value as the members of this class are String types

    /**
     * sets date based off int values of a date. Will store int version and convert and store as string as well.
     *
     * @param month           value between 1-12
     * @param day             value between 1-31
     * @param year            no B.C just A.D
     * @param dayOfWeek       value between 1-7, monday = 1, sunday = 7
     *
     */
    public void setDate(int month, int day, int year, int dayOfWeek){
        this.month = month;
        this.day = day;
        this.year = year;
        this.dayOfWeek = dayOfWeek;

        convertDateData(true);
    }

    public void setDate(int month, int day, int year){
        this.month = month;
        this.day = day;
        this.year = year;
        LocalDate dateInMonth = LocalDate.of(year, month, day);
        this.dayOfWeek = dateInMonth.getDayOfWeek().getValue();


        convertDateData(true);
    }

    /**
     *  create an instance of a DatePickerDialog that when date is selected will capture data in this Date object.
     *  sets initial date of DatePickerDialog to the current date (could make this more customizable by adding an overload with a date param)
     *
     * @param context         context for the datePickerDialog constructor
     * @return                a DatePickerDialog object that when selected will set this date's values onDateSetListener cb
     *
     */
    public DatePickerDialog CreateDatePicker(Context context){

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        //will initialize with todays date.
        // Create a DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,// do i need to do context validation here? look into it
                (view, year1, month1, dayOfMonth) -> {
                    //this wont be called
                },
                year,
                month,
                day
        );

        return datePickerDialog;
    }

    /**
     * converts the integer value of the day of the week (1-7) into a string value (monday, tuesday etc.)
     *
     * @param dayOfWeek         day of week integer value 1 = monday, 7 = sunday
     *
     */
    private void setDayOfWeek(int dayOfWeek){
        switch (dayOfWeek){
            case 1:
                dayOfWeekS = "Monday";
                break;
            case 2:
                dayOfWeekS = "Tuesday";
                break;
            case 3:
                dayOfWeekS = "Wednesday";
                break;
            case 4:
                dayOfWeekS = "Thursday";
                break;
            case 5:
                dayOfWeekS = "Friday";
                break;
            case 6:
                dayOfWeekS = "Saturday";
                break;
            case 7:
                dayOfWeekS = "Sunday";
                break;
            default:
                dayOfWeekS = "error";
                Log.d(TAG, "private void setDayOfWeek(int dayOfWeek) - reached default case, check input value must be between [1,7]");
                break;
        }
    }

    /**
     * converts the string value of the day of the week (monday, tuesday etc.) into a integer value (1-7)
     *
     * @param dayOfWeek         day of week string value "monday" = 1, "sunday" = 7
     *
     */
    private void setDayOfWeek(String dayOfWeek){

        String lower = dayOfWeek.toLowerCase();

        switch (lower){
            case "monday":
                this.dayOfWeek = 1;
                break;
            case "tuesday":
                this.dayOfWeek = 2;
                break;
            case "wednesday":
                this.dayOfWeek = 3;
                break;
            case "thursday":
                this.dayOfWeek = 4;
                break;
            case "friday":
                this.dayOfWeek = 5;
                break;
            case "saturday":
                this.dayOfWeek = 6;
                break;
            case "sunday":
                this.dayOfWeek = 7;
                break;
            default:
                Log.d(TAG, "private void setDayOfWeek(String dayOfWeek) - default case reached, check spelling of day of week. only monday-sunday is allowed");
                this.dayOfWeek = 0;
                break;
        }

    }


    /** GETTERS **/

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getYear() {
        return year;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public String getMonthS() {
        return monthS;
    }

    public String getDayS() {
        return dayS;
    }

    public String getYearS() {
        return yearS;
    }

    public String getDayOfWeekS() {
        return dayOfWeekS;
    }


    /** UTILITY **/

    /**
     * sets string data members from the int data members or the other way around.
     * assumes the data being converted is set on their respective members.
     * has data validation, and type error detection
     *
     * @param fromInt          true if the data is being converted from int to string, false the other way
     *
     */
    private void convertDateData(boolean fromInt){
        if(fromInt){
            this.monthS ="";
            this.dayS="";
            this.yearS="";
            this.dayOfWeekS="";

            try {
                if(month < 13 && month > 0){
                    this.monthS = String.valueOf(month);
                    // Log.d(TAG, "month is not between 1-12 value is:  " + String.valueOf(month));
                }

                if(day < 32 && day > 0){
                    this.dayS = String.valueOf(day);
                    // Log.d(TAG, "day is not between 1-31 value is:  " + String.valueOf(day));
                }

                if(dayOfWeek < 8 && dayOfWeek > 0){
                    setDayOfWeek(dayOfWeek);
                    // Log.d(TAG, "day of week is not between 1-7 value is:  " + String.valueOf(dayOfWeek));
                }

                this.yearS = String.valueOf(year);
            }catch (Exception e){
                Log.d(TAG, "private void setData(boolean) - error setting string data members from int mirrors " + e);
            }



        }else{

            try {
                //This is an issue because it is not very flexible
                //If method receives "jan" or "January" instead of "1" it will throw an error and provide unintentional results
                this.month = Integer.parseInt(monthS);
                this.day = Integer.parseInt(dayS);
                this.year = Integer.parseInt(yearS);
                setDayOfWeek(dayOfWeekS);
            }catch (Exception e){
                Log.d(TAG, "private void setData(boolean) - error setting int data members from string mirrors " + e);
            }


        }
    }

    public String printDateString(){
        String dateString = monthS + " " + dayS + " " + yearS + " " + dayOfWeekS;
        Log.d(TAG, dateString);

        return dateString;
    }
}
