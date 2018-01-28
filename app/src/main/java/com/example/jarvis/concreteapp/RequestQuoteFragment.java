package com.example.jarvis.concreteapp;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jarvis.concreteapp.model.Result;
import com.example.jarvis.concreteapp.network.RetrofitInterface;
import com.example.jarvis.concreteapp.utils.DatePickerFragment;
import com.google.gson.Gson;

import java.util.ArrayList;
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
 * Created by Jarvis on 21-01-2018.
 */

public class RequestQuoteFragment extends android.support.v4.app.Fragment {

    private EditText generationdate,validtill,quantity;
    private Spinner customersite;
    private String cust_id;
    Result res1;
    Spinner quality;
    private static Retrofit.Builder builder=new Retrofit.Builder().baseUrl("http://35.200.128.175")
            .addConverterFactory(GsonConverterFactory.create());
    public static Retrofit retrofit=builder.build();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
      View view=inflater.inflate(R.layout.request_quote_fragment,null);

        validtill=(EditText)view.findViewById(R.id.quote_valid_date);
        quantity=(EditText)view.findViewById(R.id.quote_quantity);
        customersite=(Spinner)view.findViewById(R.id.customer_site_spinner);
        quality=(Spinner)view.findViewById(R.id.quote_qualtiy_spinner);
        Button submit=(Button)view.findViewById(R.id.submit_quote);
        res1=((RequestQuote)getActivity()).res;
        validtill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment picker = new DatePickerFragment();
                picker.show(getActivity().getFragmentManager(), "datePicker");

            }
        });
        List<String> list = new ArrayList<String>();
        ArrayAdapter<String> adapter;
        for(int j=0;j<res1.getUser().getCustomerSite().size();j++)
        {
            list.add(res1.getUser().getCustomerSite().get(j).getName());
        }
        adapter = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customersite.setAdapter(adapter);
        customersite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
             cust_id=res1.getUser().getCustomerSite().get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startquote();
            }
        });
        Log.e("TAG", "response 33: "+res1.getUser().getId()+res1.getUser().getName()+" "+quality.getSelectedItem().toString());
      return view;

    }

    private void startquote() {

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

        startRequsetQuote(validtill.getText().toString(),quantity.getText().toString(),quality.getSelectedItem().toString());
    }

    private void startRequsetQuote (String val_date, String quantity, String quality)
    {
        RetrofitInterface retrofitInterface=retrofit.create(RetrofitInterface.class);
        Map<String,String> map=new HashMap<>();
        map.put("quality",quality);
        map.put("quantity",quantity);
        map.put("customerSite",cust_id);
        map.put("requiredDate",val_date);
        map.put("requestedBy",res1.getUser().getName());
        map.put("requestedById",res1.getUser().getId());

        Call<ResponseBody> call=retrofitInterface.quote_request(map);
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
}

