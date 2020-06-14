package com.example.covid19tracker.Fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid19tracker.MainActivity;
import com.example.covid19tracker.R;
import com.example.covid19tracker.VolleySingleton;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class WorldStats extends Fragment {
    TextView tvCases, tvRecovered, tvCritical, tvTodayCases, tvActive, tvTotalDeaths, tvTodayDeaths, tvAffectedCountries;
    static SimpleArcLoader simpleArcLoader;
    PieChart pieChart;
    LinearLayout dashPane;

    public WorldStats() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_world_stats2, container, false);
        tvCases = view.findViewById(R.id.tvCases);
        tvRecovered = view.findViewById(R.id.tvRecovered);
        tvCritical = view.findViewById(R.id.tvCritical);
        tvTodayCases = view.findViewById(R.id.tvTodayCases);
        tvActive = view.findViewById(R.id.tvActive);
        tvTotalDeaths = view.findViewById(R.id.tvTotalDeaths);
        tvTodayDeaths = view.findViewById(R.id.tvTodayDeaths);
        tvAffectedCountries = view.findViewById(R.id.tvAffectedC);

        simpleArcLoader = view.findViewById(R.id.loader);
        dashPane = view.findViewById(R.id.dashPane);
        pieChart = view.findViewById(R.id.piechart);
        fetchData();
        return view;
    }
    private void fetchData() {
        simpleArcLoader.start();
        String url = "https://corona.lmao.ninja/v2/all";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    tvCases.setText(jsonObject.getString("cases"));
                    tvRecovered.setText(jsonObject.getString("recovered"));
                    tvCritical.setText(jsonObject.getString("critical"));
                    tvTodayCases.setText(jsonObject.getString("todayCases"));
                    tvActive.setText(jsonObject.getString("active"));
                    tvTotalDeaths.setText(jsonObject.getString("deaths"));
                    tvTodayDeaths.setText(jsonObject.getString("todayDeaths"));
                    tvAffectedCountries.setText(jsonObject.getString("affectedCountries"));

                    pieChart.addPieSlice(new PieModel("Cases", Integer.parseInt(tvCases.getText().toString()), Color.parseColor("#FFA726")));
                    pieChart.addPieSlice(new PieModel("Recovered", Integer.parseInt(tvRecovered.getText().toString()), Color.parseColor("#66BB6A")));
                    pieChart.addPieSlice(new PieModel("Deaths", Integer.parseInt(tvTotalDeaths.getText().toString()), Color.parseColor("#EF5350")));
                    pieChart.addPieSlice(new PieModel("Active", Integer.parseInt(tvActive.getText().toString()), Color.parseColor("#29B6F6")));
                    pieChart.startAnimation();
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    dashPane.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    dashPane.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                dashPane.setVisibility(View.VISIBLE);
            }
        });
        RequestQueue queue = VolleySingleton.getInstance(getContext()).getRequestQueue();
        queue.add(request);

    }
}
