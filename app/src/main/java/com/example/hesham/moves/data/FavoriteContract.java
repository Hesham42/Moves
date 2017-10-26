package com.example.hesham.moves.data;

import android.provider.BaseColumns;

/**
 * Created by Hesham on 10/12/2017.
 */

public class FavoriteContract {

    public static final class FavoriteEntry implements BaseColumns {
        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_MOVIEID = "movieid";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_USERRATING = "userrating";
        public static final String COLUMN_POSTER_PATH = "posterpath";
        public static final String COLUMN_PLOT_SYNOPSIS = "overview";
        public static final String DELETEDORSAVE ="deleteorsave";



    }
}
