package com.example.assignment3;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Watchlist {

       /*Entity class Watchlist*/
        @PrimaryKey(autoGenerate = true)
        public int uid;
        @ColumnInfo(name = "movie_name")
        public String movie_name;
        @ColumnInfo(name = "release_date")
        public String release_date;
        @ColumnInfo(name = "date_added")
        public String date_added;
        public Watchlist(String movie_name, String release_date, String date_added) {
            this.movie_name=movie_name;
            this.release_date=release_date;
            this.date_added = date_added;
        }
        public int getId() {
            return uid;
        }
        public String getMovie_name() {
            return movie_name;
        }
        public void setMovie_name(String movie_name) {
            this.movie_name = movie_name;
        }
        public String getRelease_date() {
            return release_date;
        }
        public void setRelease_date(String release_date) {
            this.release_date = release_date;
        }
        public String getDate_added() {
            return date_added;
        }
        public void setDate_added (String date_added) {
            this.date_added = date_added;
        }

    }

