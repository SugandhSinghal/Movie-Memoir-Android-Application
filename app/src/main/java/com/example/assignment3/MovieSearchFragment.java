package com.example.assignment3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MovieSearchFragment extends Fragment implements AdapterView.OnItemClickListener {

    ListView movieSearchList;
    ArrayList<MovieList> movieList = new ArrayList<>();
    String MovieNameTemp;
    EditText MovieName;
    String MovieSearchResult;



    public MovieSearchFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*Inflate the View for this fragment*/
        View view = inflater.inflate(R.layout.movie_search, container, false);
        MovieName = view.findViewById(R.id.et_searchMovie);
        Button search = view.findViewById(R.id.bt_search);
        movieSearchList = view.findViewById(R.id.list);
        /*Action that takes when search button is clicked*/
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieNameTemp = MovieName.getText().toString();
                /*Async task is called to search the movie based on movie name entered by user*/
                MovieSearchAsyncTask updateAsyncTask=new MovieSearchAsyncTask();
                updateAsyncTask.execute(MovieNameTemp);
            }
        });
        /*Adapter for list view*/
        MovieSearchAdapter movieSearchAdapter = new MovieSearchAdapter(this.getActivity(),R.layout.list_view_search,movieList);
        movieSearchList.setAdapter(movieSearchAdapter);
        /*Registering on item click*/
        movieSearchList.setOnItemClickListener(this);
        return view;
    }

    /*This method notes the instance on click on any item in list view and direct user to view movie fragment*/
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        MovieViewFragment movieViewFragment = new MovieViewFragment();
        Bundle bundle = new Bundle();
        MovieList obj = (MovieList) parent.getItemAtPosition(position);
        bundle.putSerializable("MovieList",obj);
        movieViewFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.content_frame, movieViewFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    /*Adapter for list view*/
    private class MovieSearchAsyncTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params) {
            MovieSearchResult = MovieSearchAPI.search(MovieNameTemp);
            return MovieSearchResult ;
        }
        @Override
        protected void onPostExecute(String s) {
            JSONObject jsonObject = null;
            try{
                if (s != null)
                {
                    jsonObject = new JSONObject(s);

                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < 6; i++)
                    {
                        JSONObject object = jsonArray.getJSONObject(i);
                        MovieList movielist = new MovieList();
                        movielist.setTitle(object.getString("title"));
                        movielist.setRelease_date(object.getString("release_date"));
                        movielist.setPoster_path("https://image.tmdb.org/t/p/w500/" + object.getString("poster_path"));
                        movielist.setId(object.getString("id"));
                        movielist.setOver_view(object.getString("overview"));
                        movielist.setRating(object.getString("vote_average"));
                        movieList.add(movielist);
                    }
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}


