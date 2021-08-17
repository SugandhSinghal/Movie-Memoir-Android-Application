package com.example.assignment3;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.sql.Date;

public class AddNewCinemaFragment  extends Fragment {

    View view;
    EditText cinemaName , cinemaPostCode;
    String cinemaNameTemp, cinemaPostCodeTemp;
    Button addCinema, back;

    public AddNewCinemaFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstancestate) {

        view = inflater.inflate(R.layout.new_cinema_add_fragment, container, false);

        addCinema = (Button) view.findViewById(R.id.AddtoCinema);
        /*On clicking add cinema button the Cinema database is updated */
        addCinema.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                cinemaName = view.findViewById(R.id.et_addName);
                cinemaNameTemp = cinemaName.getText().toString();
                cinemaPostCode = view.findViewById(R.id.et_addPostcode);
                cinemaPostCodeTemp = cinemaPostCode.getText().toString();
                /*Async task to update cinema table*/
                AddNewCinemaFragment.UpdateDataAsyncTask updateAsyncTask=new AddNewCinemaFragment.UpdateDataAsyncTask();
                if (!(cinemaNameTemp).isEmpty() && !(cinemaPostCodeTemp).isEmpty() ) {

                    updateAsyncTask.execute(cinemaNameTemp,cinemaPostCodeTemp);
                }

            }
        });
        /*On clicking back button the user move back to AddToMemoirFragment(Problem in moving back because the object used in AddToMemoirFragment is seriziable) */
        back = (Button) view.findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                AddToMemoirFragment fragment = new AddToMemoirFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment).addToBackStack(null);
                ft.commit();
            }
        });

        return view;
    }

    /*Async task to update cinema table*/
    class UpdateDataAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String id = RestClient.getMaxId("cinema");
            Integer uId = Integer.parseInt(id) + 1;
            Cinema cinema = new Cinema(uId, params[0], Integer.valueOf(params[1]));
            RestClient.postCinema(cinema);
            return "Cinema was added!";
        }

        @Override
        protected void onPostExecute(String response) {
            Toast.makeText(getActivity(),response,Toast.LENGTH_SHORT).show();

        }
    }

}
