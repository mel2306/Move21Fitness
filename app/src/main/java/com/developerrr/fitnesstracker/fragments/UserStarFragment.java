package com.developerrr.fitnesstracker.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.developerrr.fitnesstracker.R;
import com.developerrr.fitnesstracker.activities.AddInfoActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

//Fragement Essentracking --> Funktioniert nicht einwandfrei!!!
public class UserStarFragment extends Fragment {

    //Deklaration
    ImageButton starOne,starTwo,starThree,starFour,starFive;//,starSix;
    ImageButton fruitOne,fruitTwo,vegetableOne,vegetableTwo,vegetableThree,sweetOne;

    //Beginn numOfStars=0
    int numOfStars=0;

    @Override //Mit Layout fragment_userstars verbinden
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_userstars, container, false);

        //Methode InitializeButton aufrufen
        InitializeButton(view);

        return view;
    }

    //Button Initialisieren
    private void InitializeButton(View view) {
        starOne=view.findViewById(R.id.starOne);
        starTwo=view.findViewById(R.id.starTwo);
        starThree=view.findViewById(R.id.starThree);
        starFour=view.findViewById(R.id.starFour);
        starFive=view.findViewById(R.id.starFive);
//      starSix=view.findViewById(R.id.starSix);

        fruitOne=view.findViewById(R.id.fruitOne);
        fruitTwo=view.findViewById(R.id.fruitTwo);
        vegetableOne=view.findViewById(R.id.vegetOne);
        vegetableTwo=view.findViewById(R.id.vegetTwo);
        vegetableThree=view.findViewById(R.id.vegeThree);
        sweetOne=view.findViewById(R.id.sweetsOne);

        SharedPreferences sharedPreferences = getActivity().getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        numOfStars = sharedPreferences.getInt("currStars", 0);

        String today=sharedPreferences.getString("lastDate","");
        if(today.equals(getCurrentDate())){
            setupPreferences(); //Methode setupPreferences wird aufgerufen
        }

        //Listener Fruit1
        fruitOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sharedPreferences.getInt("fruitOne", 0) == 0)
                {
                    numOfStars++;
                    makeStarsFilled(numOfStars);
                    fruitOne.setImageResource(R.drawable.ic_star_filled);
//                    fruitOne.setEnabled(false);
                    editor.putInt("fruitOne",1);
                    editor.apply();
                    editor.putInt("starsToday",numOfStars);
                    editor.putInt("currStars", numOfStars);
                    editor.apply();
                }
                else
                {
                    --numOfStars;
                    makeStarsFilled(numOfStars);
                    fruitOne.setImageResource(R.drawable.ic_star);
//                    fruitOne.setEnabled(false);
                    editor.putInt("fruitOne",0);
                    editor.apply();
                    editor.putInt("starsToday",numOfStars);
                    editor.putInt("currStars", numOfStars);
                    editor.apply();
                }

            }
        });

        //Listener Fruit2
        fruitTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sharedPreferences.getInt("fruitTwo", 0) == 0)
                {
                    numOfStars++;
                    makeStarsFilled(numOfStars);
                    fruitTwo.setImageResource(R.drawable.ic_star_filled);
//                    fruitTwo.setEnabled(false);
                    editor.putInt ("fruitTwo",1);
                    editor.apply();
                    editor.putInt("starsToday",numOfStars);
                    editor.putInt("currStars", numOfStars);
                    editor.apply();
                }
                else
                {
                    --numOfStars;
                    makeStarsFilled(numOfStars);
                    fruitTwo.setImageResource(R.drawable.ic_star);
//                    fruitTwo.setEnabled(false);
                    editor.putInt("fruitTwo",0);
                    editor.apply();
                    editor.putInt("starsToday",numOfStars);
                    editor.putInt("currStars", numOfStars);
                    editor.apply();
                }

            }
        });

        //Listener Gemüse1
        vegetableOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.getInt("vegetableOne",0) == 0) {
                    numOfStars++;
                    makeStarsFilled(numOfStars);
                    vegetableOne.setImageResource(R.drawable.ic_star_filled);
//                    vegetableOne.setEnabled(false);
                    editor.putInt("vegetableOne", 1);
                    editor.apply();

                    editor.putInt("starsToday", numOfStars);
                    editor.putInt("currStars", numOfStars);
                    editor.apply();
                }
                else
                {
                    --numOfStars;
                    makeStarsFilled(numOfStars);
                    vegetableOne.setImageResource(R.drawable.ic_star);
//                    vegetableOne.setEnabled(false);
                    editor.putInt("vegetableOne", 0);
                    editor.apply();

                    editor.putInt("starsToday", numOfStars);
                    editor.putInt("currStars", numOfStars);
                    editor.apply();
                }
            }
        });

        //Listener Gemüse2
        vegetableTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.getInt("vegetableTwo", 0) == 0) {
                    numOfStars++;
                    makeStarsFilled(numOfStars);
                    vegetableTwo.setImageResource(R.drawable.ic_star_filled);
//                    vegetableTwo.setEnabled(false);
                    editor.putInt("vegetableTwo", 1);
                    editor.apply();
                    editor.putInt("starsToday", numOfStars);
                    editor.putInt("currStars", numOfStars);
                    editor.apply();
                }
                else
                {
                    --numOfStars;
                    makeStarsFilled(numOfStars);
                    vegetableTwo.setImageResource(R.drawable.ic_star);
//                    vegetableTwo.setEnabled(false);
                    editor.putInt("vegetableTwo", 0);
                    editor.apply();
                    editor.putInt("starsToday", numOfStars);
                    editor.putInt("currStars", numOfStars);
                    editor.apply();
                }
            }
        });

        //Listener Gemüse3
        vegetableThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.getInt("vegetableThree", 0) == 0) {
                    numOfStars++;
                    makeStarsFilled(numOfStars);
                    vegetableThree.setImageResource(R.drawable.ic_star_filled);
//                    vegetableThree.setEnabled(false);
                    editor.putInt("vegetableThree", 1);
                    editor.apply();
                    editor.putInt("starsToday", numOfStars);
                    editor.putInt("currStars", numOfStars);
                    editor.apply();
                }
                else
                {
                    --numOfStars;
                    makeStarsFilled(numOfStars);
                    vegetableThree.setImageResource(R.drawable.ic_star);
//                    vegetableThree.setEnabled(false);
                    editor.putInt("vegetableThree", 0);
                    editor.apply();
                    editor.putInt("starsToday", numOfStars);
                    editor.putInt("currStars", numOfStars);
                    editor.apply();
                }


            }
        });

        //Listener Süßigkeiten1
        sweetOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.getInt("sweetOne", 0) == 0) {
//                    numOfStars++;
//                    makeStarsFilled(numOfStars);
//                    setupPreferences();
                    sweetOne.setImageResource(R.drawable.sweet_icon_filled);
//                sweetOne.setEnabled(false);
                    editor.putInt("sweetOne", 1);
                    editor.apply();

//                    editor.putInt("starsToday", numOfStars);
//                    editor.apply();
                }
                else
                {
                    sweetOne.setImageResource(R.drawable.sweet_icon);
//                sweetOne.setEnabled(false);
                    editor.putInt("sweetOne", 0);
                    editor.apply();
                }
            }
        });

    }

    //SetupPreferences --> Sterne oben Leiste anzeigen je nach Klick -->FUNKTIONIERT ND EINWANDFREI!!
    private void setupPreferences() {
        SharedPreferences sharedPreferences = getActivity().getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit(); //Interface zur Änderung von Werten in einem SharedPreferences-Objekt


        int f1=sharedPreferences.getInt("fruitOne",0);
        int f2=sharedPreferences.getInt("fruitTwo",0);
        int v1=sharedPreferences.getInt("vegetableOne",0);
        int v2=sharedPreferences.getInt("vegetableTwo",0);
        int v3=sharedPreferences.getInt("vegetableThree",0);
        int s1=sharedPreferences.getInt("sweetOne",0);

        if(f1==1){
            fruitOne.setImageResource(R.drawable.ic_star_filled);
//            fruitOne.setEnabled(false);
            numOfStars++;
        }
        else if(numOfStars > 0)
        {
            numOfStars--;
        }
        if(f2==1){
            fruitTwo.setImageResource(R.drawable.ic_star_filled);
//            fruitTwo.setEnabled(false);
            numOfStars++;
        }
        else if(numOfStars > 0)
        {
            numOfStars--;
        }
        if(v1==1){
            vegetableOne.setImageResource(R.drawable.ic_star_filled);
//            vegetableOne.setEnabled(false);
            numOfStars++;
        }
        else if(numOfStars > 0)
        {
            numOfStars--;
        }
        if(v2==1){
            vegetableTwo.setImageResource(R.drawable.ic_star_filled);
//            vegetableTwo.setEnabled(false);
            numOfStars++;
        }
        else if(numOfStars > 0)
        {
            numOfStars--;
        }

        if(v3==1){
            vegetableThree.setImageResource(R.drawable.ic_star_filled);
//            vegetableThree.setEnabled(false);
            numOfStars++;
        }
        else if(numOfStars > 0)
        {
            numOfStars--;
        }
        if(s1==1){
            sweetOne.setImageResource(R.drawable.sweet_icon_filled);
//            sweetOne.setEnabled(false);
            //numOfStars++;
        }

        int lastNumOfStars = sharedPreferences.getInt("starsToday", 0);

        lastNumOfStars = lastNumOfStars + numOfStars;

        makeStarsFilled(numOfStars);//Methode Sterne füllen
        editor.putInt("starsToday",lastNumOfStars);
        editor.apply();

    }

    @Override //Methode onDestroy
    public void onDestroy() {
        super.onDestroy(); //Wird aufgerufen, wenn das Fragment nicht mehr in Gebrauch ist. -->FUNKTIONIERT ND EINWANDFREI!!

        SharedPreferences sharedPreferences = getActivity().getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String date=sharedPreferences.getString("lastDate","");
        if(!date.equals(getCurrentDate())){
            int stars=sharedPreferences.getInt("totalStars",0);
            editor.putInt("totalStars",numOfStars+stars);
            editor.commit();
        }


        editor.putInt("starsToday",numOfStars);
        editor.commit();

    }

    //Methode um Sterne in Leiste oben zu füllen
    private void makeStarsFilled(int numOfStars) {
        SharedPreferences sharedPreferences = getActivity().getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lastDate", getCurrentDate());
        editor.apply();

        switch (numOfStars){
            case 0:
                starOne.setImageResource(R.drawable.ic_star);
                starTwo.setImageResource(R.drawable.ic_star);
                starThree.setImageResource(R.drawable.ic_star);
                starFour.setImageResource(R.drawable.ic_star);
                starFive.setImageResource(R.drawable.ic_star);
                break;
            case 1:
                starOne.setImageResource(R.drawable.ic_star_filled);
                starTwo.setImageResource(R.drawable.ic_star);
                starThree.setImageResource(R.drawable.ic_star);
                starFour.setImageResource(R.drawable.ic_star);
                starFive.setImageResource(R.drawable.ic_star);
                break;
            case 2:
                starOne.setImageResource(R.drawable.ic_star_filled);
                starTwo.setImageResource(R.drawable.ic_star_filled);
                starThree.setImageResource(R.drawable.ic_star);
                starFour.setImageResource(R.drawable.ic_star);
                starFive.setImageResource(R.drawable.ic_star);
                break;
            case 3:
                starOne.setImageResource(R.drawable.ic_star_filled);
                starTwo.setImageResource(R.drawable.ic_star_filled);
                starThree.setImageResource(R.drawable.ic_star_filled);
                starFour.setImageResource(R.drawable.ic_star);
                starFive.setImageResource(R.drawable.ic_star);
                break;
            case 4:
                starOne.setImageResource(R.drawable.ic_star_filled);
                starTwo.setImageResource(R.drawable.ic_star_filled);
                starThree.setImageResource(R.drawable.ic_star_filled);
                starFour.setImageResource(R.drawable.ic_star_filled);
                starFive.setImageResource(R.drawable.ic_star);
                break;
            case 5:
                starOne.setImageResource(R.drawable.ic_star_filled);
                starTwo.setImageResource(R.drawable.ic_star_filled);
                starThree.setImageResource(R.drawable.ic_star_filled);
                starFour.setImageResource(R.drawable.ic_star_filled);
                starFive.setImageResource(R.drawable.ic_star_filled);
                break;
        }

    }

    //aktuelles Datum
    private String getCurrentDate(){
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        return formattedDate;
    }

}