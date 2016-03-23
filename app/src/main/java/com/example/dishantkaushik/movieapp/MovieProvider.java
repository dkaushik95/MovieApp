package com.example.dishantkaushik.movieapp;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by dishantkaushik on 12/03/16.
 */


@ContentProvider(authority = MovieProvider.AUTHORITY, database = MovieDB.class)
public final class MovieProvider {
    public static final String AUTHORITY="com.example.dishantkaushik.movieapp.MovieProvider";
    @TableEndpoint(table = MovieDB.MOVIES) public static class Movies{
        @ContentUri(
                path="movies",
                type="vnd.android.cursor.dir/movie"
               // defaultSort = MovieDBContract.ListColumns._ID + "ASC"
        )
        public static final Uri MOVIES=Uri.parse("content://"+AUTHORITY+"/movies");

    }
}
