package com.example.jarvis.concreteapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.jarvis.concreteapp.model.Issue;
import com.example.jarvis.concreteapp.model.Order;
import com.example.jarvis.concreteapp.network.RetrofitInterface;
import com.example.jarvis.concreteapp.utils.Constants;
import com.example.jarvis.concreteapp.utils.DirectingClass;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Issues extends AppCompatActivity {
    private EditText issue_title,issue_desc;
    private Spinner issue_type;
    LinearLayout linearLayout;
    private static Retrofit.Builder builder=new Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());
    public static Retrofit retrofit=builder.build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues);
        Intent i=getIntent();
        final Order order;
        order=i.getParcelableExtra("Order");
        issue_title=(EditText)findViewById(R.id.IssueTitle);
        issue_desc=(EditText)findViewById(R.id.issue_description);
        issue_type=(Spinner)findViewById(R.id.issue_type);
        Button submit=(Button)findViewById(R.id.raise_issue);
        linearLayout=(LinearLayout)findViewById(R.id.issue);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startissue(order);
            }
        });
    }
    private void startissue(Order order1)
    {
        submit_issue(issue_title.getText().toString(),issue_desc.getText().toString(),issue_type.getSelectedItem().toString(),order1);
    }

    private void submit_issue(String title, String desc, String type,Order o1)
    {
        RetrofitInterface retrofitInterface=retrofit.create(RetrofitInterface.class);
        Map<String,String> map=new HashMap<>();
        map.put("title",title);
        map.put("description",desc);
        map.put("orderId",o1.getId());
        map.put("id",o1.getRequestedById());
        map.put("type",type);
        Call<ResponseBody> call=retrofitInterface.add_issue(map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Snackbar snackbar = Snackbar
                        .make(linearLayout, "Issue Reported Successfully", Snackbar.LENGTH_LONG);
                snackbar.setActionTextColor(Color.RED);
                snackbar.show();
                Log.e("TAG", "response 33: "+new Gson().toJson(response.body()));
                DirectingClass directingClass=new DirectingClass(getApplicationContext(),Issues.this);
                directingClass.performLogin();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Issues.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
