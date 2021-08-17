package com.example.assignment3;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WatchlistDAO {
    /*DAO class WatchlistDAO*/
    @Query("Select * FROM watchlist")
    LiveData<List<Watchlist>> getAll();

    @Query("Select * FROM watchlist WHERE uid = :watchlistId LIMIT 1")
    Watchlist findByID(int watchlistId);

    @Query("Select * FROM watchlist WHERE movie_name = :movie_name LIMIT 1")
    Watchlist findByName(String movie_name);

    @Insert
    void insertAll(Watchlist... watchlists);

    @Insert
    long insert (Watchlist watchlist);

    @Delete
    void delete (Watchlist  watchlist);

    @Update
    void updateWatchlist (Watchlist... watchlists);

    @Query("DELETE FROM watchlist")
    void deleteAll();

}
