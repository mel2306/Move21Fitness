package com.developerrr.fitnesstracker.roomdb;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity //Markiert eine Klasse als eine Entität. Diese Klasse hat eine zugehörige SQLite-Tabelle in der Datenbank.
public class StepTable {

    @PrimaryKey //Primärschlüssel

    @ColumnInfo(name = "day") //Spalteninfo Tag als Zahl
    public int day;

    @ColumnInfo(name = "dayname") //Spalteninfo Tag als Name
    public String dayName;


    @ColumnInfo(name = "month") //Spalteninfo Monat als Zahl
    public int month;

    @ColumnInfo(name = "monthName") //Spalteninfo Monat als Name
    public String monthName;

    @ColumnInfo(name = "year") //Spalteninfo Jahr als Zahl
    public int year;

    @ColumnInfo(name = "steps")//Spalteninfo Schritte als Zahl
    public int steps;
}
