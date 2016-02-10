package com.example.dishantkaushik.movieapp;

import java.util.ArrayList;


/**
 * Created by dishantkaushik on 09/02/16.
 * original title
 movie poster image thumbnail
 A plot synopsis (called overview in the api)
 user rating (called vote_average in the api)
 release date
 */
public class movieData {
    static ArrayList<movieData> allMovies=new ArrayList<movieData>();
    private String title;
    private String posterurl;
    private String overview;
    private Float user_rating;
    private String releaseDate;

    public String getTitle() {
        return title;
    }

    public String getPosterurl() {
        return posterurl;
    }

    public String getOverview() {
        return overview;
    }

    public Float getUser_rating() {
        return user_rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    static void reset(){
        allMovies.clear();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPosterurl(String posterurl) {
        this.posterurl = posterurl;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setUser_rating(Float user_rating) {
        this.user_rating = user_rating;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
