package com.example.hesham.moves.page;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hesham.moves.MainActivity;
import com.example.hesham.moves.R;
import com.example.hesham.moves.Utilities.InternetConnection;
import com.example.hesham.moves.Utilities.MoviesAPI;
import com.example.hesham.moves.adapter.FragmentAdapter.MyAdapter;
import com.example.hesham.moves.model.modelaLLmovesdata.MovesModel;
import com.example.hesham.moves.model.modelaLLmovesdata.ResultModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Hesham on 10/13/2017.
 */

public class BlankFragment extends Fragment {
    MoviesAPI moviesAPI;
    MovesModel PoplarModel;
    MovesModel TopRateModel;

    List<ResultModel> PopularResult = new ArrayList<>();
    List<ResultModel> TopRateResult = new ArrayList<>();

    String typeOfPage;
    public BlankFragment(String type) {
        this.typeOfPage=type;
        Log.d("Guinnessssss",String.valueOf(typeOfPage));


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        CallApi();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View rootView = inflater.inflate(R.layout.fragment_blank, container, false);
        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        rv.setHasFixedSize(true);
        if (typeOfPage.equals("Pouplar")){
            MyAdapter adapter0 = new MyAdapter(PopularResult);
            rv.setAdapter(adapter0);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
            rv.setLayoutManager(gridLayoutManager);
        }else if (typeOfPage.equals("Favourit")){
            MyAdapter adapter1 = new MyAdapter(TopRateResult);
            rv.setAdapter(adapter1);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
            rv.setLayoutManager(gridLayoutManager);
        }else if (typeOfPage.equals("TopRate")){
            MyAdapter adapter2 = new MyAdapter(PopularResult);
            rv.setAdapter(adapter2);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
            rv.setLayoutManager(gridLayoutManager);
        }

        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    private void CallApi() {
        if (InternetConnection.checkConnection(getActivity())) {

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
                        Log.d("Guinness", PoplarModel.toString());

                        PopularResult = PoplarModel.getResults();
                        Log.d("Guinness", response.toString());

//                        adapter = new MoviesAdapter(PopularResult, MainActivity.this);
//                        recyclerView.setAdapter(adapter);
                    }else
                    {
                        Log.d("Guinness"," the respons code of Popular "+response.code());
                    }
                }

                @Override
                public void onFailure(Call<MovesModel> call, Throwable t) {
                    Log.d("Guinness", "Respons get onFailure Popular");

                }
            });




            Call<MovesModel> TopRate=moviesAPI.getAllMovestop_rated();
            TopRate.enqueue(new Callback<MovesModel>() {
                @Override
                public void onResponse(Call<MovesModel> call, Response<MovesModel> response) {
                    if (response.isSuccessful()) {
                        TopRateModel = response.body();
                        Log.d("Guinness", TopRateModel.toString());

                        TopRateResult = TopRateModel.getResults();
                        Log.d("Guinness", response.toString());

                    }else
                    {
                        Log.d("Guinness"," the respons code of TopRate "+response.code());

                    }

                }

                @Override
                public void onFailure(Call<MovesModel> call, Throwable t) {
                    Log.d("Guinness", "Respons get onFailure TopRate");

                }
            });
        }
    }

}
