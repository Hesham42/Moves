package com.example.hesham.moves;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.hesham.moves.Utilities.MoviesAPI;
import com.example.hesham.moves.adapter.AdapterOfTriall.RecyclerAdapter;
import com.example.hesham.moves.model.modelVedio.ResultTrial;
import com.example.hesham.moves.model.modelVedio.Trial;
import com.example.hesham.moves.model.modelaLLmovesdata.ResultModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.hesham.moves.MainActivity.API_KEY;
import static com.example.hesham.moves.R.layout.activity_all_trial_vedio;

public class AllTrial extends AppCompatActivity {
//    MoviesAPI moviesAPI;
//    ResultModel model;
//    RecyclerView recyclerView;
//    RecyclerAdapter adapter;
//    Trial trial;
//    List<ResultTrial> resultTrials = new ArrayList<>();
//    List<String> Keys = new ArrayList<>();
//    List<String> TrialName = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_all_trial_vedio);
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(MoviesAPI.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        moviesAPI = retrofit.create(MoviesAPI.class);
//        recyclerView = (RecyclerView) findViewById(R.id.DetailsRec);
//        recyclerView.setHasFixedSize(true);
//        //to use RecycleView, you need a layout manager. default is LinearLayoutManager
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(linearLayoutManager);
//
//        Call<Trial> reCall = moviesAPI.selectedVedio(model.getId(), API_KEY);
//        reCall.enqueue(new Callback<Trial>() {
//            @Override
//            public void onResponse(Call<Trial> call, Response<Trial> response) {
//                trial = response.body();
//                resultTrials = trial.getResults();
//                for (int i = 0; i < resultTrials.size(); i++) {
//                    Log.d("Guinness", resultTrials.get(i).getKey());
//                    Keys.add(resultTrials.get(i).getKey());
//                    TrialName.add(resultTrials.get(i).getName());
//
//                }
////                adapter = new RecyclerAdapter(activity_all_trial_vedio.this, Keys, TrialName);
////                recyclerView.setAdapter(adapter);
//
//            }
//
//            @Override
//            public void onFailure(Call<Trial> call, Throwable t) {
//
//            }
//        });
//
 }
}



