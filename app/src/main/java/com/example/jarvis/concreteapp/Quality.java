package com.example.jarvis.concreteapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Quality extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
