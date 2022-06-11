package com.developerrr.fitnesstracker.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.developerrr.fitnesstracker.R;
import com.developerrr.fitnesstracker.adapters.WeekHistoryAdapter;
import com.developerrr.fitnesstracker.logics.Partition;
import com.developerrr.fitnesstracker.models.MyDate;
import com.developerrr.fitnesstracker.models.Week;
import com.developerrr.fitnesstracker.roomdb.MyDatabase;
import com.developerrr.fitnesstracker.roomdb.StepDao;
import com.developerrr.fitnesstracker.roomdb.StepTable;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

//History Fragment
public class HistoryFragment extends Fragment {

    RecyclerView recyclerView; //Eine flexible Ansicht, die einen begrenzten Einblick in einen großen Datenbestand ermöglicht

    @Override //Fragment_History layout Verbindung
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView=view.findViewById(R.id.recyclerHistory);

        //Methodenaufruf setupRecyclerView()
        setupRecyclerView();

        return view;
    }

    //Methode um Daten in History
    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        MyDatabase database=MyDatabase.getiNSTANCE(getContext());
        StepDao userDao = database.StepDao();
        List<StepTable> stepTables=userDao.getAllSteps();

        List<Week> weeksList=getWeeksList(stepTables);

        WeekHistoryAdapter adapter=new WeekHistoryAdapter(weeksList,getContext());
        recyclerView.setAdapter(adapter);
    }

    //Liste Woche
    private List<Week> getWeeksList(List<StepTable> stepTables) {
        List<Week> weekslist=new ArrayList<>();

        //Partition StepTable chunksize=7
        Partition<StepTable> list= Partition.ofSize(stepTables,7);

        for(int i=0; i< list.size(); i++){
            weekslist.add(new Week(list.get(i)));
        }

        Collections.reverse(weekslist);

        return weekslist;
    }
}