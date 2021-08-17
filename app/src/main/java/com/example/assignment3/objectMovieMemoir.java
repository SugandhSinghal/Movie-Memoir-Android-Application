package com.example.assignment3;

import java.io.Serializable;

public class objectMovieMemoir implements Serializable {

    /*Serializable object as will be trasfered from MovieMemoirFragment*/
    private String movieName;
    private String movieReleasedate;
    private String poster_path;
    private String userdate;
    private String cinemaPostcode;
    private String memoComment;
    private String memoRating;

    /*Constructor*/
    public objectMovieMemoir() {
    }

    public objectMovieMemoir(String movieName, String movieReleasedate, String poster_path, String userdate, String cinemaPostcode, String memoComment, String memoRating) {
        this.movieName = movieName;
        this.movieReleasedate = movieReleasedate;
        this.poster_path = poster_path;
        this.userdate = userdate;
        this.cinemaPostcode = cinemaPostcode;
        this.memoComment = memoComment;
        this.memoRating = memoRating;
    }

    /*Getter Setter Method*/

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieReleasedate() {
        return movieReleasedate;
    }

    public void setMovieReleasedate(String movieReleasedate) { this.movieReleasedate = movieReleasedate; }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getUserdate() {
        return userdate;
    }

    public void setUserdate(String userdate) {
        this.userdate = userdate;
    }

    public String getCinemaPostcode() {
        return cinemaPostcode;
    }

    public void setCinemaPostcode(String cinemaPostcode) {
        this.cinemaPostcode = cinemaPostcode;
    }

    public String getMemoComment() {
        return memoComment;
    }

    public void setMemoComment(String memoComment) {
        this.memoComment = memoComment;
    }

    public String getMemoRating() {
        return memoRating;
    }

    public void setMemoRating(String memoRating) {
        this.memoRating = memoRating;
    }

}
