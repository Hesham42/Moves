package com.example.hesham.moves;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hesham.moves.Utilities.MoviesAPI;
import com.example.hesham.moves.model.modelVedio.ResultTrial;
import com.example.hesham.moves.model.modelVedio.Trial;
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

    MoviesAPI moviesAPI;
    Trial trial;
    List<ResultTrial> resultTrials= new ArrayList<>();
    List<String> Keys= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MoviesAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        moviesAPI = retrofit.create(MoviesAPI.class);
        Intent i = getIntent();
        model = (ResultModel) i.getSerializableExtra("sampleObject");
        Title = (TextView) findViewById(R.id.TitleTex);
        data = (TextView) findViewById(R.id.HistroyTitile);
        Time = (TextView) findViewById(R.id.Houre);
        Rate = (TextView) findViewById(R.id.Rate);
        Dec = (TextView) findViewById(R.id.Desc);
        img=(ImageView)findViewById(R.id.ImageOfResutl);
        if (model != null) {
            Title.setText(model.getTitle());
            Dec.setText(model.getOverview());
            Picasso.with(Details.this).load("http://image.tmdb.org/t/p/w185/" + model.getPosterPath()).into(img);
            data.setText(model.getReleaseDate());
            Rate.setText(model.getVoteAverage()+"/10");


                    Call<Trial> reCall = moviesAPI.selectedVedio(model.getId());
            reCall.enqueue(new Callback<Trial>() {
                @Override
                public void onResponse(Call<Trial> call, Response<Trial> response) {
                    trial= response.body();
                    resultTrials= trial.getResults();
                    for (int i=0;i<resultTrials.size();i++)
                    {
                        Log.d("Guinness",resultTrials.get(i).getKey());
                        Keys.add(resultTrials.get(i).getKey());

                    }

                }

                @Override
                public void onFailure(Call<Trial> call, Throwable t) {

                }
            });


        }

    }
}
