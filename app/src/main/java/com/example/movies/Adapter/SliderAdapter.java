package com.example.movies.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movies.Model.ResultsItem;
import com.example.movies.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {
    Context context;
    List<ResultsItem> slides;

    public SliderAdapter(Context context, List<ResultsItem> slides) {
        this.context = context;
        this.slides = slides;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide, null);
        ImageView imageView = view.findViewById(R.id.slideimage);
        TextView textView = view.findViewById(R.id.slideText);
        Glide.with(container.getContext()).load("https://image.tmdb.org/t/p/w1280" + slides.get(position).getBackdropPath()).into(imageView);
        textView.setText(slides.get(position).getTitle());
        container.addView(view);
        return view;
    }

    public void updateData(List<ResultsItem> slides) {
        this.slides = slides;
        notifyDataSetChanged();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override

    public int getCount() {
        if (slides==null)
            return 0;
        else return slides.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
