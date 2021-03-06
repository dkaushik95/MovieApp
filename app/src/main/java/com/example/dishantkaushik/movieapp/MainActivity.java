package com.example.dishantkaushik.movieapp;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcel;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    //*Please use your own API Key in the below URL's

    private final String popularurl="http://api.themoviedb.org/3/movie/popular?api_key=ee7fc288d8f352dbd247fd51129cb18d";//here*
    private final String highestRatedUrl="http://api.themoviedb.org/3/movie/top_rated?api_key=ee7fc288d8f352dbd247fd51129cb18d";//and here*
    private RecyclerView recyclerView;
    int idchange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView=(RecyclerView)findViewById(R.id.images_view);
        parseJson(popularurl);
        setTitle("Most Popular Movies");
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idchange == 1) {
                    Snackbar.make(view, "Changed to Popular", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    parseJson(popularurl);
                    idchange = 2;
                    setTitle("Most Popular Movies");
                } else if(idchange==2) {
                    Snackbar.make(view, "Changed to Most Rated", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    parseJson(highestRatedUrl);
                    idchange = 3;
                    setTitle("Most Rated Movies");

                }
                else {
                    Snackbar.make(view, "To be changed to Favourites", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    parseFav();
                    idchange = 1;
                    setTitle("My Favourite Movies");
                }
            }
        });
    }

    public void parseFav(){
        Cursor cursor=getContentResolver().query(MovieProvider.Movies.MOVIES, new String[]{MovieDBContract.ListColumns.POSTER_URL, MovieDBContract.ListColumns._ID}, null, null, null);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(new favAdapter(getApplicationContext(),cursor));
    }

    public void parseJson(String url){
        movieData.reset();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray jsonArray=response.getJSONArray("results");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject temp=jsonArray.getJSONObject(i);
                        movieData m1=new movieData();
                        m1.setOverview(temp.getString("overview"));
                        m1.setPosterurl(temp.getString("poster_path"));
                        m1.setReleaseDate(temp.getString("release_date"));
                        m1.setTitle(temp.getString("original_title"));
                        m1.setUser_rating(Float.parseFloat(temp.getString("vote_average")));
                        m1.setMovieID(temp.getString("id"));
                        movieData.allMovies.add(i,m1);
                    }
                    recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
                    recyclerView.setAdapter(new imageAdapter(MainActivity.this));
                }
                catch (Exception e){
                    Log.e("JSON ERROR", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error", error.toString());
            }
        });

        vollySingleton.getInstance(getApplicationContext()).getRequestQueue().add(jsonObjectRequest);

    }
    
}
