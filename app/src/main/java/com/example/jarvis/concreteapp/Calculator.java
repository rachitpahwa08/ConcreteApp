package com.example.jarvis.concreteapp;

import android.content.Context;
import android.content.Intent;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

public class Calculator extends AppCompatActivity {
    EditText feild1,feild2,feild3,feild4,result;
    TextInputLayout t1,t2,t3,t4;
    TextView shape_details;
    ImageView shape;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        shape_details=(TextView)findViewById(R.id.shapetype);
        shape=(ImageView)findViewById(R.id.shapeimg);
        Intent intent=getIntent();
        int img=intent.getIntExtra("imageid",R.drawable.ic_launcher_background);
        pos=intent.getIntExtra("index",0);
        String calcname=intent.getStringExtra("Title");
        shape_details.setText(calcname);
        shape.setImageResource(img);
        result=(EditText)findViewById(R.id.result);
        Button calculate=(Button)findViewById(R.id.calculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                switch (pos+1)
                {
                    case 1: if(feild1.getText().toString().isEmpty())
                    {
                        feild1.setError("Requiered Field");
                        feild1.requestFocus();
                        return;
                    }
                        if(feild2.getText().toString().isEmpty())
                        {
                            feild2.setError("Requiered Field");
                            feild2.requestFocus();
                            return;
                        }
                        if(feild3.getText().toString().isEmpty())
                        {
                            feild3.setError("Requiered Field");
                            feild3.requestFocus();
                            return;
                        }

                            calSlab();
                            break;
                    case 2: if(feild1.getText().toString().isEmpty())
                        {feild1.setError("Requiered Field");
                         feild1.requestFocus();
                          return;
                        }
                    if(feild2.getText().toString().isEmpty())
                    {
                        feild2.setError("Requiered Field");
                        feild2.requestFocus();
                        return;
                    }
                    if(feild3.getText().toString().isEmpty())
                    {
                        feild3.setError("Requiered Field");
                        feild3.requestFocus();
                    }
                           calFooting();
                            break;
                    case 3: if(feild1.getText().toString().isEmpty())
                {
                    feild1.setError("Requiered Field");
                    feild1.requestFocus();
                    return;
                }
                    if(feild2.getText().toString().isEmpty())
                    {
                        feild2.setError("Requiered Field");
                        feild2.requestFocus();
                        return;
                    }
                    if(feild3.getText().toString().isEmpty())
                    {
                        feild3.setError("Requiered Field");
                        feild3.requestFocus();
                        return;
                    }
                           calWall();
                           break;
                    case 4:if(feild1.getText().toString().isEmpty())
                    {
                        feild1.setError("Requiered Field");
                        feild1.requestFocus();
                        return;
                    }
                        if(feild2.getText().toString().isEmpty())
                        {
                            feild2.setError("Requiered Field");
                            feild2.requestFocus();
                            return;
                        }

                          calColoumn();
                           break;
                    case 5:if(feild1.getText().toString().isEmpty())
                    {
                        feild1.setError("Requiered Field");
                        feild1.requestFocus();
                        return;
                    }
                        if(feild2.getText().toString().isEmpty())
                        {
                            feild2.setError("Requiered Field");
                            feild2.requestFocus();
                            return;
                        }
                        if(feild3.getText().toString().isEmpty())
                        {
                            feild3.setError("Requiered Field");
                            feild3.requestFocus();
                            return;
                        }
                        if(feild4.getText().toString().isEmpty())
                        {
                            feild4.setError("Requiered Field");
                            feild4.requestFocus();
                            return;
                        }
                          calCurb();
                           break;
                    case 6:if(feild1.getText().toString().isEmpty())
                    {
                        feild1.setError("Requiered Field");
                        feild1.requestFocus();
                        return;
                    }
                        if(feild2.getText().toString().isEmpty())
                        {
                            feild2.setError("Requiered Field");
                            feild2.requestFocus();
                            return;
                        }
                        if(feild3.getText().toString().isEmpty())
                        {
                            feild3.setError("Requiered Field");
                            feild3.requestFocus();
                            return;
                        }
                        if(feild4.getText().toString().isEmpty())
                        {
                            feild4.setError("Requiered Field");
                            feild4.requestFocus();
                            return;
                        }
                          calStairs();
                           break;
                    default:feild1.setText("0");
                        feild2.setText("0");
                        feild3.setText("0");
                        feild4.setText("0");
                }
            }
        });
        switch (pos+1)
        {
            case 1:t1=(TextInputLayout) findViewById(R.id.t1);
                   t1.setHint("Length(In Feet)");
                   feild1=(EditText)findViewById(R.id.field1);
                   t2=(TextInputLayout) findViewById(R.id.t2);
                   t2.setHint("Width(In Feet)");
                feild2=(EditText)findViewById(R.id.field2);
                   t3=(TextInputLayout) findViewById(R.id.t3);
                   t3.setHint("Thickness(In Inches)");
                   feild3=(EditText) findViewById(R.id.field3);
                View namebar = findViewById(R.id.field4);
                ((ViewGroup) namebar.getParent()).removeView(namebar);
                   //feild4.setVisibility(View.GONE);

                   break;
            case 2:t1=(TextInputLayout) findViewById(R.id.t1);
                   t1.setHint("Length(In Feet)");
                feild1=(EditText)findViewById(R.id.field1);
                   t2=(TextInputLayout) findViewById(R.id.t2);
                   t2.setHint("Width(In Inches)");
                feild2=(EditText)findViewById(R.id.field2);
                   t3=(TextInputLayout) findViewById(R.id.t3);
                   t3.setHint("Height(In Inches)");
                feild3=(EditText) findViewById(R.id.field3);
                View namebar1 = findViewById(R.id.field4);
                ((ViewGroup) namebar1.getParent()).removeView(namebar1);
                   //feild4.setVisibility(View.GONE);
                break;
            case 3:t1=(TextInputLayout) findViewById(R.id.t1);
                t1.setHint("Length(In Feet)");
                feild1=(EditText)findViewById(R.id.field1);
                t2=(TextInputLayout) findViewById(R.id.t2);
                t2.setHint("Height(In Feet)");
                feild2=(EditText)findViewById(R.id.field2);
                t3=(TextInputLayout) findViewById(R.id.t3);
                t3.setHint("Thickness(In Inches)");
                feild3=(EditText) findViewById(R.id.field3);
                View namebar2 = findViewById(R.id.field4);
                ((ViewGroup) namebar2.getParent()).removeView(namebar2);
                //feild4.setVisibility(View.GONE);

                break;
            case 4:t1=(TextInputLayout) findViewById(R.id.t1);
                t1.setHint("Diameter(In Inches)");
                feild1=(EditText)findViewById(R.id.field1);
                t2=(TextInputLayout) findViewById(R.id.t2);
                t2.setHint("Height(In Feet)");
                feild2=(EditText)findViewById(R.id.field2);
                View namebar3 = findViewById(R.id.field3);
                ((ViewGroup) namebar3.getParent()).removeView(namebar3);
                View namebar4 = findViewById(R.id.field4);
                ((ViewGroup) namebar4.getParent()).removeView(namebar4);
                // feild4.setVisibility(View.GONE);
                break;
            case 5:t1=(TextInputLayout) findViewById(R.id.t1);
                t1.setHint("Length(In Feet)");
                feild1=(EditText)findViewById(R.id.field1);
                t2=(TextInputLayout) findViewById(R.id.t2);
                t2.setHint("Flag Thickness(In Inches)");
                feild2=(EditText)findViewById(R.id.field2);
                t3=(TextInputLayout) findViewById(R.id.t3);
                t3.setHint("Gutter Width(In Inches)");
                feild3=(EditText) findViewById(R.id.field3);
                t4=(TextInputLayout) findViewById(R.id.t4);
                t4.setHint("Curb Height(In Inches)");
                feild4=(EditText)findViewById(R.id.field4);
                break;
            case 6:t1=(TextInputLayout) findViewById(R.id.t1);
                t1.setHint("Number of stairs");
                feild1=(EditText)findViewById(R.id.field1);
                t2=(TextInputLayout) findViewById(R.id.t2);
                t2.setHint("Tread(In Inches)");
                feild2=(EditText)findViewById(R.id.field2);
                t3=(TextInputLayout) findViewById(R.id.t3);
                t3.setHint("Riser(In Inches)");
                feild3=(EditText) findViewById(R.id.field3);
                t4=(TextInputLayout) findViewById(R.id.t4);
                t4.setHint("Width(In Feet)");
                feild4=(EditText)findViewById(R.id.field4);
                break;
             default:   feild1=(EditText) findViewById(R.id.field1);
                 feild1.setHint("Number of stairs");

                 feild2=(EditText) findViewById(R.id.field2);
                 feild2.setHint("Tread(In Inches)");

                 feild3=(EditText) findViewById(R.id.field3);
                 feild3.setHint("Riser(In Inches)");

                 feild4=(EditText) findViewById(R.id.field4);
                 feild4.setHint("Width(In Feet)");

        }


    }

    private void calStairs()
    {
      double ns,t,r,w;
      ns=Float.valueOf(feild1.getText().toString());
      t=Float.valueOf(feild2.getText().toString());
      r=Float.valueOf(feild3.getText().toString());
      w=Float.valueOf(feild4.getText().toString());
      double result1=(((((ns * t/12.0)*(ns*r/12.0))/2)+((ns*(t/12.0*r/12.0))/2))*w/27.0)*0.7645 ;
      double sol= Double.parseDouble(new DecimalFormat("##.##").format(result1));
      result.setText(Double.toString(sol)+" Cubic metre");
    }

    private void calCurb()
    {
        double l,ft,gw,ch;
        l=Float.valueOf(feild1.getText().toString());
        ft=Float.valueOf(feild2.getText().toString());
        gw=Float.valueOf(feild3.getText().toString());
        ch=Float.valueOf(feild4.getText().toString());
        double result1=((l*(ft/12.0*(gw/12.0+ch/12.0))+l*(ch/12.0*ch/12.0))/27.0)*0.7645;
        double sol= Double.parseDouble(new DecimalFormat("##.##").format(result1));
        result.setText(Double.toString(sol)+" Cubic metre");
    }

    private void calColoumn()
    {
     double result1=((3.14*Float.valueOf(feild1.getText().toString())*Float.valueOf(feild1.getText().toString())*Float.valueOf(feild2.getText().toString()))/15552)*0.7645;
        double sol= Double.parseDouble(new DecimalFormat("##.##").format(result1));
        result.setText(Double.toString(sol)+" Cubic metre");
    }

    private void calWall()
    {
        double l,h,t;
        l=Float.valueOf(feild1.getText().toString());
        h=Float.valueOf(feild2.getText().toString());
        t=Float.valueOf(feild3.getText().toString());
        double result1=((t/12.0 * l * h)/ 27.0)*0.7645;
        double sol= Double.parseDouble(new DecimalFormat("##.##").format(result1));
        result.setText(Double.toString(sol)+" Cubic metre");
    }

    private void calFooting()
    {
      double result1=((Float.valueOf(feild1.getText().toString())*Float.valueOf(feild2.getText().toString())/12*Float.valueOf(feild3.getText().toString())/ 12.0 )/ 27.0)*0.7645;
        double sol= Double.parseDouble(new DecimalFormat("##.##").format(result1));
        result.setText(Double.toString(sol)+" Cubic metre");
    }

    private void calSlab()
    {
       double result1= ((Float.valueOf(feild1.getText().toString())*Float.valueOf(feild2.getText().toString())*Float.valueOf(feild3.getText().toString())/ 12.0 )/ 27.0)*0.7645;
        double sol= Double.parseDouble(new DecimalFormat("##.##").format(result1));
        result.setText(Double.toString(sol)+" Cubic metre");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
