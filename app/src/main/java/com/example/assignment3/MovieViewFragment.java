package com.example.assignment3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MovieViewFragment extends Fragment {
    private TextView movie_name , movie_releasedate, genre, cast, country, director, story;
    private RatingBar rating;
    View view;
    Button add2Memoir, add2Watchlist;
    WatchlistDatabase db = null;
    WatchlistViewModel watchlistViewModel;
    MovieList obj;

    public MovieViewFragment(){
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstancestate){
        /*Inflate the View for this fragment*/
        view = inflater.inflate(R.layout.movie_view_fragment,container,false);
        /*getting object of type MovieList from the MovieSearchFragment*/
        Bundle bundle = getArguments();
        obj = (MovieList) bundle.getSerializable("MovieList");
        /* assigning values to all the fields in view screen*/
        movie_name = (TextView) view.findViewById(R.id.tv_nameMovie);
        movie_releasedate = (TextView) view.findViewById(R.id.tv_release_date);
        story = (TextView) view.findViewById(R.id.tv_story);
        rating = (RatingBar)  view.findViewById(R.id.Rating);
        add2Memoir = (Button) view.findViewById(R.id.bt_addMemoir);
        add2Watchlist = (Button) view.findViewById(R.id.bt_addWatchlist);
        movie_name.setText(obj.getTitle());
        movie_releasedate.setText("RELEASE DATE: " + obj.getRelease_date());
        story.setText("PLOT: " + obj.getOver_view());
        rating.setRating(Math.round(Float.parseFloat(obj.getRating())/2));

        /*Calling async tasks to get data about gener country*/
        MovieViewFragment.AsyncTaskGenerCountry dataForView = new MovieViewFragment.AsyncTaskGenerCountry();
        dataForView.execute(obj.getId());

        /*Calling async tasks to get data about gener country*/
        MovieViewFragment.AsyncTaskCredit creditAsync = new MovieViewFragment.AsyncTaskCredit();
        creditAsync.execute(obj.getId());

        /*code to get data from watchlist table using observer pattern of view model*/
        watchlistViewModel = new ViewModelProvider(this).get(WatchlistViewModel.class);
        watchlistViewModel.initalizeVars(getActivity().getApplication());
        watchlistViewModel.getAllWatchlists().observe(this, new Observer<List<Watchlist>>() {
            @Override
            public void onChanged(List<Watchlist> watchlists) {
                String movieName = "";
                for (Watchlist temp : watchlists){
                    String namemovie  = temp.getMovie_name();
                    movieName = movieName +namemovie + "," ;

                }
                /*Storing movie name in array list to compaire it with the */
                String str[] = movieName.split(",");
                List<String> ml = new ArrayList<String>();
                ml = Arrays.asList(str);
                for(int i = 0 ; i < ml.size(); i ++){
                    String s = ml.get(i);
                    if ( obj.getTitle().equals(s))
                    {
                        /*If user choose to select already added movie button would be disabled*/
                        add2Watchlist.setEnabled(false);
                       Toast.makeText(getActivity(),"Movie Exist in Watchlist",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        /*On click of a button transfer to add to memoir page*/
        add2Memoir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                AddToMemoirFragment addToMemoirFragment = new AddToMemoirFragment();
                Bundle bundle = new Bundle();
                MovieList obj1 = obj;
                bundle.putSerializable("MovieList",obj1);
                addToMemoirFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.content_frame, addToMemoirFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        /*On clicking on this button movie will be added to the watchlist*/
        add2Watchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                String movieinfo = obj.getTitle() + ","+ obj.getRelease_date()+ "," + formatter.format(date) ;
                MovieViewFragment.addWatchlist add=new MovieViewFragment.addWatchlist();
                add.execute(movieinfo);

            }
        });

        return view;
    }


    /*Async task to get genre and country*/
    private class AsyncTaskGenerCountry extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return MovieSearchAPI.searchforview(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            String[] list;
            list = MovieSearchAPI.getGenreCountry(result);
            genre = (TextView)  view.findViewById(R.id.tv_genre);
            genre.setText("GENRE: " +list[0]);
            country = (TextView) view.findViewById(R.id.tv_country);
            country.setText("COUNTRY: " + list[1]);
        }

    }

    /*Async task to get cast and director*/
    private class AsyncTaskCredit extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return MovieSearchAPI.searchcredit(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            String[] credits;
            credits = MovieSearchAPI.getCastDirector(result);
            cast = (TextView)  view.findViewById(R.id.tv_cast);
            cast.setText("CAST: " + credits[0]);
            director = (TextView)  view.findViewById(R.id.tv_director);
            director.setText("DIRECTOR: " + credits[1]);
        }

    }


    /* Async task for adding movie to watchlist*/
    private class addWatchlist extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String status = null;
            String [] details;
            try{
                if(!(params[0].isEmpty())) {
                    details = params[0].split(",");
                    if (details.length == 3){
                        Watchlist watchlist = new Watchlist(details[0],details[1], details[2]);
                        watchlistViewModel.insert(watchlist);
                        status = "Movie added to watchlist";
                    }

                } else {
                    status = "Movie not added to watchlist";
                }} catch (Exception e){

            }
        return status ;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
        }

    }

}
