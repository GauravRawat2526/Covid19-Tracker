package com.example.covid19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.covid19tracker.Fragments.TrackCountries;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class DetailActivity extends AppCompatActivity {
    private int countryPosition;
    PieChart pieChart;
    TextView countryName ,cases , todayCases , deaths , todayDeaths , recovered , todayRecovered , active  , critical;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
             finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        countryPosition = intent.getIntExtra("position",0);
        getSupportActionBar().setTitle(TrackCountries.data.get(countryPosition).getCountryName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        countryName = findViewById(R.id.dCountryName);
        cases = findViewById(R.id.dCases);
        todayCases = findViewById(R.id.dTodayCases);
        deaths = findViewById(R.id.dDeaths);
        todayDeaths = findViewById(R.id.dTodayDeaths);
        recovered = findViewById(R.id.dRecovered);
        todayRecovered = findViewById(R.id.dTodayRecovered);
        active = findViewById(R.id.dActive);
        critical = findViewById(R.id.dCritical);
        pieChart = findViewById(R.id.dPiechart);
        countryName.setText(TrackCountries.data.get(countryPosition).getCountryName());
        cases.setText(String.valueOf(TrackCountries.data.get(countryPosition).getCases()));
        todayCases.setText(String.valueOf(TrackCountries.data.get(countryPosition).getTodayCases()));
        deaths.setText(String.valueOf(TrackCountries.data.get(countryPosition).getDeaths()));
        todayDeaths.setText(String.valueOf(TrackCountries.data.get(countryPosition).getTodayDeaths()));
        recovered.setText(String.valueOf(TrackCountries.data.get(countryPosition).getRecovered()));
        todayRecovered.setText(String.valueOf(TrackCountries.data.get(countryPosition).getTodayRecovered()));
        active.setText(String.valueOf(TrackCountries.data.get(countryPosition).getActive()));
        critical.setText(String.valueOf(TrackCountries.data.get(countryPosition).getCritical()));

        pieChart.addPieSlice(new PieModel("Cases", Integer.parseInt(cases.getText().toString()), Color.parseColor("#FFA726")));
        pieChart.addPieSlice(new PieModel("Recovered", Integer.parseInt(recovered.getText().toString()), Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(new PieModel("Deaths", Integer.parseInt(deaths.getText().toString()), Color.parseColor("#EF5350")));
        pieChart.addPieSlice(new PieModel("Active", Integer.parseInt(active.getText().toString()), Color.parseColor("#29B6F6")));
        pieChart.startAnimation();
    }
}