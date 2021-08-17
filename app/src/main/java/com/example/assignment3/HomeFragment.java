package com.example.assignment3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {
    private TextView userfname;
    private TextView current_date;

    public HomeFragment() {

    }

    List<HashMap<String, String>> movieListArray;
    SimpleAdapter myListAdapter;
    ListView movieList;
    HashMap<String,String> map = new HashMap<String,String>();
    HashMap<String,String> map1 = new HashMap<String,String>();
    HashMap<String,String> map2 = new HashMap<String,String>();
    HashMap<String,String> map3 = new HashMap<String,String>();
    HashMap<String,String> map4 = new HashMap<String,String>();

    String[] colHEAD = new String[] {"Movie Name","Release Date","Rating"};
    int[] dataCell = new int[] {R.id.Moviename,R.id.Releasedate,R.id.Rating};

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*Inflate the View for this fragment*/
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        userfname =view.findViewById(R.id.tv_userfname);

         /* Use SharedPreference to get user data and top five movie data from HomeScreen*/
        SharedPreferences sharedPref= getActivity().getSharedPreferences("File_User",
                Context.MODE_PRIVATE);
        String Fname= sharedPref.getString("fname",null);
        userfname.setText(Fname);
        current_date = view.findViewById(R.id.tv_currentdate);
        /*Code to get the current date*/
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String joinDate = dtf.format(now);
        current_date.setText(joinDate);

        /* We have used list view to display top five movies of User and used SimpleAdapter*/
        movieList = view.findViewById(R.id.listViewTopfive);
        movieListArray = new ArrayList<HashMap<String, String>>();
        /*Getting topFiveMovies data through SharedPreference*/
        String MovieList= sharedPref.getString("topFiveMovies",null);
        JSONArray jsonArray = null;
        try{
            if (MovieList != null)
            {
            jsonArray = new JSONArray(MovieList);
            JSONObject m1 = (JSONObject)jsonArray.get(0);
            JSONObject m2 = (JSONObject)jsonArray.get(1);
            JSONObject m3 = (JSONObject)jsonArray.get(2);
            JSONObject m4 = (JSONObject)jsonArray.get(3);
            JSONObject m5 = (JSONObject)jsonArray.get(4);

            String m1name = m1.getString("movieName");
            String m1date = m1.getString("movieReleaseDate");
            Integer m1rating = m1.getInt("memoRating");
            map.put("Movie Name",  m1name);
            map.put("Release Date",m1date);
            map.put("Rating",m1rating.toString());
            movieListArray.add(map);

            String m2name = m2.getString("movieName");
            String m2date = m2.getString("movieReleaseDate");
            Integer m2rating = m2.getInt("memoRating");
            map1.put("Movie Name",  m2name);
            map1.put("Release Date",m2date);
            map1.put("Rating",m2rating.toString());
            movieListArray.add(map1);


            String m3name = m3.getString("movieName");
            String m3date = m3.getString("movieReleaseDate");
            Integer m3rating = m3.getInt("memoRating");
            map2.put("Movie Name",  m3name);
            map2.put("Release Date",m3date);
            map2.put("Rating",m3rating.toString());
            movieListArray.add(map2);


            String m4name = m4.getString("movieName");
            String m4date = m4.getString("movieReleaseDate");
            Integer m4rating = m4.getInt("memoRating");
            map3.put("Movie Name",  m4name);
            map3.put("Release Date",m4date);
            map3.put("Rating",m4rating.toString());
            movieListArray.add(map3);


            String m5name = m5.getString("movieName");
            String m5date = m5.getString("movieReleaseDate");
            Integer m5rating = m5.getInt("memoRating");
            map4.put("Movie Name",  m5name);
            map4.put("Release Date",m5date);
            map4.put("Rating",m5rating.toString());
            movieListArray.add(map4);
            }

            myListAdapter = new SimpleAdapter(this.getActivity(),movieListArray,R.layout.list_view_topfive, colHEAD, dataCell);
            movieList.setAdapter(myListAdapter);

        }catch (JSONException e){

            Log.e("JSON Parser", "Error parsing data " + e.toString());

        }

        return view;
    }



}
