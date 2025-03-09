package com.example.mymoviesdatabase;

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

import com.example.mymoviesdatabase.adapters.MoviesAdapter;
import com.example.mymoviesdatabase.room.FavouriteMovie;
import com.example.mymoviesdatabase.room.MainViewModel;
import com.example.mymoviesdatabase.room.Movie;

import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity {

    private MoviesAdapter adapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.itemMain){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if(id == R.id.itemFavourite){
            Intent intentFavourite = new Intent(this, FavouriteActivity.class);
            startActivity(intentFavourite);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewFavourite);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new MoviesAdapter();
        recyclerView.setAdapter(adapter);
        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        LiveData<List<FavouriteMovie>> favouriteMovies = viewModel.getFavouriteMovies();
        favouriteMovies.observe(this, new Observer<List<FavouriteMovie>>() {
            @Override
            public void onChanged(List<FavouriteMovie> favouriteMovies) {
                List<Movie> movies = new ArrayList<>();
                if(favouriteMovies != null){
                    movies.addAll(favouriteMovies);
                    adapter.setMovies(movies);
                }
            }
        });
        adapter.setOnPosterClickListener(new MoviesAdapter.OnPosterClickListener() {
            @Override
            public void onPosterClick(int position) {
                Intent intent = new Intent(FavouriteActivity.this, SecondDetailActivity.class);
                intent.putExtra("id", adapter.getMovies().get(position).getId());
                startActivity(intent);
            }
        });
    }
}