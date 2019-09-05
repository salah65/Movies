package com.example.movies.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ParentModel implements Serializable {
    String Name;
    List<ResultsItem> CategoryMovies;

    public ParentModel(String name, List<ResultsItem> categoryMovies) {
        Name = name;
        CategoryMovies = categoryMovies;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<ResultsItem> getCategoryMovies() {
        return CategoryMovies;
    }

    public void setCategoryMovies(ArrayList<ResultsItem> categoryMovies) {
        CategoryMovies = categoryMovies;
    }
}
