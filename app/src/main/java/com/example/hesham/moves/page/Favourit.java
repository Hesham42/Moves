package com.example.hesham.moves.page;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hesham.moves.MainActivity;
import com.example.hesham.moves.R;
import com.example.hesham.moves.adapter.FragmentAdapter.AdapterFavourit;
import com.example.hesham.moves.model.modelaLLmovesdata.ResultModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hesham on 10/13/2017.
 */

public class Favourit extends Fragment {

    List<ResultModel> Favourit = new ArrayList<>();

    public Favourit() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.favourit, container, false);

        MainActivity activity=(MainActivity)getActivity();
        this.Favourit=activity.getTopRateResult();

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view_favourit);

        rv.setHasFixedSize(true);

        AdapterFavourit adapter = new AdapterFavourit(Favourit,getContext());

        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return rootView;
    }
}
