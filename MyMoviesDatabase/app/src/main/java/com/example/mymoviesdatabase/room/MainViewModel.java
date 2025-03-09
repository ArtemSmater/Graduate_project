package com.example.mymoviesdatabase.room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainViewModel extends AndroidViewModel {

    private static MovieDatabase database;
    private final LiveData<List<Movie>> movies;
    private final LiveData<List<FavouriteMovie>> favouriteMovies;

    public MainViewModel(@NonNull Application application) {
        super(application);
        database = MovieDatabase.getInstance(getApplication());
        movies = database.moviesDao().getAllMovies();
        favouriteMovies = database.moviesDao().getAllFavouriteMovies();
    }

    //Movies
    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public Movie getMovieById(int id) throws ExecutionException, InterruptedException {
        return new GetMovieTask().execute(id).get();
    }

    public void deleteAllMovies() throws ExecutionException, InterruptedException {
        new DeleteAllMoviesTask().execute().get();
    }

    public void insertMovie(Movie movie) throws ExecutionException, InterruptedException {
        new InsertMovieTask().execute(movie).get();
    }

    public void deleteMovie(Movie movie) throws ExecutionException, InterruptedException {
        new DeleteMovieTask().execute(movie).get();
    }

    private static class GetMovieTask extends AsyncTask<Integer, Void, Movie>{

        @Override
        protected Movie doInBackground(Integer... integers) {
            if(integers != null && integers.length > 0){
                return database.moviesDao().getMovieById(integers[0]);
            }
            return null;
        }
    }

    private static class DeleteAllMoviesTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            database.moviesDao().deleteAllMovies();
            return null;
        }
    }

    private static class DeleteMovieTask extends AsyncTask<Movie, Void, Void>{

        @Override
        protected Void doInBackground(Movie... movies) {
            if(movies != null && movies.length > 0){
                database.moviesDao().deleteMovie(movies[0]);
            }
            return null;
        }
    }

    private static class InsertMovieTask extends AsyncTask<Movie, Void, Void>{

        @Override
        protected Void doInBackground(Movie... movies) {
            if(movies != null && movies.length > 0){
                database.moviesDao().insertMovie(movies[0]);
            }
            return null;
        }
    }

    //Favourite movies

    public LiveData<List<FavouriteMovie>> getFavouriteMovies() {
        return favouriteMovies;
    }

    public FavouriteMovie getFavouriteMovieById(int id) throws ExecutionException, InterruptedException {
        return new GetFavouriteMovieTask().execute(id).get();
    }

    public void insertFavouriteMovie(FavouriteMovie movie) throws ExecutionException, InterruptedException {
        new InsertFavouriteMovieTask().execute(movie).get();
    }

    public void deleteFavouriteMovie(FavouriteMovie movie) throws ExecutionException, InterruptedException {
        new DeleteFavouriteMovieTask().execute(movie).get();
    }
    private static class GetFavouriteMovieTask extends AsyncTask<Integer, Void, FavouriteMovie>{

        @Override
        protected FavouriteMovie doInBackground(Integer... integers) {
            if(integers != null && integers.length > 0){
                return database.moviesDao().getFavouriteMovieById(integers[0]);
            }
            return null;
        }
    }


    private static class DeleteFavouriteMovieTask extends AsyncTask<FavouriteMovie, Void, Void>{

        @Override
        protected Void doInBackground(FavouriteMovie... favouriteMovies) {
            if(favouriteMovies != null && favouriteMovies.length > 0){
                database.moviesDao().deleteFavouriteMovie(favouriteMovies[0]);
            }
            return null;
        }
    }

    private static class InsertFavouriteMovieTask extends AsyncTask<FavouriteMovie, Void, Void>{

        @Override
        protected Void doInBackground(FavouriteMovie... favouriteMovies) {
            if(favouriteMovies != null && favouriteMovies.length > 0){
                database.moviesDao().insertFavouriteMovie(favouriteMovies[0]);
            }
            return null;
        }
    }
}
