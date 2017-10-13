package com.example.hesham.moves.page;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hesham.moves.Details;
import com.example.hesham.moves.MainActivity;
import com.example.hesham.moves.R;
import com.example.hesham.moves.adapter.FragmentAdapter.AdapterFavourit;
import com.example.hesham.moves.adapter.RecyclerTouchListener;
import com.example.hesham.moves.model.modelaLLmovesdata.ResultModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hesham on 10/13/2017.
 */

public class Popular extends Fragment {
    List<ResultModel> PopularResult = new ArrayList<>();


    public Popular() {
            // Required empty public constructor

        }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View rootView = inflater.inflate(R.layout.popular, container, false);

            MainActivity activity=(MainActivity)getActivity();
            this.PopularResult=activity.getPopularResult();

            RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view_popular);
            rv.setHasFixedSize(true);
            AdapterFavourit adapter = new AdapterFavourit(PopularResult,getContext());
            rv.setAdapter(adapter);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
            rv.setLayoutManager(gridLayoutManager);
            rv.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getBaseContext(),
                    rv, new RecyclerTouchListener.ClickListener() {

                @Override
                public void onClick(View view, int position) {
                    Intent i = new Intent(getActivity(),Details.class);
                    ResultModel model=PopularResult.get(position);
                    i.putExtra("sampleObject",model);
                    startActivity(i);

                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));
            return rootView;
        }




}

