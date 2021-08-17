package com.example.assignment3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class WatchlistAdapter extends ArrayAdapter {

    /*Customized adapter for list view in watchlist fragment*/
    private List<WatchlistObject> watchlist;
    private int resource;
    private Context context;

    public WatchlistAdapter(@NonNull Context context, int resource, @NonNull List<WatchlistObject> watchlist) {
        super(context, resource, watchlist);

        this.context = context;
        this.watchlist = watchlist;
        this.resource = resource;
    }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            WatchlistObject details = watchlist.get(position);
            View view = LayoutInflater.from(context).inflate(resource,parent,false);
            TextView movie_name = (TextView) view.findViewById(R.id.Moviename);
            TextView release_date= (TextView) view.findViewById(R.id.Releasedate);
            TextView add_date= (TextView) view.findViewById(R.id.addedDate);
            movie_name.setText(details.getMovieName());
            release_date.setText(details.getReleaseDate());
            add_date.setText(details.getAddDate());

        return view;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return watchlist.get(position);
    }
}
