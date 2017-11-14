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
import android.util.Log;

import java.util.Collections;

import static com.example.hesham.moves.data.FavoriteContract.FavoriteEntry.TABLE_NAME;

/**
 * Created by guinness on 09/11/17.
 */

public class FavoriteContentProvider extends ContentProvider {
    // Define final integer constants for the directory of tasks and a single item.
    // It's convention to use 100, 200, 300, etc for directories,
    // and related ints (101, 102, ..) for items in that directory.
    public static final int Favourits = 100;
    public static final int Favourit_With_ID = 101;
    // CDeclare a static variable for the Uri matcher that you construct
    private static final UriMatcher sURI_MATCHER = buildUriMatcher();

    // Define a static buildUriMatcher method that associates URI's with their int match

    /**
     * Initialize a new matcher object without any matches,
     * then use .addURI(String authority, String path, int match) to add matches
     */
    public static UriMatcher buildUriMatcher() {


        /*
          All paths added to the UriMatcher have a corresponding int.
          For each kind of uri you may want to access, add the corresponding match with addURI.
          The two calls below add matches for the task directory and a single item by ID.
         */
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
//        Add Matches whith AddURI(String Authority, Strign Path, int code)
        uriMatcher.addURI(FavoriteContract.AUTHORITY, FavoriteContract.PATH_TASKS, Favourits);

//        single item
        uriMatcher.addURI(FavoriteContract.AUTHORITY, FavoriteContract.PATH_TASKS + "/#", Favourit_With_ID);
        return uriMatcher;

    }
    // Member variable for a TaskDbHelper that's initialized in the onCreate() method

    private FavoriteDbHelper mFavoriteDbHelper;
    /* onCreate() is where you should initialize anything you’ll need to setup
    your underlying data source.
    In this case, you’re working with a SQLite database, so you’ll need to
    initialize a DbHelper to gain access to it.
     */

    @Override
    public boolean onCreate() {
        // Complete onCreate() and initialize a TaskDbhelper on startup
        // [Hint] Declare the DbHelper as a global variable

        Context context = getContext();
        mFavoriteDbHelper = new FavoriteDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    // Implement insert to handle requests to insert a single new row of data
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        // Get access to underlying database (read-only for query)

        final SQLiteDatabase db = mFavoriteDbHelper.getWritableDatabase();
        // Write URI match code and set a variable to return a Cursor

        int match = sURI_MATCHER.match(uri);

        Uri returnUri1;
// Query for the tasks directory and write a default case
        switch (match) {
            case Favourits:
                // Insert new values into the database
                // Inserting values into tasks table
                Long id = db.insert(TABLE_NAME, null, values);
                if (id > 0) {
//                    success
                    returnUri1 = ContentUris.withAppendedId(FavoriteContract.FavoriteEntry.CONTENT_URI, id);

                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("UnKnown uri:" + uri);

        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri1;

    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri
            , @Nullable String[] projection,
            @Nullable String selection,
            @Nullable String[] selectionArgs,
            @Nullable String sortOrder)
    {
        final SQLiteDatabase db = mFavoriteDbHelper.getReadableDatabase();

        int match = sURI_MATCHER.match(uri);

        Cursor retCursor;
        switch (match) {
            case Favourits:
                retCursor = db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri" + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs)
    {
        // Get access to the database and write URI matching code to recognize a single item
        final SQLiteDatabase db = mFavoriteDbHelper.getWritableDatabase();

        int match = sURI_MATCHER.match(uri);
        // Keep track of the number of deleted tasks
        int tasksDeleted; // starts as 0

        // Write the code to delete a single row of data
        // [Hint] Use selections to delete an item by its row ID
        switch (match) {
            // Handle the single item case, recognized by the ID included in the URI path
            case Favourit_With_ID:
                Log.e("Guinness","enter switch case ");
                // Get the task ID from the URI path
                String id = uri.getPathSegments().get(1);
                // Use selections/selectionArgs to filter for this ID
                tasksDeleted = db.delete(TABLE_NAME, "movieid=?", new String[]{id});
                break;
            default:
                Log.e("Guinness","Failed in Switch");
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Notify the resolver of a change and return the number of items deleted
        Log.e("Guinness","Fished switch");
        getContext().getContentResolver().notifyChange(uri, null);
        // Return the number of tasks deleted
        return tasksDeleted;

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        throw new UnsupportedOperationException("Not yet Implemented");

    }
}
