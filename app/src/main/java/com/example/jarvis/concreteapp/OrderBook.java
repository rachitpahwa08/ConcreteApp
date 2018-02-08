package com.example.jarvis.concreteapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jarvis.concreteapp.model.Result;
import com.example.jarvis.concreteapp.network.RetrofitInterface;
import com.example.jarvis.concreteapp.utils.Constants;
import com.example.jarvis.concreteapp.utils.DirectingClass;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
    DatePickerDialog datePickerDialog;
    Result resl;
    String cust_site,supp_id;
    Calendar myCalendar;
    LinearLayout linearLayout;
    long milli_req_date;
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
        myCalendar = Calendar.getInstance();
        requierddate=(EditText)findViewById(R.id.Date_required_orderbook);
        quantity=(EditText)findViewById(R.id.Quantity_orderbook);
        customersite=(Spinner)findViewById(R.id.Location_orderbook);
        quality=(Spinner)findViewById(R.id.Quality_orderbook);
        PO=(Spinner)findViewById(R.id.PO_orderbook);
        linearLayout=(LinearLayout)findViewById(R.id.place_order);
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

        List<String> list1 = new ArrayList<String>();
        final List<String> qlist = new ArrayList<String>();
        ArrayAdapter<String> adapter1;
        for(int j=0;j<resl.getQuotes().size();j++)
        {   if(resl.getQuotes().get(j).getResponses()!=null) {
            for (int k = 0; k < resl.getQuotes().get(j).getResponses().size(); k++) {
                list1.add("name=" + resl.getQuotes().get(j).getResponses().get(k).getRmxId());
                qlist.add(resl.getQuotes().get(j).getResponses().get(k).getRmxId());

            }
        }
        }
        adapter1 = new ArrayAdapter<String>(OrderBook.this,
                android.R.layout.simple_spinner_item, list1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        PO.setAdapter(adapter1);
        PO.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                supp_id=qlist.get(i);
                Log.e("TAG","suppid"+supp_id);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Button submit=(Button)findViewById(R.id.Confirm);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        requierddate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(OrderBook.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
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

        sendOrder(quantity.getText().toString(),customersite.getSelectedItem().toString(),quality.getSelectedItem().toString(),PO.getSelectedItem().toString());
    }

    private void sendOrder(String quantity, String customersite, String quality, String supplier_id)
    {
        RetrofitInterface retrofitInterface =retrofit.create(RetrofitInterface.class);
        Map<String,String> map=new HashMap<>();
        map.put("requiredDate", String.valueOf(milli_req_date));
        map.put("quantity",quantity);
        map.put("quality",quality);
        map.put("requestedBy",resl.getUser().getName());
        map.put("requestedById",resl.getUser().getId());
        map.put("supplierId",supp_id);
        map.put("price","1000");
        map.put("companyName","Abcd");
        map.put("customerSite",cust_site);

        Call<ResponseBody> call=retrofitInterface.submit_order(map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Snackbar snackbar = Snackbar
                        .make(linearLayout, "Order Placed Successfully", Snackbar.LENGTH_LONG);
                snackbar.setActionTextColor(Color.RED);
                snackbar.show();
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

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Date date=myCalendar.getTime();
        milli_req_date=date.getTime();
        requierddate.setText(sdf.format(myCalendar.getTime()));

    }
}
