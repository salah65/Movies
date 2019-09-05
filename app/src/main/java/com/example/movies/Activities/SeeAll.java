package com.example.movies.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.Adapter.ChildAdapter;
import com.example.movies.EndlessRecyclerViewScrollListener;
import com.example.movies.Model.ParentModel;
import com.example.movies.Model.ResultsItem;
import com.example.movies.R;
import com.example.movies.viewModels.MoviesViewModel;

import java.util.List;

public class SeeAll extends AppCompatActivity {


    MoviesViewModel viewModel;
    ChildAdapter adapter;
    ParentModel model;
    private RecyclerView AllMoviesRv;
    private Toolbar toolbar;
    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false);
    private EndlessRecyclerViewScrollListener scrollListener;
    private ProgressBar progressCircular;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all);
        initView();
        viewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        model = (ParentModel) getIntent().getSerializableExtra("category");
        AllMoviesRv.setLayoutManager(layoutManager);
        adapter = new ChildAdapter(model.getCategoryMovies());
        adapter.setOnitemClickListner(new ChildAdapter.onitemClickListner() {
            @Override
            public void OnClick(ResultsItem item) {
                Intent intent = new Intent(SeeAll.this, MovieActivity.class);
                intent.putExtra("Movie", item);
                startActivity(intent);
            }
        });
        setSupportActionBar(toolbar);
        AllMoviesRv.setAdapter(adapter);
        scrollListener = new EndlessRecyclerViewScrollListener((GridLayoutManager) layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                progressCircular.setVisibility(View.VISIBLE);
                if (model.getName().equals("UpComing"))
                    viewModel.GetUpcomingMovies(getResources().getString(R.string.Movies_API_Key), page);
                else if (model.getName().equals("Populer"))
                    viewModel.GetPopulerMovies(getResources().getString(R.string.Movies_API_Key), page);
                else if (model.getName().equals("Top Rated"))
                    viewModel.GetTopRatedMovies(getResources().getString(R.string.Movies_API_Key), page);
                else if (model.getName().equals("Now Playing"))
                    viewModel.GetNowPlaying(getResources().getString(R.string.Movies_API_Key), page);


            }
        };
        AllMoviesRv.addOnScrollListener(scrollListener);
        viewModel.getSeeAllMovies().observe(this, new Observer<List<ResultsItem>>() {
            @Override
            public void onChanged(List<ResultsItem> resultsItems) {
                if (!resultsItems.isEmpty()) {
                    progressCircular.setVisibility(View.GONE);
                    model.getCategoryMovies().addAll(resultsItems);
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            startActivity(new Intent(SeeAll.this, Search.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        AllMoviesRv = findViewById(R.id.AllMovies_rv);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        progressCircular = (ProgressBar) findViewById(R.id.progress_circular);
    }
}
