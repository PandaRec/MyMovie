package com.example.mymovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymovie.adapters.ReviewAdapter;
import com.example.mymovie.adapters.TrailerAdapter;
import com.example.mymovie.data.FavoriteMovie;
import com.example.mymovie.data.MainViewModel;
import com.example.mymovie.data.Movie;
import com.example.mymovie.data.Review;
import com.example.mymovie.data.Trailer;
import com.example.mymovie.utils.JSONUtils;
import com.example.mymovie.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    private TextView textViewTitle;
    private TextView textViewOriginalTitle;
    private TextView textViewOverview;
    private TextView textViewReleaseDate;
    private TextView textViewRating;
    private ImageView imageViewBigPoster;
    private ImageView imageViewFavourite;
    private ScrollView scrollView;


    private RecyclerView recyclerViewTrailers;
    private TrailerAdapter trailerAdapter;

    private RecyclerView recyclerViewReview;
    private ReviewAdapter reviewAdapter;

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
        recyclerViewTrailers = findViewById(R.id.recycleViewTrailers);
        recyclerViewReview = findViewById(R.id.recycleViewReviews);
        scrollView = findViewById(R.id.scrollViewInfo);
        scrollView.smoothScrollTo(0,0);

        trailerAdapter = new TrailerAdapter();
        reviewAdapter = new ReviewAdapter();
        trailerAdapter.setOnCLickTrailerListener(new TrailerAdapter.OnCLickTrailerListener() {
            @Override
            public void onTrailerClick(String url) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
        recyclerViewTrailers.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerViewReview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));





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

        JSONObject json = NetworkUtils.getJSONForTrailer(id,MainActivity.getLanguage());
        ArrayList<Trailer> trailers = JSONUtils.getTrailersFromJSON(json);

        JSONObject jsonReviews = NetworkUtils.getJSONForReview(id,MainActivity.getLanguage());
        ArrayList<Review> reviews = JSONUtils.getReviewsFromJson(jsonReviews);

        trailerAdapter.setTrailers(trailers);
        recyclerViewTrailers.setAdapter(trailerAdapter);

        reviewAdapter.setReviews(reviews);
        recyclerViewReview.setAdapter(reviewAdapter);

    }

    public void onClickFavouriteStateChange(View view) {
        if(favoriteMovie==null){
            viewModel.insertFavouriteMovie(new FavoriteMovie(movie));
            Toast.makeText(this, getResources().getString(R.string.add_to_favourite), Toast.LENGTH_SHORT).show();

        }else {
            viewModel.deleteFavouriteMovie(new FavoriteMovie(movie));
            Toast.makeText(this, getResources().getString(R.string.remove_from_favorite), Toast.LENGTH_SHORT).show();

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