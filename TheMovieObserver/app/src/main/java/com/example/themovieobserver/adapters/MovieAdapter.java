package com.example.themovieobserver.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themovieobserver.R;
import com.example.themovieobserver.database.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ItemViewHolder> {

    // movie collection
    private ArrayList<Movie> movies;

    public MovieAdapter() {
        this.movies = new ArrayList<>();
    }

    // on movie click listener
    public OnMovieClickListener onMovieClickListener;

    public interface OnMovieClickListener {
        void onMovieClick(int position);
    }

    public void setOnMovieClickListener(OnMovieClickListener onMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener;
    }

    // on reach end listener
    public OnReachEndListener onReachEndListener;

    public interface OnReachEndListener {
        void onReachEnd();
    }

    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        if (movies.size() - 4 <= position && onReachEndListener != null) {
            onReachEndListener.onReachEnd();
        }
        Movie movie = movies.get(position);
        Picasso mPicasso = Picasso.get();
        mPicasso.load(movie.getPosterPath())
                .placeholder(R.drawable.baseline_local_movies_24)
                .error(R.drawable.baseline_local_movies_24)
                .into(holder.ivSmallPoster);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivSmallPoster;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ivSmallPoster = itemView.findViewById(R.id.ivSmallPoster);
            itemView.setOnClickListener(v -> {
                if (onMovieClickListener != null) {
                    onMovieClickListener.onMovieClick(getAdapterPosition());
                }
            });
        }
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addMovies(ArrayList<Movie> newMovies) {
        this.movies.addAll(newMovies);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }
}
