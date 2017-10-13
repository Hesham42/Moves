package com.example.hesham.moves;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hesham.moves.Utilities.InternetConnection;
import com.example.hesham.moves.Utilities.MoviesAPI;
import com.example.hesham.moves.adapter.AdapterOfTriall.RecyclerAdapter;
import com.example.hesham.moves.model.modelVedio.ResultTrial;
import com.example.hesham.moves.model.modelVedio.Trial;
import com.example.hesham.moves.model.modelaLLmovesdata.ResultModel;
import com.example.hesham.moves.model.modelreviews.Resultreviews;
import com.example.hesham.moves.model.modelreviews.Reviews;
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
    TextView Title, data, Time, Rate, Dec, Revo;
    RecyclerView recyclerView;
    RecyclerAdapter adapter;


    MoviesAPI moviesAPI;
    Trial trial;
    Reviews reviews;
    List<ResultTrial> resultTrials = new ArrayList<>();
    List<String> Keys = new ArrayList<>();
    List<String> TrialName = new ArrayList<>();

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
        Revo = (TextView) findViewById(R.id.Reveo);
        img = (ImageView) findViewById(R.id.ImageOfResutl);

//        recyclerView = (RecyclerView) findViewById(R.id.DetailsRec);
//        recyclerView.setHasFixedSize(true);
        //to use RecycleView, you need a layout manager. default is LinearLayoutManager
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(linearLayoutManager);

        if (InternetConnection.checkConnection(getApplicationContext())) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MoviesAPI.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            moviesAPI = retrofit.create(MoviesAPI.class);
            final Intent i = getIntent();
            model = (ResultModel) i.getSerializableExtra("sampleObject");
            if (model != null) {
                Title.setText(model.getTitle());
                Dec.setText(model.getOverview());
                Picasso.with(Details.this).load("http://image.tmdb.org/t/p/w185/" + model.getPosterPath()).into(img);
                data.setText(model.getReleaseDate());
                Rate.setText(model.getVoteAverage() + "/10");


                Call<Trial> reCall = moviesAPI.selectedVedio(model.getId());
                reCall.enqueue(new Callback<Trial>() {
                    @Override
                    public void onResponse(Call<Trial> call, Response<Trial> response) {
                        trial = response.body();
                        resultTrials = trial.getResults();
                        for (int i = 0; i < resultTrials.size(); i++) {
//                            Log.d("Guinness", resultTrials.get(i).getKey());
                            Keys.add(resultTrials.get(i).getKey());
                            TrialName.add(resultTrials.get(i).getName());

                        }
//                        adapter = new RecyclerAdapter(Details.this,Keys,TrialName);
//                        recyclerView.setAdapter(adapter);

                    }

                    @Override
                    public void onFailure(Call<Trial> call, Throwable t) {

                    }
                });


            } else {
                Toast.makeText(this, "there is no internet", Toast.LENGTH_LONG).show();

            }

//            Call<Reviews> reCallRevo = moviesAPI.selectedReviews(PoplarModel.getId());
//            reCallRevo.enqueue(new Callback<Reviews>() {
//                @Override
//                public void onResponse(Call<Reviews> call, Response<Reviews> response) {
//
//                    reviews = response.body();
//                    Log.d("Guinness",reviews.getPage().toString());
//
////                    List<Resultreviews> resultreviews = reviews.getResults();
////                    for (int i = 0; i < resultreviews.size(); i++) {
////                        List<String> strings = new ArrayList<String>();
////                        strings.add(resultreviews.get(i).getContent());
////                    }
//
//
//                }
//
//                @Override
//                public void onFailure(Call<Reviews> call, Throwable t) {
//                    Log.d("Guinness", "Failed in reviews ");
//                }
//            });

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
}

