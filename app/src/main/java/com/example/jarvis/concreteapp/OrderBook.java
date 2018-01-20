package com.example.jarvis.concreteapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jarvis.concreteapp.model.Result;
import com.example.jarvis.concreteapp.network.RetrofitInterface;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderBook extends AppCompatActivity {
    private EditText requierddate,quantity,customersite;
    Spinner quality,PO;
    Date currentTime = Calendar.getInstance().getTime();
    DatePickerDialog datePickerDialog;
    Result resl;
    private static Retrofit.Builder builder=new Retrofit.Builder().baseUrl("http://35.200.128.175")
            .addConverterFactory(GsonConverterFactory.create());
    public static Retrofit retrofit=builder.build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_book);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        requierddate=(EditText)findViewById(R.id.Date_required_orderbook);
        quantity=(EditText)findViewById(R.id.Quantity_orderbook);
        customersite=(EditText)findViewById(R.id.Location_orderbook);
        quality=(Spinner)findViewById(R.id.Quality_orderbook);
        PO=(Spinner)findViewById(R.id.PO_orderbook);
        Button submit=(Button)findViewById(R.id.Confirm);
        requierddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(OrderBook.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                requierddate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        Intent i=getIntent();
        resl=i.getParcelableExtra("Result");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startOrderbook();
            }
        });
    }

    private void startOrderbook()
    {
        if(requierddate.getText().toString().isEmpty()){
            requierddate.setError("Required Field");
            requierddate.requestFocus();
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
        sendOrder(requierddate.getText().toString(),quantity.getText().toString(),customersite.getText().toString(),quality.getSelectedItem().toString(),PO.getSelectedItem().toString());
    }

    private void sendOrder(String requiered_date, String quantity, String customersite, String quality, String supplier_id)
    {
        RetrofitInterface retrofitInterface =retrofit.create(RetrofitInterface.class);
        Map<String,String> map=new HashMap<>();
        map.put("requiredByDate",requiered_date);
        map.put("quantity",quantity);
        map.put("quality",quality);
        map.put("requestedBy",resl.getUser().getName());
        map.put("requestedById",resl.getUser().getId());
        map.put("supplierId",supplier_id);
        map.put("customerSite",customersite);

        Call<ResponseBody> call=retrofitInterface.submit_order(map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(OrderBook.this,new Gson().toJson(response.body()),Toast.LENGTH_SHORT).show();
                Log.e("TAG", "response 33: "+new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(OrderBook.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
