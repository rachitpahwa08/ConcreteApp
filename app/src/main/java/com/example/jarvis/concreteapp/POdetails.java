package com.example.jarvis.concreteapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jarvis.concreteapp.model.PO;
import com.example.jarvis.concreteapp.network.RetrofitInterface;
import com.example.jarvis.concreteapp.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;
import java.text.ParseException;
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

public class POdetails extends AppCompatActivity {
    Gson gson = new GsonBuilder().setLenient().create();
    Retrofit.Builder builder=new Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson));
    Retrofit retrofit=builder.build();
    PO p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podetails);
        Intent i=getIntent();
        String customerSite;
        p=i.getParcelableExtra("PO");
        customerSite=i.getStringExtra("customersite");
        TextView gendate,valid,quantity,quality,price,requestedby,status,site;
        gendate=(TextView)findViewById(R.id.po_gen_date);
        valid=(TextView)findViewById(R.id.po_valid);
        quantity=(TextView)findViewById(R.id.po_quantity);
        quality=(TextView)findViewById(R.id.po_quality);
        requestedby=(TextView)findViewById(R.id.po_request);
        status=(TextView)findViewById(R.id.po_status);
        price=(TextView)findViewById(R.id.po_price);
        site=(TextView)findViewById(R.id.po_site);
        Button button=(Button)findViewById(R.id.deletepobutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                RetrofitInterface retrofitInterface=retrofit.create(RetrofitInterface.class);
                Map<String,String> map=new HashMap<>();
                map.put("id",p.getId());
                Call<ResponseBody> call=retrofitInterface.delete_po(map);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        Log.e("TAG", "response 33: "+response.body()+"id="+p.getId());
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(view.getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        long milliseconds=Long.parseLong(p.getGenerationDate());
        Date date = new Date(milliseconds);
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM,yyyy", Locale.ENGLISH);
        gendate.setText("PO created on:"+formatter.format(date));
        String date1=p.getValidTill();
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd MMMM,yyyy");

        try {
            Date d=dateFormat.parse(date1);
            valid.setText("PO valid till:"+dateFormat.format(d));
        }
        catch(Exception e) {
            //java.text.ParseException: Unparseable date: Geting error
            System.out.println("Excep"+e);
        }
        quality.setText("Quality:"+p.getQuality());
        quantity.setText("Quantity:"+p.getQuantity());
        requestedby.setText("PO requested by:"+p.getRequestedBy());
        site.setText("Customer Site:"+customerSite);
        if((!p.getConfirmedBySupplier())&&(!p.getDeletedByContractor()))
        {
            status.setText("PO status:In Progress");
        }
        else if(p.getDeletedByContractor())
        {
            status.setText("PO status:PO deleted");
        }
        else
            status.setText("PO status:Confirmed");


        price.setText("Price:\u20B9"+p.getPrice());
    }

}
