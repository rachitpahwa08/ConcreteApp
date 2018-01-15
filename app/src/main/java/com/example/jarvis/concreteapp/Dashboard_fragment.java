package com.example.jarvis.concreteapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Jarvis on 21-12-2017.
 */

public class Dashboard_fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.dashboard_fragment, container, false);
       //Set onclick Listener for buttons in Dashboard fragment


        Button about_concrete = (Button) rootView.findViewById(R.id.about_concrete);
        about_concrete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AboutConcrete.class);
                startActivity(intent);
            }
        });
        Button quality = (Button) rootView.findViewById(R.id.quality);
        quality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Quality.class);
                startActivity(intent);
            }
        });
        Button order_book = (Button) rootView.findViewById(R.id.order_book);
        order_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), OrderBook.class);
                startActivity(intent);
            }
        });

        return rootView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("DashBoard");
    }


}
