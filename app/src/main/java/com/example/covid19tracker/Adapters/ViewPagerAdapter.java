package com.example.covid19tracker.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.covid19tracker.Fragments.TrackCountries;
import com.example.covid19tracker.Fragments.WorldStats;

public class ViewPagerAdapter extends FragmentStateAdapter {
    TrackCountries trackCountries;
    WorldStats worldStats;
     static int PAGES = 2;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                if(worldStats == null)
                    worldStats = new WorldStats();
                return worldStats;
            case 1:
                if(trackCountries == null)
                    trackCountries = new TrackCountries();
                return trackCountries;
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return PAGES;
    }
}
