package com.example.dishantkaushik.movieapp;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by dishantkaushik on 12/03/16.
 */
public class MovieDBContract {
    public interface ListColumns {

        @DataType(DataType.Type.INTEGER) @PrimaryKey String _ID = "_id";

        @DataType(DataType.Type.TEXT)  String TITLE = "title";

        @DataType(DataType.Type.TEXT) String POSTER_URL="poster_url";

        @DataType(DataType.Type.TEXT) String OVERVIEW="overview";

        @DataType(DataType.Type.TEXT) String USER_RATING="user_rating";

        @DataType(DataType.Type.TEXT) String RELEASE_DATE="release_date";



    }
    public interface RatingListColumns{
        @DataType(DataType.Type.INTEGER) @PrimaryKey String _RID="_rid";

        @DataType(DataType.Type.TEXT) String AUTHOR="author";

        @DataType(DataType.Type.TEXT) String CONTENT="content";
    }
    public interface VideosListColumns{
        @DataType(DataType.Type.INTEGER) @PrimaryKey String _VID="_vid";

        @DataType(DataType.Type.TEXT) String NAME="name";

        @DataType(DataType.Type.TEXT) String SITE="site";

        @DataType(DataType.Type.TEXT) String SIZE="size";

        @DataType(DataType.Type.TEXT) String KEY="key";
    }
}
