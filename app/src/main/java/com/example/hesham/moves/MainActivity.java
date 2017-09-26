package com.example.hesham.moves;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.hesham.moves.adapter.adapterOfallMoves.MoviesAdapter;
import com.example.hesham.moves.adapter.RecyclerTouchListener;
import com.example.hesham.moves.model.modelaLLmovesdata.MovesModel;
import com.example.hesham.moves.model.modelaLLmovesdata.ResultModel;
import com.example.hesham.moves.Utilities.MoviesAPI;

import java.util.ArrayList;
import java.util.List;

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
    MovesModel model;
    List<ResultModel> resultModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        if (!connectedInternet())
//        {
//            Toast.makeText(this,"Opent the internet connection ",Toast.LENGTH_LONG).show();
//            return;
//}
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MoviesAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        moviesAPI = retrofit.create(MoviesAPI.class);

        recyclerView = (RecyclerView) findViewById(R.id.rec);
        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView

        Call<MovesModel> reCall = moviesAPI.getAllMoves();
        reCall.enqueue(new Callback<MovesModel>() {
            @Override
            public void onResponse(Call<MovesModel> call, Response<MovesModel> response) {
                if (response.isSuccessful()) {
                    model = response.body();
                    Log.d("Guinness", model.toString());

                    resultModels = model.getResults();
                    Log.d("Guinness", response.toString());

//                for (int i = 0; i < resultModels.size(); i++){
//                Log.d("Guinness", "http://image.tmdb.org/t/p/w185/" + resultModels.get(i).getPosterPath().toString());

//            }
                    adapter = new MoviesAdapter(resultModels, MainActivity.this);
                    recyclerView.setAdapter(adapter);
                }else
                    {
                        Log.d("Guinness"," the respons code of Main "+response.code());
                    }
            }

            @Override
            public void onFailure(Call<MovesModel> call, Throwable t) {
                Log.d("Guinness", "Respons get onFailure");

            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                recyclerView, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {
//             Log.d("Guinness",resultModels.get(position).getId().toString());
                Intent i = new Intent(MainActivity.this,Details.class);
                ResultModel model=resultModels.get(position);
                i.putExtra("sampleObject",model);
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public boolean connectedInternet() {
        ConnectivityManager manager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }

    }
}
