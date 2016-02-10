package com.example.dishantkaushik.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Movie_detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent=getIntent();
        int position=intent.getIntExtra("movie_position", 0);
        ImageView imageView=(ImageView)findViewById(R.id.movie_detail_poster);
        Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w500//"+movieData.allMovies.get(position).getPosterurl()).fit().centerCrop().into(imageView);
        setTitle(movieData.allMovies.get(position).getTitle());
        ((TextView)findViewById(R.id.year_data)).setText(movieData.allMovies.get(position).getReleaseDate().substring(0,4));
        ((TextView)findViewById(R.id.overview_text)).setText(movieData.allMovies.get(position).getOverview());
        ((TextView)findViewById(R.id.rating_text)).setText(movieData.allMovies.get(position).getUser_rating().toString()+"/10");
        //((TextView)findViewById(R.id.year_data)).setText(movieData.allMovies.get(position).getReleaseDate());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
