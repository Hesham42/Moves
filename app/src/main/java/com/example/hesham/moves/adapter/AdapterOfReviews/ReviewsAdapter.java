package com.example.hesham.moves.adapter.AdapterOfReviews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hesham.moves.R;
import com.example.hesham.moves.model.modelreviews.Resultreviews;

import java.util.List;

/**
 * Created by Hesham on 10/26/2017.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.MyViewHolder> {
    private List<Resultreviews> resultModels;
    private Context context;

    public ReviewsAdapter(Context context, List<Resultreviews> resultModels) {
        this.resultModels = resultModels;
        this.context = context;
    }


    @Override
    public ReviewsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_of_reviews, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ReviewsAdapter.MyViewHolder holder, int position) {
        holder.author.setText(resultModels.get(position).getAuthor());
        holder.Content.setText(resultModels.get(position).getContent());

    }

    @Override
    public int getItemCount() {
       return resultModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView author,Content;

        public MyViewHolder(View itemView) {
            super(itemView);
            author=itemView.findViewById(R.id.author);
            Content=itemView.findViewById(R.id.content);
        }
    }
}
