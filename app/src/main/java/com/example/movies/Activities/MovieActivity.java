package com.example.movies.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movies.Adapter.CastAdapter;
import com.example.movies.Adapter.ChildAdapter;
import com.example.movies.Model.CastItem;
import com.example.movies.Model.ResultsItem;
import com.example.movies.R;
import com.example.movies.viewModels.DetailsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MovieActivity extends AppCompatActivity {

    ResultsItem resultsItem;
    DetailsViewModel viewModel;
    View.OnClickListener onVideoClickListener;
    private ImageView dropimage;
    private TextView Name;
    private RatingBar ratingBar;
    private TextView overview;
    private ImageView poster;
    private TextView releaseDate;
    CastAdapter castAdapter;
    ChildAdapter childAdapter;
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
    RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
    private FloatingActionButton floatingActionButton;
    private RecyclerView cast;
    private RecyclerView similarmoviesRv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        initView();
        final Intent intent = new Intent(this, VideoPlayer.class);
        viewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);

        resultsItem = (ResultsItem) getIntent().getSerializableExtra("Movie");
        castAdapter = new CastAdapter(null);
        childAdapter = new ChildAdapter(null,100);
        similarmoviesRv.setLayoutManager(layoutManager2);
        similarmoviesRv.setAdapter(childAdapter);
        childAdapter.setOnitemClickListner(new ChildAdapter.onitemClickListner() {
            @Override
            public void OnClick(ResultsItem item) {
                Intent intent = new Intent(MovieActivity.this, MovieActivity.class);
                intent.putExtra("Movie", item);
                startActivity(intent);
            }
        });
        cast.setAdapter(castAdapter);
        viewModel.getmovieTrailer(resultsItem.getId(), getResources().getString(R.string.Movies_API_Key));
        viewModel.getSimilarMovies(resultsItem.getId(), getResources().getString(R.string.Movies_API_Key));
        cast.setLayoutManager(layoutManager);
        viewModel.getCast(resultsItem.getId(), getResources().getString(R.string.Movies_API_Key));
        onVideoClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        };
        viewModel.getVideoKey().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!s.trim().isEmpty()) {
                    intent.putExtra("videoId", s);
                }
            }
        });

        Glide.with(this).load("https://image.tmdb.org/t/p/w1280" + resultsItem.getBackdropPath())
                .into(dropimage);
        Glide.with(this).load("https://image.tmdb.org/t/p/w500" + resultsItem.getPosterPath())
                .into(poster);
        Name.setText(resultsItem.getOriginalTitle());
        ratingBar.setRating((float) resultsItem.getVoteAverage() / 2);
        overview.setText(resultsItem.getOverview());
        releaseDate.setText(resultsItem.getReleaseDate());
        floatingActionButton.setOnClickListener(onVideoClickListener);
        viewModel.getCast().observe(this, new Observer<List<CastItem>>() {
            @Override
            public void onChanged(List<CastItem> castItems) {
                if (!castItems.isEmpty())
                    castAdapter.Update(castItems);
            }
        });
        viewModel.getSimilarMovies().observe(this, new Observer<List<ResultsItem>>() {
            @Override
            public void onChanged(List<ResultsItem> resultsItems) {
                if (!resultsItems.isEmpty())
                    childAdapter.Update(resultsItems);
            }
        });


    }

    private void initView() {
        dropimage = findViewById(R.id.dropimage);
        Name = findViewById(R.id.Name);
        ratingBar = findViewById(R.id.ratingBar);
        overview = findViewById(R.id.overview);
        poster = findViewById(R.id.poster);
        releaseDate = findViewById(R.id.releaseDate);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        cast = (RecyclerView) findViewById(R.id.cast);
        similarmoviesRv = (RecyclerView) findViewById(R.id.similarmovies_rv);
    }
}
