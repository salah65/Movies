package com.example.movies.viewModels;

import android.app.Application;

import com.example.movies.APIs.ApiManager;
import com.example.movies.Model.ParentModel;
import com.example.movies.Model.Response;
import com.example.movies.Model.ResultsItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;

public class MoviesViewModel extends AndroidViewModel {
    ArrayList<ParentModel> MoviesCategory = new ArrayList<>();
  MutableLiveData<ArrayList<ParentModel>>Movies;
  MutableLiveData<List<ResultsItem>>seeAllMovies;

    public MutableLiveData<List<ResultsItem>> getSeeAllMovies() {
        return seeAllMovies;
    }

    public MutableLiveData<ArrayList<ParentModel>> getMovies() {
        return Movies;
    }

    public MoviesViewModel(@NonNull Application application) {
        super(application);
        Movies = new MutableLiveData<>();
        seeAllMovies = new MutableLiveData<>();

    }


   public void getMovies(String apikey, int pagenumber){
        GetUpcomingMovies(apikey, pagenumber);
        GetNowPlaying(apikey, pagenumber);
        GetTopRatedMovies(apikey, pagenumber);
        GetPopulerMovies(apikey, pagenumber);

   }

    public void GetUpcomingMovies(String apikey, int pagenumber) {
        ApiManager.getApis().GetUpComing(apikey, pagenumber).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                MoviesCategory.add(new ParentModel("UpComing", response.body().getResults()));
                seeAllMovies.postValue(response.body().getResults());
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });

    }

    public void GetPopulerMovies(String apikey, int pagenumber) {
        ApiManager.getApis().GetPopular(apikey, pagenumber).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                MoviesCategory.add(new ParentModel("Populer", response.body().getResults()));
                Movies.postValue(MoviesCategory);
                seeAllMovies.postValue(response.body().getResults());

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });

    }

    public void GetTopRatedMovies(String apikey, int pagenumber) {
        ApiManager.getApis().GetTopRated(apikey, pagenumber).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                MoviesCategory.add(new ParentModel("Top Rated", response.body().getResults()));
                seeAllMovies.postValue(response.body().getResults());
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });

    }

    public void GetNowPlaying(String apikey, int pagenumber) {
        ApiManager.getApis().GetNowPlaying(apikey, pagenumber).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                MoviesCategory.add(new ParentModel("Now Playing", response.body().getResults()));
                seeAllMovies.postValue(response.body().getResults());
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });

    }
}
