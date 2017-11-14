package com.example.hesham.moves;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hesham.moves.Utilities.InternetConnection;
import com.example.hesham.moves.Utilities.MoviesAPI;
import com.example.hesham.moves.Utilities.NetworkStateChangeReceiver;
import com.example.hesham.moves.adapter.AdapterOfReviews.ReviewsAdapter;
import com.example.hesham.moves.adapter.AdapterOfTrial.AdapterOfTrial;
import com.example.hesham.moves.model.modelVedio.Trailer;
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

import static com.example.hesham.moves.Utilities.NetworkStateChangeReceiver.IS_NETWORK_AVAILABLE;

public class ReviewsActivity extends AppCompatActivity {
    RecyclerView Rec;
    ImageView imageView;

    MoviesAPI moviesAPI;
    Reviews reviews;
    List<Resultreviews> resultreviewsList = new ArrayList<>();
    ReviewsAdapter adapter;

    public static final String API_KEY = BuildConfig.API_KEY;

    int id;
    String img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        Intent i = getIntent();
        if (i != null) {
            img = i.getStringExtra("img");
            id = Integer.parseInt(i.getStringExtra("id"));
            imageView = (ImageView) findViewById(R.id.ImgeOfBackgroundOfReview);
            Picasso.with(ReviewsActivity.this).load("http://image.tmdb.org/t/p/w185/" + img).into(imageView);

            adapter = new ReviewsAdapter(this, resultreviewsList);
            Rec = (RecyclerView) findViewById(R.id.RecReview);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            Rec.setLayoutManager(mLayoutManager);
            Rec.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            GETAPI();
            IntentFilter intentFilter = new IntentFilter(NetworkStateChangeReceiver.NETWORK_AVAILABLE_ACTION);
            LocalBroadcastManager.getInstance(this).registerReceiver(
                    new BroadcastReceiver() {
                        @Override
                        public void onReceive(Context context, Intent intent) {
                            boolean isNetworkAvailable = intent.getBooleanExtra(IS_NETWORK_AVAILABLE, false);
                            String networkStatus = isNetworkAvailable ? "connected" : "disconnected";
                            if (networkStatus == "connected") {
                                GETAPI();
                            } else if (networkStatus == "disconnected") {
                                Toast.makeText(getApplicationContext(), "ther is no internet Connection pleas open the internet", Toast.LENGTH_LONG).show();

                            }
                        }
                    }, intentFilter);

        }
    }


    private void GETAPI() {
        if (InternetConnection.checkConnection(ReviewsActivity.this)) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MoviesAPI.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            moviesAPI = retrofit.create(MoviesAPI.class);
            Call<Reviews> TrialRecall = moviesAPI.selectedReviews(id, API_KEY);
            TrialRecall.enqueue(new Callback<Reviews>() {
                @Override
                public void onResponse(Call<Reviews> call, Response<Reviews> response) {

                    resultreviewsList = response.body().getResults();
                    Rec.setAdapter(new ReviewsAdapter(getApplicationContext(), resultreviewsList));
                    Rec.smoothScrollToPosition(0);

                }

                @Override
                public void onFailure(Call<Reviews> call, Throwable t) {

                }
            });


        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            GETAPI();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            GETAPI();
        }
    }

}
