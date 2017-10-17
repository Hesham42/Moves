package com.example.hesham.moves;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hesham.moves.Utilities.InternetConnection;
import com.example.hesham.moves.Utilities.MoviesAPI;
import com.example.hesham.moves.adapter.AdapterOFAllMovies.MoviesAdapter;
import com.example.hesham.moves.adapter.RecyclerTouchListener;
import com.example.hesham.moves.model.modelaLLmovesdata.MovesModel;
import com.example.hesham.moves.model.modelaLLmovesdata.ResultModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MoviesAdapter adapter;
    GridLayoutManager gridLayoutManager;


    MoviesAPI moviesAPI;
    MovesModel PoplarModel;
    MovesModel TopRateModel;

    List<ResultModel> PopularResult = new ArrayList<>();
    List<ResultModel> TopRateResult = new ArrayList<>();
    List<ResultModel> Favourit = new ArrayList<>();
    int flag=0;
    public static final String API_KEY = BuildConfig.API_KEY;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.rec);
        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        CallApi();

    }


    private  void CallApi() {
        if (InternetConnection.checkConnection(MainActivity.this)) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MoviesAPI.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            moviesAPI = retrofit.create(MoviesAPI.class);


            Call<MovesModel> PopularRecall = moviesAPI.getAllMovesPopular();
            PopularRecall.enqueue(new Callback<MovesModel>() {
                @Override
                public void onResponse(Call<MovesModel> call, Response<MovesModel> response) {
                    if (response.isSuccessful()) {
                        PoplarModel = response.body();
                        Log.e("Guinness", "p main" + PoplarModel.toString());

                        PopularResult = PoplarModel.getResults();
//                      Log.e("Guinness", response.toString());
                        flag=1;
                        adapter = new MoviesAdapter(PopularResult, MainActivity.this);
                        recyclerView.setAdapter(adapter);


                    } else {
                        Log.d("Guinness", " the respons code of popular " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<MovesModel> call, Throwable t) {
                    Log.d("Guinness", "Respons get onFailure popular");

                }
            });




            final Call<MovesModel> TopRate = moviesAPI.getAllMovestop_rated();
            TopRate.enqueue(new Callback<MovesModel>() {
                @Override
                public void onResponse(Call<MovesModel> call, Response<MovesModel> response) {
                    if (response.isSuccessful()) {
                        TopRateModel = response.body();
                        Log.e("Guinness", "top " + TopRateModel.toString());

                        TopRateResult = TopRateModel.getResults();
//                        Log.d("Guinness", response.toString());
                    } else {
                        Log.d("Guinness", " the respons code of TopRate " + response.code());

                    }

                }

                @Override
                public void onFailure(Call<MovesModel> call, Throwable t) {
                    Log.d("Guinness", "Respons get onFailure TopRate");

                }
            });

            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                    recyclerView, new RecyclerTouchListener.ClickListener() {

                @Override
                public void onClick(View view, int position) {
//             Log.d("Guinness",resultModels.get(position).getId().toString());
                    if (flag==1){

                        Intent i = new Intent(MainActivity.this,Details.class);
                        ResultModel model=getPopularResult().get(position);
                        i.putExtra("sampleObject",model);
                        startActivity(i);

                    }else if (flag==2){

                        Intent i = new Intent(MainActivity.this,Details.class);
                        ResultModel model=getTopRateResult().get(position);
                        i.putExtra("sampleObject",model);
                        startActivity(i);

                    }
                    else if (flag==3)
                    {
                        Intent i = new Intent(MainActivity.this,Details.class);
                        ResultModel model=getFavourit().get(position);
                        i.putExtra("sampleObject",model);
                        startActivity(i);

                    }


                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));

        }



    }




    @Override
    public void onResume() {
        super.onResume();
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
            flag=1;
            adapter = new MoviesAdapter(getPopularResult(), MainActivity.this);
            recyclerView.setAdapter(adapter);

        }
        if (id == R.id.Favourit) {
            flag=3;
            adapter = new MoviesAdapter(getFavourit(), MainActivity.this);
            recyclerView.setAdapter(adapter);


        }
        if (id == R.id.TopRate) {
            flag=2;
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
    }


}
