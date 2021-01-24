package com.example.mymovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymovie.data.FavoriteMovie;
import com.example.mymovie.data.MainViewModel;
import com.example.mymovie.data.Movie;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    private TextView textViewTitle;
    private TextView textViewOriginalTitle;
    private TextView textViewOverview;
    private TextView textViewReleaseDate;
    private TextView textViewRating;
    private ImageView imageViewBigPoster;
    private ImageView imageViewFavourite;

    private int id;
    private Movie movie;
    private FavoriteMovie favoriteMovie;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        textViewTitle = findViewById(R.id.textViewTitle);
        textViewOriginalTitle = findViewById(R.id.textViewOriginalTitle);
        textViewOverview = findViewById(R.id.textViewOverview);
        textViewRating = findViewById(R.id.textViewRating);
        textViewReleaseDate = findViewById(R.id.textViewReleaseDate);
        imageViewBigPoster = findViewById(R.id.imageViewBigPoster);
        imageViewFavourite = findViewById(R.id.imageViewFavourite);

        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(MainViewModel.class);
        Intent intent = getIntent();
        if(intent!=null && intent.hasExtra("id")){
            id = intent.getIntExtra("id",-1);
        }else {
            finish();
        }
        movie = viewModel.getMovieById(id);
        //setValues();

        Picasso.get().load(movie.getBigPosterPath()).into(imageViewBigPoster);
        textViewTitle.setText(movie.getTitle());
        textViewReleaseDate.setText(movie.getReleaseDate());
        textViewRating.setText(Double.toString(movie.getVoteAverage()));
        textViewOverview.setText(movie.getOverview());
        textViewOriginalTitle.setText(movie.getOriginalTitle());

        setFavourite();

    }

    public void onClickFavouriteStateChange(View view) {
        if(favoriteMovie==null){
            viewModel.insertFavouriteMovie(new FavoriteMovie(movie));
            Toast.makeText(this, "Добавлено", Toast.LENGTH_SHORT).show();

        }else {
            viewModel.deleteFavouriteMovie(new FavoriteMovie(movie));
            Toast.makeText(this, "Удалено", Toast.LENGTH_SHORT).show();

        }
        setFavourite();
    }

    private void setFavourite(){
        favoriteMovie = viewModel.getFavouriteMovieById(id);
        if(favoriteMovie==null){
            imageViewFavourite.setImageResource(R.drawable.favourite_add_to);

        }else {
            imageViewFavourite.setImageResource(R.drawable.favourite_remove);

        }

    }
}