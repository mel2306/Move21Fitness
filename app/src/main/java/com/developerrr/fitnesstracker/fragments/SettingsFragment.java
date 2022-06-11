package com.developerrr.fitnesstracker.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.developerrr.fitnesstracker.R;
import com.developerrr.fitnesstracker.activities.AddInfoActivity;

import java.util.ArrayList;
import java.util.List;

// Einstellungen Fragment
public class SettingsFragment extends Fragment {

    //Deklaration
    Button addInfoBtn;
    TextView starsToday,starsTotal,badgesEarned;
    TextView userNameField;
    TextView weight,height,gender,age;

    @Override //Mit Layout fragment_settings verbinden
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_settings, container, false);

        //Zuweisung zu Layout Variablen
        //Suche nach einer untergeordneten Ansicht mit der angegebenen ID.
        //Wenn diese Ansicht die angegebene ID hat, wird diese Ansicht zurückgegeben.
        //starsToday=view.findViewById(R.id.numbersOne);
        starsTotal=view.findViewById(R.id.numbersTwo);
        badgesEarned=view.findViewById(R.id.numbersthree);
        userNameField=view.findViewById(R.id.userField);
        age=view.findViewById(R.id.ageval);
        weight=view.findViewById(R.id.weightval);
        height=view.findViewById(R.id.heightval);
        gender=view.findViewById(R.id.genderval);


        //Schnittstelle für den Zugriff auf und die Änderung von Einstellungsdaten,
        // die von getSharedPreferences(String, int) zurückgegeben werden.
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());


        //Zeigt Namen an
        String name=sharedPreferences.getString("username","");
        userNameField.setText(name);

        //Zeigt die weiteren gespeicherten Parameter in den EInstellungen an
        String aget=sharedPreferences.getString("age","");
        String weightt=sharedPreferences.getString("weight","");
        String heightt=sharedPreferences.getString("height","");
        String gendert=sharedPreferences.getString("gender","");
        gender.setText(gendert+" ");
        height.setText(heightt+" cm");
        weight.setText(weightt+" kg");
        age.setText(aget+" Jahre");

        //Listener addInfoBtn um bei Klick des Buttons auf AddInfoActivity zu kommen
        addInfoBtn=view.findViewById(R.id.addInfoBtn);
        addInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AddInfoActivity.class));
            }
        });

        return view;
    }

    @Override //Wenn Fragment nicht mehr in Gebrauch ist
    public void onDestroy() {
        super.onDestroy(); //Wird aufgerufen, wenn das Fragment nicht mehr in Gebrauch ist.
        // MODE_Private: Dateierstellungsmodus: der Standardmodus, bei dem die erstellte Datei
        // nur von der aufrufenden Anwendung aufgerufen werden kann
        SharedPreferences sharedPreferences = getActivity().getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username",userNameField.getText().toString());
        editor.commit(); //Übertragen Sie Ihre Einstellungsänderungen von diesem Editor zurück auf das SharedPreferences-Objekt, das er bearbeitet.
    }
}