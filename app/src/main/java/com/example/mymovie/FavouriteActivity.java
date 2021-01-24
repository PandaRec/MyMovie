package com.example.mymovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.mymovie.data.FavoriteMovie;
import com.example.mymovie.data.MainViewModel;
import com.example.mymovie.data.Movie;

import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity {

private RecyclerView recyclerViewFavourite;

private MovieAdapter adapter;
private MainViewModel viewModel;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_main:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_favourite:
                Intent intent1 = new Intent(this,FavouriteActivity.class);
                startActivity(intent1);
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        recyclerViewFavourite = findViewById(R.id.recycleViewFavourite);
        adapter = new MovieAdapter();
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(MainViewModel.class);

        recyclerViewFavourite.setLayoutManager(new GridLayoutManager(this,2));
        recyclerViewFavourite.setAdapter(adapter);

        LiveData<List<FavoriteMovie>> favouriteMovies = viewModel.getFavouriteMovies();
        favouriteMovies.observe(this, new Observer<List<FavoriteMovie>>() {
            @Override
            public void onChanged(List<FavoriteMovie> favoriteMovies) {
                if(favoriteMovies!=null){
                    List<Movie> movies = new ArrayList<>();
                    movies.addAll(favoriteMovies);
                    adapter.setMovies(movies);
                }
            }
        });

        adapter.setOnPosterClickListener(new MovieAdapter.OnPosterClickListener() {
            @Override
            public void onPosterClick(int position) {
                int id = adapter.getMovies().get(position).getId();
                Intent intent = new Intent(FavouriteActivity.this,DetailsActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });



    }


}