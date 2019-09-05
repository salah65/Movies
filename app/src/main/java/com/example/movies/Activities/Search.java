package com.example.movies.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.Adapter.ChildAdapter;
import com.example.movies.EndlessRecyclerViewScrollListener;
import com.example.movies.Model.ResultsItem;
import com.example.movies.R;
import com.example.movies.viewModels.SearchViewModel;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Search extends AppCompatActivity {

    private Toolbar searchBar;
    private SearchView searchView;
    private RecyclerView searchResults;
    SearchViewModel viewModel;
    ChildAdapter adapter;
    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false);
    EndlessRecyclerViewScrollListener scrollListener;
    String Query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        setSupportActionBar(searchBar);
        viewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        adapter = new ChildAdapter(null,100);
        adapter.setOnitemClickListner(new ChildAdapter.onitemClickListner() {
            @Override
            public void OnClick(ResultsItem item) {
                Intent intent = new Intent(Search.this, MovieActivity.class);
                intent.putExtra("Movie", item);
                startActivity(intent);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                     Query = URLEncoder.encode(newText, StandardCharsets.UTF_8.toString());
                    viewModel.searchFor(getResources().getString(R.string.Movies_API_Key), Query);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                return true;
            }
        });
        searchResults.setLayoutManager(layoutManager);
        searchResults.setAdapter(adapter);
        viewModel.getSearchResults().observe(this, new Observer<List<ResultsItem>>() {
            @Override
            public void onChanged(List<ResultsItem> resultsItems) {
                adapter.Update(resultsItems);
            }
        });

    }

    private void initView() {
        searchBar = (Toolbar) findViewById(R.id.search_bar);
        searchView = (SearchView) findViewById(R.id.search_view);
        searchResults = (RecyclerView) findViewById(R.id.search_results);
    }
}
