package com.example.assignment3;

public class postcodeCount {

    /*postcodeCount class for MovieReport*/
    private String cinemaPostcode;
    private String count;

    /*Constructors*/
    public postcodeCount(){
    }

    public postcodeCount(String cinemaPostcode, String count) {
        this.cinemaPostcode = cinemaPostcode;
        this.count = count;
    }

    /*Getters Setters*/
    public String getCinemaPostcode() {
        return cinemaPostcode;
    }

    public void setCinemaPostcode(String cinemaPostcode) {
        this.cinemaPostcode = cinemaPostcode;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }






}
