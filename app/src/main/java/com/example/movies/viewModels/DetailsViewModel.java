package com.example.movies.viewModels;

import android.app.Application;

import com.example.movies.APIs.ApiManager;
import com.example.movies.Model.CastItem;
import com.example.movies.Model.CastResponse;
import com.example.movies.Model.ResultsItem;
import com.example.movies.Model.Videos.VideoResponse;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsViewModel extends AndroidViewModel {
    MutableLiveData<String> VideoKey;
    MutableLiveData<List<CastItem>>cast;
    MutableLiveData<List<ResultsItem>>SimilarMovies;

    public MutableLiveData<List<ResultsItem>> getSimilarMovies() {
        return SimilarMovies;
    }

    public MutableLiveData<List<CastItem>> getCast() {
        return cast;
    }

    public DetailsViewModel(@NonNull Application application) {
        super(application);
        VideoKey = new MutableLiveData<>();
        cast = new MutableLiveData<>();
        SimilarMovies = new MutableLiveData<>();
    }

    public MutableLiveData<String> getVideoKey() {
        return VideoKey;
    }

    public void getmovieTrailer(int id, String Apikey) {
        ApiManager.getApis().GetVideos(id, Apikey).enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                if (!response.body().getResults().isEmpty())
                    VideoKey.postValue(response.body().getResults().get(0).getKey());

            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {

            }
        });

    }

    public void getCast(int movieid, String ApiKey) {
        ApiManager.getApis().GetCast(movieid, ApiKey).enqueue(new Callback<CastResponse>() {
            @Override
            public void onResponse(Call<CastResponse> call, Response<CastResponse> response) {
                cast.postValue(response.body().getCast());
            }

            @Override
            public void onFailure(Call<CastResponse> call, Throwable t) {

            }
        });
    }
    public  void  getSimilarMovies(int movieid, String ApiKey){
        ApiManager.getApis().GetSimilarMovies(movieid, ApiKey).enqueue(new Callback<com.example.movies.Model.Response>() {
            @Override
            public void onResponse(Call<com.example.movies.Model.Response> call, Response<com.example.movies.Model.Response> response) {
                SimilarMovies.postValue(response.body().getResults());
            }

            @Override
            public void onFailure(Call<com.example.movies.Model.Response> call, Throwable t) {

            }
        });
    }
}
