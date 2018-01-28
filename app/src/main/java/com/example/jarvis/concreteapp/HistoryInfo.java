package com.example.jarvis.concreteapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jarvis.concreteapp.model.Order;
import com.example.jarvis.concreteapp.model.PO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HistoryInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_info);
        Intent i=getIntent();
       final Order order;
        order=i.getParcelableExtra("Order");
        String site=i.getStringExtra("ordersite");
        TextView gendate,required_date,quality,quantity,requestedby,price,ordersite,statusdesc;
        gendate=(TextView)findViewById(R.id.order_gen_date);
        required_date=(TextView)findViewById(R.id.order_required);
        quality=(TextView)findViewById(R.id.order_quality);
        quantity=(TextView)findViewById(R.id.order_quantity);
        requestedby=(TextView)findViewById(R.id.order_requestedby);
        price=(TextView)findViewById(R.id.order_price);
        statusdesc=(TextView)findViewById(R.id.order_desc);
        ordersite=(TextView)findViewById(R.id.order_site);
        Button issue=(Button)findViewById(R.id.issue_submit);
        issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HistoryInfo.this,Issues.class);
                 intent.putExtra("Order", order);
                   startActivity(intent);
            }
        });
        long milliseconds=Long.parseLong(order.getGenerationDate());
        Date date = new Date(milliseconds);
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM,yyyy", Locale.ENGLISH);
        gendate.setText("Order Date:"+formatter.format(date));
        required_date.setText("Requested Delivery:"+order.getRequiredByDate());
        quality.setText("Quality:"+order.getQuality());
        quantity.setText("Quantity:"+order.getQuantity());
        requestedby.setText("Order Requested By:"+order.getRequestedBy());
        price.setText("Price:\u20B9"+order.getPrice());
        statusdesc.setText(order.getStatusDesc());
        ordersite.setText("Customer Site:"+site);

    }
}
