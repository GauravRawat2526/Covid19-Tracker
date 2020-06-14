package com.example.covid19tracker.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.covid19tracker.Adapters.RecyclerAdapter;
import com.example.covid19tracker.DetailActivity;
import com.example.covid19tracker.Interfaces.RecycleClickListener;
import com.example.covid19tracker.Models.CovidData;
import com.example.covid19tracker.R;
import com.example.covid19tracker.VolleySingleton;
import com.leo.simplearcloader.SimpleArcLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class TrackCountries extends Fragment implements RecycleClickListener {

    RecyclerView recyclerView ;
    EditText search;
    SimpleArcLoader simpleArcLoader;
    LinearLayoutManager linearLayoutManager;
    public static List<CovidData> data;
    List<CovidData> savedData;
    RecyclerAdapter recyclerAdapter;
    public TrackCountries() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     View view = inflater.inflate(R.layout.fragment_track_countries, container, false);
      recyclerView = view.findViewById(R.id.recyclerView);
      search = view.findViewById(R.id.search);
      simpleArcLoader = view.findViewById(R.id.loader2);
      linearLayoutManager = new LinearLayoutManager(getContext());
      recyclerView.setLayoutManager(linearLayoutManager);
      data = new ArrayList<>();
      savedData = new ArrayList<>();
      fetchData();
      recyclerAdapter = new RecyclerAdapter(data,savedData,getContext(),this);
      recyclerView.setAdapter(recyclerAdapter);
      recyclerView.setVisibility(View.VISIBLE);
       search.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

           }

           @Override
           public void afterTextChanged(Editable s) {
            filterList(s.toString());
           }
       });
       return view;
    }

    private void filterList(String text) {
        if(recyclerAdapter != null){
            recyclerAdapter.getFilter().filter(text);
        }
        }


    private void fetchData() {
       simpleArcLoader.start();
       String url = "https://disease.sh/v2/countries";
         final StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
             @Override
             public void onResponse(String response) {
                 try {
                     JSONArray jsonArray = new JSONArray(response);
                     for(int i = 0 ; i<jsonArray.length() ; i++) {
                         JSONObject jsonObject = jsonArray.getJSONObject(i);
                          String countryName = jsonObject.getString("country");
                          JSONObject countryInfo = jsonObject.getJSONObject("countryInfo");
                          String countryFlag = countryInfo.getString("flag");
                          int cases = jsonObject.getInt("cases");
                          int todayCases = jsonObject.getInt("todayCases");
                          int deaths = jsonObject.getInt("deaths");
                          int todayDeaths = jsonObject.getInt("todayDeaths");
                          int recovered = jsonObject.getInt("recovered");
                          int todayRecovered = jsonObject.getInt("todayRecovered");
                          int active = jsonObject.getInt("active");
                          int critical = jsonObject.getInt("critical");
                          data.add(new CovidData(countryName,countryFlag,cases,todayCases,deaths,todayDeaths,recovered,todayRecovered,active,critical));
                          savedData.add(new CovidData(countryName,countryFlag,cases,todayCases,deaths,todayDeaths,recovered,todayRecovered,active,critical));
                     }
                     simpleArcLoader.stop();
                     simpleArcLoader.setVisibility(View.GONE);
                 } catch (JSONException e) {
                     e.printStackTrace();
                     simpleArcLoader.stop();
                     simpleArcLoader.setVisibility(View.GONE);
                     Toast.makeText(getContext(), e.getMessage(),Toast.LENGTH_SHORT ).show();
                 }
             }
         }, new ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {
                 Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
             }
         });
        RequestQueue requestQueue = VolleySingleton.getInstance(getContext()).getRequestQueue();
        requestQueue.add(request);
    }

    @Override
    public void onItemClick(View view, int position) {
       startActivity(new Intent(getActivity(), DetailActivity.class).putExtra("position",position));
    }
}