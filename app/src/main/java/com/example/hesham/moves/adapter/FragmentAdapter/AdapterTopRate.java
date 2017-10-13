package com.example.hesham.moves.adapter.FragmentAdapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hesham.moves.R;
import com.example.hesham.moves.model.modelaLLmovesdata.ResultModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hesham on 10/13/2017.
 */

public class AdapterTopRate  extends RecyclerView.Adapter<AdapterTopRate.MyViewHolder> {
    List<ResultModel> resultModels =new ArrayList<>();


    Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public CardView mCardView;
        public ImageView img;
        public MyViewHolder(View v){
            super(v);

            mCardView = (CardView) v.findViewById(R.id.card_view_top_rate);
            img=(ImageView)v.findViewById(R.id.iv_image_top_rate);
        }

    }

    public AdapterTopRate(List<ResultModel> resultModels, Context activity){
        this.resultModels=resultModels;
        this.context=activity;
    }

    @Override
    public AdapterTopRate.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_top_rate, parent, false);
        AdapterTopRate.MyViewHolder vh = new AdapterTopRate.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AdapterTopRate.MyViewHolder holder, int position){
        Picasso.with(context).load("http://image.tmdb.org/t/p/w185/" + resultModels.get(position).getPosterPath()).into(holder.img);

    }

    @Override
    public int getItemCount() { return resultModels.size(); }

}