package com.example.movies.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.movies.Model.CastItem;
import com.example.movies.R;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {
    List<CastItem> Cast;

    public CastAdapter(List<CastItem> cast) {
        Cast = cast;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CastItem item = Cast.get(position);
        holder.CastName.setText(item.getName());

        Glide.with(holder.itemView)
                .load("https://image.tmdb.org/t/p/w500" + item.getProfilePath())
                .placeholder(R.drawable.ic_person_black_24dp)
                .into(holder.castProfile);
    }

    @Override
    public int getItemCount() {
        if (Cast==null)
        return 0;
        else if (Cast.size()>10)
            return 10;
        else return Cast.size();
    }
    public void Update(List<CastItem> cast){
        this.Cast=cast;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView castProfile;
        TextView CastName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            castProfile = itemView.findViewById(R.id.castphoto);
            CastName = itemView.findViewById(R.id.castname);
        }
    }
}
