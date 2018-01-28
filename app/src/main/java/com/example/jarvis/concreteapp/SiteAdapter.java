package com.example.jarvis.concreteapp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jarvis.concreteapp.model.CustomerSite;
import com.example.jarvis.concreteapp.model.User;
import com.example.jarvis.concreteapp.network.RetrofitInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jarvis on 26-01-2018.
 */

public class SiteAdapter extends RecyclerView.Adapter<SiteAdapter.ViewHolder> {
    Gson gson = new GsonBuilder().create();
    Retrofit.Builder builder=new Retrofit.Builder().baseUrl("http://35.200.128.175").addConverterFactory(GsonConverterFactory.create(gson));
    Retrofit retrofit=builder.build();
    RetrofitInterface retrofitInterface=retrofit.create(RetrofitInterface.class);
private List<CustomerSite> siteList;
private User user;
    public SiteAdapter(List<CustomerSite> siteList,User user) {
        this.siteList = siteList;
        this.user=user;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.site_card,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.sitename.setText("Site Name:"+siteList.get(position).getName());
        holder.siteaddress.setText(siteList.get(position).getAddress());
        holder.deletesite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Map<String,String> map=new HashMap<>();
                map.put("userid",user.getId());
                map.put("siteid",siteList.get(position).getId());
                Call<ResponseBody> call=retrofitInterface.delete_site(map);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(view.getContext(),new Gson().toJson(response.body()),Toast.LENGTH_SHORT).show();
                        Log.e("TAG", "response 33: "+new Gson().toJson(response.body()));
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(view.getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return siteList.size();
    }


    public  class ViewHolder extends  RecyclerView.ViewHolder{

        public TextView sitename,siteaddress;
        Button deletesite;
        public ViewHolder(View itemView) {
            super(itemView);
            sitename = (TextView) itemView.findViewById(R.id.site_name);
            siteaddress=(TextView)itemView.findViewById(R.id.siteaddress);
            deletesite=(Button)itemView.findViewById(R.id.delete_site);
        }
    }
}
