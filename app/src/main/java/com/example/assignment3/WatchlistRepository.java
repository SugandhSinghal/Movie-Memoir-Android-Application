package com.example.assignment3;


import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WatchlistRepository {

    /*WatchlistRepository*/
    private WatchlistDAO dao;
    private LiveData<List<Watchlist>> allWatchlist;
    private Watchlist watchlist;


    public WatchlistRepository(Application application){
        WatchlistDatabase db = WatchlistDatabase.getInstance(application);
        dao=db.watchlistDao();
    }
    public LiveData<List<Watchlist>> getAllWatchlist() {
        allWatchlist=dao.getAll();
        return allWatchlist;
    }
    public void insert(final Watchlist watchlist){
        WatchlistDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(watchlist);
            }
        });
    }
    public void deleteAll(){
        WatchlistDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.deleteAll();
            }
        });
    }
    public void delete(final Watchlist watchlist){
        WatchlistDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.delete(watchlist);
            }
        });
    }
    public void insertAll(final Watchlist... watchlists){
        WatchlistDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insertAll(watchlists);
            }
        });
    }
    public void updateWatchlist(final Watchlist... watchlists){
        WatchlistDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.updateWatchlist(watchlists);
            }
        });
    }
        public Watchlist findByID(final int watchlistId){
            WatchlistDatabase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    Watchlist runWatchlist= dao.findByID(watchlistId);
                    setWatchlist(runWatchlist);
                }
            });
            return watchlist;
        }

    public Watchlist findByName(final String movie_name){
        WatchlistDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Watchlist runWatchlist= dao.findByName(movie_name);
                setWatchlist(runWatchlist);
            }
        });
        return watchlist;
    }

        public void setWatchlist(Watchlist watchlist){
            this.watchlist=watchlist;
        }
    }
