package com.example.jarvis.concreteapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jarvis.concreteapp.model.Result;
import com.example.jarvis.concreteapp.network.RetrofitInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jarvis on 05-01-2018.
 */

public class AskQuote extends Fragment {

    private EditText validtill,quantity,customersite,supplierID,price;
    Spinner quality;
    private static Retrofit.Builder builder=new Retrofit.Builder().baseUrl("http://35.200.128.175")
            .addConverterFactory(GsonConverterFactory.create());
    public static Retrofit retrofit=builder.build();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.ask_quote,container,false);
        final Result resl=((DashBoard)getActivity()).r;

        validtill=(EditText)view.findViewById(R.id.valid_date);
        quantity=(EditText)view.findViewById(R.id.quantity);
        customersite=(EditText)view.findViewById(R.id.customer_site);
        price=(EditText)view.findViewById(R.id.price_PO);

        quality=(Spinner)view.findViewById(R.id.quality_spinner);
        Button submit=(Button)view.findViewById(R.id.submit_createPO);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startPo(resl);
            }
        });
        return view;

    }

    private void startPo(Result res1)
    {

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

        if(price.getText().toString().isEmpty()){
            price.setError("Required Field");
            customersite.requestFocus();
            return;
        }
        createPO(validtill.getText().toString(),quantity.getText().toString(),quality.getSelectedItem().toString(),price.getText().toString(),res1);
    }

    private void createPO(String val_date, String quantity, String quality,String price,Result res1)
    {
        RetrofitInterface retrofitInterface=retrofit.create(RetrofitInterface.class);
        Map<String,String> map=new HashMap<>();

        map.put("validTill",val_date);
        map.put("quantity",quantity);
        map.put("quality",quality);
        map.put("price",price);
        map.put("customerSite","5a644a697819df03473a9f87");
        map.put("requestedBy",res1.getUser().getName());
        map.put("requestedById",res1.getUser().getId());
        map.put("supplierId","5a522bd3917f08288f4eea1a");

        Call<ResponseBody> call=retrofitInterface.create_po(map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getActivity(),new Gson().toJson(response.body()),Toast.LENGTH_SHORT).show();
                Log.e("TAG", "response 33: "+new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
