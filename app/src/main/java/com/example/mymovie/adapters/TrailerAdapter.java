package com.example.mymovie.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovie.R;
import com.example.mymovie.data.Trailer;

import java.util.ArrayList;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {
    private ArrayList<Trailer> trailers;

    private OnCLickTrailerListener onCLickTrailerListener;

    public void setOnCLickTrailerListener(OnCLickTrailerListener onCLickTrailerListener) {
        this.onCLickTrailerListener = onCLickTrailerListener;
    }

    public interface OnCLickTrailerListener{
        void onTrailerClick(String url);
    }

    public ArrayList<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(ArrayList<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item,parent,false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        Trailer trailer = trailers.get(position);
        holder.textViewNameOfTrailer.setText(trailer.getName());
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }



    public class TrailerViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewNameOfTrailer;

        public TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNameOfTrailer = itemView.findViewById(R.id.textViewTrailer);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(onCLickTrailerListener!=null)
                            onCLickTrailerListener.onTrailerClick(trailers.get(getAdapterPosition()).getKey());
                    }
                });

        }
    }
}
