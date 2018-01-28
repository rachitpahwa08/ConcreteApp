package com.example.jarvis.concreteapp.network;

/**
 * Created by Jarvis on 04-01-2018.
 */


import com.example.jarvis.concreteapp.model.History;
import com.example.jarvis.concreteapp.model.PO;
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

    @FormUrlEncoded
    @POST("/requestquote")
    Call<ResponseBody>quote_request(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("/cancelquote")
    Call<ResponseBody>cancel_quote(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("/createpo")
    Call<ResponseBody>create_po(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("/deletepo")
    Call<ResponseBody>delete_po(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("/addorder")
    Call<ResponseBody>submit_order(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("/cancelorder")
    Call<ResponseBody>cancel_order(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("/addissue")
    Call<ResponseBody>add_issue(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("/addsite")
    Call<ResponseBody>add_site(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("/deletesite")
    Call<ResponseBody>delete_site(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("/profile")
    Call<ResponseBody>edit_profile(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("/history")
    Call<Result>history(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("/allpo")
    Call<Result>getpo(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("/forgot")
    Call<ResponseBody>forgot_pass(@FieldMap Map<String,String> map);

}
