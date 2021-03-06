package com.example.mymovie.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface MovieDao {

    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> getAllMovies();

    @Query("SELECT * FROM movies where id==:movieId")
    Movie getMovieById(int movieId);

    @Insert
    void insertMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

    @Query("DELETE FROM movies")
    void deleteAllMovies();

    //-------------------------------------------
    @Query("SELECT * FROM favorite_movies")
    LiveData<List<FavoriteMovie>> getAllFavouriteMovies();

    @Query("SELECT * FROM favorite_movies where id==:favouriteMovieId")
    FavoriteMovie getFavouriteMovieById(int favouriteMovieId);

    @Insert
    void insertFavouriteMovie(FavoriteMovie favoriteMovie);

    @Delete
    void deleteFavouriteMovie(FavoriteMovie favoriteMovie);

    @Query("DELETE FROM favorite_movies")
    void deleteAllFavouriteMovies();


}
