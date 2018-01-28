package com.example.jarvis.concreteapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jarvis.concreteapp.model.User;
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

public class AddSite extends AppCompatActivity {
    User user;
    private static Retrofit.Builder builder=new Retrofit.Builder().baseUrl("http://35.200.128.175")
            .addConverterFactory(GsonConverterFactory.create());
    public static Retrofit retrofit=builder.build();
    EditText sitename,site_address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i=getIntent();
        user=i.getParcelableExtra("User");
        setContentView(R.layout.activity_add_site);
        sitename=(EditText) findViewById(R.id.sitename);
        site_address=(EditText) findViewById(R.id.site_address);
        Button submit=(Button)findViewById(R.id.submit_site);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddsite();
            }
        });
    }

    private void startAddsite()
    {
        if(sitename.getText().toString().isEmpty()){
            sitename.setError("Required Field");
            sitename.requestFocus();
            return;
        }
        if(site_address.getText().toString().isEmpty()){
            site_address.setError("Required Field");
            site_address.requestFocus();
            return;
        }
        submitAddsite(sitename.getText().toString(),site_address.getText().toString());
    }

    private void submitAddsite(String name, String address)
    {
        RetrofitInterface retrofitInterface=retrofit.create(RetrofitInterface.class);
        Map<String,String> map=new HashMap<>();
        map.put("name",name);
        map.put("lat","0");
        map.put("long","0");
        map.put("address",address);
        map.put("_id",user.getId()
        );
        Call<ResponseBody> call=retrofitInterface.add_site(map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(AddSite.this,new Gson().toJson(response.body()),Toast.LENGTH_SHORT).show();
                Log.e("TAG", "response 33: "+new Gson().toJson(response.body()));


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AddSite.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}
