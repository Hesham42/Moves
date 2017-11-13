package com.example.hesham.moves.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.hesham.moves.model.modelaLLmovesdata.ResultModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.hesham.moves.data.FavoriteContract.FavoriteEntry.COLUMN_MOVIEID;
import static com.example.hesham.moves.data.FavoriteContract.FavoriteEntry.COLUMN_OVERVIEW;
import static com.example.hesham.moves.data.FavoriteContract.FavoriteEntry.COLUMN_POSTER_PATH;
import static com.example.hesham.moves.data.FavoriteContract.FavoriteEntry.COLUMN_TITLE;
import static com.example.hesham.moves.data.FavoriteContract.FavoriteEntry.COLUMN_USERRATING;
import static com.example.hesham.moves.data.FavoriteContract.FavoriteEntry.TABLE_NAME;

/**
 * Created by Hesham on 10/17/2017.
 */

public class FavoriteDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "favorite.db";

    private static final int DATABASE_VERSION = 1;

    public static final String LOGTAG = "FAVORITE";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase db;

    public FavoriteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() {
        Log.i(LOGTAG, "Database Opened");
        db = dbhandler.getWritableDatabase();
    }

    public void close() {
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_MOVIEID + " INTEGER PRIMARY KEY, " +
                COLUMN_TITLE + " TEXT NOT NULL, " +
                COLUMN_USERRATING + " REAL NOT NULL, " +
                COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                COLUMN_OVERVIEW + " TEXT NOT NULL" +
                "); ";



        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public void addFavorite(ResultModel movie) {
        boolean flag = false;
        ///////////////////////////////////////////////////////
        String[] columns = {
                COLUMN_MOVIEID
        };
        String sortOrder =
                FavoriteContract.FavoriteEntry._ID + " ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);

        if (cursor.moveToFirst()) {
            do {
                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_MOVIEID)
                )) == movie.getId()) {
                    flag = true;
                }

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
//////////////////////////////////////////////
        if (flag == false) {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_MOVIEID, movie.getId());
            values.put(COLUMN_TITLE, movie.getOriginalTitle());
            values.put(COLUMN_USERRATING, movie.getVoteAverage());
            values.put(COLUMN_POSTER_PATH, movie.getPosterPath());
            values.put(COLUMN_OVERVIEW, movie.getOverview());

            db.insert(TABLE_NAME, null, values);
            db.close();

        }
    }

    public void deleteFavorite(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, FavoriteContract.FavoriteEntry._ID + "=" + id, null);
    }

    public List<ResultModel> getAllFavorite() {
        String[] columns = {
                COLUMN_MOVIEID,
                COLUMN_TITLE,
                COLUMN_USERRATING,
                COLUMN_POSTER_PATH,
                COLUMN_OVERVIEW

        };
        String sortOrder =
                FavoriteContract.FavoriteEntry._ID + " ASC";
        List<ResultModel> favoriteList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);

        if (cursor.moveToFirst()) {
            do {
                ResultModel movie = new ResultModel();
                movie.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_MOVIEID))));
                movie.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                movie.setVoteAverage(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_USERRATING))));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndex(COLUMN_POSTER_PATH)));
                movie.setOverview(cursor.getString(cursor.getColumnIndex(COLUMN_OVERVIEW)));

                favoriteList.add(movie);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return favoriteList;
    }
    public boolean Exists(int ID) {
        boolean flag = false;
        String[] columns = {
                COLUMN_MOVIEID
        };
        String sortOrder =
                FavoriteContract.FavoriteEntry.COLUMN_MOVIEID+ " ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);

        if (cursor.moveToFirst()) {
            do {
                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_MOVIEID)
                )) == ID) {
                    flag = true;
                }

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return flag;
}
}