package com.example.hesham.moves;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.hesham.moves.R;
import com.example.hesham.moves.Utilities.InternetConnection;
import com.example.hesham.moves.Utilities.MoviesAPI;
import com.example.hesham.moves.adapter.AdapterOFAllMovies.MoviesAdapter;
import com.example.hesham.moves.model.modelaLLmovesdata.MovesModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AllReviews extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_reviews);

        }
}
