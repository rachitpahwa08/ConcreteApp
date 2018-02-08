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

import com.example.jarvis.concreteapp.model.Order;
import com.example.jarvis.concreteapp.model.PO;
import com.example.jarvis.concreteapp.network.RetrofitInterface;
import com.example.jarvis.concreteapp.utils.Constants;
import com.example.jarvis.concreteapp.utils.DirectingClass;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

public class HistoryInfo extends AppCompatActivity {

    Gson gson = new GsonBuilder().create();
    Retrofit.Builder builder=new Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson));
    Retrofit retrofit=builder.build();
    RetrofitInterface retrofitInterface=retrofit.create(RetrofitInterface.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_info);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i=getIntent();
       final Order order;
        order=i.getParcelableExtra("Order");
        String site=i.getStringExtra("ordersite");
        TextView gendate,required_date,quality,quantity,requestedby,price,ordersite,statusdesc,supplier;
        gendate=(TextView)findViewById(R.id.order_gen_date);
        required_date=(TextView)findViewById(R.id.order_required);
        quality=(TextView)findViewById(R.id.order_quality);
        quantity=(TextView)findViewById(R.id.order_quantity);
        requestedby=(TextView)findViewById(R.id.order_requestedby);
        price=(TextView)findViewById(R.id.order_price);
        statusdesc=(TextView)findViewById(R.id.order_desc);
        ordersite=(TextView)findViewById(R.id.order_site);
        supplier=(TextView)findViewById(R.id.order_supplier);
        final LinearLayout linearLayout=(LinearLayout)findViewById(R.id.history_info);
        Button issue=(Button)findViewById(R.id.issue_submit);
        issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HistoryInfo.this,Issues.class);
                 intent.putExtra("Order", order);
                   startActivity(intent);
            }
        });
        long milliseconds=Long.parseLong(order.getGenerationDate());
        Date date = new Date(milliseconds);
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM,yyyy", Locale.ENGLISH);
        gendate.setText("Order Date:"+formatter.format(date));
        if(!order.getRequiredByDate().contains("/"))
        {
            long req_date= Long.parseLong(order.getRequiredByDate());
            Date date1 = new Date(req_date);
            required_date.setText("Requested Delivery:"+formatter.format(date1));
        }
        else
        {required_date.setText("Requested Delivery:"+order.getRequiredByDate());}
        quality.setText("Quality:"+order.getQuality());
        quantity.setText("Quantity:"+order.getQuantity());
        requestedby.setText("Order Requested By:"+order.getRequestedBy());
        price.setText("Price:\u20B9"+order.getPrice());
        statusdesc.setText(order.getStatusDesc());
        ordersite.setText("Customer Site:"+site);
        supplier.setText("Supplier:"+order.getSupplierId());

        if(order.getStatus().equals("cancelled"))
        {
            View b = findViewById(R.id.cancel_order);
            b.setVisibility(View.GONE);
        }
        Button cancel_order=(Button)findViewById(R.id.cancel_order);
        cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final Map<String,String> map=new HashMap<>();
                map.put("orderId",order.getId());
                new AlertDialog.Builder(HistoryInfo.this)
                        .setTitle("Cancel Order")
                        .setMessage("Are you sure you want to cancel order?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Call<ResponseBody> call= retrofitInterface.cancel_order(map);
                                call.enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        Snackbar snackbar = Snackbar
                                                .make(linearLayout, "Order Canceled Successfully", Snackbar.LENGTH_LONG);
                                        snackbar.setActionTextColor(Color.RED);
                                        snackbar.show();
                                        Log.e("TAG", "response 33: "+new Gson().toJson(response.body()));
                                        DirectingClass directingClass=new DirectingClass(view.getContext(),HistoryInfo.this);
                                        directingClass.performLogin();
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Toast.makeText(view.getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
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
