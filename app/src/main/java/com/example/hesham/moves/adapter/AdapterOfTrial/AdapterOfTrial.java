package com.example.hesham.moves.adapter.AdapterOfTrial;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hesham.moves.R;
import com.example.hesham.moves.model.modelVedio.ResultTrial;
import com.example.hesham.moves.model.modelVedio.Trailer;
import com.example.hesham.moves.model.modelaLLmovesdata.ResultModel;

import java.util.List;

/**
 * Created by Hesham on 10/26/2017.
 */

public class AdapterOfTrial extends RecyclerView.Adapter<AdapterOfTrial.MyViewHolder> {

    private Context mContext;

    public AdapterOfTrial(Context mContext, List<ResultTrial> trailerList) {
        this.mContext = mContext;
        this.trailerList = trailerList;
    }

    private List<ResultTrial> trailerList;

    @Override
    public AdapterOfTrial.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trial, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterOfTrial.MyViewHolder holder, int position) {
        holder.title.setText(trailerList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.titleTrial);
            thumbnail = (ImageView) itemView.findViewById(R.id.imageViewTrial);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        ResultTrial clickedDataItem = trailerList.get(pos);
                        String videoId = trailerList.get(pos).getKey();
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + videoId));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("VIDEO_ID", videoId);
                        mContext.startActivity(intent);

                        Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getName(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
