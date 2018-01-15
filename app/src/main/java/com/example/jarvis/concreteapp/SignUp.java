package com.example.jarvis.concreteapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jarvis.concreteapp.network.RetrofitInterface;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUp extends AppCompatActivity {
    private EditText name,mobile,email,password,confirm_password,pan,gstin;

   private static Retrofit.Builder builder=new Retrofit.Builder().baseUrl("http://35.200.128.175").addConverterFactory(GsonConverterFactory.create());
public static Retrofit retrofit=builder.build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

           name=(EditText)findViewById(R.id.fullName);
           mobile=(EditText)findViewById(R.id.mobile);
           email=(EditText)findViewById(R.id.email);
           password=(EditText)findViewById(R.id.password);
           confirm_password=(EditText)findViewById(R.id.confirmPass);
           pan=(EditText)findViewById(R.id.PAN);
           gstin=(EditText)findViewById(R.id.GSTIN);
        Button register=(Button)findViewById(R.id.SignUp);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegister();
            }
        });
    }

    private void startRegister() {
        if(name.getText().toString().isEmpty()){
            name.setError("Email/Phone is Required");
            name.requestFocus();
            return;
        }
        if(email.getText().toString().isEmpty()){
            email.setError("Email is Required");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
            email.setError("Please enter a valid email or password");
            email.requestFocus();
            return;
        }
        if(mobile.getText().toString().isEmpty()){
            mobile.setError("Phone is Required");
            mobile.requestFocus();
            return;
        }
        if(password.getText().toString().isEmpty()){
            password.setError("Password is Required");
            password.requestFocus();
            return;
        }
        if(!confirm_password.getText().toString().equals(password.getText().toString())){
            confirm_password.setError("Password does not match");
            confirm_password.requestFocus();
            return;
        }

        if(gstin.getText().toString().isEmpty()){
            gstin.setError("Email/Phone is Required");
            gstin.requestFocus();
            return;
        }
        if(pan.getText().toString().isEmpty()){
            pan.setError("Email/Phone is Required");
            pan.requestFocus();
            return;
        }

    startSignup(name.getText().toString(),email.getText().toString(),password.getText().toString(),confirm_password.getText().toString(),pan.getText().toString(),gstin.getText().toString(),mobile.getText().toString());
    }

    private void startSignup(String name, String email, String password, String confirm_password, String pan, String gstin,String mobile)
    {

        RetrofitInterface retrofitInterface=retrofit.create(RetrofitInterface.class);
        Map<String,String> map=new HashMap<>();
        map.put("name",name);
        map.put("email",email);
        map.put("contact",mobile);
        map.put("pan",pan);
        map.put("gstin",gstin);
        map.put("password",password);
        map.put("password2",confirm_password);

        Call<ResponseBody> call=retrofitInterface.signup(map);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(SignUp.this,response.message(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                 Toast.makeText(SignUp.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }

    public void gotoLogin(View view)
    {
        Intent i=new Intent(this,LoginActivity.class);
        startActivity(i);
    }
}
