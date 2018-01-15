package com.example.jarvis.concreteapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.zip.Inflater;

/**
 * Created by Jarvis on 21-12-2017.
 */

public class Purchase_fragment extends Fragment {

    private FragmentActivity myContext;
    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.purchase_fragment,null);
        TabLayout tabLayout=(TabLayout) view.findViewById(R.id.purchase_tab);
        ViewPager viewPager=(ViewPager) view.findViewById(R.id.purchase_viewpager);


        PurchaseTabs purchaseTabs=new PurchaseTabs(myContext.getSupportFragmentManager());
        viewPager.setAdapter(purchaseTabs);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Purchase");
    }
}
