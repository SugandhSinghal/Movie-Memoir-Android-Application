package com.example.assignment3;

import java.io.Serializable;

public class MovieList implements Serializable {

    /*MovieList object with Serializable implemented so that it can be transfered between fragments*/
    private String title;
    private String release_date;
    private String poster_path;
    private String over_view;
    private String id ;
    private String rating;


    /*Constructors*/
    public MovieList(){
    }

    public MovieList(String title, String release_date, String poster_path, String over_view, String id, String rating) {
        this.title = title;
        this.release_date = release_date;
        this.poster_path = poster_path;
        this.over_view = over_view;
        this.id = id;
        this.rating = rating;
    }
    /*Getters and Setters*/

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOver_view() { return over_view; }

    public void setOver_view(String over_view) { this.over_view = over_view; }

    public String getRating() { return rating; }

    public void setRating(String rating) { this.rating = rating; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

}
