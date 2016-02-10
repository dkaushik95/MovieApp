package com.example.dishantkaushik.movieapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
    private final String popularurl="http://api.themoviedb.org/3/movie/popular?api_key=";//here*
    private final String highestRatedUrl="http://api.themoviedb.org/3/movie/top_rated?api_key=";//and here*
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
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idchange == 1) {
                    Snackbar.make(view, "Changed to Popular", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    parseJson(popularurl);
                    idchange = 2;
                } else {
                    Snackbar.make(view, "Changed to Most Rated", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    parseJson(highestRatedUrl);
                    idchange = 1;
                }
            }
        });
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
                        movieData.allMovies.add(i,m1);

                    }
                    recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
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
