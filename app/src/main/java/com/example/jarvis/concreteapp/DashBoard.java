package com.example.jarvis.concreteapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jarvis.concreteapp.model.Result;
import com.example.jarvis.concreteapp.model.User;
import static android.R.attr.fragment;

public class DashBoard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
Result r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i=getIntent();
        r=i.getParcelableExtra("Result");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
       // View headerView = navigationView.getHeaderView(0);
        //navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        TextView name = (TextView)header.findViewById(R.id.user_name);
        name.setText(r.getUser().getName());
        navigationView.setCheckedItem(R.id.dashboard);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.screen_area,new Dashboard_fragment());
        ft.commit();
        Toast.makeText(DashBoard.this,r.getUser().getId(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment=null;

        if (id == R.id.dashboard) {
            fragment= new Dashboard_fragment();

        } else if (id == R.id.profile) {
            fragment=new Profile_fragment();

        } else if (id == R.id.history) {
             fragment=new History_fragment();
        } else if (id == R.id.purchase) {
             fragment=new Purchase_fragment();
        } else if (id == R.id.issue) {
             fragment=new Issue_fragment();
        } else if (id == R.id.logout) {
            new AlertDialog.Builder(this)
                    .setTitle("Log Out")
                    .setMessage("Are you sure you want to Logout?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //FirebaseAuth.getInstance().signOut();
                            Intent i = new Intent(DashBoard.this, LoginActivity.class);
                            i.setFlags(i.FLAG_ACTIVITY_NEW_TASK | i.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i); // Launch the Homescreen Activity
                            finish();         // Close down the SettingsActivity
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();

        }

        if(fragment!=null)
        {
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction ft=fragmentManager.beginTransaction();
            ft.replace(R.id.screen_area,fragment);

            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
