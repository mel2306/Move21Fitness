package com.developerrr.fitnesstracker.adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.developerrr.fitnesstracker.R;
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
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WeekHistoryAdapter extends RecyclerView.Adapter<WeekHistoryAdapter.WeekViewHolder> {

    private static final int SUCCESS_STEPS_COUNT = 12000; //zahl ab der Sucess bei schritten
    List<Week> weeksList;
    Context context;

    public WeekHistoryAdapter(List<Week> weeksList, Context context) {
        this.weeksList = weeksList;
        this.context = context;
    }



    @NonNull
    @Override
    public WeekViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_history_item_week,parent,false);

        return new WeekViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeekViewHolder holder, int position) {

        List<StepTable> steps=weeksList.get(position).getDays();

        BarData data = createChartData(steps);
        configureChartAppearance(holder.chart);
        prepareChartData(data, holder.chart);


        holder.chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override //wenn Barchart geklickt wird markiert und Info über Tag und Schritte gezeigt
            public void onValueSelected(Entry e, Highlight h) {
                StepTable selectedBar = null;
                for(StepTable s :steps){
                    if(s.steps==h.getY()){
                        selectedBar=s;
                    }
                }
                String val="GetY "+h.getY()+"\n GetX"+h.getX()+"\n Entry: "+e+"\nString "+h;
                Toast.makeText(context, String.valueOf(selectedBar.steps)+"\n"+selectedBar.dayName, Toast.LENGTH_SHORT).show();

            }

            //wenn keine Barchart ausgewählt keine Nachricht
            @Override
            public void onNothingSelected() {

            }
        });

    }

    @Override
    //Methode getItemCount --> return Größe von weekList
    public int getItemCount() {
        return weeksList.size();
    }

    //Daten für Graph vorbereiten
    private void prepareChartData(BarData data,BarChart chart) {
        data.setValueTextSize(12f); //Textgröße in der Schritte gezeigt werden solle
        chart.setData(data); //Legt ein neues Datenobjekt für das Diagramm fest.
        chart.invalidate(); // gesamte Ansicht validieren
    }

    //Graph Erscheinungsbild festlegen
    private void configureChartAppearance(BarChart chart) {
        String[] DAYS = { " ", " ", " ", " ", " ", " ", " " }; //Wochentage rein oder keine Wochentage in Diagramm zeigen
        chart.getDescription().setEnabled(false);
        chart.setDrawValueAboveBar(false);

        XAxis xAxis = chart.getXAxis(); //xAchsen Beschriftung
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return DAYS[(int) value];
            }
        });

        YAxis axisLeft = chart.getAxisLeft(); //yAchsen Beschriftung links
        axisLeft.setGranularity(10f);
        axisLeft.setAxisMinimum(0);

        YAxis axisRight = chart.getAxisRight(); //yAchsen Beschriftung rechts
        axisRight.setGranularity(10f);
        axisRight.setAxisMinimum(0);
    }

    //BarData --> Datenobjekt, das alle Daten für das BarChart darstellt
    private BarData createChartData(List<StepTable> stepTables) {

        ArrayList<BarEntry> values = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();

        //BarEntry
        for (int i = 0; i < stepTables.size(); i++) {
            float x = i;
            if(stepTables.get(i).steps > SUCCESS_STEPS_COUNT) { //Schritte größer als 12000 dann Sucess
                values.add(new BarEntry(x, stepTables.get(i).steps));
            }else {
                values2.add(new BarEntry(x, stepTables.get(i).steps)); //sonst Failure
            }
        }


        BarDataSet set1 = new BarDataSet(values, "erfolgreich"); //Label Sucess
        BarDataSet set2 = new BarDataSet(values2, "nicht erfolgreich"); //Label Failure

        //Farbe ändern je nachdem ob Sucess oder Failure
        set1.setColor(ColorTemplate.MATERIAL_COLORS[0]);
        set2.setColor(ColorTemplate.MATERIAL_COLORS[1]);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();

        //Data Set Problem mit anzeige?
        dataSets.add(set1);
        dataSets.add(set2);

        BarData data = new BarData(dataSets);
        return data;
    }

    //Innere Klasse WeekViewHolder extends ....
    class WeekViewHolder extends RecyclerView.ViewHolder{

        BarChart chart;

        public WeekViewHolder(@NonNull View itemView) {
            super(itemView);

            //Zufügen von Item_Barchart (single_history_item_week)
            chart=itemView.findViewById(R.id.item_barchart);
        }
    }
}
