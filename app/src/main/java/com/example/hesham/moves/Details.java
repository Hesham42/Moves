package com.example.hesham.moves;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hesham.moves.Utilities.CommentUpdateModel;
import com.example.hesham.moves.Utilities.InternetConnection;
import com.example.hesham.moves.Utilities.MoviesAPI;
import com.example.hesham.moves.adapter.AdapterOFAllMovies.MoviesAdapter;
import com.example.hesham.moves.adapter.AdapterOfTrial.AdapterOfTrial;
import com.example.hesham.moves.data.FavoriteDbHelper;
import com.example.hesham.moves.model.modelVedio.ResultTrial;
import com.example.hesham.moves.model.modelVedio.Trailer;
import com.example.hesham.moves.model.modelaLLmovesdata.MovesModel;
import com.example.hesham.moves.model.modelaLLmovesdata.ResultModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Details extends AppCompatActivity {
    ResultModel model;
    ImageView img;
    TextView Title, data, Time, Rate, Dec;
    String chechs=null;
    int id;
    String title;
    String time;
    Double rate;
    String dec;
    String image;
    int fav=0;

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
        Title = (TextView) findViewById(R.id.TitleTex);
        data = (TextView) findViewById(R.id.HistroyTitile);
        Rate = (TextView) findViewById(R.id.Rate);
        Dec = (TextView) findViewById(R.id.Desc);
        Dec.setMovementMethod(new ScrollingMovementMethod());
        img = (ImageView) findViewById(R.id.ImageOfResutl);

        if (savedInstanceState == null) {
            Intent i = getIntent();
            model = (ResultModel) i.getSerializableExtra("sampleObject");
            try{
             chechs =  i.getStringExtra("fav");
                if (chechs!=null){
                   fav=1;
                }else{

                }
            }catch (Exception e){
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
            fav=savedInstanceState.getInt("fav");
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
        outState.putInt("fav",fav);
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            GetData();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            GetData();
        }
    }

    private void GetData() {
        Title.setText(title);
        Dec.setText(dec);
        Picasso.with(Details.this).load("http://image.tmdb.org/t/p/w185/" + image).into(img);
        data.setText(time);
        Rate.setText(rate + "/10");
    }

    public void saveFavorite(){
        favoriteDbHelper = new FavoriteDbHelper(this);
        ResultModel favorite = new ResultModel();
        favorite=model;
        favoriteDbHelper.addFavorite(favorite);
    }

    public void OnFavorite(View view) {
        if (fav==0){
            fav=1;
            saveFavorite();
            Toast.makeText(this,"You save this Movies on favourit  :"+title,Toast.LENGTH_LONG).show();
        }else if (fav==1){
            favoriteDbHelper = new FavoriteDbHelper(Details.this);
            favoriteDbHelper.deleteFavorite(id);
            CommentUpdateModel.getInstance().DeleteComment();
            fav=0;
            Toast.makeText(this,"You delete this Movies from favourit :"+title,Toast.LENGTH_LONG).show();
        }
    }
}