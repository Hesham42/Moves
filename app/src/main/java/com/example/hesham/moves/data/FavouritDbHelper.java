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

/**
 * Created by Hesham on 10/17/2017.
 */

public class FavouritDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favorite.db";
    private static final int DATABASE_VERSION = 1;

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase db;

    public FavouritDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() {
        Log.d("Guinness", "Databse open" + "--------->");
        db = dbhandler.getWritableDatabase();
    }

    public void close() {
        Log.d("Guinness", "Databse close" + "--------->");
        dbhandler.close();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + FavouriteContract.FavoriteEntry.TABLE_NAME + " (" +
                FavouriteContract.FavoriteEntry._ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FavouriteContract.FavoriteEntry.COLUMN_MOVIEID + "INTEGER, " +
                FavouriteContract.FavoriteEntry.COLUMN_TITILE + "TEXT NOT NULL, " +
                FavouriteContract.FavoriteEntry.COLUMN_USERRATING + "REAL NOT NULL, " +
                FavouriteContract.FavoriteEntry.COLUMN_POSTPATH + "TEXT NOT NULL, " +
                FavouriteContract.FavoriteEntry.COLUMN_Overview + "TEXT NOT NULL, " + "); ";


        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavouriteContract.FavoriteEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addFavorie(ResultModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FavouriteContract.FavoriteEntry.COLUMN_MOVIEID, model.getId());
        values.put(FavouriteContract.FavoriteEntry.COLUMN_TITILE, model.getTitle());
        values.put(FavouriteContract.FavoriteEntry.COLUMN_USERRATING, model.getVoteAverage());
        values.put(FavouriteContract.FavoriteEntry.COLUMN_POSTPATH, model.getBackdropPath());
        values.put(FavouriteContract.FavoriteEntry.COLUMN_Overview, model.getOverview());

        db.insert(FavouriteContract.FavoriteEntry.TABLE_NAME, null, values);
        db.close();
    }

    public void deletedFavourit(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FavouriteContract.FavoriteEntry.TABLE_NAME, FavouriteContract.FavoriteEntry.COLUMN_MOVIEID + "=" + id, null);
//        db.close();
    }

    public List<ResultModel> getAllFavourit() {
        String[] columns = {
                FavouriteContract.FavoriteEntry._ID,
                FavouriteContract.FavoriteEntry.COLUMN_MOVIEID,
                FavouriteContract.FavoriteEntry.COLUMN_TITILE,
                FavouriteContract.FavoriteEntry.COLUMN_USERRATING,
                FavouriteContract.FavoriteEntry.COLUMN_POSTPATH,
                FavouriteContract.FavoriteEntry.COLUMN_Overview
        };
        String SortOdrder = FavouriteContract.FavoriteEntry._ID + "ASC";
        List<ResultModel> favouritList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(FavouriteContract.FavoriteEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                SortOdrder);

        if (cursor.moveToFirst()) {
            do {
                ResultModel model = new ResultModel();
                model.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FavouriteContract.FavoriteEntry.COLUMN_MOVIEID))));
                model.setOriginalTitle(cursor.getString(cursor.getColumnIndex(FavouriteContract.FavoriteEntry.COLUMN_TITILE)));
                model.setVoteAverage(Double.parseDouble(cursor.getString(cursor.getColumnIndex(FavouriteContract.FavoriteEntry.COLUMN_USERRATING))));
                model.setPosterPath(cursor.getString(cursor.getColumnIndex(FavouriteContract.FavoriteEntry.COLUMN_POSTPATH)));
                model.setOverview(cursor.getString(cursor.getColumnIndex(FavouriteContract.FavoriteEntry.COLUMN_Overview)));
                favouritList.add(model);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return favouritList;
    }
}
