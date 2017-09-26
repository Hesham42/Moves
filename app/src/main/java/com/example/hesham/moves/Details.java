package com.example.hesham.moves;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hesham.moves.model.modelaLLmovesdata.ResultModel;
import com.squareup.picasso.Picasso;

public class Details extends AppCompatActivity {
    ResultModel model;
    ImageView img;
    TextView Title, data, Time, Rate, Dec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent i = getIntent();
        model = (ResultModel) i.getSerializableExtra("sampleObject");
        Title = (TextView) findViewById(R.id.TitleTex);
        data = (TextView) findViewById(R.id.HistroyTitile);
        Time = (TextView) findViewById(R.id.Houre);
        Rate = (TextView) findViewById(R.id.Rate);
        Dec = (TextView) findViewById(R.id.Desc);
        img=(ImageView)findViewById(R.id.ImageOfResutl);
        if (model != null) {
            Title.setText(model.getTitle());
            Dec.setText(model.getOverview());
            Picasso.with(Details.this).load("http://image.tmdb.org/t/p/w185/" + model.getPosterPath()).into(img);
            data.setText(model.getReleaseDate());
            Rate.setText(model.getVoteAverage()+"/10");


        }

    }
}
