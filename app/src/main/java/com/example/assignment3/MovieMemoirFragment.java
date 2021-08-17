package com.example.assignment3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.github.mikephil.charting.data.BarEntry;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MovieMemoirFragment extends Fragment implements AdapterView.OnItemClickListener {
    ListView movieMemoirList;
    ArrayList<String> post;
    ArrayList<objectMovieMemoir> memoirList = new ArrayList<>();

    public MovieMemoirFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*Inflate the View for this fragment*/
        View view = inflater.inflate(R.layout.movie_memoir_fragment, container, false);
        movieMemoirList = view.findViewById(R.id.lv_movie_memoir);

        /*Getting the memoir list through shared preferences*/
        SharedPreferences sharedPref= getActivity().getSharedPreferences("File_User",
                Context.MODE_PRIVATE);
        String movieMemoir= sharedPref.getString("memoirlist",null);

        /*Storing the values of all the movies in and object of type objectMovieMemoir and then adding those objects to arraylist*/
        JSONArray jsonArray = null;
        try{
            if(movieMemoir != null){
                jsonArray = new JSONArray(movieMemoir);
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    objectMovieMemoir omm = new objectMovieMemoir();
                    omm.setMovieName(object.getString("movieName"));
                    omm.setMovieReleasedate(object.getString("movieReleasedate"));
                    omm.setUserdate(object.getString("userdate"));
                    omm.setCinemaPostcode(object.getJSONObject("cinemaId").getString("cinemaPostcode"));
                    omm.setMemoComment(object.getString("memoComment"));
                    omm.setMemoRating(object.getString("memoRating"));
                    String movieName = omm.getMovieName();
                    String result = MovieSearchAPI.search(movieName);
                    String poster = MovieSearchAPI.getMoviePoster(result);
                    omm.setPoster_path(poster);
                    memoirList.add(omm);
               }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*adding the above arraylist of received movies in the customised adapter*/
        MovieMemoirAdapter movieMemoirAdapter = new MovieMemoirAdapter(this.getActivity(),R.layout.list_view_memoir,memoirList);
        movieMemoirList.setAdapter(movieMemoirAdapter);
        movieMemoirList.setOnItemClickListener(this);


        return view;
    }

     /*Async task to get the poster path from search option(code is not used because was confused how to use it inside arraylist as will be displayed in list view)*/
    private class UpdateDataAsyncTask1 extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params) {
            String result = MovieSearchAPI.search(params[0]);
            return result;
        }
        @Override
        protected void onPostExecute(String result) {

            String poster = MovieSearchAPI.getMoviePoster(result);
            post.add(poster);

        }
    }

    /*On click of item will be directed to new page showing movie details*/
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        MovieMemoirViewFragment movieMemoirViewFragment = new MovieMemoirViewFragment();
        Bundle bundle = new Bundle();
        objectMovieMemoir obj = (objectMovieMemoir) parent.getItemAtPosition(position);
        bundle.putSerializable("objectMovieMemoir", obj);
        movieMemoirViewFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.content_frame, movieMemoirViewFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}
