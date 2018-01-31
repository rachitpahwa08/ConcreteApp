package com.example.jarvis.concreteapp;


import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.example.jarvis.concreteapp.model.Result;
import com.example.jarvis.concreteapp.model.User;
import com.example.jarvis.concreteapp.network.RetrofitInterface;
import com.example.jarvis.concreteapp.utils.Constants;
import com.example.jarvis.concreteapp.utils.SessionManagement;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private Button signin;
    ProgressDialog progressDialog;
    public static final String TAG = LoginActivity.class.getSimpleName();
    private String email,password;
    Result result=null;
    Context context;
    SessionManagement session;

    Gson gson = new GsonBuilder().setLenient().create();

    OkHttpClient client = new OkHttpClient();
    Retrofit.Builder builder=new Retrofit.Builder().baseUrl(Constants.BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create(gson));
  Retrofit retrofit=builder.build();
   RetrofitInterface retrofitInterface=retrofit.create(RetrofitInterface.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        signin = (Button) findViewById(R.id.email_sign_in_button);
        context = this.getApplicationContext();
        session = new SessionManagement(getApplicationContext());

        signin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                 email=mEmailView.getText().toString();
                 password=mPasswordView.getText().toString();
                startSignin();
            }
        });
    }



    public void startSignin(){

        String email=mEmailView.getText().toString();
        String password=mPasswordView.getText().toString();

        if(email.isEmpty()){
          mEmailView.setError("Email/Phone is Required");
          mEmailView.requestFocus();
          return;
      }
      if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
          mEmailView.setError("Please enter a valid email or password");
          mEmailView.requestFocus();
          return;
      }
        if(password.isEmpty()){
            mPasswordView.setError("Password is Required");
            mPasswordView.requestFocus();
            return;
        }
        if(password.length()<3){
            mPasswordView.setError("Minimum length of Password should be 6 characters");
            mPasswordView.requestFocus();
            return;
        }
        loginprocess();
        progressDialog=new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Signing In");
        progressDialog.setCancelable(false);
        progressDialog.show();

    }
   private void loginprocess()
   {
       Call<Result> call=retrofitInterface.login(email,password);

       call.enqueue(new Callback<Result>() {
           @Override
           public void onResponse(Call<Result> call, retrofit2.Response<Result> response) {

               result=  response.body(); // have your all data
               if(result.getMsg()!=null&&result.getMsg().getPassmsg().equals("Password is incorrect"))
             {
                 progressDialog.cancel();
                 Log.e("TAG", "response 33: " + new Gson().toJson(response.body()));
              mPasswordView.setError("Incorrect Password");
              mPasswordView.requestFocus();
                 return ;
             }
                 else {
                 User user = result.getUser();
                 String id = user.getId();
                 Toast.makeText(LoginActivity.this, id, Toast.LENGTH_SHORT).show();
                   progressDialog.cancel();
                 Log.e("TAG", "response 33: " + new Gson().toJson(response.body()));
                 Log.e("TAG", "response 33: " + response.body());
                   session.createLoginSession(email, password);
                 Intent intent = new Intent(LoginActivity.this, DashBoard.class);
                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                 intent.putExtra("Result", result);
                 startActivity(intent);
                 finish();
             }

             }
           @Override
           public void onFailure(Call<Result> call, Throwable t) {
               Toast.makeText(LoginActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
               progressDialog.cancel();
           }

       });
   }
   Result getResult()
{
    return result;
}


    void SignUp(View view)
    {
    Intent intent=new Intent(LoginActivity.this,SignUp.class);
    startActivity(intent);
    }
      void forgotPassword(View view)
     {
       Intent intent=new Intent(LoginActivity.this,Password_reset.class);
       startActivity(intent);
     }

}

