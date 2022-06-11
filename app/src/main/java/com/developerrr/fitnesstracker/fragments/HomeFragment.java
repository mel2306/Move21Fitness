package com.developerrr.fitnesstracker.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.developerrr.fitnesstracker.R;
import com.developerrr.fitnesstracker.activities.CameraActivity;
import com.developerrr.fitnesstracker.models.MyDate;
import com.developerrr.fitnesstracker.roomdb.MyDatabase;
import com.developerrr.fitnesstracker.roomdb.StepTable;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment implements SensorEventListener{

    Button cameraBtn;

    //every number of minutes handler will save number of steps to room database.
    //Jede Anzahl von Minuten wird von einem Handler in einer Raumdatenbank gespeichert (Schritte)
    private static final long SAVEINTERVAL = 2;

    //Beginnt mit 0
    private Integer stepCount = 0;
    //Variablen deklarieren
    TextView stepsTv, speedTv,caloriesTv,distanceTv,lastRebootTV;

    //Konstante, welche das Standard-Service identifiziert
    //Sensoren deklarieren
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private boolean isSensorPresent = false; //default-wert = false --> sensor nicht verfügbar
    String age,height,weight,name,gender;
    private float totalStepsTeken;
    private String lastRebootTVText;

    //Ein Handler ermöglicht das Senden und Verarbeiten von
    // Message- und Runnable-Objekten, die der MessageQueue eines Threads zugeordnet sind
    Handler handler;
    //Die Runnable-Schnittstelle sollte von jeder Klasse implementiert werden, deren Instanzen von einem
    // Thread ausgeführt werden sollen. Die Klasse muss eine Methode ohne Argumente namens run definieren.
    Runnable runnable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Konstante, welche das Standard-Service identifiziert
        mSensorManager = (SensorManager)
                getContext().getSystemService(Context.SENSOR_SERVICE);
        //Eine Konstante, die einen Schrittdetektorsensor beschreibt.
        // Ein solcher Sensor löst bei jedem Schritt des Benutzers ein Ereignis aus. --> nicht 0
        //wenn sensor nicht null -> issensorPresent true
        //else --> false
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
                != null)
        {
            mSensor =
                    mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
            //isSensorPresent nun true
            isSensorPresent = true;
        }
        else
        {
            isSensorPresent = false;
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        doFuncitonality(view);

        //Listener cameraBtn
        cameraBtn=view.findViewById(R.id.cameraBtn);

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override//wenn man auf Button "Foto aufnehmen" klickt öffnet camera activity
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CameraActivity.class));
            }
        });

        return view;
    }
    //Zeit zum letzten Booten (wird nd verwendet)
    private String getTimeSinceBoot(long durationInMillis) {

        //getting date from millis
        Date date=new Date(durationInMillis);
        String[] time=date.toString().split(" ");
        String timet=time[0]+" "+time[1]+" "+time[2]+" "+time[3];

        return timet;
    }

    private void doFuncitonality(View view) {

        //Geben Sie einen Bezeichnung diese Ansicht an, um sie später mit View abzurufen
        stepsTv = view.findViewById(R.id.stepsTv);
        speedTv = view.findViewById(R.id.speedTv);
        caloriesTv=view.findViewById(R.id.caloriesTv);
        distanceTv=view.findViewById(R.id.distanceTv);
        lastRebootTV=view.findViewById(R.id.lastRebootTime);

        //Beginn wert setzen
        stepsTv.setText("0.0");
        speedTv.setText("0.0 \nk/Std ");
        caloriesTv.setText("0.0 Kalorien\n verbrannt");
        distanceTv.setText("0.0 \nMeter");

        SharedPreferences sharedPreferences = getActivity().getPreferences(MODE_PRIVATE);

        age=sharedPreferences.getString("Alter","");
        height=sharedPreferences.getString("Größe","");
        weight=sharedPreferences.getString("Gewicht","");
        name=sharedPreferences.getString("Name","");
        gender=sharedPreferences.getString("Geschlecht","Männlich");

        stepCount = sharedPreferences.getInt(getCurrentDate().getFullDate().toString(), 0);
        stepsTv.setText(String.valueOf(stepCount));

        int yestSteps = sharedPreferences.getInt(getYesterdayDateString().toString(), 0);
        lastRebootTV.setText("Schritte gestern: "+yestSteps);

        stepsTv.setText(String.valueOf(stepCount));
        caloriesTv.setText(getCaloriesBurned(stepCount));
        distanceTv.setText(getDistanceCovered(stepCount));
        speedTv.setText("4.82 km/h \n Geschw.");

        totalStepsTeken=stepCount;

//        MyDatabase database=MyDatabase.getiNSTANCE(getContext());
//        String result="";
//        for(StepTable s : database.StepDao().getAllSteps()){
//            result.concat("Steps: "+s.steps+" Day: "+s.dayName+"\n");
//            Log.d("TAG", "doFuncitonality: Steps: "+s.steps+" Date: "+s.day+"\n");
//        }


// adding dumy data to roomdatabase.
//        Date d = new Date();
//        Random random=new Random();
//
//        CharSequence s  = DateFormat.format("d MMMM, yyyy ", d.getTime());
//        for(int i=1; i<31; i++){
//            MyDate date=new MyDate("April",getDaywithDate(i),i,4,2022,s);
//            addStep(random.nextInt(18000),date);
//       }


        //Dadurch werden alle 2 Minuten die Gesamtschritte in der room database gespeichert
        handler = new Handler();
        runnable=new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, SAVEINTERVAL * 60 * 1000); // alle 2 Minuten
                addStep(totalStepsTeken,getCurrentDate());
            }
        };
        handler.postDelayed(runnable, SAVEINTERVAL * 60 * 1000);
    }
    //date gestern
    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }
    //date yesterday als String zurück
    private CharSequence getYesterdayDateString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM, yyyy ");
        return dateFormat.format(yesterday());
    }

    //wird nicht verwendet
    private String getDaywithDate(int date){
        List<String> dates=new ArrayList<>();
        dates.add("Sun");
        dates.add("Mon");
        dates.add("Tue");
        dates.add("Wed");
        dates.add("Thu");
        dates.add("Fri");
        dates.add("Sat");
        dates.add("Sun");
        dates.add("Mon");
        dates.add("Tue");
        dates.add("Wed");
        dates.add("Thu");
        dates.add("Fri");
        dates.add("Sat");
        dates.add("Sun");
        dates.add("Mon");
        dates.add("Tue");
        dates.add("Wed");
        dates.add("Thu");
        dates.add("Fri");
        dates.add("Sat");
        dates.add("Sun");
        dates.add("Mon");
        dates.add("Tue");
        dates.add("Wed");
        dates.add("Thu");
        dates.add("Fri");
        dates.add("Sat");
        dates.add("Sun");
        dates.add("Mon");
        dates.add("Tue");
        dates.add("Wed");
        dates.add("Thu");
        dates.add("Fri");
        dates.add("Sat");


        return dates.get(date);
    }

    //Was passiert wenn Fragment nicht länger verwendet/Pause
    public void onPause() {
        super.onPause();

        if(isSensorPresent)
        {
            mSensorManager.unregisterListener(this);
        }

        SharedPreferences sharedPreferences = getActivity().getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(getCurrentDate().toString());
        editor.apply();
        editor.putInt(getCurrentDate().getFullDate().toString(), stepCount);
        editor.apply();
    }
    //Was passiert wenn Fragment nicht mehr gestartet
    public void onStop() {
        super.onStop();

        SharedPreferences sharedPreferences = getActivity().getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(getCurrentDate().toString());
        editor.commit();
        editor.apply();
        editor.putInt(getCurrentDate().getFullDate().toString(), stepCount);
        editor.apply();

        handler.removeCallbacks(runnable);


    }

    //was passiert wenn Fragment läuft/sichtbar für Benutzer
    public void onResume() {
        super.onResume();
        if(isSensorPresent)
        {
            mSensorManager.registerListener(this, mSensor,
                    SensorManager.SENSOR_DELAY_FASTEST);//Bekommt Sensordaten so schnell wie möglich
        }

        SharedPreferences sharedPreferences = getActivity().getPreferences(MODE_PRIVATE);
        stepCount = sharedPreferences.getInt(getCurrentDate().getFullDate().toString(), 0);

        handler.postDelayed(runnable,SAVEINTERVAL * 60 * 1000);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

//
//        SharedPreferences sharedPreferences = getActivity().getPreferences(MODE_PRIVATE);
//        SharedPreferences.Editor editor=sharedPreferences.edit();
//
//        totalStepsTeken = sharedPreferences.getInt(getCurrentDate().getFullDate().toString(), 0);

        //Text im Fragment
        totalStepsTeken=(float) (totalStepsTeken+event.values[0]);
        stepCount=(int)totalStepsTeken;
        stepsTv.setText(String.valueOf(stepCount));
        caloriesTv.setText(getCaloriesBurned(totalStepsTeken));
        distanceTv.setText(getDistanceCovered(totalStepsTeken));
        speedTv.setText("4.82 km/h \n Speed");

        //this will store steps daily on sensor change


//
//        editor.putInt(getCurrentDate().getFullDate().toString(), (int) totalStepsTeken);

        addStep(totalStepsTeken,getCurrentDate());

//
//        if(event.sensor.getType()==Sensor.TYPE_STEP_DETECTOR){
//
//        }

        //totalStepsTeken=event.values[0];
//
//        stepsTv.setText(String.valueOf(event.values[0]));
//        caloriesTv.setText(getCaloriesBurned(event.values[0]));
//        distanceTv.setText(getDistanceCovered(event.values[0]));
//        speedTv.setText("4.82 km/h \n Speed");
//
//        //this will store steps daily on sensor change
//
//
//        lastRebootTV.setText(getStepsYesterday());

    }

    //schritte gestern bekommen (wird nie verwendet)
    private String getStepsYesterday() {
        MyDatabase database=MyDatabase.getiNSTANCE(getContext());
        SharedPreferences sharedPreferences = getActivity().getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int yesterdaySteps = 0;
        for(StepTable a : database.StepDao().getAllSteps()){
            if(a.day==1){
                int month=a.month-1;
                yesterdaySteps=getPreviousMonthSteps(month);
                lastRebootTVText="Steps Last Month :"+yesterdaySteps;
                editor.putInt("lastmonthsteps",yesterdaySteps);
                editor.commit();
                editor.apply();
            }else {
                if (a.day == getCurrentDate().getDayOfMonth() - 1) {
                    if (a.month == getCurrentDate().getMonthOfYear()) {
                        if (a.year == getCurrentDate().getYear()) {
                            yesterdaySteps = a.steps;
                            lastRebootTVText="Steps Yesterday :"+yesterdaySteps;
                        }
                    }
                }
            }

        }
        return lastRebootTVText; //returned Steps gestern
    }

    //Schritte Monat
    private int getPreviousMonthSteps(int month) {
        int toSteps=0;
        MyDatabase database=MyDatabase.getiNSTANCE(getContext());
        for(StepTable s :database.StepDao().getAllSteps()){
            if(s.month==month){
                toSteps+=s.steps;
            }
        }

        return toSteps;
    }

    //Schritte in DB
    private void addStep(float steps, MyDate myDate) {
        MyDatabase database=MyDatabase.getiNSTANCE(getContext());

        StepTable step=new StepTable();
        step.dayName=myDate.getDay();
        step.day=myDate.getDayOfMonth();
        step.month=myDate.getMonthOfYear();
        step.monthName=myDate.getMonth();
        step.year=myDate.getYear();
        step.steps=(int)steps;
        database.StepDao().insertStep(step);

    }

    //Aktuelles Datum bekommen
    private MyDate getCurrentDate(){
        Date d = new Date();
        CharSequence s  = DateFormat.format("d MMMM, yyyy ", d.getTime());
        //this sequence will return something like this April 6 2022
        String dayOfTheWeek = (String) DateFormat.format("EEEE", d); // Thursday
        String day          = (String) DateFormat.format("dd",   d); // 20
        String monthString  = (String) DateFormat.format("MMMM",  d); // Jun
        String monthNumber  = (String) DateFormat.format("MM",   d); // 06
        String year         = (String) DateFormat.format("yyyy", d);
        MyDate myDate=new MyDate(monthString,dayOfTheWeek,Integer.parseInt(day),Integer.parseInt(monthNumber),Integer.parseInt(year),s);
        return myDate;
    }

    //Zurückgelegte Distanz in Metern anzeigen
    private String getDistanceCovered(float steps) {

        double steplen=0.762; //Schrittlänge im Durschnitt

        double dis=steplen * steps;
        if(dis < 1000){
            return dis + "\n Meter"; //Meter
        }else {
            dis=dis/1000;
            return new DecimalFormat("##.##").format(dis) + "\n Kilometer"; //Wenn Kilometerbereich
        }

    }

    //Kalorien verbrannt
    private String getCaloriesBurned(float steps) {
        double caloriesburned;

        caloriesburned=steps * 0.04; //pro Schritt 0.04 Kalorien vebrennen im Durschnitt
        if(caloriesburned < 1000){
            return new DecimalFormat("##.##").format(caloriesburned) +" Kalorien verbrannt";
        }else {
            caloriesburned=caloriesburned/1000;
            return new DecimalFormat("##.##").format(caloriesburned) + "Kilokalorien verbrannt";
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}