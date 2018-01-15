package com.example.jarvis.concreteapp.network;

/**
 * Created by Jarvis on 04-01-2018.
 */


import com.example.jarvis.concreteapp.model.Response;
import com.example.jarvis.concreteapp.model.Result;
import com.example.jarvis.concreteapp.model.User;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

public interface RetrofitInterface {

    @FormUrlEncoded
    @POST("/signup")
    Call<ResponseBody>signup(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("/login")
    Call<Result>login(@Field(value="username",encoded = true) String username,@Field(value="password",encoded = true) String password);




    /*@GET("users/{email}")
    Observable<User> getProfile(@Path("email") String email);

    @PUT("users/{email}")
    Observable<Response> changePassword(@Path("email") String email, @Body User user);

    @POST("users/{email}/password")
    Observable<Response> resetPasswordInit(@Path("email") String email);

    @POST("users/{email}/password")
    Observable<Response> resetPasswordFinish(@Path("email") String email, @Body User user);*/
}
