package com.example.jarvis.concreteapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.jarvis.concreteapp.model.Result;
import com.example.jarvis.concreteapp.model.User;
import com.example.jarvis.concreteapp.network.RetrofitInterface;
import com.example.jarvis.concreteapp.utils.SessionManagement;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashScreen extends AppCompatActivity {
    SessionManagement session;
    Gson gson = new GsonBuilder().setLenient().create();
    Result result;

    OkHttpClient client = new OkHttpClient();
    Retrofit.Builder builder=new Retrofit.Builder().baseUrl("http://35.200.128.175").client(client).addConverterFactory(GsonConverterFactory.create(gson));
    Retrofit retrofit=builder.build();
    RetrofitInterface retrofitInterface=retrofit.create(RetrofitInterface.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
       final User user;
        Thread myThread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(1500);
                    session = new SessionManagement(getApplicationContext());
                   // Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
                    session.checkLogin(SplashScreen.this);
                    HashMap<String, String> user = session.getUserDetails();
                    String email = user.get(SessionManagement.KEY_EMAIL);
                    String password = user.get(SessionManagement.KEY_PASS);
                    Call<Result> call=retrofitInterface.login(email,password);

                    call.enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, retrofit2.Response<Result> response) {

                            result=  response.body(); // have your all data
                               Log.e("TAG", "response 33: " + new Gson().toJson(response.body()));
                                Log.e("TAG", "response 33: " + response.body());
                                Intent intent = new Intent(getApplicationContext(), DashBoard.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("Result", result);
                                startActivity(intent);
                                finish();

                        }
                        @Override
                        public void onFailure(Call<Result> call, Throwable t) {

                            Log.e("TAG", "response 33: " +t.getMessage());
                            Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    });
                    /*Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();*/
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }
    }

