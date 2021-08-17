package com.example.assignment3;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        /*Getting data from HomeScreen through shared preferences*/
        SharedPreferences spUserData = this.getSharedPreferences("File_User", Context.MODE_PRIVATE);
        String address = spUserData.getString("address", "No address");
        String postcode = spUserData.getString("codep", "No postcode");
        String cinemalist = spUserData.getString("cinemalist", "No list found");

        /*Async task for home*/
        AsyncTaskMap map = new AsyncTaskMap();
        map.execute(address, postcode);

        /*Calling get Cinema method*/
        String [] snippet = getListCinema(cinemalist);
        /*Async tasks for Cinema*/
        AsyncTaskCinema map1 = new AsyncTaskCinema();
        map1.execute(snippet[0]);
        AsyncTaskCinema map2 = new AsyncTaskCinema();
        map2.execute(snippet[1]);
        AsyncTaskCinema map3 = new AsyncTaskCinema();
        map3.execute(snippet[2]);

        /* Obtain the SupportMapFragment and get notified when the map is ready to be used.*/
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /*Method Storing CinemaName an postcode into a arrayList*/
    public static String[] getListCinema(String s) {
        String[] snippet = new String[8];
        try {

            JSONArray jsonArray = new JSONArray(s);
            if (jsonArray != null && jsonArray.length() > 0) {
                for (int i = 0; i < 8; i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    snippet[i] = object.getString("cinemaName") + " " +object.getString("cinemaPostcode") ;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return snippet;
    }

    /*Async task for cinemas in google map*/
    private class AsyncTaskCinema extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return MapAPI.MapSearch1(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            String[] latLong;
            latLong = MapAPI.getLatLong(result);
            LatLng userLatLng = new LatLng(Double.parseDouble(latLong[0]), Double.parseDouble(latLong[1]));
            mMap.addMarker(new MarkerOptions().position(userLatLng ).title("Cinema").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
            float zoomLevel = (float) 10.0;
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, zoomLevel));
        }
    }

    /*Async task for home in google map*/
    private class AsyncTaskMap extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            /*Calling method from MapAPI to get data about latitude and longitude*/
            return MapAPI.MapSearch(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            String[] latLong;
            latLong = MapAPI.getLatLong(result);
            LatLng userLatLng = new LatLng(Double.parseDouble(latLong[0]), Double.parseDouble(latLong[1]));
            mMap.addMarker(new MarkerOptions().position(userLatLng ).title("Home"));
            float zoomLevel = (float) 10.0;
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, zoomLevel));
        }
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

}
