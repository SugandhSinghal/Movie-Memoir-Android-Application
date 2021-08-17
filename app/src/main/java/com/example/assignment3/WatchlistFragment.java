package com.example.assignment3;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class WatchlistFragment extends Fragment implements AdapterView.OnItemClickListener{

    WatchlistDatabase db = null;
    WatchlistViewModel watchlistViewModel;
    ListView moviewatchlist;
    ArrayList<WatchlistObject> watchlistArray = new ArrayList<>();
    WatchlistObject obj;

    public WatchlistFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*Inflate the View for this fragment*/
        View view = inflater.inflate(R.layout.watchlist_fragment, container, false);

        Button deleteMovie = view.findViewById(R.id.bt_delete);
        Button watchDetails = view.findViewById(R.id.bt_view);

        moviewatchlist = view.findViewById(R.id.watchlist_list);
        watchlistViewModel = new ViewModelProvider(this).get(WatchlistViewModel.class);
        watchlistViewModel.initalizeVars(getActivity().getApplication());
      //  moviewatchlist.setOnItemClickListener(this);
        /*Code to delete the selected item from the list(didn't worked because there was problem in fetching instance)*/
        deleteMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // moviewatchlist.setOnClickListener((View.OnClickListener) getActivity());
               Watchlist w =  watchlistViewModel.findByName(obj.getMovieName());
               watchlistViewModel.delete(w);

            }
        });

        /*Code to transfer the selected movie to movie view screen (code didn't work because there was problem in fetching instance of selected option)*/
  /*      moviewatchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new MovieViewFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });*/
       // watchlistViewModel.deleteAll(); / *testing purpose*/

        /*Code to load all the movies from watchlist and set to list view*/
        watchlistViewModel.getAllWatchlists().observe(this, new Observer<List<Watchlist>>() {
            @Override
            public void onChanged(List<Watchlist> watchlists) {
                String allWatchlist = "";
                for (Watchlist temp : watchlists) {
                    WatchlistObject obj = new WatchlistObject();
                    obj.setMovieName(temp.getMovie_name());
                    obj.setReleaseDate(temp.getRelease_date());
                    obj.setAddDate(temp.getDate_added());
                    watchlistArray.add(obj);
                }

                WatchlistAdapter watchlistAdapter = new WatchlistAdapter(getActivity(), R.layout.list_view_watchlist, watchlistArray);
                moviewatchlist.setAdapter(watchlistAdapter);
            }

        });

        return view;
    }

    /*Code to get the item instance*/
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        obj = (WatchlistObject) parent.getItemAtPosition(position);
    }

}

