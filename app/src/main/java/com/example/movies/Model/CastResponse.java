package com.example.movies.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CastResponse{

	@SerializedName("cast")
	private List<CastItem> cast;

	@SerializedName("id")
	private int id;

	@SerializedName("crew")
	private List<CrewItem> crew;

	public void setCast(List<CastItem> cast){
		this.cast = cast;
	}

	public List<CastItem> getCast(){
		return cast;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setCrew(List<CrewItem> crew){
		this.crew = crew;
	}

	public List<CrewItem> getCrew(){
		return crew;
	}

	@Override
 	public String toString(){
		return 
			"CastResponse{" + 
			"cast = '" + cast + '\'' + 
			",id = '" + id + '\'' + 
			",crew = '" + crew + '\'' + 
			"}";
		}
}