package com.example.assignment3;

import java.sql.Time;
import java.util.Date;

public class Memoir {

    /*Memoir object used in AddToMemoirFragment*/
    private Cinema cinemaId;
    private String memoComment;
    private Integer memoId;
    private Double memoRating;
    private String movieName;
    private Date movieReleasedate;
    private Users userId;
    private Date userdate;
    private Time usertime;

    /*Constructor*/
    public Memoir(Cinema cinemaId, String memoComment, Integer memoId, Double memoRating, String movieName, Date movieReleasedate, Users userId, Date userdate, Time usertime) {
        this.cinemaId = cinemaId;
        this.memoComment = memoComment;
        this.memoId = memoId;
        this.memoRating = memoRating;
        this.movieName = movieName;
        this.movieReleasedate = movieReleasedate;
        this.userId = userId;
        this.userdate = userdate;
        this.usertime = usertime;
    }

    /*Getter Setter Methods*/
    public Cinema getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Cinema cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getMemoComment() {
        return memoComment;
    }

    public void setMemoComment(String memoComment) {
        this.memoComment = memoComment;
    }

    public Integer getMemoId() {
        return memoId;
    }

    public void setMemoId(Integer memoId) {
        this.memoId = memoId;
    }

    public Double getMemoRating() {
        return memoRating;
    }

    public void setMemoRating(Double memoRating) {
        this.memoRating = memoRating;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Date getMovieReleasedate() {
        return movieReleasedate;
    }

    public void setMovieReleasedate(Date movieReleasedate) {
        this.movieReleasedate = movieReleasedate;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public Date getUserdate() {
        return userdate;
    }

    public void setUserdate(Date userdate) {
        this.userdate = userdate;
    }

    public Time getUsertime() {
        return usertime;
    }

    public void setUsertime(Time usertime) {
        this.usertime = usertime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (memoId != null ? memoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Memoir other = (Memoir) object;
        if ((this.memoId == null && other.memoId != null) || (this.memoId != null && !this.memoId.equals(other.memoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "restws.Memoir[ memoId=" + memoId + " ]";
    }


}

