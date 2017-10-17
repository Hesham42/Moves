package com.example.hesham.moves.data;

import android.provider.BaseColumns;

/**
 * Created by Hesham on 10/12/2017.
 */

public class FavouriteContract {

    public static final class FavoriteEntry implements BaseColumns {
        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_MOVIEID = "movieid";
        public static final String COLUMN_TITILE = "title";
        public static final String COLUMN_USERRATING = "userrating";
        public static final String COLUMN_POSTPATH = "postpath";
        public static final String COLUMN_PLOT_SYNOPSIS = "overview";




    }
}
