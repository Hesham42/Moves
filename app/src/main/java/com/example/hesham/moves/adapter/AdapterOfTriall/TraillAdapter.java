package com.example.hesham.moves.adapter.AdapterOfTriall;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hesham.moves.R;

import java.util.List;

/**
 * Created by Hesham on 9/25/2017.
 */

public class TraillAdapter  extends RecyclerView.Adapter<TraillAdapter.MyViewHolder>{
    Context context;
    List<String> Keys;

    public TraillAdapter(Context context, List<String> keys) {
        this.context = context;
        Keys = keys;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.activity_details,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
      }

    @Override
    public int getItemCount() {
        return Keys.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
