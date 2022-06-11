package com.developerrr.fitnesstracker.models;

import com.developerrr.fitnesstracker.roomdb.StepTable;

import java.util.List;

//Woche mit DB (StepTable)
public class Week {
    List<StepTable> days; //Liste mit Tagen

    public Week(List<StepTable> days) {
        this.days = days;
    } //Tage vergleichen DB

    public List<StepTable> getDays() {
        return days;
    } //Tage zurückliefern aus DB

    public void setDays(List<StepTable> days) {
        this.days = days;
    } //wird nie verwendet
}
