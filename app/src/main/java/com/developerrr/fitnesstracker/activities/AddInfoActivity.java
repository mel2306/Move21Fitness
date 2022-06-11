package com.developerrr.fitnesstracker.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.developerrr.fitnesstracker.R;

import java.util.ArrayList;
import java.util.List;

public class AddInfoActivity extends AppCompatActivity {

    //Erstellen eines Spinners
    AppCompatSpinner genderSpinner;
    //Variablen für Eingabetextfeld
    EditText et_username,et_age,et_height,et_weight;
    //Gendervariable für ausgewähltes Geschlecht
    private String selectedGender;
    //Speichern
    Button saveBtn;

    //ZurückButton mit Bild
    ImageButton backArrow;

    //Verknüpfung mit Activity_Add_info (Layout und Funktionen)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info);

        InitializeViews();

    }

    //Zuweisung von eingegebenen Werten zu entsprechenden Variablen
    private void InitializeViews() {
        genderSpinner=findViewById(R.id.ed_infogender);
        et_age=findViewById(R.id.ed_infoage);
        et_height=findViewById(R.id.ed_infoheight);
        et_weight=findViewById(R.id.ed_infoweight);
        et_username=findViewById(R.id.et_infoname);
        saveBtn=findViewById(R.id.info_submitBtn);

        backArrow=findViewById(R.id.backArrow);

        //Was passiert bei Klick auf ZurückPfeil? --> beendet Seite und geht zu den Einstellungen zurück
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //setup Spinner wird aufgerufen (Genderauswahl)
        setupSpinner();

        //Was passiert, wenn man auf Speichern klickt?
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Wenn alle Felder korrekt ausgefüllt sind --> Daten werden gespeichert
                //Methodenaufruf notEmptyFields (überprüft, ob Wert leer ist)
                if(!notEmptyFields()){
                    if(Double.valueOf(et_age.getText().toString()) < 99 && Double.valueOf(et_age.getText().toString()) > 5 ){
                        if(Double.valueOf(et_height.getText().toString()) > 45 && Double.valueOf(et_height.getText().toString()) < 250){
                            if(Double.valueOf(et_weight.getText().toString().trim()) > 10 && Double.valueOf(et_weight.getText().toString().trim()) < 200){
                                //Methodenaufruf, um Daten richtig zu speichern
                                SaveDateCorrectly();
                             //Wenn nicht alle Felder (korrekt) ausgefüllt sind --> Fehlermeldung
                            }else {
                                Toast.makeText(AddInfoActivity.this, "Gewicht ist nicht korrekt eingegeben!", Toast.LENGTH_SHORT).show();

                            }
                        }else {
                            Toast.makeText(AddInfoActivity.this, "Größe ist nicht korrekt eingegeben!", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(AddInfoActivity.this, "Alter ist nicht korrekt eingegeben!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(AddInfoActivity.this, "Bitte fülle alle Felder aus!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    //Methode für Auswahl des Geschlechts
    private void setupSpinner() {
        List<String> categories = new ArrayList<String>();
        categories.add("Männlich");
        categories.add("Weiblich");

        //Layout für Spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(dataAdapter);

        //wenn nichts ausgewählt --> Männlich (Default-Wert)
        genderSpinner.setSelection(0);
        selectedGender="Männlich";
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedGender=adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    //Methode zur Speicherung der eingegebenen Daten
    private void SaveDateCorrectly() {
        //
        //String name=et_username.getText().toString().trim();
        //Werte werden in SharedPreferences Objekt gespeichert
        //SharedPreferences sharedPreferences = this.getPreferences(MODE_PRIVATE);
        //trim = Leerzeichen entfernen
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("age",et_age.getText().toString().trim());
        editor.putString("height",et_height.getText().toString().trim());
        editor.putString("weight",et_weight.getText().toString().trim());
        editor.putString("username",et_username.getText().toString().trim());
        editor.putString("gender",selectedGender);
        editor.commit();
        editor.apply();



    //Benachrichtigung, wenn erfolreich gespeichert
        Toast.makeText(this, "Daten erfolgreich gespeichert!", Toast.LENGTH_SHORT).show();

    }
    //Methode, um zu sagen, ob Wert empty ist
    private boolean notEmptyFields() {
        return TextUtils.isEmpty(et_username.getText().toString().trim()) ||
                TextUtils.isEmpty(et_weight.getText().toString().trim()) ||
                TextUtils.isEmpty(et_height.getText().toString().trim()) ||
                TextUtils.isEmpty(et_age.getText().toString().trim());
    }
}