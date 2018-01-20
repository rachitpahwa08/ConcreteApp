package com.example.jarvis.concreteapp;

import android.content.Intent;
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
private EditText generationdate,validtill,quantity,customersite;
Result res1;
Spinner quality;
    private static Retrofit.Builder builder=new Retrofit.Builder().baseUrl("http://35.200.128.175")
                                            .addConverterFactory(GsonConverterFactory.create());
    public static Retrofit retrofit=builder.build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_quote);
       generationdate=(EditText)findViewById(R.id.quote_start_date);
       validtill=(EditText)findViewById(R.id.quote_valid_date);
       quantity=(EditText)findViewById(R.id.quote_quantity);
       customersite=(EditText)findViewById(R.id.quote_customer_site);
       quality=(Spinner)findViewById(R.id.quote_qualtiy_spinner);
        Button submit=(Button)findViewById(R.id.submit_quote);
        Intent i=getIntent();
        res1=i.getParcelableExtra("Result");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startquote();
            }
        });
        Log.e("TAG", "response 33: "+res1.getUser().getId()+res1.getUser().getName()+" "+quality.getSelectedItem().toString());
    }

    private void startquote() {
        if(generationdate.getText().toString().isEmpty()){
            generationdate.setError("Required Field");
            generationdate.requestFocus();
            return;
        }
        if(validtill.getText().toString().isEmpty()){
            validtill.setError("Required Field");
            validtill.requestFocus();
            return;
        }
        if(quantity.getText().toString().isEmpty()){
            quantity.setError("Required Field");
            quantity.requestFocus();
            return;
        }
        if(customersite.getText().toString().isEmpty()){
            customersite.setError("Required Field");
            customersite.requestFocus();
            return;
        }
    startRequsetQuote(generationdate.getText().toString(),validtill.getText().toString(),quantity.getText().toString(),quality.getSelectedItem().toString(),customersite.getText().toString());
    }

    private void startRequsetQuote(String gen_date, String val_date, String quantity, String quality, String customer_site)
    {
        RetrofitInterface retrofitInterface=retrofit.create(RetrofitInterface.class);
        Map<String,String> map=new HashMap<>();
        map.put("quality",quality);
        map.put("quantity",quantity);
        map.put("customerSite",customer_site);
        map.put("generationDate",gen_date);
        map.put("requiredDate",val_date);
        map.put("requestedBy",res1.getUser().getName());
        map.put("requestedById",res1.getUser().getId());

        Call<ResponseBody> call=retrofitInterface.quote_request(map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(RequestQuote.this,new Gson().toJson(response.body()),Toast.LENGTH_SHORT).show();
                Log.e("TAG", "response 33: "+new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RequestQuote.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }


}
