package com.example.jarvis.concreteapp;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.example.jarvis.concreteapp.model.Response;
import com.example.jarvis.concreteapp.model.Result;
import com.example.jarvis.concreteapp.model.User;
import com.example.jarvis.concreteapp.network.RetrofitInterface;
import com.example.jarvis.concreteapp.utils.Constants;
import com.example.jarvis.concreteapp.utils.SharedPreferencesHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.subscriptions.CompositeSubscription;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    //private FirebaseAuth mAuth;
    //private FirebaseAuth.AuthStateListener mAuthListener;
    private Button signin;
    private ProgressBar progressBar;
    private CheckBox checkBox;
    public static final String TAG = LoginActivity.class.getSimpleName();
    private CompositeSubscription mSubscriptions;
    private SharedPreferences mSharedPreferences;
    private RelativeLayout mview;
    private String email,password;
    Result result;
    Context context;
    CheckBox cb_remember_me;

    Gson gson = new GsonBuilder().setLenient().create();

    OkHttpClient client = new OkHttpClient();
    Retrofit.Builder builder=new Retrofit.Builder().baseUrl("http://35.200.128.175").client(client).addConverterFactory(GsonConverterFactory.create(gson));
  Retrofit retrofit=builder.build();
   RetrofitInterface retrofitInterface=retrofit.create(RetrofitInterface.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mview = (RelativeLayout) findViewById(R.id.login);
        mSubscriptions = new CompositeSubscription();
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        signin = (Button) findViewById(R.id.email_sign_in_button);
        progressBar = (ProgressBar) findViewById(R.id.login_progress);
        context = this.getApplicationContext();
        cb_remember_me = (CheckBox) findViewById(R.id.cb_remember);

        signin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                 email=mEmailView.getText().toString();
                 password=mPasswordView.getText().toString();
                startSignin();
            }
        });
    }


   /* @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }*/

    /*public void login(final View view) {
        RequestParams params = new RequestParams();
        params.put("username", mEmailView.getText().toString());
        params.put("password", mPasswordView.getText().toString());

        AsyncClient.post("/login", params, new mJsonHttpResponseHandler(this) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getInt(context.getString(R.string.server_response)) == 1) {

                       /*if (cb_remember_me.isChecked()) {

                           SharedPreferencesHandler.writeString(context, "username", mEmailView.getText().toString());
                           SharedPreferencesHandler.writeString(context, "password", mPasswordView.getText().toString());
                           SharedPreferencesHandler.writeBoolean(context, "rememberMe", true);

                       }*/

                       /* Toast.makeText(context, response.getString(context.getString(R.string.server_message)), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(context, DashBoard.class);
                        startActivity(i);
                        finish();
                    } else if (response.getInt(context.getString(R.string.server_response)) == 0) {
                        Toast.makeText(context, response.getString(context.getString(R.string.server_message)), Toast.LENGTH_SHORT).show();
                        view.setEnabled(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }*/



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
        progressBar.setVisibility(View.VISIBLE);

          /*mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(@NonNull Task<AuthResult> task) {
                 progressBar.setVisibility(View.GONE);
                  if(task.isSuccessful())
                  {
                   Intent intent= new Intent(LoginActivity.this,DashBoard.class);
                      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                      startActivity(intent);
                  }
                  else{

                      Toast.makeText(LoginActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                  }
              }
          });*/


   }
   private void loginprocess()
   {
      //Login login=new Login(email,password);

       Call<Result> call=retrofitInterface.login(email,password);


       call.enqueue(new Callback<Result>() {
           @Override
           public void onResponse(Call<Result> call, retrofit2.Response<Result> response) {

              result=  response.body(); // have your all data
               User user =result.getUser();
              String id=user.getId();
                 Toast.makeText(LoginActivity.this,id,Toast.LENGTH_SHORT).show();
                   progressBar.setVisibility(View.GONE);
                   Log.e("TAG", "response 33: "+new Gson().toJson(response.body()));
               Log.e("TAG", "response 33: "+response.body());
                   Intent intent=new Intent(LoginActivity.this,DashBoard.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   intent.putExtra("Result",result);
                   startActivity(intent);


             }
           @Override
           public void onFailure(Call<Result> call, Throwable t) {
               Toast.makeText(LoginActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
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
    /*private void initSharedPreferences() {

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
    }

    private void loginProcess(String email, String password) {

        mSubscriptions.add(NetworkUtil.getRetrofit(email, password).login()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }


    private void handleResponse(Response response) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this,response.getToken(),Toast.LENGTH_LONG).show();
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Constants.TOKEN,response.getToken());
        editor.putString(Constants.EMAIL,response.getMessage());
        editor.apply();

        mEmailView.setText(null);
        mPasswordView.setText(null);

        Intent intent = new Intent(this, DashBoard.class);
        startActivity(intent);
        Toast.makeText(this,"Login",Toast.LENGTH_LONG).show();

    }

    private void handleError(Throwable error) {

        progressBar.setVisibility(View.GONE);

        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();

            try {

                String errorBody = ((HttpException) error).response().errorBody().string();
                Response response = gson.fromJson(errorBody,Response.class);
                showSnackBarMessage(response.getMessage());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            showSnackBarMessage("Network Error !");
        }
    }
    private void showSnackBarMessage(String message) {

        if (mview != null) {

            Snackbar.make(mview,message,Snackbar.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }*/
}

