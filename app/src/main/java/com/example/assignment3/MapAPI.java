package com.example.assignment3;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MapAPI {
    private static final String API_KEY = "xxxx";

    /*Code to get result about latitute and longitude for home from geocode*/
    public static String MapSearch(String address, String postcode) {
        address = address.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        try {
            url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address="+ address+"+"+ postcode + ",+CA&key=" +API_KEY);
            connection = (HttpURLConnection) url.openConnection();
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

    /*Code to get result about latitute and longitude for cinema from geocode*/
    public static String MapSearch1(String address) {
        address = address.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        try {
            url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address="+ address + ",+CA&key=" +API_KEY);
            connection = (HttpURLConnection) url.openConnection();
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

    /*Latitute longitude calculation from result received from api*/
    public static String[] getLatLong(String s){
        String[] snippet = new String[2];
        try{
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            if(jsonArray != null && jsonArray.length() > 0) {
                JSONObject json1 = jsonArray.getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
                snippet[0] = json1.getString("lat");
                snippet[1] = json1.getString("lng");

            }
        }catch (Exception e){
            e.printStackTrace();
            snippet[0] = "Null";
        }
        return snippet;
    }


}
