package com.example.jarvis.concreteapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jarvis.concreteapp.model.PO;
import com.example.jarvis.concreteapp.model.Response;
import com.example.jarvis.concreteapp.model.Result;
import com.example.jarvis.concreteapp.model.User;
import com.example.jarvis.concreteapp.network.RetrofitInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

/**
 * Created by Jarvis on 23-01-2018.
 */

public class AvailablePO extends Fragment {

    Result r1;
    Gson gson = new GsonBuilder().create();
    Retrofit.Builder builder=new Retrofit.Builder().baseUrl("http://35.200.128.175").addConverterFactory(GsonConverterFactory.create(gson));
    Retrofit retrofit=builder.build();
    RetrofitInterface retrofitInterface=retrofit.create(RetrofitInterface.class);
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.available_po,null);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerViewPO);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(gridLayoutManager);
         r1=((DashBoard)getActivity()).r;
        Map<String,String> map=new HashMap<>();
        map.put("userId",r1.getUser().getId());
        Log.e("TAG", "response 33: " + r1.getUser().getId());
        Call<Result> call=retrofitInterface.getpo(map);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, retrofit2.Response<Result> response) {

                Result r=  response.body(); // have your all data

                Log.e("TAG", "response 33: " + new Gson().toJson(response.body()));
                Log.e("TAG", "response 33: " + response.body());
                PoAdapter poAdapter=new PoAdapter(r.getPOs(),r1.getUser().getCustomerSite());
                recyclerView.setAdapter(poAdapter);

            }
            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }

        });
        /*Call<Result> call=retrofitInterface.login(r1.getUser().getEmail(),"1234");
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, retrofit2.Response<Result> response) {

                Result r=  response.body(); // have your all data

                Log.e("TAG", "response 33: " + new Gson().toJson(response.body()));
                Log.e("TAG", "response 33: " + response.body());
                PoAdapter poAdapter=new PoAdapter(r.getPOs());
                recyclerView.setAdapter(poAdapter);

            }
            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }

        });*/
        return view;
    }
}
