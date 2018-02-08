package com.example.jarvis.concreteapp;

import android.app.Fragment;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ListView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Jarvis on 31-01-2018.
 */

public class Quantity_fragment extends android.support.v4.app.Fragment {

    ListView list;
    String[] web = {
            "Concrete slab Calculator",
            "Concrete Footing Calculator",
            "Concrete Wall Calculator",
            "Concrete Column Calculator",
            "Curb and gutter Barrier Calculator",
            "Stairs Calculator Calculator"
    } ;
    Integer[] imageId = {
            R.drawable.slab_rect,
            R.drawable.concrete_footing,
            R.drawable.concrete_wall,
            R.drawable.concrete_column,
            R.drawable.curb_barrier,
            R.drawable.stairs,
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.quantity_fragment,null);
        CalculatorList adapter = new CalculatorList(getActivity(), web, imageId);
        list=(ListView)view.findViewById(R.id.calclist);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent=new Intent(getContext(),Calculator.class);
                intent.putExtra("Title",web[i]);
                intent.putExtra("imageid",imageId[i]);
                intent.putExtra("index",i);
                startActivity(intent);
            }
        });
        return view;
    }
}
