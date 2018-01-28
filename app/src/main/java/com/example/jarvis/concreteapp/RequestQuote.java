package com.example.jarvis.concreteapp;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jarvis.concreteapp.model.Result;
import com.example.jarvis.concreteapp.network.RetrofitInterface;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestQuote extends AppCompatActivity {
    Result res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_quote);
        Intent i=getIntent();
        res=i.getParcelableExtra("Result");
        TabLayout tabLayout=(TabLayout) findViewById(R.id.quote_tab);
        ViewPager viewPager=(ViewPager) findViewById(R.id.quote_viewpager);

        QuoteTabs quoteTabs=new QuoteTabs(getSupportFragmentManager());
        viewPager.setAdapter(quoteTabs);
        tabLayout.setupWithViewPager(viewPager);

    }
}
