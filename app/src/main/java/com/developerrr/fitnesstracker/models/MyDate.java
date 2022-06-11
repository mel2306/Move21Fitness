package com.developerrr.fitnesstracker.models;

//Datum
public class MyDate {

    //Deklaration
    String month;
    String day;
    int dayOfMonth;
    int monthOfYear;
    int year;
    CharSequence fullDate;

    //Zuweisung
    public MyDate(String month, String day, int dayOfMonth, int monthOfYear, int year,CharSequence fullDate) {
        this.month = month;
        this.day = day;
        this.dayOfMonth = dayOfMonth;
        this.monthOfYear = monthOfYear;
        this.year = year;
        this.fullDate=fullDate;
    }


    public CharSequence getFullDate() {
        return fullDate;
    } //Ganzes Datum
    public void setFullDate(CharSequence fullDate) {
        this.fullDate = fullDate;
    }//nie verwendet

    public String getMonth() {
        return month;
    } //Monat
    public void setMonth(String month) {
        this.month = month;
    }//nie verwendet

    public String getDay() {
        return day;
    }//Tag (Wochentag)
    public void setDay(String day) {
        this.day = day;
    }//nie verwendet

    public int getDayOfMonth() {
        return dayOfMonth;
    }//Tag im Monat
    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }//nie verwendet

    public int getMonthOfYear() {
        return monthOfYear;
    }//Monat
    public void setMonthOfYear(int monthOfYear) {
        this.monthOfYear = monthOfYear;
    }//nie verwendet

    public int getYear() {
        return year;
    } //Jahr
    public void setYear(int year) {
        this.year = year;
    }//nie verwendet
}
