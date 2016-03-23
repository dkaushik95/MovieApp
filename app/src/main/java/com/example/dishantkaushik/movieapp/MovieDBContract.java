package com.example.dishantkaushik.movieapp;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by dishantkaushik on 12/03/16.
 */
public class MovieDBContract {
    public interface ListColumns {

        @DataType(DataType.Type.INTEGER) @PrimaryKey @AutoIncrement String _ID = "_id";

        @DataType(DataType.Type.TEXT)  String TITLE = "title";

        @DataType(DataType.Type.TEXT) String POSTER_URL="poster_url";

        @DataType(DataType.Type.TEXT) String OVERVIEW="overview";

        @DataType(DataType.Type.TEXT) String USER_RATING="user_rating";

        @DataType(DataType.Type.TEXT) String RELEASE_DATE="release_date";

    }
}
