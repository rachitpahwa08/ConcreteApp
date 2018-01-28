package com.example.jarvis.concreteapp;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jarvis.concreteapp.model.CustomerSite;
import com.example.jarvis.concreteapp.model.Quote;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Jarvis on 24-01-2018.
 */

public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.ViewHolder>{

    private List<Quote> quoteList;
    private List<CustomerSite> customerSite;

    public QuotesAdapter(List<Quote> quoteList,List<CustomerSite> customerSite) {
        this.quoteList = quoteList;
        this.customerSite=customerSite;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.quotes_card,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        long milliseconds=Long.parseLong(quoteList.get(position).getGenerationDate());
        Date date = new Date(milliseconds);
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM,yyyy", Locale.ENGLISH);
        holder.gendate.setText(formatter.format(date));
        holder.customersite.setText(getSitename(quoteList.get(position).getCustomerSite()));
        holder.requestedby.setText("Requested By"+quoteList.get(position).getRequestedBy());
        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(view.getContext(),QuoteInfo.class);
                i.putExtra("quote", quoteList.get(position));
                i.putExtra("customersite_quote",getSitename(customerSite.get(position).getId()));
                view.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return quoteList.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{

        public TextView gendate,requestedby,customersite;
        public Button info;

        public ViewHolder(View itemView) {
            super(itemView);
            gendate = (TextView) itemView.findViewById(R.id.quote_gen_date);
            requestedby=(TextView)itemView.findViewById(R.id.quote_requestedby);
            customersite=(TextView)itemView.findViewById(R.id.quotecustomer_site);
            info=(Button)itemView.findViewById(R.id.quote_details);
        }
    }
   String getSitename(String id)
   { int position1=0;
    for(int i=0;i<customerSite.size();i++)
    {
        if(id==customerSite.get(i).getId())
        {
            position1=i;
            Log.e("TAG", "response 33: "+customerSite.get(i).getId()+"id="+id);
        }
    }

    return customerSite.get(position1).getName();
   }
}
