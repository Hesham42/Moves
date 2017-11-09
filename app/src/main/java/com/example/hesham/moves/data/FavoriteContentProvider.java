package com.example.hesham.moves.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by guinness on 09/11/17.
 */

public class FavoriteContentProvider extends ContentProvider {

    public static final int Favourits=100;
    public static final int Favourit_With_ID=101;
    private static final UriMatcher sURI_MATCHER=buildUriMatcher();


    public static UriMatcher buildUriMatcher()
    {
        UriMatcher uriMatcher= new UriMatcher(UriMatcher.NO_MATCH);
//        Add Matches whith AddURI(String Authority, Strign Path, int code)
        uriMatcher.addURI(FavoriteContract.AUTHORITY,FavoriteContract.PATH_TASKS,Favourits);

//        single item
        uriMatcher.addURI(FavoriteContract.AUTHORITY,FavoriteContract.PATH_TASKS+"/#",Favourit_With_ID);
        return uriMatcher;

    }

    private FavoriteDbHelper mFavoriteDbHelper;
    @Override
    public boolean onCreate() {
         Context context =getContext();
         mFavoriteDbHelper= new FavoriteDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        final SQLiteDatabase db =mFavoriteDbHelper.getWritableDatabase();

        int match=sURI_MATCHER.match(uri);

        Uri returnUri1;

        switch (match){
            case Favourits:
//                Inserting values into tasks Table
                Long id =db.insert(FavoriteContract.FavoriteEntry.TABLE_NAME,null,values);
                if (id>0)
                {
//                    success
                    returnUri1= ContentUris.withAppendedId(FavoriteContract.FavoriteEntry.CONTENT_URI,id);

                }else {
                    throw new SQLException("Failed to insert row into "+uri);
                }
                break;
                default:
                    throw new UnsupportedOperationException("UnKnown uri:"+uri);

        }
        getContext().getContentResolver().notifyChange(uri,null);
        return returnUri1;

    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
       final SQLiteDatabase db=mFavoriteDbHelper.getReadableDatabase();

       int match=sURI_MATCHER.match(uri);

       Cursor retCursor;
       switch (match)
       {
           case Favourits:
            retCursor=db.query(FavoriteContract.FavoriteEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder);
               break;


           default:
                   throw new UnsupportedOperationException("Unknown uri"+uri);
       }
       retCursor.setNotificationUri(getContext().getContentResolver(),uri);
       return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet Implemented");

    }
}
