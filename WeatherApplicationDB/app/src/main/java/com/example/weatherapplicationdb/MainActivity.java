package com.example.weatherapplicationdb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

@SuppressLint("Range")
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Weather> weatherItems;
    private ItemAdapter adapter;
    private EditText editText;
    private String url = "https://api.weatherapi.com/v1/current.json?key=5ae2220b3fb34b67b1d170640240308&q=%s&aqi=no&lang=ru";
    private WeatherDBHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        recyclerView = findViewById(R.id.recyclerView);
        dbHelper = new WeatherDBHelper(this);
        weatherItems = new ArrayList<>();
        getInfo();
        adapter = new ItemAdapter(weatherItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        touchHelper.attachToRecyclerView(recyclerView);
        for(int i = 0; i < adapter.getWeatherItems().size(); i++){
            try {
                updateInfo(i);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        adapter.setOnClickListener(new ItemAdapter.OnClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClickUpdate(int position) throws ExecutionException, InterruptedException {
                updateInfo(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Information has updated!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            remove(viewHolder.getAdapterPosition());
        }
    });

    private void remove(int position) {
        int id = weatherItems.get(position).getId();
        String where = WeatherContract.ItemEntry._ID + "=?";
        String[] whereArgs = new String[]{Integer.toString(id)};
        database.delete(WeatherContract.ItemEntry.TABLE_NAME, where, whereArgs);
        getInfo();
        adapter.notifyDataSetChanged();
    }

    private static class DownloadWebTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder builder = new StringBuilder();
            URL url = null;
            HttpsURLConnection urlConnection = null;
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpsURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader streamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(streamReader);
                String line = reader.readLine();
                while (line != null) {
                    builder.append(line);
                    line = reader.readLine();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

            return builder.toString();
        }
    }

    private void getInfo() {
        weatherItems.clear();
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(WeatherContract.ItemEntry.TABLE_NAME, null, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex(WeatherContract.ItemEntry.COLUMN_TITLE));
            String description = cursor.getString(cursor.getColumnIndex(WeatherContract.ItemEntry.COLUMN_DESCRIPTION));
            String imgURL = cursor.getString(cursor.getColumnIndex(WeatherContract.ItemEntry.COLUMN_URLS));
            int id = cursor.getInt(cursor.getColumnIndex(WeatherContract.ItemEntry._ID));
            weatherItems.add(new Weather(title, description, imgURL, id));
        }
        cursor.close();
    }

    private void updateInfo(int position) throws ExecutionException, InterruptedException {
        database = dbHelper.getWritableDatabase();
        String city = adapter.getWeatherItems().get(position).getTitle();
        String finalLine = String.format(Locale.getDefault(), url, city);
        DownloadWebTask webTask = new DownloadWebTask();
        String result = webTask.execute(finalLine).get();

        try {
            JSONObject jsonObject = new JSONObject(result);
            if(jsonObject == null){
                Toast.makeText(this, "Wrong city!", Toast.LENGTH_SHORT).show();
            } else{
                String town = jsonObject.getJSONObject("location").getString("name");
                String current = jsonObject.getJSONObject("location").getString("localtime");
                String updated = jsonObject.getJSONObject("current").getString("last_updated");
                String description = jsonObject.getJSONObject("current").getJSONObject("condition").getString("text");
                String pngURL = "https:" + jsonObject.getJSONObject("current").getJSONObject("condition").getString("icon");
                String actualTemp = jsonObject.getJSONObject("current").getString("temp_c");
                String feelsLike = jsonObject.getJSONObject("current").getString("feelslike_c");
                ContentValues contentValues = new ContentValues();
                contentValues.put(WeatherContract.ItemEntry.COLUMN_TITLE, town);
                contentValues.put(WeatherContract.ItemEntry.COLUMN_DESCRIPTION, String.format("Last update: %s\nLocal time: %s\nDescription: %s\nTemp: %s\nFeels like: %s", updated, current, description, actualTemp, feelsLike));
                contentValues.put(WeatherContract.ItemEntry.COLUMN_URLS, pngURL);
                database.update(WeatherContract.ItemEntry.TABLE_NAME,contentValues, "title=?", new String[]{town});
                getInfo();
                adapter.notifyDataSetChanged();
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void putInfo() throws ExecutionException, InterruptedException {
        String city = editText.getText().toString().trim();
        database = dbHelper.getWritableDatabase();
        if (!city.isEmpty()) {
            String finalLine = String.format(Locale.getDefault(), url, city);
            DownloadWebTask webTask = new DownloadWebTask();
            String result = webTask.execute(finalLine).get();
            try {
                JSONObject jsonObject = new JSONObject(result);
                String town = jsonObject.getJSONObject("location").getString("name");
                String updated = jsonObject.getJSONObject("current").getString("last_updated");
                String description = jsonObject.getJSONObject("current").getJSONObject("condition").getString("text");
                String pngURL = "https:" + jsonObject.getJSONObject("current").getJSONObject("condition").getString("icon");
                String actualTemp = jsonObject.getJSONObject("current").getString("temp_c");
                String feelsLike = jsonObject.getJSONObject("current").getString("feelslike_c");
                ContentValues contentValues = new ContentValues();
                contentValues.put(WeatherContract.ItemEntry.COLUMN_TITLE, town);
                contentValues.put(WeatherContract.ItemEntry.COLUMN_DESCRIPTION, String.format("Last update: %s\nDescription: %s\nTemp: %s\nFeels like: %s", updated, description, actualTemp, feelsLike));
                contentValues.put(WeatherContract.ItemEntry.COLUMN_URLS, pngURL);
                database.insert(WeatherContract.ItemEntry.TABLE_NAME, null, contentValues);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        } else {
            Toast.makeText(this, "Do not leave empty fields!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickAdd(View view) throws ExecutionException, InterruptedException {
        putInfo();
        editText.setText("");
        getInfo();
        adapter.notifyDataSetChanged();
    }
}