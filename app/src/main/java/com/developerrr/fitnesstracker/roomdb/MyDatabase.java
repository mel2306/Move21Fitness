package com.developerrr.fitnesstracker.roomdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//Zuweisung dieser als Datenbank und des Benutzers als Tabelle der Datenbank
@Database(entities = {StepTable.class},version = 4,exportSchema = false)

//Klasse Datenbank extends RoomDatabase
public abstract class MyDatabase extends RoomDatabase {
    public abstract StepDao StepDao(); //Interface StepDao

    private static MyDatabase iNSTANCE;

    public  static MyDatabase getiNSTANCE(Context context){
        if(iNSTANCE==null){
            //Erzeugt einen RoomDatabase.Builder für eine dauerhafte Datenbank.
            // Sobald eine Datenbank erstellt ist, sollten Sie einen Verweis auf sie behalten und sie wiederverwenden.
            //context: Gibt den Kontext des einzelnen, globalen Anwendungsobjekts des aktuellen Prozesses zurück.
            iNSTANCE= Room.databaseBuilder(context.getApplicationContext(),MyDatabase.class,"Move21DB")
                    .allowMainThreadQueries() //Deaktiviert die Abfrageprüfung des Hauptthreads für Room.
                    .fallbackToDestructiveMigration()//Ermöglicht es Room, Datenbanktabellen destruktiv neu zu erstellen, wenn
                    // Migrationen, die alte Datenbankschemata in die neueste Schemaversion migrieren würden, nicht gefunden werden.
                    .build(); //Erzeugt die Datenbank und initialisiert sie.

        }
        return iNSTANCE;
    }

}