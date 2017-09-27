package com.example.hesham.moves.Utilities;

import com.example.hesham.moves.model.modelVedio.Trial;
import com.example.hesham.moves.model.modelaLLmovesdata.MovesModel;
import com.example.hesham.moves.model.modelreviews.Reviews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Hesham on 9/20/2017.
 */
//http://api.themoviedb.org/3/movie/popular?api_key=28f81313599c7074d6380330fe1dca22


public interface MoviesAPI {
    String BASE_URL = "http://api.themoviedb.org";


    @GET("/3/movie/popular?api_key=28f81313599c7074d6380330fe1dca22")
    Call<MovesModel> getAllMoves();
    @GET("/3/movie/{id}/videos?api_key=28f81313599c7074d6380330fe1dca22")
    Call<Trial> selectedVedio(@Path("id") int id);
    @GET("/3/movie/{id}/Reviews?api_key=28f81313599c7074d6380330fe1dca22")
    Call<Reviews> selectedReviews(@Path("id") int id);

}
