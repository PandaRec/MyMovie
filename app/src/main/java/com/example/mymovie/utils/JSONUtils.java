package com.example.mymovie.utils;

import com.example.mymovie.data.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONUtils {

    private static final String BASE_POSTER_URL = "https://image.tmdb.org/t/p/";
    private static final String SMALL_POSTER_SIZE="w185";
    private static final String BIG_POSTER_SIZE="w780";


    private static final String KEY_ID="id";
    private static final String KEY_RESULTS="results";
    private static final String KEY_BACKDROP_PATH = "backdrop_path";
    private static final String KEY_ORIGINAL_TITLE = "original_title";
    private static final String KEY_OVERVIEW = "overview";
    private static final String KEY_POSTER_PATH = "poster_path";
    private static final String KEY_TITLE = "title";
    private static final String KEY_RELEASE_DATE = "release_date";
    private static final String KEY_VOTE_COUNT = "vote_count";
    private static final String KEY_VOTE_AVERAGE = "vote_average";

    public static ArrayList<Movie> getMoviesFromJSON(JSONObject jsonObject){
        ArrayList<Movie> movies = new ArrayList<>();
        if (jsonObject==null)
            return movies;
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULTS);
            for(int i =0;i<jsonArray.length();i++){
                JSONObject object = jsonArray.getJSONObject(i);
                int id = object.getInt(KEY_ID);
                String backdropPath = object.getString(KEY_BACKDROP_PATH);
                String originalTitle = object.getString(KEY_ORIGINAL_TITLE);
                String overview = object.getString(KEY_OVERVIEW);
                String posterPath = BASE_POSTER_URL+BIG_POSTER_SIZE+object.getString(KEY_POSTER_PATH);
                String bigPosterPath = BASE_POSTER_URL+SMALL_POSTER_SIZE+object.getString(KEY_POSTER_PATH);
                String title = object.getString(KEY_TITLE);
                String releaseDate = object.getString(KEY_RELEASE_DATE);
                int voteCount = object.getInt(KEY_VOTE_COUNT);
                double voteAverage = object.getDouble(KEY_VOTE_AVERAGE);
                Movie movie = new Movie(id,backdropPath,originalTitle,overview,posterPath,bigPosterPath,title,releaseDate,voteCount,voteAverage);
                movies.add(movie);
            }
        }catch (Exception e){}
        return movies;
    }


}
