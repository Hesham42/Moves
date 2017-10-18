package com.example.hesham.moves;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hesham.moves.Utilities.InternetConnection;
import com.example.hesham.moves.Utilities.MoviesAPI;
import com.example.hesham.moves.adapter.AdapterOfTriall.RecyclerAdapter;
import com.example.hesham.moves.data.FavouritDbHelper;
import com.example.hesham.moves.model.modelVedio.ResultTrial;
import com.example.hesham.moves.model.modelVedio.Trial;
import com.example.hesham.moves.model.modelaLLmovesdata.ResultModel;
import com.example.hesham.moves.model.modelreviews.Reviews;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.hesham.moves.MainActivity.API_KEY;

public class Details extends AppCompatActivity {
    ResultModel model;
    ImageView img;
    TextView Title, data, Reviewer, Rate, Dec;
    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    private FavouritDbHelper favouritDbHelper;
    private final AppCompatActivity activity = Details.this;
    Boolean Deleted = false;

    MoviesAPI moviesAPI;
    Trial trial;
    List<ResultTrial> resultTrials = new ArrayList<>();
    List<String> Keys = new ArrayList<>();
    List<String> TrialName = new ArrayList<>();

    Reviews reviews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Title = (TextView) findViewById(R.id.TitleTex);
        data = (TextView) findViewById(R.id.HistroyTitile);
        Rate = (TextView) findViewById(R.id.Rate);
        Dec = (TextView) findViewById(R.id.Desc);
        img = (ImageView) findViewById(R.id.ImageOfResutl);

        if (InternetConnection.checkConnection(getApplicationContext())) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MoviesAPI.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            moviesAPI = retrofit.create(MoviesAPI.class);

            Intent i = getIntent();
            model = (ResultModel) i.getSerializableExtra("sampleObject");
            if (model != null) {
                Title.setText(model.getTitle());
                Dec.setText(model.getOverview());
                Picasso.with(Details.this).load("http://image.tmdb.org/t/p/w185/" + model.getPosterPath()).into(img);
                data.setText(model.getReleaseDate());
                Rate.setText(model.getVoteAverage() + "/10");

                Call<Reviews> reCall = moviesAPI.selectedReviews(model.getId(), API_KEY);
                reCall.enqueue(new Callback<Reviews>() {
                    @Override
                    public void onResponse(Call<Reviews> call, Response<Reviews> response) {

                    }

                    @Override
                    public void onFailure(Call<Reviews> call, Throwable t) {

                    }
                });


            } else {
                Toast.makeText(this, "there is no internet", Toast.LENGTH_LONG).show();

            }


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    public void OnFavourit(View view) {
        if (Deleted == false) {
            SharedPreferences.Editor editor = getSharedPreferences("com.example.hesham.moves.Details", MODE_PRIVATE).edit();
            editor.putBoolean("Favourit Added", true);
            editor.commit();
            SaveFavourit();
            Toast.makeText(this, "Added To Favourit", Toast.LENGTH_LONG).show();

        }
        if (Deleted == true) {
            favouritDbHelper = new FavouritDbHelper(this);
            favouritDbHelper.deletedFavourit(model.getId());
            SharedPreferences.Editor editor = getSharedPreferences("com.example.hesham.moves.Details", MODE_PRIVATE).edit();
            editor.putBoolean("Favourit Removed", true);
            editor.commit();
            Toast.makeText(this, "Remove To Favourit", Toast.LENGTH_LONG).show();
        }
        if (Deleted == true) {
            Deleted = false;
        }
        if (Deleted == false) {
            Deleted = true;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    private void SaveFavourit() {
        favouritDbHelper = new FavouritDbHelper(activity);
        favouritDbHelper.addFavorie(model);

    }

    public void OnTrial(View view) {
        Intent intent = new Intent(Details.this, AllTrial.class);
        startActivity(intent);
    }

    public void OnReviews(View view) {
        Intent intent = new Intent(Details.this, AllReviews.class);
        startActivity(intent);

    }
}