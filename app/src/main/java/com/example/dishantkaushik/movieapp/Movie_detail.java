package com.example.dishantkaushik.movieapp;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dishantkaushik.movieapp.generated.*;
import com.example.dishantkaushik.movieapp.generated.MovieProvider;
import com.squareup.picasso.Picasso;

public class Movie_detail extends AppCompatActivity {
    int idchange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        if (getIntent().hasExtra("movie_position")){
            final ContentResolver mp=getContentResolver();
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_movie_detail);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            Intent intent=getIntent();
            idchange=1;

            final int position=intent.getIntExtra("movie_position", 0);
            ImageView imageView=(ImageView)findViewById(R.id.movie_detail_poster);
            Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w500//"+movieData.allMovies.get(position).getPosterurl()).fit().centerCrop().into(imageView);
            setTitle(movieData.allMovies.get(position).getTitle());
            ((TextView)findViewById(R.id.year_data)).setText(movieData.allMovies.get(position).getReleaseDate().substring(0,4));
            ((TextView)findViewById(R.id.overview_text)).setText(movieData.allMovies.get(position).getOverview());
            ((TextView)findViewById(R.id.rating_text)).setText(movieData.allMovies.get(position).getUser_rating().toString()+"/10");
            //((TextView)findViewById(R.id.year_data)).setText(movieData.allMovies.get(position).getReleaseDate());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            final FloatingActionButton fav = (FloatingActionButton) findViewById(R.id.fav);
            final ContentValues values = new ContentValues();
            values.put(MovieDBContract.ListColumns.TITLE,movieData.allMovies.get(position).getTitle());
            values.put(MovieDBContract.ListColumns.OVERVIEW,movieData.allMovies.get(position).getOverview());
            values.put(MovieDBContract.ListColumns.POSTER_URL,movieData.allMovies.get(position).getPosterurl());
            values.put(MovieDBContract.ListColumns.RELEASE_DATE,movieData.allMovies.get(position).getReleaseDate());
            values.put(MovieDBContract.ListColumns.USER_RATING,movieData.allMovies.get(position).getUser_rating());

            fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (idchange == 1) {
                        //added to favourites
                        Snackbar.make(view, "Added to Favourites", Snackbar.LENGTH_LONG)
                                .setAction("Undo", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        idchange = 1;
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                            fav.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_icon, getTheme()));
                                        } else {
                                            fav.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_icon));
                                        }
                                        mp.delete(com.example.dishantkaushik.movieapp.MovieProvider.Movies.MOVIES, MovieDBContract.ListColumns.TITLE + "=?", new String[]{movieData.allMovies.get(position).getTitle()});
                                        //Remove to favourites here !

                                    }
                                }).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            fav.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_icon_on, getTheme()));
                        } else {
                            fav.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_icon_on));
                        }
                        Toast.makeText(getApplicationContext(),""+mp.insert(com.example.dishantkaushik.movieapp.MovieProvider.Movies.MOVIES, values),Toast.LENGTH_SHORT).show();

                        //fav.setImageResource();
                        idchange = 2;
                    } else if (idchange == 2) {
                        //removed from favourites
                        Snackbar.make(view, "Removed from Favourites", Snackbar.LENGTH_LONG)
                                .setAction("Undo", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        idchange = 2;
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                            fav.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_icon_on, getTheme()));
                                        } else {
                                            fav.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_icon_on));
                                        }
                                        //Add to favourites here !
                                        mp.insert(com.example.dishantkaushik.movieapp.MovieProvider.Movies.MOVIES, values);


                                    }
                                }).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            fav.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_icon, getTheme()));
                        } else {
                            fav.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_icon));
                        }
                        //Remove to favourites here !
                        mp.delete(com.example.dishantkaushik.movieapp.MovieProvider.Movies.MOVIES, MovieDBContract.ListColumns.TITLE + "=?", new String[]{movieData.allMovies.get(position).getTitle()});
                        idchange = 1;
                    }
                }
            });
        }
        else if (getIntent().hasExtra("fav_position")){
            final ContentResolver mp=getContentResolver();
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_movie_detail);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            Intent intent=getIntent();
            final int id=intent.getIntExtra("fav_position", 0);
            ImageView imageView=(ImageView)findViewById(R.id.movie_detail_poster);
            Cursor cursor=getContentResolver().query(com.example.dishantkaushik.movieapp.MovieProvider.Movies.MOVIES,null,MovieDBContract.ListColumns._ID+"="+id, null, null);
            cursor.moveToFirst();
            Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w500//"+cursor.getString(cursor.getColumnIndex(MovieDBContract.ListColumns.POSTER_URL))).fit().centerCrop().into(imageView);
            setTitle(cursor.getString(cursor.getColumnIndex(MovieDBContract.ListColumns.TITLE)));
            ((TextView)findViewById(R.id.year_data)).setText(cursor.getString(cursor.getColumnIndex(MovieDBContract.ListColumns.RELEASE_DATE)).substring(0, 4));
            ((TextView)findViewById(R.id.overview_text)).setText(cursor.getString(cursor.getColumnIndex(MovieDBContract.ListColumns.OVERVIEW)));
            ((TextView)findViewById(R.id.rating_text)).setText(cursor.getString(cursor.getColumnIndex(MovieDBContract.ListColumns.USER_RATING)).substring(0, 3) + "/10");
            //((TextView)findViewById(R.id.year_data)).setText(movieData.allMovies.get(position).getReleaseDate());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }
    }
}