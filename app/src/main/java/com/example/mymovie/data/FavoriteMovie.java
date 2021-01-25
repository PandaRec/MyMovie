package com.example.mymovie.data;

import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "favorite_movies")
public class FavoriteMovie extends Movie {
    public FavoriteMovie(int uniqueId,int id, String backdropPath, String originalTitle, String overview, String smallPosterPath, String bigPosterPath, String title, String releaseDate, int voteCount, double voteAverage) {
        super(uniqueId,id, backdropPath, originalTitle, overview, smallPosterPath, bigPosterPath, title, releaseDate, voteCount, voteAverage);
    }

    @Ignore
    public FavoriteMovie(Movie movie){
        super(movie.getUniqueId(),movie.getId(),movie.getBackdropPath(),movie.getOriginalTitle(),movie.getOverview(),
                movie.getSmallPosterPath(),movie.getBigPosterPath(),movie.getTitle(),
                movie.getReleaseDate(),movie.getVoteCount(),movie.getVoteAverage());
    }
}
