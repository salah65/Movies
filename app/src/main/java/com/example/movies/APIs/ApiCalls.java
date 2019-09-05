package com.example.movies.APIs;

import com.example.movies.Model.CastResponse;
import com.example.movies.Model.Response;
import com.example.movies.Model.Videos.VideoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiCalls {
    @GET("movie/upcoming")
    Call<Response> GetUpComing(@Query("api_key") String ApiKey, @Query("page") int PageNumber);

    @GET("movie/top_rated")
    Call<Response> GetTopRated(@Query("api_key") String ApiKey, @Query("page") int PageNumber);

    @GET("movie/popular")
    Call<Response> GetPopular(@Query("api_key") String ApiKey, @Query("page") int PageNumber);

    @GET("movie/now_playing")
    Call<Response> GetNowPlaying(@Query("api_key") String ApiKey, @Query("page") int PageNumber);

    @GET("movie/{movie_id}/videos")
    Call<VideoResponse> GetVideos(@Path("movie_id") int id, @Query("api_key") String ApiKey);

    @GET("movie/{movie_id}/credits")
    Call<CastResponse> GetCast(@Path("movie_id") int id, @Query("api_key") String ApiKey);

    @GET("movie/{movie_id}/similar")
    Call<Response> GetSimilarMovies(@Path("movie_id") int id, @Query("api_key") String ApiKey);

    @GET("search/movie")
    Call<Response> SearchForMovie(@Query("api_key") String ApiKey, @Query("query") String SearchQuery);
}
