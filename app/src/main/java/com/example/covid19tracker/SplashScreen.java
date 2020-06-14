package com.example.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EasySplashScreen config = new EasySplashScreen(SplashScreen.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(3000)
                .withBackgroundColor(Color.parseColor("#ededf2"))
                .withAfterLogoText("Covid19 Tracker")
                .withLogo(R.drawable.logo);

        config.getAfterLogoTextView().setTextColor(Color.parseColor("#EF5350"));

        View splashScreen = config.create();
        setContentView(splashScreen);
    }
}