package com.example.assignment3;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieSearchAdapter extends ArrayAdapter{

    /* Customised adapter for the list view used in Movie Search fragment*/
    private List<MovieList> movieDetailsLists;
    private int resource;
    private Context context;

    public MovieSearchAdapter(@NonNull Context context, int resource, @NonNull List<MovieList> movieLists) {
        super(context, resource, movieLists);
        this.context = context;
        this.movieDetailsLists = movieLists;
        this.resource = resource;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MovieList details = movieDetailsLists.get(position);
        View view = LayoutInflater.from(context).inflate(resource,parent,false);
        TextView title = (TextView) view.findViewById(R.id.Moviename);
        TextView release_date= (TextView) view.findViewById(R.id.Releasedate);
        ImageView img = (ImageView) view.findViewById(R.id.memoir_ig);
        title.setText(details.getTitle());
        release_date.setText(details.getRelease_date());
        Glide.with(context).load(details.getPoster_path()).into(img);

        return view;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return movieDetailsLists.get(position);
    }
}
