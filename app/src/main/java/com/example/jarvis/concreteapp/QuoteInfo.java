package com.example.jarvis.concreteapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jarvis.concreteapp.model.Quote;
import com.example.jarvis.concreteapp.network.RetrofitInterface;
import com.example.jarvis.concreteapp.utils.Constants;
import com.example.jarvis.concreteapp.utils.DirectingClass;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuoteInfo extends AppCompatActivity {
    Retrofit.Builder builder=new Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit=builder.build();
    Quote quote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_info);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i=getIntent();
        quote=i.getParcelableExtra("quote");
        String site=i.getStringExtra("customersite_quote");
        TextView gendate,quantity,quality,requestedby,cust_site,valdate;
        gendate=(TextView)findViewById(R.id.Quote_gen_date);
        quality=(TextView)findViewById(R.id.Quote_quality);
        quantity=(TextView)findViewById(R.id.Quote_quantity);
        requestedby=(TextView)findViewById(R.id.Quote_requestedby);
        cust_site=(TextView)findViewById(R.id.Quote_site);
        Button cancel_quote=(Button)findViewById(R.id.deletequotebutton);
        final LinearLayout linearLayout=(LinearLayout)findViewById(R.id.quote_info);
        cancel_quote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                new AlertDialog.Builder(QuoteInfo.this)
                        .setTitle("Delete Quote")
                        .setMessage("Are you sure you want to Cancel this Quote?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                RetrofitInterface retrofitInterface=retrofit.create(RetrofitInterface.class);
                                Map<String,String> map=new HashMap<>();
                                map.put("quoteId",quote.getId());
                                Call<ResponseBody> call=retrofitInterface.cancel_quote(map);
                                call.enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        Snackbar snackbar = Snackbar
                                                .make(linearLayout, "Quote Cancelled Successfully", Snackbar.LENGTH_LONG);
                                        snackbar.setActionTextColor(Color.RED);
                                        snackbar.show();
                                        Log.e("TAG", "response 33: "+new Gson().toJson(response.body()));
                                        DirectingClass directingClass=new DirectingClass(getApplicationContext(),QuoteInfo.this);
                                        directingClass.performLogin();

                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Toast.makeText(view.getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                });
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();

            }
        });
        long milliseconds=Long.parseLong(quote.getGenerationDate());
        Date date = new Date(milliseconds);
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM,yyyy", Locale.ENGLISH);
        gendate.setText("Quote created on:"+formatter.format(date));
        quality.setText("Quality:"+quote.getQuality());
        quantity.setText("Quantity:"+quote.getQuantity());
        requestedby.setText("PO requested by:"+quote.getRequestedBy());
        cust_site.setText("Customer Site:"+site);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
