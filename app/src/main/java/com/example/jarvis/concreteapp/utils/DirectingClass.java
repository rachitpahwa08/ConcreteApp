package com.example.jarvis.concreteapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.jarvis.concreteapp.DashBoard;
import com.example.jarvis.concreteapp.model.Result;
import com.example.jarvis.concreteapp.network.RetrofitInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jarvis on 28-01-2018.
 */

public class DirectingClass
{
    SessionManagement session;
    Gson gson = new GsonBuilder().setLenient().create();
    Result result;
    Context context;
    Activity activity;

    public DirectingClass(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    OkHttpClient client = new OkHttpClient();
    Retrofit.Builder builder=new Retrofit.Builder().baseUrl(Constants.BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create(gson));
    Retrofit retrofit=builder.build();
    RetrofitInterface retrofitInterface=retrofit.create(RetrofitInterface.class);
  public void performLogin()
  {
      session = new SessionManagement(context);
      // Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
      session.checkLogin(activity);
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
              Intent intent = new Intent(context, DashBoard.class);
              intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
              intent.putExtra("Result", result);
              context.startActivity(intent);
              activity.finish();

          }
          @Override
          public void onFailure(Call<Result> call, Throwable t) {

              Log.e("TAG", "response 33: " +t.getMessage());
              Toast.makeText(context,t.getMessage(),Toast.LENGTH_SHORT).show();
          }

      });
  }
}
