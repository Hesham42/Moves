package com.example.hesham.moves;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.hesham.moves.Utilities.CommentUpdateModel;
import com.example.hesham.moves.Utilities.InternetConnection;
import com.example.hesham.moves.Utilities.MoviesAPI;
import com.example.hesham.moves.Utilities.NetworkStateChangeReceiver;
import com.example.hesham.moves.adapter.AdapterOFAllMovies.MoviesAdapter;
import com.example.hesham.moves.adapter.RecyclerTouchListener;
import com.example.hesham.moves.data.FavoriteContract;
import com.example.hesham.moves.data.FavoriteDbHelper;
import com.example.hesham.moves.model.modelaLLmovesdata.MovesModel;
import com.example.hesham.moves.model.modelaLLmovesdata.ResultModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.hesham.moves.Utilities.NetworkStateChangeReceiver.IS_NETWORK_AVAILABLE;
import static com.example.hesham.moves.data.FavoriteContentProvider.Favourit_With_ID;

public class MainActivity extends AppCompatActivity  implements CommentUpdateModel.OnCommentAddedListener, LoaderManager.LoaderCallbacks<Object> {
    private RecyclerView recyclerView;
    private MoviesAdapter adapter;
    GridLayoutManager gridLayoutManager;
    MoviesAPI moviesAPI;
    MovesModel PoplarModel;
    MovesModel TopRateModel;
    private static final int Favourit_LOADER_ID = 0;


    List<ResultModel> PopularResult = new ArrayList<>();
    List<ResultModel> TopRateResult = new ArrayList<>();
    List<ResultModel> Favourit = new ArrayList<>();

    int flag = 0;
    public static final String API_KEY = BuildConfig.API_KEY;


    private FavoriteDbHelper favoriteDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        favoriteDbHelper = new FavoriteDbHelper(MainActivity.this);
        recyclerView = (RecyclerView) findViewById(R.id.rec);
        recyclerView.setHasFixedSize(true);
        CommentUpdateModel.getInstance().setListener(this);
        gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        CallApi();
        IntentFilter intentFilter = new IntentFilter(NetworkStateChangeReceiver.NETWORK_AVAILABLE_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        boolean isNetworkAvailable = intent.getBooleanExtra(IS_NETWORK_AVAILABLE, false);
                        String networkStatus = isNetworkAvailable ? "connected" : "disconnected";
                        if (networkStatus == "connected") {
                            CallApi();
                        } else if (networkStatus == "disconnected") {
                            Toast.makeText(getApplicationContext(), "ther is no internet Connection pleas open the internet", Toast.LENGTH_LONG).show();

                        }
                    }
                }, intentFilter);

    }


    private void CallApi() {
        if (InternetConnection.checkConnection(MainActivity.this)) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MoviesAPI.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            moviesAPI = retrofit.create(MoviesAPI.class);


            Call<MovesModel> PopularRecall = moviesAPI.getAllMovesPopular(API_KEY);
            PopularRecall.enqueue(new Callback<MovesModel>() {
                @Override
                public void onResponse(Call<MovesModel> call, Response<MovesModel> response) {
                    if (response.isSuccessful()) {
                        PoplarModel = response.body();
//                        Log.e("Guinness", "p main" + PoplarModel.toString());

                        PopularResult = PoplarModel.getResults();
//                      Log.e("Guinness", response.toString());
                        flag = 1;
                        adapter = new MoviesAdapter(PopularResult, MainActivity.this);
                        recyclerView.setAdapter(adapter);


                    } else {
//                        Log.d("Guinness", " the respons code of popular " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<MovesModel> call, Throwable t) {
//                    Log.d("Guinness", "Respons get onFailure popular");

                }
            });


            final Call<MovesModel> TopRate = moviesAPI.getAllMovestop_rated(API_KEY);
            TopRate.enqueue(new Callback<MovesModel>() {
                @Override
                public void onResponse(Call<MovesModel> call, Response<MovesModel> response) {
                    if (response.isSuccessful()) {
                        TopRateModel = response.body();
//                        Log.e("Guinness", "top " + TopRateModel.toString());

                        TopRateResult = TopRateModel.getResults();
//                        Log.d("Guinness", response.toString());
                    } else {
//                        Log.d("Guinness", " the respons code of TopRate " + response.code());



                    }

                }

                @Override
                public void onFailure(Call<MovesModel> call, Throwable t) {
//                    Log.d("Guinness", "Respons get onFailure TopRate");

                }
            });

            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                    recyclerView, new RecyclerTouchListener.ClickListener() {

                @Override
                public void onClick(View view, int position) {
//             Log.d("Guinness",resultModels.get(position).getId().toString());
                    if (InternetConnection.checkConnection(MainActivity.this)) {
                        if (flag == 1) {

                            Intent i = new Intent(getBaseContext(), Details.class);
                            ResultModel model = getPopularResult().get(position);
                            i.putExtra("sampleObject", model);
                            startActivity(i);

                        } else if (flag == 2) {

                            Intent i = new Intent(MainActivity.this, Details.class);
                            ResultModel model = getTopRateResult().get(position);
                            i.putExtra("sampleObject", model);
                            startActivity(i);

                        } else if (flag == 3) {
                            Intent i = new Intent(MainActivity.this, Details.class);
                            ResultModel model = getFavourit().get(position);
                            i.putExtra("sampleObject", model);
                            i.putExtra("fav","fav");
                            startActivity(i);

                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "ther is no internet Connection pleas open the internet  to Open the Ditails ", Toast.LENGTH_LONG).show();

                    }


                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));

        } else {
            Toast.makeText(this, "ther is no internet Connection pleas open the internet ", Toast.LENGTH_LONG).show();
        }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Pouplar) {
            if (InternetConnection.checkConnection(MainActivity.this)) {

            } else {
                Toast.makeText(this, "ther is no internet Connection pleas open the internet ", Toast.LENGTH_LONG).show();

            }
            flag = 1;
            adapter = new MoviesAdapter(getPopularResult(), MainActivity.this);
            recyclerView.setAdapter(adapter);

        }
        if (id == R.id.Favourit) {
            flag = 3;
            adapter = new MoviesAdapter(getFavourit(), MainActivity.this);
            recyclerView.setAdapter(adapter);

        }
        if (id == R.id.TopRate) {
            if (InternetConnection.checkConnection(MainActivity.this)) {
            } else {
                Toast.makeText(this, "ther is no internet Connection pleas open the internet ", Toast.LENGTH_LONG).show();
            }
            flag = 2;
            adapter = new MoviesAdapter(getTopRateResult(), MainActivity.this);
            recyclerView.setAdapter(adapter);

        }
        return super.onOptionsItemSelected(item);
    }


    public List<ResultModel> getPopularResult() {
        return PopularResult;
    }

    public List<ResultModel> getTopRateResult() {
        return TopRateResult;
    }

    public List<ResultModel> getFavourit() {
        Favourit= favoriteDbHelper.getAllFavorite();
        return Favourit;

    }




    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            CallApi();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            CallApi();
        }
    }


    @Override
    public void commentDelete() {
      Favourit=favoriteDbHelper.getAllFavorite();
        adapter = new MoviesAdapter(Favourit, MainActivity.this);
        recyclerView.setAdapter(adapter);
    }



    @Override
    protected void onResume() {
        super.onResume();

        // re-queries for all tasks
        getSupportLoaderManager().restartLoader(Favourit_LOADER_ID, null, this);
    }

    @Override
    public Loader<Object> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object data) {

    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {

    }
}
