package com.example.mymoviesdatabase.utils;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class NetworkUtils {

    private static final String API_KEY = "8003e56f4aa20c3ff9ce19bf9bd43d27";
    private static final String BASE_URL = "https://api.themoviedb.org/3/discover/movie";
    private static final String BASE_VIDEO_URL = "https://api.themoviedb.org/3/movie/%s/videos";
    private static final String BASE_REVIEWS_URL = "https://api.themoviedb.org/3/movie/%s/reviews";

    private static final String PARAMS_API_KEY = "api_key";
    private static final String PARAMS_LANGUAGE = "language";
    private static final String PARAMS_SORT_BY = "sort_by";
    private static final String PARAMS_PAGE = "page";
    private static final String PARAMS_VOTE_COUNT = "vote_count.gte";

    private static final String VALUE_LANGUAGE_RU = "ru-RU";
    private static final String VALUE_LANGUAGE_US = "en-US";
    private static final String SORT_BY_POPULARITY = "popularity.desc";
    private static final String SORT_BY_TOP_RATED = "vote_average.desc";
    private static final String MIN_VOTE_COUNT = "1000";

    public static final int POPULARITY = 0;
    public static final int TOP_RATED = 1;




    public static URL buildVideoResponse(int id) throws MalformedURLException {
        URL result = null;
        Uri uri = Uri.parse(String.format(BASE_VIDEO_URL, id)).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, API_KEY)
                .appendQueryParameter(PARAMS_LANGUAGE, VALUE_LANGUAGE_US)
                .build();
        result = new URL(uri.toString());
        return result;
    }

    public static URL buildReviewsResponse(int id) throws MalformedURLException {
        URL result = null;
        Uri uri = Uri.parse(String.format(BASE_REVIEWS_URL, id)).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, API_KEY)
                .appendQueryParameter(PARAMS_LANGUAGE, VALUE_LANGUAGE_US)
                .build();
        result = new URL(uri.toString());
        return result;
    }

    @NonNull
    public static URL buildResponse(int sort, int page) throws MalformedURLException {
        URL result = null;
        String methodOfSort;
        if(sort == 0){
            methodOfSort = SORT_BY_POPULARITY;
        } else{
            methodOfSort = SORT_BY_TOP_RATED;
        }
        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAMS_VOTE_COUNT, MIN_VOTE_COUNT)
                .appendQueryParameter(PARAMS_API_KEY, API_KEY)
                .appendQueryParameter(PARAMS_LANGUAGE, VALUE_LANGUAGE_RU)
                .appendQueryParameter(PARAMS_SORT_BY, methodOfSort)
                .appendQueryParameter(PARAMS_PAGE, Integer.toString(page))
                .build();
        result = new URL(uri.toString());
        return result;
    }

    public static JSONObject getJSONVideoTask (int id) throws MalformedURLException, ExecutionException, InterruptedException {
        JSONObject result = null;
        URL url = buildVideoResponse(id);
        result = new DownloadJSONTask().execute(url).get();
        return result;
    }

    public static JSONObject getJSONReviewsTask (int id) throws MalformedURLException, ExecutionException, InterruptedException {
        JSONObject result = null;
        URL url = buildReviewsResponse(id);
        result =  new DownloadJSONTask().execute(url).get();
        return result;
    }

    public static class JSONLoader extends AsyncTaskLoader<JSONObject>{
        private Bundle bundle;

        private OnStartLoaderListener onStartLoaderListener;
        public interface OnStartLoaderListener{
            void onStartLoader();
        }

        public void setOnStartLoaderListener(OnStartLoaderListener onStartLoaderListener) {
            this.onStartLoaderListener = onStartLoaderListener;
        }

        public JSONLoader(@NonNull Context context, Bundle bundle) {
            super(context);
            this.bundle = bundle;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            if (onStartLoaderListener != null){
                onStartLoaderListener.onStartLoader();
            }
            forceLoad();
        }

        @Nullable
        @Override
        public JSONObject loadInBackground() {
            if(bundle == null){
                return null;
            }
            String urlAsLine = bundle.getString("url");
            URL url = null;
            try {
                url = new URL(urlAsLine);
                JSONObject result = null;
                if(url == null){
                    return null;
                }
                HttpURLConnection urlConnection = null;
                try {
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = urlConnection.getInputStream();
                    InputStreamReader streamReader = new InputStreamReader(inputStream);
                    BufferedReader reader = new BufferedReader(streamReader);
                    StringBuilder builder = new StringBuilder();
                    String line = reader.readLine();
                    while (line != null){
                        builder.append(line);
                        line = reader.readLine();
                    }
                    result = new JSONObject(builder.toString());
                } catch (IOException | JSONException e) {
                    throw new RuntimeException(e);
                } finally {
                    if(urlConnection != null){
                        urlConnection.disconnect();
                    }
                }
                return result;
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private static class DownloadJSONTask extends AsyncTask<URL, Void, JSONObject>{

        @Override
        protected JSONObject doInBackground(URL... urls) {
            JSONObject result = null;
            if(urls == null || urls.length == 0){
                return result;
            }
            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) urls[0].openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader streamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder builder = new StringBuilder();
                String line = reader.readLine();
                while (line != null){
                    builder.append(line);
                    line = reader.readLine();
                }
                result = new JSONObject(builder.toString());
            } catch (IOException | JSONException e) {
                throw new RuntimeException(e);
            } finally {
                if(urlConnection != null){
                    urlConnection.disconnect();
                }
            }
            return result;
        }
    }
}
