package com.example.jarvis.concreteapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jarvis.concreteapp.model.Result;
import com.example.jarvis.concreteapp.network.RetrofitInterface;
import com.example.jarvis.concreteapp.utils.Constants;
import com.example.jarvis.concreteapp.utils.DirectingClass;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderBook extends AppCompatActivity {
    private EditText requierddate,quantity;
    Spinner quality,PO,customersite;
    Date currentTime = Calendar.getInstance().getTime();
    DatePickerDialog datePickerDialog;
    Result resl;
    String cust_site;
    private static Retrofit.Builder builder=new Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());
    public static Retrofit retrofit=builder.build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_book);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i=getIntent();
        resl=i.getParcelableExtra("Result");
        requierddate=(EditText)findViewById(R.id.Date_required_orderbook);
        quantity=(EditText)findViewById(R.id.Quantity_orderbook);
        customersite=(Spinner)findViewById(R.id.Location_orderbook);
        quality=(Spinner)findViewById(R.id.Quality_orderbook);
        PO=(Spinner)findViewById(R.id.PO_orderbook);
        List<String> list = new ArrayList<String>();
        ArrayAdapter<String> adapter;
        for(int j=0;j<resl.getUser().getCustomerSite().size();j++)
        {
            list.add(resl.getUser().getCustomerSite().get(j).getName());
        }
        adapter = new ArrayAdapter<String>(OrderBook.this,
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customersite.setAdapter(adapter);
        customersite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cust_site=resl.getUser().getCustomerSite().get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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

        sendOrder(requierddate.getText().toString(),quantity.getText().toString(),customersite.getSelectedItem().toString(),quality.getSelectedItem().toString(),PO.getSelectedItem().toString());
    }

    private void sendOrder(String requiered_date, String quantity, String customersite, String quality, String supplier_id)
    {
        RetrofitInterface retrofitInterface =retrofit.create(RetrofitInterface.class);
        Map<String,String> map=new HashMap<>();
        map.put("requiredDate",requiered_date);
        map.put("quantity",quantity);
        map.put("quality",quality);
        map.put("requestedBy",resl.getUser().getName());
        map.put("requestedById",resl.getUser().getId());
        map.put("supplierId",resl.getUser().getId());
        map.put("price","1000");
        map.put("companyName","ABCD");
        map.put("customerSite",cust_site);

        Call<ResponseBody> call=retrofitInterface.submit_order(map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(OrderBook.this,new Gson().toJson(response.body()),Toast.LENGTH_SHORT).show();
                Log.e("TAG", "response 33: "+new Gson().toJson(response.body()));
                DirectingClass directingClass=new DirectingClass(getApplicationContext(),OrderBook.this);
                directingClass.performLogin();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(OrderBook.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
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
