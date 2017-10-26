package com.example.hesham.moves.Utilities;

import com.example.hesham.moves.model.modelVedio.Trailer;
import com.example.hesham.moves.model.modelaLLmovesdata.MovesModel;
import com.example.hesham.moves.model.modelreviews.Reviews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Hesham on 9/20/2017.
 */
//http://api.themoviedb.org/3/movie/popular?api_key=KEYs


public interface MoviesAPI {
    String BASE_URL = "http://api.themoviedb.org/3/";

    @GET("movie/popular")
    Call<MovesModel> getAllMovesPopular(@Query("api_key") String APIKEY);
    @GET("movie/top_rated")
    Call<MovesModel> getAllMovestop_rated(@Query("api_key") String APIKEY);
    @GET("movie/{id}/videos")
    Call<Trailer> selectedVedio(@Path("id") int id, @Query("api_key") String APIKEY);
    @GET("movie/{id}/reviews")
    Call<Reviews> selectedReviews(@Path("id") int id,@Query("api_key") String APIKEY);

}
