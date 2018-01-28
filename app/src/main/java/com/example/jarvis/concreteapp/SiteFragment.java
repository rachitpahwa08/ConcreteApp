package com.example.jarvis.concreteapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jarvis.concreteapp.model.Result;

/**
 * Created by Jarvis on 21-01-2018.
 */

public class SiteFragment extends Fragment
{
    Result r1;
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.site_fragment,null);
         r1=((DashBoard)getActivity()).r;
        FloatingActionButton addsite=(FloatingActionButton)view.findViewById(R.id.add_site);
        addsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),AddSite.class);
                i.putExtra("User",r1.getUser());
                startActivity(i);
            }
        });
        recyclerView=(RecyclerView)view.findViewById(R.id.siteRecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(gridLayoutManager);
        Result r1=((DashBoard)getActivity()).r;
        SiteAdapter siteAdapter=new SiteAdapter(r1.getUser().getCustomerSite(),r1.getUser());
        recyclerView.setAdapter(siteAdapter);
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Customer Site");
    }
}
