package com.example.assignment3;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class MovieSearchAPI {
    private static final String API_KEY = "xxxx";

    /*Searching movie from api.themovie.org by movie name*/
    public static String search(String keyword) {
        keyword = keyword.replace(" ", "%20");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        try {
          //  https://api.themoviedb.org/3/search/movie?api_key=f03db0ead4f3c33748cbdff46ea788ba&language=en-US&query=harry%20potter&page=1&include_adult=false
            url = new URL("https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language=en-US&query=" + keyword + "&page=1&include_adult=false");

            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            connection.disconnect();
        }
        return textResult;
    }

    /*Searching movie from api.themovie.org for view screen*/
    public static String searchforview (String keyword) {
        keyword = keyword.replace(" ", "%20");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        try {
            //https://api.themoviedb.org/3/movie/671?api_key=f03db0ead4f3c33748cbdff46ea788ba&language=en-US
            url = new URL("https://api.themoviedb.org/3/movie/" + keyword + "?api_key=" + API_KEY + "&language=en-US");

            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            connection.disconnect();
        }
        return textResult;
    }

    /*Searching movie from api.themovie.org by movie id to get credit that is cast and director info*/
    public static String searchcredit (String keyword) {
        keyword = keyword.replace(" ", "%20");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        try {
           // https://api.themoviedb.org/3/movie/24428/credits?api_key=f03db0ead4f3c33748cbdff46ea788ba
            url = new URL("https://api.themoviedb.org/3/movie/" + keyword + "/credits?api_key=" + API_KEY );

            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            connection.disconnect();
        }
        return textResult;
    }

    /*Conversion of api result to get genre and country of movie. Input parameter will be result from api search*/
    public static String[] getGenreCountry(String s){
        String[] snippet = new String[2];
        try{
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray1 = jsonObject.getJSONArray("genres");
            JSONArray jsonArray2 = jsonObject.getJSONArray("production_companies");
            JSONObject json1 = jsonArray1.getJSONObject(0);
            JSONObject json2 = jsonArray2.getJSONObject(0);
            snippet[0] = json1.getString("name");
            snippet[1] = json2.getString("origin_country");
           }catch (Exception e){
              e.printStackTrace();
              snippet[0] = "Null";
           }
        return snippet;
    }

    /*Conversion of api result to get cast and director. Input parameter will be result from api search*/
    public static String[] getCastDirector(String s){
        String[] snippet = new String[2];
        try{
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray1 = jsonObject.getJSONArray("cast");
            JSONArray jsonArray2 = jsonObject.getJSONArray("crew");
            JSONObject json1 = jsonArray1.getJSONObject(0);
            JSONObject json2 = jsonArray2.getJSONObject(0);
            snippet[0] = json1.getString("name");
            snippet[1] = json2.getString("name");
        }catch (Exception e){
            e.printStackTrace();
            snippet[0] = "Null";
        }
        return snippet;
    }

    /*To get data about movie */
    public static ArrayList<MovieList> getMovieSearch(String result){
        JSONObject jsonObject = null;
        ArrayList<MovieList> movieList = new ArrayList<>();
        try{
            if (result != null)
            {

                jsonObject = new JSONObject(result);

                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < 6; i++)
                {
                    JSONObject object = jsonArray.getJSONObject(i);
                    MovieList movielist = new MovieList();
                    movielist.setTitle(object.getString("title"));
                    movielist.setRelease_date(object.getString("release_date"));
                    movielist.setPoster_path("https://image.tmdb.org/t/p/w500/" + object.getString("poster_path"));
                    movieList.add(movielist);
                }
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }

            return movieList;
    }

    /*To get poster path*/
    public static String getMoviePoster(String result){
        String path = null;
        JSONObject jsonObject = null;
        ArrayList<MovieList> movieList = new ArrayList<>();
        try{
            if (result != null)
            {

                jsonObject = new JSONObject(result);

                JSONArray jsonArray = jsonObject.getJSONArray("results");
                    JSONObject object = jsonArray.getJSONObject(0);
                     path= "https://image.tmdb.org/t/p/w500/" + object.getString("poster_path");

                }
            } catch (JSONException ex) {
            ex.printStackTrace();
        }
           return path;
    }


}
