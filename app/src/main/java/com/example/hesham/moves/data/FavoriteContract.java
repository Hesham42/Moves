package com.example.hesham.moves.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Hesham on 10/12/2017.
 */

public class FavoriteContract {
/* todo: Add Content provider Constants to the Contract
    * Client need to know to acess the task data, and it's your job to provide
    * these content URI's for the path to that data
    * 1) Content Authority
    * 2) base Content URl
    * 3)path(s) to the tasks directory
    * 4)Content  URL for data in the taskEntry Class
    * */

//    the authority .which is how your code knows which content  provider to acess
    public static String AUTHORITY="com.example.hesham.moves";
//    the base Content URl="content://"+AUTHORITY
    public static final Uri BASE_CONTENT_URI=Uri.parse("content://"+AUTHORITY);
//    Define the possible path for accessing data in this contract
//    this is the path for the favourit directory
    public static final String PATH_TASKS="favorite";


    public static final class FavoriteEntry implements BaseColumns {
        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_MOVIEID = "movieid";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_USERRATING = "userrating";
        public static final String COLUMN_POSTER_PATH = "posterpath";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();



    }
}
