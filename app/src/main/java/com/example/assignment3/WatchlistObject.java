package com.example.assignment3;

public class WatchlistObject {

    /*Watchlist object to store moviename, releasedate, addDate*/
    private String MovieName;
    private String ReleaseDate;
    private String addDate;

    /*Constructors*/
    public WatchlistObject(){
    }

    public WatchlistObject(String movieName, String releaseDate, String addDate) {
        MovieName = movieName;
        ReleaseDate = releaseDate;
        this.addDate = addDate;
    }

    /*Getters and Setters*/

    public String getMovieName() {
        return MovieName;
    }

    public void setMovieName(String movieName) {
        MovieName = movieName;
    }

    public String getReleaseDate() {
        return ReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        ReleaseDate = releaseDate;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }
}
