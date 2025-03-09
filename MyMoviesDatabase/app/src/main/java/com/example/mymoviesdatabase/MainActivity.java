package com.example.mymoviesdatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.mymoviesdatabase.adapters.MoviesAdapter;
import com.example.mymoviesdatabase.room.MainViewModel;
import com.example.mymoviesdatabase.room.Movie;
import com.example.mymoviesdatabase.utils.JSONUtils;
import com.example.mymoviesdatabase.utils.NetworkUtils;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONObject> {

    private MoviesAdapter adapter;
    private TextView textViewPopularity;
    private TextView textViewByRated;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch switchChoose;
    private ProgressBar progressBar;
    private MainViewModel viewModel;
    private static final int LOADER_ID = 154;
    private int page = 1;
    private int methodOfSort;
    private boolean isLoading = false;
    private LoaderManager loaderManager;

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

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        loaderManager = LoaderManager.getInstance(this);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        switchChoose = findViewById(R.id.switchChoose);
        textViewPopularity = findViewById(R.id.textViewPopularity);
        textViewByRated = findViewById(R.id.textViewTopRated);
        textViewByRated.setTextColor(getColor(R.color.white));
        textViewPopularity.setTextColor(getColor(R.color.red));
        recyclerView.setLayoutManager(new GridLayoutManager(this, getColumnCount()));
        adapter = new MoviesAdapter();
        recyclerView.setAdapter(adapter);
        switchChoose.setChecked(true);
        switchChoose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                page = 1;
                try {
                    sortMovies(isChecked);
                } catch (MalformedURLException | InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        switchChoose.setChecked(false);
        adapter.setOnPosterClickListener(new MoviesAdapter.OnPosterClickListener() {
            @Override
            public void onPosterClick(int position) {
                Intent intent = new Intent(getApplicationContext(), SecondDetailActivity.class);
                intent.putExtra("id", adapter.getMovies().get(position).getId());
                startActivity(intent);
            }
        });
        adapter.setOnReachEndListener(new MoviesAdapter.OnReachEndListener() {
            @Override
            public void onReachEnd(int position) {
                if(!isLoading){
                    try {
                        getData(page);
                    } catch (MalformedURLException | ExecutionException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        LiveData<List<Movie>> getMovieFromDB = viewModel.getMovies();
        getMovieFromDB.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if(page == 1){
                    adapter.setMovies(movies);
                }
            }
        });
    }

    private int getColumnCount(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = (int) (displayMetrics.widthPixels / displayMetrics.density);
        return Math.max(width / 185, 2);
    }

    public void onClickByPopularity(View view) throws MalformedURLException, ExecutionException, InterruptedException {
        sortMovies(false);
        switchChoose.setChecked(false);
    }

    public void onClickByTopRated(View view) throws MalformedURLException, ExecutionException, InterruptedException {
        sortMovies(true);
        switchChoose.setChecked(true);
    }

    private void sortMovies(boolean byPopularity) throws MalformedURLException, ExecutionException, InterruptedException {
        if(byPopularity){
            methodOfSort = NetworkUtils.TOP_RATED;
            textViewByRated.setTextColor(getColor(R.color.red));
            textViewPopularity.setTextColor(getColor(R.color.white));
        }else {
            methodOfSort = NetworkUtils.POPULARITY;
            textViewPopularity.setTextColor(getColor(R.color.red));
            textViewByRated.setTextColor(getColor(R.color.white));
        }
        getData(page);
    }

    private void getData(int page) throws MalformedURLException, ExecutionException, InterruptedException {
        URL url = NetworkUtils.buildResponse(methodOfSort, page);
        Bundle bundle = new Bundle();
        bundle.putString("url", url.toString());
        loaderManager.restartLoader(LOADER_ID, bundle, this);
    }

    @NonNull
    @Override
    public Loader<JSONObject> onCreateLoader(int id, @Nullable Bundle args) {
        NetworkUtils.JSONLoader jsonLoader = new NetworkUtils.JSONLoader(this, args);
        jsonLoader.setOnStartLoaderListener(new NetworkUtils.JSONLoader.OnStartLoaderListener() {
            @Override
            public void onStartLoader() {
                isLoading = true;
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        return jsonLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<JSONObject> loader, JSONObject data) {
        ArrayList<Movie> movies = new ArrayList<>();
        try {
            movies = JSONUtils.getDataFromJSONObject(data);
            if (movies.size() > 0){
                if(page == 1){
                    viewModel.deleteAllMovies();
                    adapter.clear();
                }
                for(Movie movie : movies){
                    viewModel.insertMovie(movie);
                }
                adapter.addList(movies);
                page++;
            }
            loaderManager.destroyLoader(LOADER_ID);
            isLoading = false;
            progressBar.setVisibility(View.INVISIBLE);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<JSONObject> loader) {
    }
}