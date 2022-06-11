package com.developerrr.fitnesstracker.activities;

import android.hardware.Camera;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.developerrr.fitnesstracker.R;
import com.developerrr.fitnesstracker.fragments.BadgesFragment;
import com.developerrr.fitnesstracker.fragments.HistoryFragment;
import com.developerrr.fitnesstracker.fragments.HomeFragment;
import com.developerrr.fitnesstracker.fragments.SettingsFragment;
import com.developerrr.fitnesstracker.fragments.UserStarFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

//von hier aus steuere ich alle Fragments (also Ansichten unten in der Leiste)
    //diese werde immer über activity_main gelegt, sodass die Navigationsleiste immer sichtbar ist

    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //activity_main sichtbar setzen
        setContentView(R.layout.activity_main);

        container=findViewById(R.id.fragmentContainer);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new HomeFragment()).commit();

        //Navigationsleiste unten
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        //Wenn Tab unten ausgewählt wird soll das passieren....
        bottomNavigationView.setOnItemSelectedListener(item -> {

            //Beginn ist selectedFragment null
            Fragment selectedFragment=null;

            //Was passiert wenn ich in Navigationsleiste unten auf anderen Tab gehe
            //--> es wird die activity_main immer angezeigt und darauf lege ich je nach Auswahl mein passendes Fragment
            switch (item.getItemId()){
                case R.id.page_1:
                    selectedFragment=new HomeFragment(); //Home Menü Fragment
                   break;
                case R.id.page_2:
                    selectedFragment=new HistoryFragment(); //Auflistung der History Fragment
                    break;
                case R.id.page_3:
                    selectedFragment=new BadgesFragment(); //Medaillien Fragment
                   break;
                case R.id.page_4:
                    selectedFragment=new UserStarFragment(); //Sterne Obst/Gemüse Fragment
                    break;
                case R.id.page_5:
                    selectedFragment=new SettingsFragment(); //Einstellungen Fragment
                    break;
            }

            //Rückgabe des FragmentManagers für die Interaktion mit Fragmenten, die mit dieser Aktivität verbunden sind.
            //beginn der Transaktion -->Fragment je nach Auswahl zu ändern
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,selectedFragment).commit();

            return true;
        });

        showCustomUI(); //Methodenaufruf showCustomUI()
    }

    private void showCustomUI() {
        //Ruft das aktuelle Fenster für die Aktivität ab.
        /*Abrufen der Fensterdekoransicht der obersten Ebene (die den
        Standardfensterrahmen/die Standarddekorationen und den Inhalt des Clients darin enthält),
        die dem Fenstermanager als Fenster hinzugefügt werden kann.*/
        View decorView = getWindow().getDecorView();

        //Verlangen Sie, dass die Sichtbarkeit der Statusleiste oder anderer Bildschirm-/Fensterdekorationen geändert wird.
        decorView.setSystemUiVisibility(
                // erlaubt es, Artefakte zu vermeiden, wenn man in diesen Modus hinein- und herausschaltet, auf Kosten der Tatsache, dass ein Teil der
                // Benutzeroberfläche durch Bildschirmdekorationen verdeckt sein kann, wenn diese angezeigt werden.
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    
}