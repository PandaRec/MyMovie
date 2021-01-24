package com.example.mymovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymovie.data.MainViewModel;
import com.example.mymovie.data.Movie;
import com.example.mymovie.utils.JSONUtils;
import com.example.mymovie.utils.NetworkUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private TextView textViewPopularity;
    private TextView textViewTopRated;
    private SwitchCompat switchCompat;
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
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycleViewPosters);
        textViewPopularity = findViewById(R.id.textViewPopularity);
        textViewTopRated = findViewById(R.id.textViewTopRated);
        switchCompat = findViewById(R.id.switchSortBy);
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(MainViewModel.class);


        movieAdapter = new MovieAdapter();
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));


        switchCompat.setChecked(true);

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setMethodOfSort(isChecked);
            }
        });
        switchCompat.setChecked(false);
        recyclerView.setAdapter(movieAdapter);

        movieAdapter.setOnPosterClickListener(new MovieAdapter.OnPosterClickListener() {
            @Override
            public void onPosterClick(int position) {
                int id = movieAdapter.getMovies().get(position).getId();
                //Toast.makeText(MainActivity.this, ""+position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);

            }
        });

        movieAdapter.setOnReachEndListener(new MovieAdapter.OnReachEndListener() {
            @Override
            public void onReachEnd() {
                Toast.makeText(MainActivity.this, "Reached", Toast.LENGTH_SHORT).show();
            }
        });


        LiveData<List<Movie>> moviesFromDB = viewModel.getMovies();
        moviesFromDB.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                movieAdapter.setMovies(movies);
            }
        });


    }

    public void onClickSortByPopularityPressed(View view) {
        setMethodOfSort(false);
        switchCompat.setChecked(false);
        textViewPopularity.setTextColor(getResources().getColor(R.color.gray));
        textViewTopRated.setTextColor(getResources().getColor(R.color.white));

        textViewPopularity.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        textViewTopRated.setPaintFlags(0);
    }

    public void onClickSortByTopRatedPressed(View view) {
        setMethodOfSort(true);
        switchCompat.setChecked(true);
        textViewTopRated.setTextColor(getResources().getColor(R.color.gray));
        textViewPopularity.setTextColor(getResources().getColor(R.color.white));

        textViewTopRated.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        textViewPopularity.setPaintFlags(0);

    }

    private void setMethodOfSort(boolean isTopRated){
        int methodOfSort;
        if(isTopRated){
            methodOfSort = NetworkUtils.VOTE_AVERAGE;
            textViewTopRated.setTextColor(getResources().getColor(R.color.gray));
            textViewPopularity.setTextColor(getResources().getColor(R.color.white));

            textViewTopRated.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            textViewPopularity.setPaintFlags(0);

        }else {
            methodOfSort = NetworkUtils.POPULARITY;
            textViewPopularity.setTextColor(getResources().getColor(R.color.gray));
            textViewTopRated.setTextColor(getResources().getColor(R.color.white));

            textViewPopularity.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            textViewTopRated.setPaintFlags(0);

        }
        downloadData(methodOfSort,1);

    }

    private void downloadData(int methodOfSort, int page){
        JSONObject tempJson = NetworkUtils.getJSONFromNetwork(methodOfSort,1);
        ArrayList<Movie> movies = JSONUtils.getMoviesFromJSON(tempJson);
        if (movies!=null && movies.size()>0){
            viewModel.deleteAllMovies();
            for (Movie movie:movies){
                viewModel.insertMovie(movie);
            }
        }
    }
}