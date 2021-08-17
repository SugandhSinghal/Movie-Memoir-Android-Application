package com.example.assignment3;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddToMemoirFragment extends Fragment {

    View view;
    private TextView movie_name, movie_releasedate, watch_date;
    Button addCinema, addMemoir;
    String temp_watch_date;
    private ImageView movie_img;
    Spinner cinema;
    Cinema objectCinema;
    String userdate;
    private String cinemaTemp;
    private String watchTimeTemp;
    private String commentTemp;
    private RatingBar rating;
    private EditText watchTime;
    private EditText comment;
    Users user;
    double rate;
    List<String> list = new ArrayList<>();
    List<Cinema> list2 = new ArrayList<>();

    public AddToMemoirFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstancestate) {
        view = inflater.inflate(R.layout.add_to_memoir_fragment, container, false);

        /*Receiving object from movie view screen*/
        Bundle bundle = getArguments();
        final MovieList object = (MovieList) bundle.getSerializable("MovieList");
        movie_name = (TextView) view.findViewById(R.id.tv_nameMovie);
        movie_name.setText(object.getTitle());
        movie_releasedate = (TextView) view.findViewById(R.id.tv_release_date);
        movie_releasedate.setText(object.getRelease_date());
        movie_img = (ImageView) view.findViewById(R.id.Ig_movie_image);
        Glide.with(this).load(object.getPoster_path()).into(movie_img);
        watchTime = (EditText) view.findViewById(R.id.et_watchTime);
        watchTimeTemp = watchTime.getText().toString();
        comment = (EditText) view.findViewById(R.id.et_comment);
        commentTemp = comment.getText().toString();
        rating = (RatingBar)  view.findViewById(R.id.Rating);
        rate = Double.valueOf(rating.getRating());
        addCinema = (Button)  view.findViewById(R.id.AddCinema);

        /*On clicking addCinema will be directed to AddNewCinemaFragment*/
        addCinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewCinemaFragment fragment = new AddNewCinemaFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment).addToBackStack(null);
                ft.commit();

            }
        });

        view.findViewById(R.id.Watchdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        SharedPreferences sharedPref= getActivity().getSharedPreferences("File_User",
                Context.MODE_PRIVATE);
        String userid= sharedPref.getString("Id",null);

       // AddToMemoirFragment.GetUserObjectAsyncTask togetuserAsyncTask=new AddToMemoirFragment.GetUserObjectAsyncTask();
       // togetuserAsyncTask.execute(userid);

        cinema = view.findViewById(R.id.sp_cinema);
       AddToMemoirFragment.UpdateDataAsyncTask updateAsyncTask=new AddToMemoirFragment.UpdateDataAsyncTask();
       updateAsyncTask.execute();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//      cinema.setAdapter(adapter);

        cinema.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cinemaTemp = parent.getItemAtPosition(position).toString();
                objectCinema = list2.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // AddToMemoirFragment.UpdateDataToMemoirAsyncTask addMemoir=new AddToMemoirFragment.UpdateDataToMemoirAsyncTask();
        //  addMemoir.execute(object.getTitle(),object.getRelease_date())

        return view;
    }

    /*Date picker code*/
    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        watch_date = (TextView) view.findViewById(R.id.tv_watchDate);
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            watch_date.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear+1)
                    + "-" + String.valueOf(year));
            userdate = String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear+1) + "-" + String.valueOf(year);

        }
    };

    /* Asyn method to fetch data from cinema table and display on spinner*/
    class UpdateDataAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String cinemalist = RestClient.getCinemaList();
            return cinemalist;
        }
        @Override
        protected void onPostExecute(String response) {
            JSONArray jsonArray = null;
            try{
                if(response != null){
                    jsonArray = new JSONArray(response);
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        list.add(object.getString("cinemaName") + "  "+ object.getString("cinemaPostcode"));
                        Cinema cinema = new Cinema(Integer.valueOf(object.getString("cinemaId")),object.getString("cinemaName"),Integer.valueOf(object.getString("cinemaPostcode")));
                        list2.add(cinema);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
                    cinema.setAdapter(adapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
         }
        }

   /*Async task to get user information*/
    class GetUserObjectAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String userinfo = RestClient.getUserById(params[0]);
            return userinfo;
        }

        @Override
        protected void onPostExecute(String response) {

            JSONObject jsonObject = null;
            try{
                if(response != null){
                    jsonObject = new JSONObject(response);
                    user = new Users();
                    user.setAddress(jsonObject.getString("userAddress"));
                    user.setGender(jsonObject.getString("userGender"));
                    user.setName(jsonObject.getString("userName"));
                    user.setDob(Date.valueOf(jsonObject.getString("userDob")));
                    user.setUserId(Integer.valueOf(jsonObject.getString("userId")));
                    user.setPostcode(Integer.valueOf(jsonObject.getString("userPostcode")));
                    user.setState(jsonObject.getString("userState"));
                    user.setSurname(jsonObject.getString("userSurname"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    /*Async task to update the memoir table(not working because of multitreading as users info is also on async task)*/
  /*  class UpdateDataToMemoirAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String id = RestClient.getMaxId("Memoir");
            Integer uId = Integer.parseInt(id) + 1;
            Memoir object = new Memoir(objectCinema,commentTemp,uId,rate,params[0],Date.valueOf(params[1]),user,Date.valueOf(userdate), Time.valueOf(watchTimeTemp) );

            return "Movie added to memoir table";
        }

        @Override
        protected void onPostExecute(String response) {
            Toast.makeText(getActivity(),response,Toast.LENGTH_SHORT).show();

        }
    }*/

}






