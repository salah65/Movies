package com.example.movies.Model.Videos;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class VideoResponse{

	@SerializedName("id")
	private int id;

	@SerializedName("results")
	private List<ResultsItem> results;

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setResults(List<ResultsItem> results){
		this.results = results;
	}

	public List<ResultsItem> getResults(){
		return results;
	}

	@Override
 	public String toString(){
		return 
			"VideoResponse{" + 
			"id = '" + id + '\'' + 
			",results = '" + results + '\'' + 
			"}";
		}
}