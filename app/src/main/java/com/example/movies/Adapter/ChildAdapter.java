package com.example.movies.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.movies.Model.ResultsItem;
import com.example.movies.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ViewHolder> {
    onitemClickListner onitemClickListner;
    List<ResultsItem> Movies;
    int ViewType;

    public ChildAdapter(List<ResultsItem> movies) {
        Movies = movies;
    }

    public ChildAdapter(List<ResultsItem> movies, int viewType) {
        Movies = movies;
        this.ViewType = viewType;
    }

    public void setOnitemClickListner(ChildAdapter.onitemClickListner onitemClickListner) {
        this.onitemClickListner = onitemClickListner;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewType = this.ViewType;
        View view;
        if (viewType == 100)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.black_movie_item, parent, false);
        else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ResultsItem item = Movies.get(position);
        Glide.with(holder.itemView)
                .load("https://image.tmdb.org/t/p/w500" + item.getPosterPath())
                .into(holder.MovieCover);

        holder.MovieName.setText(item.getTitle());
        float vote = (float) (item.getVoteAverage() / 2);
        holder.MovieRateText.setText(item.getVoteAverage() + "/10");
        holder.MovieRate.setRating(vote);
        if (onitemClickListner != null)
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onitemClickListner.OnClick(item);
                }
            });
    }

    public void Update(List<ResultsItem> movies) {
        this.Movies = movies;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (Movies != null)
            return Movies.size();
        else
            return 0;
    }

    public interface onitemClickListner {
        void OnClick(ResultsItem item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView MovieCover;
        TextView MovieName;
        TextView MovieRateText;
        RatingBar MovieRate;
        View view;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            MovieCover = itemView.findViewById(R.id.moviepicture);
            MovieName = itemView.findViewById(R.id.moviename);
            MovieRate = itemView.findViewById(R.id.ratingBar);
            MovieRateText = itemView.findViewById(R.id.rateText);
        }
    }
}
