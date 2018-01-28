package com.example.jarvis.concreteapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jarvis.concreteapp.model.Result;

/**
 * Created by Jarvis on 05-01-2018.
 */

public class AvailableQuote extends Fragment {
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.available_quote,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.quote_recyclerview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(gridLayoutManager);
        Result res1=((RequestQuote)getActivity()).res;
        QuotesAdapter quotesAdapter=new QuotesAdapter(res1.getQuotes(),res1.getUser().getCustomerSite());
        recyclerView.setAdapter(quotesAdapter);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
