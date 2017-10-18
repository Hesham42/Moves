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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hesham.moves.Utilities.InternetConnection;
import com.example.hesham.moves.Utilities.MoviesAPI;
import com.example.hesham.moves.Utilities.NetworkStateChangeReceiver;
import com.example.hesham.moves.model.modelVedio.ResultTrial;
import com.example.hesham.moves.model.modelVedio.Trial;
import com.example.hesham.moves.model.modelaLLmovesdata.ResultModel;
import com.example.hesham.moves.model.modelreviews.Resultreviews;
import com.example.hesham.moves.model.modelreviews.Reviews;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.hesham.moves.Utilities.NetworkStateChangeReceiver.IS_NETWORK_AVAILABLE;

public class Details extends AppCompatActivity {
    ResultModel model;
    ImageView img;
    TextView Title, data, Time, Rate, Dec;
    MoviesAPI moviesAPI;
    Trial trial;
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
        Dec.setMovementMethod(new ScrollingMovementMethod());
        img = (ImageView) findViewById(R.id.ImageOfResutl);
        IntentFilter intentFilter = new IntentFilter(NetworkStateChangeReceiver.NETWORK_AVAILABLE_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean isNetworkAvailable = intent.getBooleanExtra(IS_NETWORK_AVAILABLE, false);
                String networkStatus = isNetworkAvailable ? "connected" : "disconnected";
                if (networkStatus == "connected") {
                    CallApi();
                } else {
                    Toast.makeText(getApplicationContext(), "Opent ther internet to get data ", Toast.LENGTH_LONG).show();

                }

            }
        }, intentFilter);

        CallApi();
    }

    private void CallApi() {
        if (InternetConnection.checkConnection(Details.this)) {
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


            }
        } else {
            Toast.makeText(getApplicationContext(), "Opent ther internet to get data ", Toast.LENGTH_LONG).show();

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