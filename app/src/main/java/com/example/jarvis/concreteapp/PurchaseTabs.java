package com.example.jarvis.concreteapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Jarvis on 05-01-2018.
 */

public class PurchaseTabs extends FragmentStatePagerAdapter {

    String[] titles=new String[]{"Create PO","Available PO"};

    public PurchaseTabs(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0: AskQuote askQuote=new AskQuote();
                return askQuote;
            case 1: AvailableQuote availableQuote=new AvailableQuote();
                return availableQuote;

            default:return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
