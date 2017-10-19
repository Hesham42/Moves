package com.example.hesham.moves;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NavUtils;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hesham.moves.Utilities.InternetConnection;
import com.example.hesham.moves.Utilities.MoviesAPI;
import com.example.hesham.moves.model.modelaLLmovesdata.ResultModel;
import com.squareup.picasso.Picasso;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Details extends AppCompatActivity {
    ResultModel model;
    ImageView img;
    TextView Title, data, Time, Rate, Dec;
    MoviesAPI moviesAPI;

    String title;
    String time;
    Double rate;
    String dec;
    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Title = (TextView) findViewById(R.id.TitleTex);
        data = (TextView) findViewById(R.id.HistroyTitile);
        Time = (TextView) findViewById(R.id.Houre);
        Rate = (TextView) findViewById(R.id.Rate);
        Dec = (TextView) findViewById(R.id.Desc);
        Dec.setMovementMethod(new ScrollingMovementMethod());
        img = (ImageView) findViewById(R.id.ImageOfResutl);

        if (savedInstanceState == null) {
            Intent i = getIntent();
            model = (ResultModel) i.getSerializableExtra("sampleObject");
            if (model != null) {
                title = model.getTitle();
                rate = model.getVoteAverage();
                dec = model.getOverview();
                image = model.getPosterPath();
                time = model.getReleaseDate();
            }

        } else {
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
}





//    private void CallApi() {
//        if (InternetConnection.checkConnection(Details.this)) {
//
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl(MoviesAPI.BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//
//            moviesAPI = retrofit.create(MoviesAPI.class);
//        } else {
//            Toast.makeText(getApplicationContext(), "Opent ther internet to get data ", Toast.LENGTH_LONG).show();
//
//        }
//    }
