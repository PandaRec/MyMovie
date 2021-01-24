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
    private LiveData<List<FavoriteMovie>> favouriteMovies;

    private static MovieDatabase database;


    public MainViewModel(@NonNull Application application) {
        super(application);
        database = MovieDatabase.getInstance(getApplication());
        movies = database.movieDao().getAllMovies();
        favouriteMovies = database.movieDao().getAllFavouriteMovies();
    }

    public  LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public LiveData<List<FavoriteMovie>> getFavouriteMovies() {
        return favouriteMovies;
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

    //there need create methods for favorite
    public FavoriteMovie getFavouriteMovieById(int favouriteMovieId){
        try{
            return new GetFavouriteMovieByIdTask().execute(favouriteMovieId).get();
        }catch (Exception e){}
        return null;
    }
    public void insertFavouriteMovie(FavoriteMovie favoriteMovie){
        new InsertFavouriteMovieTask().execute(favoriteMovie);
    }

    public void deleteFavouriteMovie(FavoriteMovie favoriteMovie){
        new DeleteFavouriteMovieTask().execute(favoriteMovie);
    }

    public void deleteAllFavouriteMovies(){
        new DeleteAllFavouriteMoviesTask().execute();
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
    private static class GetFavouriteMovieByIdTask extends AsyncTask<Integer,Void,FavoriteMovie>{
        @Override
        protected FavoriteMovie doInBackground(Integer... integers) {
            if(integers!=null && integers.length>0){
                return database.movieDao().getFavouriteMovieById(integers[0]);
            }
            return null;
        }
    }

    private static class InsertFavouriteMovieTask extends AsyncTask<FavoriteMovie,Void,Void>{
        @Override
        protected Void doInBackground(FavoriteMovie... favoriteMovies) {
            if(favoriteMovies!=null && favoriteMovies.length>0){
                database.movieDao().insertFavouriteMovie(favoriteMovies[0]);
            }
            return null;
        }
    }

    private static class DeleteFavouriteMovieTask extends AsyncTask<FavoriteMovie,Void,Void>{
        @Override
        protected Void doInBackground(FavoriteMovie... favoriteMovies) {
            if(favoriteMovies!=null && favoriteMovies.length>0){
                database.movieDao().deleteFavouriteMovie(favoriteMovies[0]);
            }
            return null;
        }
    }

    private static class DeleteAllFavouriteMoviesTask extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            database.movieDao().deleteAllFavouriteMovies();
            return null;
        }
    }
}
