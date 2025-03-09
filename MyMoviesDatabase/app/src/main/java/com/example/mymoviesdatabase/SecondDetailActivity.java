package com.example.mymoviesdatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymoviesdatabase.adapters.ReviewAdapter;
import com.example.mymoviesdatabase.adapters.TrailerAdapter;
import com.example.mymoviesdatabase.room.FavouriteMovie;
import com.example.mymoviesdatabase.room.MainViewModel;
import com.example.mymoviesdatabase.room.Movie;
import com.example.mymoviesdatabase.room.Review;
import com.example.mymoviesdatabase.room.Trailer;
import com.example.mymoviesdatabase.utils.JSONUtils;
import com.example.mymoviesdatabase.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class SecondDetailActivity extends AppCompatActivity {

    private ImageView imageViewBigPoster;
    private ImageView imageView;
    private TextView textViewTitle;
    private TextView textViewOrgTitle;
    private TextView textViewRating;
    private TextView textViewRelease;
    private TextView textViewOverview;
    private MainViewModel viewModel;
    private int id;
    private Movie movie;
    private FavouriteMovie favouriteMovie;

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

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_detail);
        imageViewBigPoster = findViewById(R.id.imageViewBigPoster);
        imageView = findViewById(R.id.imageView);
        textViewTitle = findViewById(R.id.textViewTitleName);
        textViewOrgTitle = findViewById(R.id.textViewOrgTitleName);
        textViewRating = findViewById(R.id.textViewRatingValue);
        textViewRelease = findViewById(R.id.textViewReleaseValue);
        textViewOverview = findViewById(R.id.textViewOverName);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        try {
            getMovieData();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        RecyclerView recyclerViewTrailers = findViewById(R.id.recyclerViewVideos);
        RecyclerView recyclerViewReviews = findViewById(R.id.recyclerViewReviews);
        recyclerViewTrailers.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewReviews.setLayoutManager(new LinearLayoutManager(this));
        TrailerAdapter trailerAdapter = new TrailerAdapter();
        ReviewAdapter reviewAdapter = new ReviewAdapter();
        ArrayList<Trailer> trailers = new ArrayList<>();
        ArrayList<Review> reviews = new ArrayList<>();
        JSONObject objectTrailer = null;
        JSONObject objectReviews = null;
        try {
            objectTrailer = NetworkUtils.getJSONVideoTask(movie.getId());
            objectReviews = NetworkUtils.getJSONReviewsTask(movie.getId());
            trailers = JSONUtils.getTrailerData(objectTrailer);
            reviews = JSONUtils.getReviewData(objectReviews);
        } catch (MalformedURLException | ExecutionException | InterruptedException | JSONException e) {
            throw new RuntimeException(e);
        }
        recyclerViewTrailers.setAdapter(trailerAdapter);
        recyclerViewReviews.setAdapter(reviewAdapter);
        trailerAdapter.setTrailers(trailers);
        reviewAdapter.setReviews(reviews);
        trailerAdapter.setOnVideoClickListener(new TrailerAdapter.OnVideoClickListener() {
            @Override
            public void onVideoClick(String url) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void getMovieData() throws ExecutionException, InterruptedException {
        Intent intent = getIntent();
        id = 0;
        if(intent != null && intent.hasExtra("id")){
            id = intent.getIntExtra("id", -1);
        }else {
            finish();
        }
        movie = viewModel.getMovieById(id);
        Picasso.get().load(movie.getPosterPathBig()).into(imageViewBigPoster);
        textViewTitle.setText(movie.getTitle());
        textViewOrgTitle.setText(movie.getOriginalTitle());
        textViewRating.setText(Double.toString(movie.getVoteCount()));
        textViewRelease.setText(movie.getReleaseData());
        textViewOverview.setText(movie.getOverview());
        setFavourite();
    }

    public void onClickAddToFavourite(View view) throws ExecutionException, InterruptedException {
        if(favouriteMovie == null){
            viewModel.insertFavouriteMovie(new FavouriteMovie(movie));
            Toast.makeText(this, "Added to favourite!", Toast.LENGTH_SHORT).show();
        }else{
            viewModel.deleteFavouriteMovie(favouriteMovie);
            Toast.makeText(this, "Delete from favourite!", Toast.LENGTH_SHORT).show();
        }
        setFavourite();
    }

    private void setFavourite() throws ExecutionException, InterruptedException {
        int resId;
        favouriteMovie = viewModel.getFavouriteMovieById(id);
        if(favouriteMovie == null){
            resId = android.R.drawable.btn_star_big_off;
        } else {
            resId = android.R.drawable.btn_star_big_on;
        }
        imageView.setImageResource(resId);
    }
}