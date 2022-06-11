package com.developerrr.fitnesstracker.roomdb;


import androidx.room.Dao; //Markiert die Klasse als Datenzugriffsobjekt.
//Data Access Objects sind die Hauptklassen, in denen Sie Ihre Datenbankinteraktionen definieren. Sie können eine Vielzahl von Abfragemethoden enthalten.
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;//Eine Reihe von Konfliktbehandlungsstrategien für verschiedene Dao-Methoden.
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao //Data Access Objects
public interface StepDao {

    @Query("Select * from StepTable") //Markiert eine Methode in einer Dao annotierten Klasse als Abfragemethode
    List<StepTable> getAllSteps();

    @Insert(onConflict = OnConflictStrategy.REPLACE)//Markiert eine Methode in einer Dao annotierten Klasse als Insert-Methode.
    void insertStep(StepTable... steps);

    @Query("Select * from steptable WHERE day LIKE :day")
    StepTable getStepsByDay(int day);//nie verwendet



}
