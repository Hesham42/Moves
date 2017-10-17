package com.example.hesham.moves.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Hesham on 10/17/2017.
 */

public class FavouritDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME= "favorite.db";
    private static final int DATABASE_VERSION=1;

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase db;

    public FavouritDbHelper(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    public void open() {
        Log.d("Guinness","Databse open"+"--------->");
        db= dbhandler.getWritableDatabase();
    }
    public void close() {
        Log.d("Guinness","Databse close"+"--------->");
         dbhandler.close();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    final  String SQL_CREATE_FAVORITE_TABLE="CREATE TABLE "+FavouriteContract.FavoriteEntry.TABLE_NAME + " ("+
            FavouriteContract.FavoriteEntry._ID+"INTEGER PRIMARY KEY AUTOINCREMENT, "+
            FavouriteContract.FavoriteEntry.COLUMN_MOVIEID+"INTEGER, "+
            FavouriteContract.FavoriteEntry.COLUMN_TITILE+"TEXT NOT NULL, "+
            FavouriteContract.FavoriteEntry.COLUMN_USERRATING+"REAL NOT NULL, "+
            FavouriteContract.FavoriteEntry.COLUMN_POSTPATH+"TEXT NOT NULL, "+
            FavouriteContract.FavoriteEntry.COLUMN_PLOT_SYNOPSIS+"TEXT NOT NULL, "+"); ";


        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
