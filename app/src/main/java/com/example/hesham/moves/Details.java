package com.example.hesham.moves;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hesham.moves.Utilities.CommentUpdateModel;
import com.example.hesham.moves.Utilities.InternetConnection;
import com.example.hesham.moves.data.FavoriteContract;
import com.example.hesham.moves.data.FavoriteDbHelper;
import com.example.hesham.moves.model.modelaLLmovesdata.ResultModel;
import com.squareup.picasso.Picasso;

import static com.example.hesham.moves.data.FavoriteContract.FavoriteEntry.CONTENT_URI;

public class Details extends AppCompatActivity {
    ResultModel model;
    ImageView img;
    TextView Title, data, Time, Rate, Dec;
    String chechs = null;
    int id;
    String title;
    String time;
    Double rate;
    String dec;
    String image;
    int fav = 0;
    private static final int Favoirut_LOADER_ID = 0;

    private FavoriteDbHelper favoriteDbHelper;

    ResultModel favorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        favoriteDbHelper = new FavoriteDbHelper(Details.this);
        Title = (TextView) findViewById(R.id.TitleTex);
        data = (TextView) findViewById(R.id.HistroyTitile);
        Rate = (TextView) findViewById(R.id.Rate);
        Dec = (TextView) findViewById(R.id.Desc);
        Dec.setMovementMethod(new ScrollingMovementMethod());
        img = (ImageView) findViewById(R.id.ImageOfResutl);

        if (savedInstanceState == null) {
            Intent i = getIntent();
            model = (ResultModel) i.getSerializableExtra("sampleObject");
            try {
                chechs = i.getStringExtra("fav");
                if (chechs != null) {
                    fav = 1;
                } else {

                }
            } catch (Exception e) {
            }

            if (model != null) {
                id = model.getId();
                title = model.getTitle();
                rate = model.getVoteAverage();
                dec = model.getOverview();
                image = model.getPosterPath();
                time = model.getReleaseDate();
            }

        } else {
            fav = savedInstanceState.getInt("fav");
            id = savedInstanceState.getInt("id");
            title = savedInstanceState.getString("title");
            rate = savedInstanceState.getDouble("rate");
            dec = savedInstanceState.getString("dec");
            image = savedInstanceState.getString("image");
            time = savedInstanceState.getString("time");
        }
        Title.setText(title);
        Dec.setText(dec);
        Picasso.with(Details.this).load("http://image.tmdb.org/t/p/w185/" + image).into(img);
        data.setText(time);
        Rate.setText(rate + "/10");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("fav", fav);
        outState.putInt("id", id);
        outState.putString("title", title);
        outState.putDouble("rate", rate);
        outState.putString("dec", dec);
        outState.putString("image", image);
        outState.putString("time", time);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);

    }


    public void ONTrial(View view) {
        if (InternetConnection.checkConnection(Details.this)) {
            Intent i = new Intent(getBaseContext(), Trial.class);
            i.putExtra("id", String.valueOf(id));
            i.putExtra("img", image);
            startActivity(i);
        } else {
            Toast.makeText(getApplicationContext(),
                    "ther is no internet Connection pleas open the internet  to Open the Trials ",
                    Toast.LENGTH_LONG).show();

        }
    }

    public void OnReviews(View view) {
        if (InternetConnection.checkConnection(Details.this)) {
            Intent i = new Intent(getBaseContext(), ReviewsActivity.class);
            i.putExtra("id", String.valueOf(id));
            i.putExtra("img", image);
            startActivity(i);
        } else {
            Toast.makeText(getApplicationContext(),
                    "ther is no internet Connection pleas open the internet  to Open the ReviewsActivity ",
                    Toast.LENGTH_LONG).show();

        }

    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//
//        // Checks the orientation of the screen
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            GetData();
//        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
//            GetData();
//        }
//    }

    private void GetData() {
        Title.setText(title);
        Dec.setText(dec);
        Picasso.with(Details.this).load("http://image.tmdb.org/t/p/w185/" + image).into(img);
        data.setText(time);
        Rate.setText(rate + "/10");
    }

    public void SaveAndDelelteFavorite() {
        ResultModel favorite = new ResultModel();
        boolean flag = favoriteDbHelper.Exists(model.getId());
        if (flag == false) {
            favorite = model;
            ContentValues values = new ContentValues();
            values.put(FavoriteContract.FavoriteEntry.COLUMN_MOVIEID, model.getId());
            values.put(FavoriteContract.FavoriteEntry.COLUMN_TITLE, model.getOriginalTitle());
            values.put(FavoriteContract.FavoriteEntry.COLUMN_USERRATING, model.getVoteAverage());
            values.put(FavoriteContract.FavoriteEntry.COLUMN_POSTER_PATH, model.getPosterPath());
            values.put(FavoriteContract.FavoriteEntry.COLUMN_OVERVIEW, model.getOverview());

            Uri uri = getContentResolver().insert(CONTENT_URI, values);
            Log.e("Guinness","enter save ");
            if (uri != null) {
                Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
            }

        } else {
//-------------------------------------------------------
            Log.e("Guinness","enter delete statment");
            int id = (int) model.getId();

            // Build appropriate uri with String row id appended
            String stringId = Integer.toString(id);
            Uri uri = FavoriteContract.FavoriteEntry.CONTENT_URI;
            uri = uri.buildUpon().appendPath(stringId).build();

            // COMPLETED (2) Delete a single row of data using a ContentResolver
            Log.e("Guinness","start at Delete with URL ");
             int i=getContentResolver().delete(uri, null, null);
            Log.e("Guinness","returnt statment = "+Integer.toString(i));

            CommentUpdateModel.getInstance().DeleteComment();
            finish();
//            -------------------------------------------------
//          favoriteDbHelper.deleteFavorite(id);

            Toast.makeText(this, "You delete the :::::->>>> :" + title, Toast.LENGTH_LONG).show();

        }

    }

    public void OnFavorite(View view) {
        SaveAndDelelteFavorite();
    }
}