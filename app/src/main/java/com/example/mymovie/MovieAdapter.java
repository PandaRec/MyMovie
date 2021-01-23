package com.example.mymovie;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovie.data.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private ArrayList<Movie> movies;
    private OnPosterClickListener onPosterClickListener;
    private OnReachEndListener onReachEndListener;

    public MovieAdapter(){
        movies = new ArrayList<>();
    }

    public void setOnPosterClickListener(OnPosterClickListener onPosterClickListener) {
        this.onPosterClickListener = onPosterClickListener;
    }

    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

    interface OnPosterClickListener{
        void onPosterClick(int position);
    }
    interface OnReachEndListener{
        void onReachEnd();
    }
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item,parent,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        if(onReachEndListener!=null && position>movies.size()-4){
            onReachEndListener.onReachEnd();
        }

        Movie movie = movies.get(position);
        Picasso.get().load(movie.getSmallPosterPath()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewPoster);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onPosterClickListener!=null)
                    onPosterClickListener.onPosterClick(getAdapterPosition());
                }
            });
        }
    }

    public void addMovies(ArrayList<Movie> movies){
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }
}
