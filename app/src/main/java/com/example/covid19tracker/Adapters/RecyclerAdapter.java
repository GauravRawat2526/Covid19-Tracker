package com.example.covid19tracker.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.covid19tracker.Fragments.TrackCountries;
import com.example.covid19tracker.Interfaces.RecycleClickListener;
import com.example.covid19tracker.Models.CovidData;
import com.example.covid19tracker.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.viewmodel> implements  Filterable {

     private List<CovidData> list;
     private List<CovidData> savedList;
     Context mContext;
     RecycleClickListener onClick;
    public RecyclerAdapter(List<CovidData> list ,List<CovidData> savedList,Context mContext , RecycleClickListener onClick) {
        this.list = list;
        this.mContext = mContext;
        this.savedList = savedList;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public viewmodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.countries_list , parent , false);
        return  new viewmodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewmodel holder, int position) {
            holder.countries_Name.setText(list.get(position).getCountryName());
            Glide.with(mContext).load(list.get(position).getCountryFlag()).into(holder.countries_Flag);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class  viewmodel extends RecyclerView.ViewHolder {
        TextView countries_Name ;
        ImageView countries_Flag;
        public viewmodel(@NonNull View itemView) {
            super(itemView);
            countries_Name = itemView.findViewById(R.id.country_Name);
            countries_Flag = itemView.findViewById(R.id.country_flag);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        onClick.onItemClick(v,getAdapterPosition());
                }
            });
        }
    }

    @Override
    public Filter getFilter() {
        return covidCountriesFilter;
    }
    private Filter covidCountriesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
          List<CovidData> covidData  = new ArrayList<>();
          if(constraint == null || constraint.length() == 0 || constraint.toString().equals("")){
              covidData.addAll(savedList);
          }else{
              String filterPattern = constraint.toString().toLowerCase().trim();
              for(CovidData item : savedList){
                  if(item.getCountryName().toLowerCase().contains(filterPattern)){
                      covidData.add(item);
                  }
              }
          }
          FilterResults results = new FilterResults();
          results.values = covidData;
          return  results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}