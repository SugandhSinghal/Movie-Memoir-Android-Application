package com.example.assignment3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.sql.Date;

public class HomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    String fname;
    Integer userId;
    String address;
    Integer postcode;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        /*adding the toolbar*/
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nv);
        toggle = new ActionBarDrawerToggle(this,
                drawerLayout,R.string.Open,R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        /*These two lines of code show the navicon drawer icon top left hand side*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);
        replaceFragment(new HomeFragment());

        /*Getting data from Users object*/
        Bundle bundle = getIntent().getExtras();
        Users user = bundle.getParcelable("user");
        fname = user.getName();
        userId = user.getUserId();
        address = user.getAddress();
        postcode = user.getPostcode();
        String codep = String.valueOf(postcode);
        String Id = String.valueOf(userId);

        /*Async task to get top five movies of user*/
        TopFiveMoviesAsyncTask topFiveAsyncTask=new TopFiveMoviesAsyncTask();
        topFiveAsyncTask.execute(Id);

        /*Async task to get movie memoir*/
        GetMovieMemoirAsyncTask getMovieMemoir = new GetMovieMemoirAsyncTask();
        getMovieMemoir.execute(Id);

        /*Async task to get cimema data*/
        CinemaDataAsyncTask cinemadata =new CinemaDataAsyncTask();
        cinemadata.execute();

        /*User data saved in shared preference*/
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("File_User", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPref.edit();
        ed.putString("fname",fname);
        ed.putString("Id",Id);
        ed.putString("address",address);
        ed.putString("codep",codep);
        ed.apply();

    }

    /*Async task to get cimema data*/
    private class CinemaDataAsyncTask extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground(Void... voids) {
            String cinemalist = RestClient.getCinema();
            return cinemalist;
        }
        @Override
        protected void onPostExecute(String cinemalist) {
            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("File_User", Context.MODE_PRIVATE);
            SharedPreferences.Editor ed = sharedPref.edit();
            ed.putString("cinemalist",cinemalist);
            ed.apply();
        }
    }


    /*Async task to get top five movies of user*/
     private class TopFiveMoviesAsyncTask extends AsyncTask<String, Void, String>
     {
        @Override
        protected String doInBackground(String... params) {
            String topFiveMovies = RestClient.getTopFiveMovies(params[0]);
            return topFiveMovies;
        }
        @Override
        protected void onPostExecute(String topFiveMovies) {
            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("File_User", Context.MODE_PRIVATE);
            SharedPreferences.Editor ed = sharedPref.edit();
            ed.putString("topFiveMovies",topFiveMovies);
            ed.apply();

        }
    }

    /*Async task to get movie memoir*/
     private class GetMovieMemoirAsyncTask extends AsyncTask<String, Void, String>
     {
        @Override
        protected String doInBackground(String... params) {
            String memoirlist = RestClient.getMovieMemoir(params[0]);
            return memoirlist;
        }
        @Override
        protected void onPostExecute(String memoirlist) {
            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("File_User", Context.MODE_PRIVATE);
            SharedPreferences.Editor ed = sharedPref.edit();
            ed.putString("memoirlist",memoirlist);
            ed.apply();

        }
    }



    /*Code to set items fragments and activities to navigation item select*/
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:
                replaceFragment(new HomeFragment());
                break;
            case R.id.nav_movie_search:
                replaceFragment(new MovieSearchFragment());
                break;
            case R.id.nav_movie_memoir:
                replaceFragment(new MovieMemoirFragment());
                break;
            case R.id.nav_watchlist:
                replaceFragment(new WatchlistFragment());
                break;

            case R.id.nav_reports:
                Intent rintent = new Intent(this, MovieReport.class);
                startActivity(rintent);
                return true;
            case R.id.nav_maps:
                Intent intent1 = new Intent(this, MapsActivity.class);
                startActivity(intent1);
                return true;

        }
        /*this code closes the drawer after you selected an item from the menu, otherwise stay open*/
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /*Code for fragment replacement in content frame*/
    private void replaceFragment(Fragment nextFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, nextFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }


}
