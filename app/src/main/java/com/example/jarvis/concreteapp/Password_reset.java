package com.example.jarvis.concreteapp;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Password_reset extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
