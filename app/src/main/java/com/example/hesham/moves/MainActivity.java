package com.example.hesham.moves;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.database.Cursor;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.app.LoaderManager;
import android.content.Loader;

import com.example.hesham.moves.Utilities.CommentUpdateModel;
import com.example.hesham.moves.Utilities.InternetConnection;
import com.example.hesham.moves.Utilities.MoviesAPI;
import com.example.hesham.moves.Utilities.NetworkStateChangeReceiver;
import com.example.hesham.moves.adapter.AdapterOFAllMovies.MoviesAdapter;
import com.example.hesham.moves.adapter.RecyclerTouchListener;
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
import static com.example.hesham.moves.data.FavoriteContract.FavoriteEntry.COLUMN_MOVIEID;
import static com.example.hesham.moves.data.FavoriteContract.FavoriteEntry.COLUMN_OVERVIEW;
import static com.example.hesham.moves.data.FavoriteContract.FavoriteEntry.COLUMN_POSTER_PATH;
import static com.example.hesham.moves.data.FavoriteContract.FavoriteEntry.COLUMN_TITLE;
import static com.example.hesham.moves.data.FavoriteContract.FavoriteEntry.COLUMN_USERRATING;
import static com.example.hesham.moves.data.FavoriteContract.FavoriteEntry.CONTENT_URI;


public class MainActivity extends AppCompatActivity
        implements CommentUpdateModel.OnCommentAddedListener, LoaderManager.LoaderCallbacks<Cursor> {
    private RecyclerView recyclerView;
    private MoviesAdapter adapter;
    GridLayoutManager gridLayoutManager;
    MoviesAPI moviesAPI;
    MovesModel PoplarModel;
    MovesModel TopRateModel;
    private static final int Favourit_LOADER_ID = 1;


    List<ResultModel> PopularResult = new ArrayList<>();
    List<ResultModel> TopRateResult = new ArrayList<>();
    List<ResultModel> Favourit = new ArrayList<>();

    int flag = 0;
    public static final String API_KEY = BuildConfig.API_KEY;
    private boolean firstTimeLoaded=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            if(firstTimeLoaded==false){
                getLoaderManager().initLoader(Favourit_LOADER_ID, null,this);
                firstTimeLoaded=true;
            }else{
                getLoaderManager().restartLoader(Favourit_LOADER_ID,null,this);
            } }
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
          return Favourit;
//        Favourit= favoriteDbHelper.getAllFavorite();
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
        Log.e("Guinness","enter the DELETE Inteface Function");
            getLoaderManager().restartLoader(Favourit_LOADER_ID,null,this);
             adapter.notifyDataSetChanged();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] columns = {
                COLUMN_MOVIEID,
                COLUMN_TITLE,
                COLUMN_USERRATING,
                COLUMN_POSTER_PATH,
                COLUMN_OVERVIEW
        };

            if (id == 1) {
                return new android.content.CursorLoader(MainActivity.this,
                        CONTENT_URI,
                        columns,
                        null,
                        null,
                        null);
            }
            return null;

    }
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Cursor mFavouritCursor =data;
        if (mFavouritCursor !=null&&mFavouritCursor.getCount()>0){
              Favourit= new ArrayList<>();
            if (mFavouritCursor.moveToFirst()) {
                do {
                    Log.e("Guinness","enter loop");
                    ResultModel movie = new ResultModel();
                    movie.setId(Integer.parseInt(mFavouritCursor.getString(mFavouritCursor.getColumnIndex(COLUMN_MOVIEID))));
                    movie.setTitle(mFavouritCursor.getString(mFavouritCursor.getColumnIndex(COLUMN_TITLE)));
                    movie.setVoteAverage(Double.parseDouble(mFavouritCursor.getString(mFavouritCursor.getColumnIndex(COLUMN_USERRATING))));
                    movie.setPosterPath(mFavouritCursor.getString(mFavouritCursor.getColumnIndex(COLUMN_POSTER_PATH)));
                    movie.setOverview(mFavouritCursor.getString(mFavouritCursor.getColumnIndex(COLUMN_OVERVIEW)));
                    Favourit.add(movie);

                } while (mFavouritCursor.moveToNext());
                Log.e("Guinness","Loader set data after while loop");
                adapter = new MoviesAdapter(Favourit, MainActivity.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

        } else {
            Toast.makeText(this,"there is no Favourit in the list ",Toast.LENGTH_SHORT).show();
            Log.e("Guinness","enter else statment");
            Favourit=new ArrayList<>();
            adapter = new MoviesAdapter(Favourit, MainActivity.this);
            recyclerView.setAdapter(adapter);
        }

        mFavouritCursor.close();

        }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.e("Guinness","enter reset function ");
        adapter = new MoviesAdapter(Favourit, MainActivity.this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onResume() {
        super.onResume();
        // re-queries for all tasks
        getLoaderManager().restartLoader(Favourit_LOADER_ID,null,this);

    }
}