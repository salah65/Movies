package com.example.movies.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.movies.Adapter.ChildAdapter;
import com.example.movies.Adapter.ParentAdapter;
import com.example.movies.Adapter.SliderAdapter;
import com.example.movies.Model.ParentModel;
import com.example.movies.Model.ResultsItem;
import com.example.movies.R;
import com.example.movies.viewModels.MoviesViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import io.supercharge.shimmerlayout.ShimmerLayout;

public class HomePage extends AppCompatActivity {


    MoviesViewModel viewModel;
    int page = 1;
    SliderAdapter sliderAdapter;
    ParentAdapter adapter;
    private Toolbar toolbar;
    private ViewPager viewpager;
    private RecyclerView MoviesRv;
    private ShimmerLayout shimmer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        initView();

        viewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        adapter = new ParentAdapter(null, this);
        adapter.setOnSeeAllClickListner(new ParentAdapter.OnSeeAllClickListner() {
            @Override
            public void Onclick(ParentModel item) {
                Intent intent = new Intent(HomePage.this, SeeAll.class);
                intent.putExtra("category",  item);

                startActivity(intent);
            }
        });
        adapter.setOnClickListener(new ChildAdapter.onitemClickListner() {
            @Override
            public void OnClick(ResultsItem item) {
                Intent intent = new Intent(HomePage.this, MovieActivity.class);
                intent.putExtra("Movie", item);
                startActivity(intent);
            }
        });
        shimmer.startShimmerAnimation();
        MoviesRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        MoviesRv.setAdapter(adapter);
        setSupportActionBar(toolbar);
        sliderAdapter = new SliderAdapter(this, null);
        viewpager.setAdapter(sliderAdapter);

        viewModel.getMovies(getResources().getString(R.string.Movies_API_Key), page);
        viewModel.getMovies().observe(this, new Observer<ArrayList<ParentModel>>() {
            @Override
            public void onChanged(ArrayList<ParentModel> parentModels) {
                if (!parentModels.isEmpty()) {
                    shimmer.setVisibility(View.GONE);
                    adapter.UpdateData(parentModels);
                    sliderAdapter.updateData(parentModels.get(0).getCategoryMovies());
                    AutoSlide(4);

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
            startActivity(new Intent(HomePage.this,Search.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        viewpager = findViewById(R.id.viewpager);
        MoviesRv = findViewById(R.id.Movies_rv);
        shimmer = findViewById(R.id.shimmer);
    }

    void AutoSlide(int sec) {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (viewpager.getCurrentItem() == sliderAdapter.getCount() - 1)
                    viewpager.setCurrentItem(0);
                else viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
            }
        };
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 6000, sec * 1000);
    }


}
