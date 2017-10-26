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
import com.example.hesham.moves.adapter.AdapterOfTrial.AdapterOfTrial;
import com.example.hesham.moves.model.modelVedio.ResultTrial;
import com.example.hesham.moves.model.modelVedio.Trailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.hesham.moves.Utilities.NetworkStateChangeReceiver.IS_NETWORK_AVAILABLE;

public class Trial extends AppCompatActivity {
    RecyclerView Rec;
    ImageView imageView;

    MoviesAPI moviesAPI;
    AdapterOfTrial adapterOfTrial;
    Trailer trailers;
    List<ResultTrial> trialList = new ArrayList<>();

    public static final String API_KEY = BuildConfig.API_KEY;

    int id;
    String img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trial);
        Intent i = getIntent();
        if (i!=null){
            img =i.getStringExtra("img");
            id=Integer.parseInt(i.getStringExtra("id"));
            imageView=(ImageView)findViewById(R.id.ImgeOfBackground);
            Picasso.with(Trial.this).load("http://image.tmdb.org/t/p/w185/" + img).into(imageView);

            adapterOfTrial = new AdapterOfTrial(this, trialList);
            Rec = (RecyclerView) findViewById(R.id.RecTrial);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            Rec.setLayoutManager(mLayoutManager);
            Rec.setAdapter(adapterOfTrial);
            adapterOfTrial.notifyDataSetChanged();
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
        if (InternetConnection.checkConnection(Trial.this)) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MoviesAPI.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            moviesAPI = retrofit.create(MoviesAPI.class);


            Call<Trailer> TrialRecall = moviesAPI.selectedVedio(id, API_KEY);
            TrialRecall.enqueue(new Callback<Trailer>() {
                @Override
                public void onResponse(Call<Trailer> call, Response<Trailer> response) {
                    trailers = response.body();
                    Log.e("the Trials -> ", trailers.toString());
                    trialList = trailers.getResults();
                    Rec.setAdapter(new AdapterOfTrial(getApplicationContext(), trialList));
                    Rec.smoothScrollToPosition(0);
                }

                @Override
                public void onFailure(Call<Trailer> call, Throwable t) {

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
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            GETAPI();
        }
    }

}
