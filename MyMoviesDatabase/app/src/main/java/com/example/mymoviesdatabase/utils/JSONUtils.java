package com.example.mymoviesdatabase.utils;

import com.example.mymoviesdatabase.room.Movie;
import com.example.mymoviesdatabase.room.Review;
import com.example.mymoviesdatabase.room.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONUtils {

    private static final String KEY_RESULTS = "results";

    //For reviews
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_UPDATED = "updated_at";

    //For movie videos
    private static final String KEY_VIDEO_KEY = "key";
    private static final String KEY_NAME = "name";

    //Movie information
    private static final String KEY_ID = "id";
    private static final String KEY_VOTE_COUNT = "vote_count";
    private static final String KEY_TITLE = "title";
    private static final String KEY_VOTE_AVERAGE = "vote_average";
    private static final String KEY_ORIGINAL_TITLE = "original_title";
    private static final String KEY_OVERVIEW = "overview";
    private static final String KEY_POSTER_PATH = "poster_path";
    private static final String KEY_BACKDROP_PATH = "backdrop_path";
    private static final String KEY_RELEASE_DATA = "release_date";

    private static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/";
    private static final String POSTER_SIZE_SMALL = "w185";
    private static final String POSTER_SIZE_BIG = "w780";

    public static ArrayList<Trailer> getTrailerData(JSONObject jsonObject) throws JSONException {
        ArrayList<Trailer> result = new ArrayList<>();
        if (jsonObject == null) {
            return result;
        }
        JSONArray trailersArray = jsonObject.getJSONArray(KEY_RESULTS);
        for(int i = 0; i < trailersArray.length(); i++){
            JSONObject trailer = trailersArray.getJSONObject(i);
            String name = trailer.getString(KEY_NAME);
            String keyVideo = "https://www.youtube.com/watch?v=" + trailer.getString(KEY_VIDEO_KEY);
            result.add(new Trailer(keyVideo, name));
        }
        return result;
    }

    public static ArrayList<Review> getReviewData(JSONObject jsonObject) throws JSONException {
        ArrayList<Review> result = new ArrayList<>();
        if (jsonObject == null) {
            return result;
        }
        JSONArray reviewsArray = jsonObject.getJSONArray(KEY_RESULTS);
        for(int i = 0; i < reviewsArray.length(); i++){
            JSONObject review = reviewsArray.getJSONObject(i);
            String author = review.getString(KEY_AUTHOR);
            String content = review.getString(KEY_CONTENT);
            String updated = review.getString(KEY_UPDATED);
            result.add(new Review(author, content, updated));
        }
        return result;
    }

    public static ArrayList<Movie> getDataFromJSONObject(JSONObject jsonObject) {
        ArrayList<Movie> result = new ArrayList<>();
        if (jsonObject == null) {
            return result;
        }
        try {
            JSONArray moviesArray = jsonObject.getJSONArray(KEY_RESULTS);
            for (int i = 0; i < moviesArray.length(); i++) {
                JSONObject movie = moviesArray.getJSONObject(i);
                int id = movie.getInt(KEY_ID);
                int voteCount = movie.getInt(KEY_VOTE_COUNT);
                double voteAverage = movie.getDouble(KEY_VOTE_AVERAGE);
                String title = movie.getString(KEY_TITLE);
                String originalTitle = movie.getString(KEY_ORIGINAL_TITLE);
                String overview = movie.getString(KEY_OVERVIEW);
                String posterPath = BASE_IMAGE_URL + POSTER_SIZE_SMALL + movie.getString(KEY_POSTER_PATH);
                String posterPathBig = BASE_IMAGE_URL + POSTER_SIZE_BIG + movie.getString(KEY_POSTER_PATH);
                String backdropPath = movie.getString(KEY_BACKDROP_PATH);
                String releaseData = movie.getString(KEY_RELEASE_DATA);
                result.add(new Movie(id, voteCount, voteAverage, title, originalTitle, overview, posterPath, posterPathBig, backdropPath, releaseData));
            }
        } catch(JSONException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
