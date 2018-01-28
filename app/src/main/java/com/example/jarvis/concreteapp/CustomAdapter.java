package com.example.jarvis.concreteapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jarvis.concreteapp.model.CustomerSite;
import com.example.jarvis.concreteapp.model.History;
import com.example.jarvis.concreteapp.model.Order;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Jarvis on 22-01-2018.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private List<Order> my_data;
    private List<CustomerSite> customerSite;
    int position1;
    public CustomAdapter(List<Order> my_data,List<CustomerSite> customerSite) {

        this.customerSite=customerSite;
        this.my_data = my_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_card,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        long milliseconds=Long.parseLong(my_data.get(position).getGenerationDate());
        Date date = new Date(milliseconds);
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM,yyyy", Locale.ENGLISH);
        holder.orderdate.setText(formatter.format(date));
        holder.status.setText(my_data.get(position).getStatus());
        holder.requestedby.setText("Order Requested By:"+my_data.get(position).getRequestedBy());
        holder.price.setText("\u20B9"+my_data.get(position).getPrice());

        final Order order=my_data.get(position);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(view.getContext(),HistoryInfo.class);
                i.putExtra("Order", order);
                i.putExtra("ordersite",getSitename(my_data.get(position).getId()));
                view.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{

        public TextView orderdate,status,requestedby,price;
             Button button;

        public ViewHolder(View itemView) {
            super(itemView);
            orderdate = (TextView) itemView.findViewById(R.id.order_date);
            status=(TextView)itemView.findViewById(R.id.status);
            requestedby=(TextView)itemView.findViewById(R.id.requestedby);
            price=(TextView)itemView.findViewById(R.id.price_history);
            button=(Button)itemView.findViewById(R.id.history_details);
        }
    }
    String getSitename(String id)
    { int position1;
        for(int i=0;i<customerSite.size();i++)
        {
            if(id==customerSite.get(i).getId())
            {
                position1=i;
                return customerSite.get(position1).getName();
            }
        }
        return "Null";
    }
}