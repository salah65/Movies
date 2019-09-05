package com.example.movies.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.movies.APIs.ApiManager;
import com.example.movies.Model.Response;
import com.example.movies.Model.ResultsItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class SearchViewModel extends AndroidViewModel {
    MutableLiveData<List<ResultsItem>> searchResults;

    public MutableLiveData<List<ResultsItem>> getSearchResults() {
        return searchResults;
    }

    public SearchViewModel(@NonNull Application application) {
        super(application);
        searchResults = new MutableLiveData<>();
    }

    public void searchFor(String API, String query) {
        ApiManager.getApis().SearchForMovie(API, query).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful() && !response.body().getResults().isEmpty())
                    searchResults.postValue(response.body().getResults());
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }
}
