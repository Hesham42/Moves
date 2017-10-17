package com.example.hesham.moves.Utilities;

import android.util.Log;

import com.example.hesham.moves.BuildConfig;
import com.example.hesham.moves.model.modelVedio.Trial;
import com.example.hesham.moves.model.modelaLLmovesdata.MovesModel;
import com.example.hesham.moves.model.modelreviews.Reviews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.example.hesham.moves.MainActivity.API_KEY;

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
    Call<Trial> selectedVedio(@Path("id") int id,@Query("api_key") String APIKEY);
    @GET("movie/{id}/Reviews")
    Call<Reviews> selectedReviews(@Path("id") int id,@Query("api_key") String APIKEY);

}
