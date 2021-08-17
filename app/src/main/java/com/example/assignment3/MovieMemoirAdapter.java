package com.example.assignment3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieMemoirAdapter extends ArrayAdapter {

    /*Customized adapter for view list used in MovieMemoirFragment*/
    private List<objectMovieMemoir> movieMemoirs;
    private int resource;
    private Context context;
    public MovieMemoirAdapter(@NonNull Context context, int resource, @NonNull List<objectMovieMemoir> movieMemoirs) {
        super(context, resource, movieMemoirs);
        this.context = context;
        this.movieMemoirs = movieMemoirs;
        this.resource = resource;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        objectMovieMemoir details = movieMemoirs.get(position);
        View view = LayoutInflater.from(context).inflate(resource,parent,false);
        TextView title = (TextView) view.findViewById(R.id.Moviename);
        TextView release_date= (TextView) view.findViewById(R.id.Releasedate);
        ImageView img = (ImageView) view.findViewById(R.id.memoir_ig);
        TextView watchdate= (TextView) view.findViewById(R.id.Watchdate);
        TextView cinemaPostcode = (TextView) view.findViewById(R.id.Cinema_postcode);
        TextView memoComment = (TextView)  view.findViewById(R.id.Comment);
        RatingBar memoRating = (RatingBar) view.findViewById(R.id.Rating);
        title.setText(details.getMovieName());
        release_date.setText(details.getMovieReleasedate());
        Glide.with(context).load(details.getCinemaPostcode()).into(img);
        watchdate.setText(details.getUserdate());
        cinemaPostcode.setText(details.getCinemaPostcode());
        memoComment.setText(details.getMemoComment());
        memoRating.setRating(Float.parseFloat(details.getMemoRating()));
        return view;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return movieMemoirs.get(position);
    }
}
