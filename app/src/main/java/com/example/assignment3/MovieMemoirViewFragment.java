package com.example.assignment3;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieMemoirViewFragment extends Fragment {

    private TextView movie_name, movie_releasedate, genre, cast, country, director, story;
    private RatingBar rating;
    View view;
    objectMovieMemoir obj;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstancestate) {
        view = inflater.inflate(R.layout.movie_memoir_view_fragment, container, false);
        /*Getting the data in from of object from one fragment to another here it is coming from MovieMemoirFragment*/
        Bundle bundle = getArguments();
        obj = (objectMovieMemoir) bundle.getSerializable("objectMovieMemoir");

        movie_name = (TextView) view.findViewById(R.id.tv_nameMovie);
        movie_releasedate = (TextView) view.findViewById(R.id.tv_release_date);
        story = (TextView) view.findViewById(R.id.tv_story);
        rating = (RatingBar)  view.findViewById(R.id.Rating);
        movie_name.setText(obj.getMovieName());
        rating.setRating(Float.parseFloat(obj.getMemoRating()));
        /*Async task to get the further movie details*/
        MovieMemoirViewFragment.MovieSearchAsyncTask updateAsyncTask=new MovieMemoirViewFragment.MovieSearchAsyncTask();
        updateAsyncTask.execute(obj.getMovieName());
        return view;
    }

    /*Async task to get the further movie details*/
    private class MovieSearchAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String MovieSearchResult = MovieSearchAPI.search(params[0]);
            return MovieSearchResult;
        }
        @Override
        protected void onPostExecute(String s) {
            JSONObject jsonObject = null;
            try {
                if (s != null) {
                    jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    JSONObject object = jsonArray.getJSONObject(0);
                    MovieList movielist = new MovieList();
                    movielist.setTitle(object.getString("title"));
                    movielist.setRelease_date(object.getString("release_date"));
                    movielist.setPoster_path("https://image.tmdb.org/t/p/w500/" + object.getString("poster_path"));
                    movielist.setId(object.getString("id"));
                    movielist.setOver_view(object.getString("overview"));
                    movielist.setRating(object.getString("vote_average"));
                    movie_releasedate.setText("RELEASE DATE: " + movielist.getRelease_date());
                    story.setText("PLOT: " +movielist.getOver_view());

                    /*Calling async task to get further details related to movie*/
                    MovieMemoirViewFragment.AsyncTaskGenerCountry generCountryAsync = new MovieMemoirViewFragment.AsyncTaskGenerCountry();
                    generCountryAsync.execute(movielist.getId());

                    MovieMemoirViewFragment.AsyncTaskCredit creditAsync = new MovieMemoirViewFragment.AsyncTaskCredit();
                    creditAsync.execute(movielist.getId());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
            cast = (TextView) view.findViewById(R.id.tv_cast);
            cast.setText("CAST: " + credits[0]);
            director = (TextView) view.findViewById(R.id.tv_director);
            director.setText("DIRECTOR: " + credits[1]);
        }

    }

}

