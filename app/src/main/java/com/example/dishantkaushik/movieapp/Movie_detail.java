package com.example.dishantkaushik.movieapp;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Movie_detail extends AppCompatActivity {
    int idchange=0;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    FloatingActionButton fab;
    ContentResolver mp;
    String[] data = new String[5];
    Intent intent;
    ContentValues values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        fab=(FloatingActionButton)findViewById(R.id.fav);
        viewPager = (ViewPager) findViewById(R.id.pager);
        mp=getContentResolver();
        intent = getIntent();
        checkiffav();
        if (!checkiffav()) {
            final int position = intent.getIntExtra("movie_position", 0);
            data[0] = movieData.allMovies.get(position).getTitle();
            data[1] = movieData.allMovies.get(position).getOverview();
            data[2] = movieData.allMovies.get(position).getReleaseDate();
            data[3] = movieData.allMovies.get(position).getUser_rating().toString() + "/10";
            data[4] = movieData.allMovies.get(position).getMovieID();
            values=new ContentValues();
            values.put(MovieDBContract.ListColumns.TITLE,movieData.allMovies.get(position).getTitle());
            values.put(MovieDBContract.ListColumns.OVERVIEW,movieData.allMovies.get(position).getOverview());
            values.put(MovieDBContract.ListColumns.POSTER_URL,movieData.allMovies.get(position).getPosterurl());
            values.put(MovieDBContract.ListColumns.RELEASE_DATE,movieData.allMovies.get(position).getReleaseDate());
            values.put(MovieDBContract.ListColumns.USER_RATING,movieData.allMovies.get(position).getUser_rating());
            values.put(MovieDBContract.ListColumns._ID,movieData.allMovies.get(position).getMovieID());

            ImageView imageView = (ImageView) findViewById(R.id.movie_detail_poster);
            Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w500//" + movieData.allMovies.get(position).getPosterurl()).fit().centerCrop().into(imageView);
            setupViewPager(viewPager, data);
            tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);
        }
        else {
            final String id=intent.getStringExtra("movie_id");
            ImageView imageView=(ImageView)findViewById(R.id.movie_detail_poster);
            Cursor cursor=getContentResolver().query(com.example.dishantkaushik.movieapp.MovieProvider.Movies.MOVIES,null,MovieDBContract.ListColumns._ID+"="+id, null, null);
            cursor.moveToFirst();
            Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w500//"+cursor.getString(cursor.getColumnIndex(MovieDBContract.ListColumns.POSTER_URL))).fit().centerCrop().into(imageView);
            data[0]=cursor.getString(cursor.getColumnIndex(MovieDBContract.ListColumns.TITLE));
            data[1]=cursor.getString(cursor.getColumnIndex(MovieDBContract.ListColumns.OVERVIEW));
            data[2]=cursor.getString(cursor.getColumnIndex(MovieDBContract.ListColumns.RELEASE_DATE));
            data[3]=cursor.getString(cursor.getColumnIndex(MovieDBContract.ListColumns.USER_RATING));
            data[4]=cursor.getString(cursor.getColumnIndex(MovieDBContract.ListColumns._ID));

            setupViewPager(viewPager, data);
            tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);
        }
    }
    private boolean checkiffav() {
        int position=intent.getIntExtra("movie_position",0);
        String favid=intent.getStringExtra("movie_id");
        Cursor cursor=getContentResolver().query(com.example.dishantkaushik.movieapp.MovieProvider.Movies.MOVIES, null, MovieDBContract.ListColumns._ID + "=" + favid, null, null);
        cursor.moveToFirst();
        if (cursor.getCount()==0){
            idchange=1;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_icon, getTheme()));
            } else {
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_icon));
            }
            return false;
        }
        else{
            idchange=2;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_icon_on, getTheme()));
            } else {
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_icon_on));
            }
            return true;
        }
    }

    private void setupViewPager(ViewPager viewPager,String[]data) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        movie_overview mo=new movie_overview();
        Bundle bl=new Bundle();
        bl.putString("title",data[0]);
        bl.putString("overview",data[1]);
        bl.putString("release_date",data[2]);
        bl.putString("rating",data[3]);
        mo.setArguments(bl);
        adapter.addFragment(mo, "Overview");
        adapter.addFragment(new movie_reviews(), "Reviews");
        adapter.addFragment(new movie_videos(), "Videos");
        viewPager.setAdapter(adapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    public void changefav(View view) {
        if (idchange==1){
            idchange=2;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_icon_on, getTheme()));
            } else {
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_icon_on));
            }
            //Add to favourites here !!
            mp.insert(com.example.dishantkaushik.movieapp.MovieProvider.Movies.MOVIES, values);
            Snackbar.make(view, "Added to Favourites", Snackbar.LENGTH_LONG)
            .setAction("Undo", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    idchange = 1;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_icon, getTheme()));
                    } else {
                        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_icon));
                    }
                    //Remove to favourites here !!
                    mp.delete(MovieProvider.Movies.MOVIES,MovieDBContract.ListColumns._ID+"=?",new String[]{data[4]});
                }
            });
        }
        else if (idchange==2){
            idchange=1;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_icon, getTheme()));
            } else {
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_icon));
            }
            //Remove to favourites here !!
            mp.delete(MovieProvider.Movies.MOVIES,MovieDBContract.ListColumns._ID+"=?",new String[]{data[4]});
            Snackbar.make(view, "Removed to Favourites", Snackbar.LENGTH_LONG)
                    .setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            idchange = 2;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_icon_on, getTheme()));
                            } else {
                                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_icon_on));
                            }
                            //Add to favourites here !!
                            mp.insert(com.example.dishantkaushik.movieapp.MovieProvider.Movies.MOVIES, values);
                        }
                    });
        }
        checkiffav();
    }
}
//        if (getIntent().hasExtra("movie_position")){
//            final ContentResolver mp=getContentResolver();
//            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//            setSupportActionBar(toolbar);
//            Intent intent=getIntent();
//            idchange=1;
//            final int position=intent.getIntExtra("movie_position", 0);
//            ImageView imageView=(ImageView)findViewById(R.id.movie_detail_poster);
//            Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w500//"+movieData.allMovies.get(position).getPosterurl()).fit().centerCrop().into(imageView);
//            setTitle(movieData.allMovies.get(position).getTitle());
//            ((TextView)findViewById(R.id.year_data)).setText(movieData.allMovies.get(position).getReleaseDate().substring(0,4));
//            ((TextView)findViewById(R.id.overview_text)).setText(movieData.allMovies.get(position).getOverview());
//            ((TextView)findViewById(R.id.rating_text)).setText(movieData.allMovies.get(position).getUser_rating().toString()+"/10");
//            //((TextView)findViewById(R.id.year_data)).setText(movieData.allMovies.get(position).getReleaseDate());
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            final FloatingActionButton fav = (FloatingActionButton) findViewById(R.id.fav);
//            final ContentValues values = new ContentValues();
//            values.put(MovieDBContract.ListColumns.TITLE,movieData.allMovies.get(position).getTitle());
//            values.put(MovieDBContract.ListColumns.OVERVIEW,movieData.allMovies.get(position).getOverview());
//            values.put(MovieDBContract.ListColumns.POSTER_URL,movieData.allMovies.get(position).getPosterurl());
//            values.put(MovieDBContract.ListColumns.RELEASE_DATE,movieData.allMovies.get(position).getReleaseDate());
//            values.put(MovieDBContract.ListColumns.USER_RATING,movieData.allMovies.get(position).getUser_rating());
//            values.put(MovieDBContract.ListColumns._ID,movieData.allMovies.get(position).getMovieID());
//
//            fav.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (idchange == 1) {
//                        //added to favourites
//                        Snackbar.make(view, "Added to Favourites", Snackbar.LENGTH_LONG)
//                                .setAction("Undo", new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        idchange = 1;
//                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                            fav.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_icon, getTheme()));
//                                        } else {
//                                            fav.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_icon));
//                                        }
//                                        mp.delete(com.example.dishantkaushik.movieapp.MovieProvider.Movies.MOVIES, MovieDBContract.ListColumns.TITLE + "=?", new String[]{movieData.allMovies.get(position).getTitle()});
//                                        //Remove to favourites here !
//
//                                    }
//                                }).show();
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                            fav.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_icon_on, getTheme()));
//                        } else {
//                            fav.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_icon_on));
//                        }
//                        Toast.makeText(getApplicationContext(),""+mp.insert(com.example.dishantkaushik.movieapp.MovieProvider.Movies.MOVIES, values),Toast.LENGTH_SHORT).show();
//
//                        //fav.setImageResource();
//                        idchange = 2;
//                    } else if (idchange == 2) {
//                        //removed from favourites
//                        Snackbar.make(view, "Removed from Favourites", Snackbar.LENGTH_LONG)
//                                .setAction("Undo", new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        idchange = 2;
//                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                            fav.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_icon_on, getTheme()));
//                                        } else {
//                                            fav.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_icon_on));
//                                        }
//                                        //Add to favourites here !
//                                        mp.insert(com.example.dishantkaushik.movieapp.MovieProvider.Movies.MOVIES, values);
//
//
//                                    }
//                                }).show();
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                            fav.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_icon, getTheme()));
//                        } else {
//                            fav.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_icon));
//                        }
//                        //Remove to favourites here !
//                        mp.delete(com.example.dishantkaushik.movieapp.MovieProvider.Movies.MOVIES, MovieDBContract.ListColumns.TITLE + "=?", new String[]{movieData.allMovies.get(position).getTitle()});
//                        idchange = 1;
//                    }
//                }
//            });
//        }
//        else if (getIntent().hasExtra("fav_position")){
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_movie_detail);
//            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//            setSupportActionBar(toolbar);
//            Intent intent=getIntent();
//            final int id=intent.getIntExtra("fav_position", 0);
//            ImageView imageView=(ImageView)findViewById(R.id.movie_detail_poster);
//            Cursor cursor=getContentResolver().query(com.example.dishantkaushik.movieapp.MovieProvider.Movies.MOVIES,null,MovieDBContract.ListColumns._ID+"="+id, null, null);
//            cursor.moveToFirst();
//            Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w500//"+cursor.getString(cursor.getColumnIndex(MovieDBContract.ListColumns.POSTER_URL))).fit().centerCrop().into(imageView);
//            setTitle(cursor.getString(cursor.getColumnIndex(MovieDBContract.ListColumns.TITLE)));
//            ((TextView)findViewById(R.id.year_data)).setText(cursor.getString(cursor.getColumnIndex(MovieDBContract.ListColumns.RELEASE_DATE)).substring(0, 4));
//            ((TextView)findViewById(R.id.overview_text)).setText(cursor.getString(cursor.getColumnIndex(MovieDBContract.ListColumns.OVERVIEW)));
//            ((TextView)findViewById(R.id.rating_text)).setText(cursor.getString(cursor.getColumnIndex(MovieDBContract.ListColumns.USER_RATING)).substring(0, 3) + "/10");
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        }