package com.example.jarvis.concreteapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by Jarvis on 05-01-2018.
 */

public class AskQuote extends Fragment {

    private EditText validtill,quantity,price;
    Spinner quality,customersite,supplier;
    String cust_id,supp_id;
    LinearLayout linearLayout;
    int price_string=-1;
    long milli_valdate;
    Calendar myCalendar;
    DatePickerDialog datePickerDialog;
    private static Retrofit.Builder builder=new Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());
    public static Retrofit retrofit=builder.build();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.ask_quote,container,false);
        final Result resl=((DashBoard)getActivity()).r;

        myCalendar = Calendar.getInstance();
        validtill=(EditText)view.findViewById(R.id.valid_date);
        quantity=(EditText)view.findViewById(R.id.quantity);
        customersite=(Spinner) view.findViewById(R.id.customer_site);
        price=(EditText)view.findViewById(R.id.price_PO);

        quality=(Spinner)view.findViewById(R.id.quality_spinner);
        supplier=(Spinner)view.findViewById(R.id.supplier_spinner);
        Button submit=(Button)view.findViewById(R.id.submit_createPO);
        linearLayout=(LinearLayout)view.findViewById(R.id.createpo);
        price.setText("0");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputManager = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                startPo(resl);
            }
        });
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

        validtill.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        List<String> list = new ArrayList<String>();
        final List<String> qlist = new ArrayList<String>();
        final List<Integer> price_list = new ArrayList<>();
        ArrayAdapter<String> adapter;
        for(int j=0;j<resl.getUser().getCustomerSite().size();j++)
        {
            list.add(resl.getUser().getCustomerSite().get(j).getName());
        }
        adapter = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customersite.setAdapter(adapter);
        customersite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cust_id=resl.getUser().getCustomerSite().get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        List<String> list1 = new ArrayList<String>();
        ArrayAdapter<String> adapter1;
        for(int j=0;j<resl.getQuotes().size();j++)
        {   if(resl.getQuotes().get(j).getResponses()!=null) {
            for (int k = 0; k < resl.getQuotes().get(j).getResponses().size(); k++) {
                list1.add("name=" + resl.getQuotes().get(j).getResponses().get(k).getRmxId() + "price=" + resl.getQuotes().get(j).getResponses().get(k).getPrice());
                qlist.add(resl.getQuotes().get(j).getResponses().get(k).getRmxId());
                price_list.add(resl.getQuotes().get(j).getResponses().get(k).getPrice());
            }
          }
        }
        adapter1 = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_spinner_item, list1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        supplier.setAdapter(adapter1);
        supplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            supp_id=qlist.get(i);
                Log.e("TAG","suppid"+supp_id);
                Log.e("TAG","suppid"+price_list.get(i));
             price_string=price_list.get(i);
             if(price_string!=-1)
             { price.setText(String.valueOf(price_string));}
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Log.e("TAG","suppid"+supp_id);
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
        createPO(quantity.getText().toString(),quality.getSelectedItem().toString(),price.getText().toString(),res1);
    }

    private void createPO(String quantity, String quality,String price,Result res1)
    {
        RetrofitInterface retrofitInterface=retrofit.create(RetrofitInterface.class);
        Map<String,String> map=new HashMap<>();

        map.put("validTill", String.valueOf(milli_valdate));
        map.put("quantity",quantity);
        map.put("quality",quality);
        map.put("price", String.valueOf(price_string));
        map.put("customerSite",cust_id);
        map.put("requestedBy",res1.getUser().getName());
        map.put("requestedById",res1.getUser().getId());
        map.put("supplierId",supp_id);

        Call<ResponseBody> call=retrofitInterface.create_po(map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Snackbar snackbar = Snackbar
                        .make(linearLayout, "PO Created Successfully", Snackbar.LENGTH_LONG);
                snackbar.setActionTextColor(Color.RED);
                snackbar.show();
                Log.e("TAG", "response 33: "+new Gson().toJson(response.body()));
                DirectingClass directingClass=new DirectingClass(getContext(),getActivity());
                directingClass.performLogin();
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
    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Date date=myCalendar.getTime();
        milli_valdate=date.getTime();
        Log.e("TAG", "Time 33: "+milli_valdate);
        validtill.setText(sdf.format(myCalendar.getTime()));

    }
}
