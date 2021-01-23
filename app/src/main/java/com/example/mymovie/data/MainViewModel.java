package com.example.mymovie.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private LiveData<List<Movie>> movies;
    private static MovieDatabase database;
    public MainViewModel(@NonNull Application application) {
        super(application);
        database = MovieDatabase.getInstance(getApplication());
        movies = database.movieDao().getAllMovies();
    }

    public  LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public Movie getMovieById(int movieId){
        try {
            return new GetMovieByIdTask().execute(movieId).get();
        }catch (Exception e){}
        return null;
    }
    public void insertMovie(Movie movie){
            new InsertMovieTask().execute(movie);
    }
    public void deleteMovie(Movie movie){
            new DeleteMovieTask().execute(movie);
    }
    public void deleteAllMovies(){
        new DeleteAllMovies().execute();
    }

    private static class GetMovieByIdTask extends AsyncTask<Integer,Void,Movie>{
        @Override
        protected Movie doInBackground(Integer... integers) {
            if(integers!=null && integers.length>0){
                return database.movieDao().getMovieById(integers[0]);
            }
            return null;
        }
    }

    private static class InsertMovieTask extends AsyncTask<Movie,Void,Void>{
        @Override
        protected Void doInBackground(Movie... movies) {
            if(movies!=null && movies.length>0){
                database.movieDao().insertMovie(movies[0]);
            }
            return null;
        }
    }

    private static class DeleteMovieTask extends AsyncTask<Movie,Void,Void>{
        @Override
        protected Void doInBackground(Movie... movies) {
            if(movies!=null && movies.length>0){
                database.movieDao().deleteMovie(movies[0]);
            }
            return null;
        }
    }

    private static class DeleteAllMovies extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            database.movieDao().deleteAllMovies();
            return null;
        }
    }
}
