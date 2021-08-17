package com.example.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MovieReport extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    String date;
    String reportSDate;
    String reportEDate;
    Boolean sdate = false;
    Boolean edate = false;
    boolean syear = false;
    String year = "2020";
    Spinner years;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_report);

        /*Selecting the start date and end date by clicking on button*/
        findViewById(R.id.btn_startDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
                sdate = true;
            }
        });
        findViewById(R.id.btn_endDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
                edate = true;
            }
        });

        /*Selecting the year throught spinner*/
        years = (Spinner) findViewById(R.id.sp_year);
        List<String> list1 = new ArrayList<>();
        list1.add("2015");
        list1.add("2016");
        list1.add("2017");
        list1.add("2018");
        list1.add("2019");
        list1.add("2020");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list1);
        //adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        years.setAdapter(adapter);

        years.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year =  parent.getItemAtPosition(position).toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        /*Calling Async task to get bar graph*/
        BarGraphAsyncTask barGraphAsyncTask = new BarGraphAsyncTask();
        barGraphAsyncTask.execute(year);

    }

   /*Code for date picker*/
    public void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(MovieReport.this, this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        int newMonth = month + 1;
        date = year+"-"+newMonth+"-"+dayOfMonth;
        /*Code to hold value of start date and end date*/
        if(sdate) {
            reportSDate = date;
            sdate = false;
        Button reportSdate = (Button) findViewById(R.id.btn_startDate);
        reportSdate.setText(reportSDate);}
        else if (edate){
        reportEDate = date ;
        edate = false;
        Button reportEdate = (Button) findViewById(R.id.btn_endDate);
        reportEdate.setText(reportEDate);
        /*Calling pie async task*/
        PieReportAsyncTask pieReportAsyncTask = new PieReportAsyncTask();
        pieReportAsyncTask.execute();}
        }


    /*Async task for pie chart*/
    private class PieReportAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... v) {
            SharedPreferences userData = MovieReport.this.getSharedPreferences("File_User", Context.MODE_PRIVATE);
            String userId = userData.getString("Id", null);
            return RestClient.getNoOfMoviesWatchedPerPostcode(userId, reportSDate, reportEDate);
        }
        @Override
        protected void onPostExecute(String result) {
            /*Calling method for map construction*/
            draw_PieChart(result);
        }
    }


    /*Async task for bar graph*/
    private class BarGraphAsyncTask extends AsyncTask<String ,Void, String>{

        @Override
        protected String doInBackground(String... params) {
            SharedPreferences userData = MovieReport.this.getSharedPreferences("File_User", Context.MODE_PRIVATE);
            String userId = userData.getString("Id", null);
            return RestClient.getNoOfMoviesWatchedInYear(userId, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            draw_BarGraph(result);
        }
    }
    /*Method to draw Bar graph*/
    private void draw_BarGraph(String result){
        BarChart barChart = (BarChart) findViewById(R.id.barChart);
        ArrayList<BarEntry> entries = new ArrayList<>();
        JSONArray jsonArray = null;
        try{
            if(result != null){
                jsonArray = new JSONArray(result);
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    CountMonth cm = new CountMonth();
                    cm.setMonth(object.getString("month"));
                    cm.setCount(object.getString("count"));
                    entries.add(new BarEntry(i, Float.parseFloat(cm.getCount())));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        BarDataSet bardataset = new BarDataSet(entries, "Months");
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");
        labels.add("July");
        labels.add("August");
        labels.add("September");
        labels.add("October");
        labels.add("November");
        labels.add("December");

        BarData data = new BarData(bardataset);
        barChart.setData(data);
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.animateY(5000);
    }

    /*Method for getting pie char*/
    private void draw_PieChart(String result) {
        PieChart pieChart = findViewById(R.id.pieChart);
        pieChart.setUsePercentValues(true);
        ArrayList<PieEntry> yvalues = new ArrayList<PieEntry>();
        JSONArray jsonArray = null;
        try{
            if(result != null){
                jsonArray = new JSONArray(result);
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    postcodeCount pc = new postcodeCount();
                    pc.setCinemaPostcode(object.getString("cinemaPostcode"));
                    pc.setCount(object.getString("count"));
                    yvalues.add(new PieEntry(Integer.parseInt(pc.getCount()), pc.getCinemaPostcode() , i));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PieDataSet dataSet = new PieDataSet(yvalues, "Movie Postcode");
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        pieChart.setData(data);
        pieChart.setTransparentCircleRadius(58f);
        data.setValueTextSize(13f);
        pieChart.setHoleRadius(58f);
        data.setValueTextColor(Color.BLACK);
        pieChart.setDrawHoleEnabled(true);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
    }
}
