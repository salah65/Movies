package com.example.movies.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movies.Activities.MovieActivity;
import com.example.movies.Model.ParentModel;
import com.example.movies.Model.ResultsItem;
import com.example.movies.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ParentAdapter extends RecyclerView.Adapter<ParentAdapter.ViewHolder> {
    ArrayList<ParentModel> CategoryList;
    Context context;
    ChildAdapter adapter;
    ChildAdapter.onitemClickListner onClickListener;
    OnSeeAllClickListner onSeeAllClickListner;

    public void setOnSeeAllClickListner(OnSeeAllClickListner onSeeAllClickListner) {
        this.onSeeAllClickListner = onSeeAllClickListner;
    }

    public void setOnClickListener(ChildAdapter.onitemClickListner onClickListener) {
        this.onClickListener = onClickListener;
    }

    public ParentAdapter(ArrayList<ParentModel> categoryList, Context context) {
        CategoryList = categoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parent_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final ParentModel model = CategoryList.get(position);
        holder.CategoryName.setText(model.getName());
        holder.CategoryMovies.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        adapter = new ChildAdapter(model.getCategoryMovies());
        adapter.setOnitemClickListner(onClickListener);
        holder.CategoryMovies.setAdapter(adapter);
        holder.seeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSeeAllClickListner.Onclick(model);
            }
        });


    }
    public void UpdateData(ArrayList<ParentModel> categoryList){
        this.CategoryList=categoryList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (CategoryList != null)
            return CategoryList.size();
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView CategoryName;
        TextView seeall;
        RecyclerView CategoryMovies;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            CategoryName = itemView.findViewById(R.id.categoryName);
            CategoryMovies = itemView.findViewById(R.id.parent_rv);
            seeall = itemView.findViewById(R.id.seeAllBtn);
        }
    }
    public interface OnSeeAllClickListner{
        void Onclick(ParentModel item);
    }

}
