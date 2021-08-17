package com.example.assignment3;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class WatchlistViewModel extends ViewModel {

    /*WatchlistViewModel */
    private WatchlistRepository cRepository;
    private MutableLiveData<List<Watchlist>> allWatchlist;

    public WatchlistViewModel() {
        allWatchlist = new MutableLiveData<>();
    }

    public void setWatchlist(List<Watchlist> watchlists) {
        allWatchlist.setValue(watchlists);
    }

    public LiveData<List<Watchlist>> getAllWatchlists() {
        return cRepository.getAllWatchlist();
    }

    public void initalizeVars(Application application){
        cRepository = new WatchlistRepository(application);
    }
    public void insert(Watchlist watchlist) {
        cRepository.insert(watchlist);
    }
    public void insertAll(Watchlist... watchlists) {
        cRepository.insertAll(watchlists);
    }

    public void delete(Watchlist watchlist){cRepository.delete(watchlist);}

    public void deleteAll() {
        cRepository.deleteAll();
    }

    public void update(Watchlist... watchlists) {
        cRepository.updateWatchlist(watchlists);
    }

    public Watchlist findByID(int watchlistId){
        return cRepository.findByID(watchlistId);
    }

    public Watchlist findByName(String movie_name){
        return cRepository.findByName(movie_name);
    }


}