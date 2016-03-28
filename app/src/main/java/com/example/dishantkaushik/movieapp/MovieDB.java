package com.example.dishantkaushik.movieapp;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by dishantkaushik on 12/03/16.
 */

@Database(version = MovieDB.VERSION)

public final class MovieDB {
    public static final int VERSION=1;
    @Table(MovieDBContract.ListColumns.class) public static final String MOVIES="movies";
    @Table(MovieDBContract.RatingListColumns.class) public static final String RATINGS="ratings";
    @Table(MovieDBContract.VideosListColumns.class) public static final String VIDEOS="videos";

}
