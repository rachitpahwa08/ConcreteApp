package com.example.jarvis.concreteapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.example.jarvis.concreteapp.model.CustomerSite;
import com.example.jarvis.concreteapp.model.PO;



import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


/**
 * Created by Jarvis on 23-01-2018.
 */

public class PoAdapter extends RecyclerView.Adapter<PoAdapter.PoViewHolder> {


    List<PO> poList;
    List<CustomerSite> customerSite;

    public PoAdapter(List<PO> poList,List<CustomerSite> customerSite) {

        this.customerSite=customerSite;
        this.poList = poList;
    }

    @Override
    public PoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.availabe_po_cards,parent,false);
        PoViewHolder poViewHolder=new PoViewHolder(itemView);
        return poViewHolder;
    }

    @Override
    public void onBindViewHolder(PoViewHolder holder, final int position) {
        long milliseconds=Long.parseLong(poList.get(position).getGenerationDate());
        Date date = new Date(milliseconds);
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM,yyyy", Locale.ENGLISH);


        holder.gendate.setText(formatter.format(date));
        holder.price.setText("\u20B9"+String.valueOf(poList.get(position).getPrice()));
        if(!poList.get(position).getConfirmedBySupplier()&&!poList.get(position).getDeletedByContractor())
        {
            holder.confirm_status.setText("Not Confirmed");
        }
        else if(poList.get(position).getDeletedByContractor())
        {
            holder.confirm_status.setText("PO deleted");
        }
        else holder.confirm_status.setText("Confirmed");
        holder.requestedby.setText("Requested By:"+poList.get(position).getRequestedBy());
        final PO po=poList.get(position);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(view.getContext(),POdetails.class);
                i.putExtra("PO", po);
                i.putExtra("customersite",getSitename(customerSite.get(position).getId()));
                view.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return poList.size();
    }

    public  static class PoViewHolder extends RecyclerView.ViewHolder{
        TextView gendate,price,confirm_status,requestedby;
        Button button;
        CardView cardView;
    public PoViewHolder(View itemView) {
        super(itemView);

        cardView=(CardView)itemView.findViewById(R.id.PO_card);
        gendate=(TextView)itemView.findViewById(R.id.po_date);
        price=(TextView)itemView.findViewById(R.id.price_PO);
        confirm_status=(TextView)itemView.findViewById(R.id.POstatus);
        requestedby=(TextView)itemView.findViewById(R.id.PO_requestedby);
        button=(Button)itemView.findViewById(R.id.po_detail);
    }
}

    String getSitename(String id)
    { int position1=0;
        for(int i=0;i<customerSite.size();i++)
        {
            if(id==customerSite.get(i).getId())
            {
                position1=i;
                break;
            }
        }
        return customerSite.get(position1).getName();
    }
}
